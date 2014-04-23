package ru.skipor.MathLogic.Proof;

import ru.skipor.MathLogic.Form.Form;
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
    private static String lastErrorMessage;

    public static String getLastErrorMessage() {
        return lastErrorMessage;
    }

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

//
//    public int removeCopies() {
//        int count = 0;
//
//        Form last = statements.get(statements.size() - 1);
//
//        Set<Form> set = new HashSet<>(statements.size() / 2);
//        List<Form> noCopiesStatements = new ArrayList<>(statements.size() / 2);
//        for (Form Form : statements) {
//            if (!set.contains(Form)) {
//                noCopiesStatements.add(Form);
//                set.add(Form);
//            } else {
//                count++;
//            }
//
//        }
//        if (!noCopiesStatements.get(noCopiesStatements.size() - 1).equals(last)) {
//            noCopiesStatements.add(last);
//        }
//
//        statements = noCopiesStatements;
//
//        return count;
//    }


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

    public int check() throws IllegalStateException {

        RulesChecker modusPonensFounder = new RulesChecker(statements.size());
        Set<Form> assumptionsSet = new HashSet<>();
        if (assumptions != null) {
            assumptionsSet.addAll(assumptions);
        }

        int statementsRead = 0;
        for (Form nextStatement : statements) {
            statementsRead++;
            if (
                    AxiomsSystems.isAxiom(nextStatement)
                    || modusPonensFounder.isModusPonens(nextStatement)
                    || modusPonensFounder.isExistentialIntroduction(nextStatement)
                    || modusPonensFounder.isUniversalIntroduction(nextStatement)
                    || assumptionsSet.contains(nextStatement)
                    ) {
                modusPonensFounder.add(nextStatement);
            } else {
                StringBuilder builder = new StringBuilder("Вывод некорректен начиная с формулы номер ").append(statementsRead);
                String additionalError = AxiomsSystems.getErrorMessage();
                if (additionalError == null) {
                    additionalError = modusPonensFounder.getLastErrorMessage();
                }
                if (additionalError != null) {
                    builder.append(": ");
                    builder.append(additionalError);
                }
                lastErrorMessage = builder.toString();


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
