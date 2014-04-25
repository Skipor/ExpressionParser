package ru.skipor.MathLogic.Proof;

import ru.skipor.MathLogic.Form.Form;
import ru.skipor.MathLogic.Form.BinaryNode;
import ru.skipor.MathLogic.Form.Parser.FormParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 9/15/13
 * Time: 11:20 PM
 */
public class Proof {
    public List<Form> statements;
    public List<Form> assumptions;     // can be null
    public Form proving;

    public static final String DELIMER = ",";
    public static final String PROVABLE_FROM_SIGN = "|-";
    public static final String PROVABLE_FROM_REGEX = "\\Q" + PROVABLE_FROM_SIGN + "\\E";


    private Proof(List<Form> statements, List<Form> assumptions) {
        this.statements = statements;
        this.assumptions = assumptions;
        setProovingAsLastStatement();
    }

    private void setProovingAsLastStatement() {
        proving = statements.get(statements.size() - 1);
    }

    public static Proof createProof(String fileName) {
        try (Reader reader = new FileReader(fileName)) {
            return createProof(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Proof createProof(Reader reader) {
        return new Proof(reader);
    }

    public static Proof createProof(List<Form> statements) {
        return new Proof(statements);
    }

    public static Proof createProof(Proof proof) {
        return new Proof(proof);
    }

    public static Proof createProof(List<Form> statements, List<Form> assumptions) {
        return new Proof(statements, assumptions);
    }


    public int removeCopies() {
        int count = 0;

        Form last = statements.get(statements.size() - 1);

        Set<Form> set = new HashSet<>(statements.size() / 2);
        List<Form> noCopiesStatements = new ArrayList<>(statements.size() / 2);
        for (Form Form : statements) {
            if (!set.contains(Form)) {
                noCopiesStatements.add(Form);
                set.add(Form);
            } else {
                count++;
            }

        }
        if (!noCopiesStatements.get(noCopiesStatements.size() - 1).equals(last)) {
            noCopiesStatements.add(last);
        }

        statements = noCopiesStatements;

        return count;
    }


    public static int checkFile(String fileName) {
        return createProof(fileName).check();
    }

    private Proof(List<Form> statements) {
        this(statements, null);
    }


    private Proof(Reader in) {


        statements = new ArrayList<>();
        assumptions = null;
        try (
                BufferedReader reader = new BufferedReader(in)
        ) {


            String firstString = reader.readLine();
            if (firstString.matches(".*" + PROVABLE_FROM_REGEX + ".+")) {
                assumptions = new ArrayList<>();
                String[] strings = firstString.split(PROVABLE_FROM_REGEX);
                if (strings.length != 2) {
                    throw new IllegalArgumentException("unsupported header format");
                }
                assumptions.addAll(FormParser.parseForms(strings[0]));
                proving = FormParser.parse(strings[1]);
            } else {
                statements.add(FormParser.parse(firstString));
            }

            String readed;
            while ((readed = reader.readLine()) != null) {


                statements.add(FormParser.parse(readed));
//            System.out.println(nextStatement.toString());
//            System.out.println(Integer.toString(linesReaded));
            }
            setProovingAsLastStatement();


        } catch (FormParser.ParserException e) {
            System.err.println("Exception on statement number " + statements.size());
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Proof(Proof proof) {
        this.statements = new ArrayList<>(proof.statements);
        this.assumptions = new ArrayList<>(proof.assumptions);
        this.proving = proof.proving;
    }

    static class ModusPonensFounder {

        private final Map<Form, List<Form>> mpMap;
        private final Set<Form> statemetnsSet;

        ModusPonensFounder() {
            mpMap = new HashMap<>();
            statemetnsSet = new HashSet<>();
        }

        ModusPonensFounder(int size) {
            mpMap = new HashMap<>(size);
            statemetnsSet = new HashSet<>();
        }

        public void add(Form statement) {
            statemetnsSet.add(statement);
            if (statement instanceof BinaryNode) {
                final Form ra = ((BinaryNode) statement).rightArgument;
                final Form la = ((BinaryNode) statement).leftArgument;
                if (!mpMap.containsKey(ra)) {

                    final ArrayList<Form> list = new ArrayList<>();
                    list.add(la);
                    mpMap.put(ra, list);
                } else {
                    mpMap.get(ra).add(la);
                }
            }

        }

        public void addAll(Collection<? extends Form> forms) {
            for (Form form : forms) {
                add(form);
            }
        }

        public boolean isModusPonens(Form statement) {
            List<Form> leftParts = mpMap.get(statement);
            if (leftParts != null) {
                for (ListIterator<Form> formListIterator = leftParts.listIterator(leftParts.size()); formListIterator.hasPrevious(); ) {
                    final Form form = formListIterator.previous();
                    if (statemetnsSet.contains(form)) {
                        return true;
                    }
                }
            }

            return false;

        }


        public Form getModusPonensConditionalStatement(Form consequent) {
            List<Form> leftParts = mpMap.get(consequent);
            if (leftParts != null) {
                for (ListIterator<Form> formListIterator = leftParts.listIterator(leftParts.size()); formListIterator.hasPrevious(); ) {
                    final Form form = formListIterator.previous();
                    if (statemetnsSet.contains(form)) {
                        return form;
                    }
                }
            }

            return null;

        }
    }

    public int check() throws IllegalStateException {

        ModusPonensFounder modusPonensFounder = new ModusPonensFounder(statements.size());
        Set<Form> assumptionsSet = new HashSet<>();
        if (assumptions != null) {
            assumptionsSet.addAll(assumptions);
        }

        int statementsRead = 0;
        for (Form nextStatement : statements) {
            statementsRead++;
            if (AxiomsSystems.isAxiom(nextStatement)
                    || modusPonensFounder.isModusPonens(nextStatement)
                    || assumptionsSet.contains(nextStatement)
                    ) {
                modusPonensFounder.add(nextStatement);
            } else {
//                System.out.println("standart fail " + (statementsRead) + " " + nextStatement);
                return statementsRead;

            }
        }

        if (proving != null && !statements.get(statements.size() - 1).equals(proving)) {
//            System.out.println("proving fail " + statementsRead); // todo
            return statementsRead + 1;
        }

        return 0; //means, that all is OK

    }





    @Override
    public String toString() {
        return toString(false);
    }

    public String toString(boolean headerIfNotNeeded) {

        StringBuilder builder = new StringBuilder();
        if (assumptions != null && !assumptions.isEmpty() || headerIfNotNeeded) {
            if (assumptions != null) {
                for (Iterator<Form> formIterator = assumptions.iterator(); formIterator.hasNext(); ) {
                    Form assumption = formIterator.next();
                    builder.append(assumption);
                    if (formIterator.hasNext()) {
                        builder.append(DELIMER);
                    }
                    builder.append(' ');
                }
            }
            builder.append(PROVABLE_FROM_SIGN);
            builder.append(' ');
            builder.append(proving);
            builder.append('\n');
        }
        for (Iterator<Form> formIterator = statements.iterator(); formIterator.hasNext(); ) {
            Form statement = formIterator.next();
            builder.append(statement);
            if (formIterator.hasNext()) {
                builder.append('\n');
            }
        }
        return builder.toString();

    }




}