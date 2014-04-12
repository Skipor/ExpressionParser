package ru.skipor.MathLogic.Proof;

import ru.skipor.MathLogic.Form.*;
import ru.skipor.MathLogic.Form.Parser.FormParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    public static final String HEADER_SPLIT_REGEX = " *" + DELIMER + " *| *" + PROVABLE_FROM_REGEX + " *";


    public Proof(List<Form> statements, List<Form> assumptions) {
        this.statements = statements;
        this.assumptions = assumptions;
        proving = statements.get(statements.size() - 1);
    }



    public int removeCopies() {
        int count = 0;
        Set<Form> set = new HashSet<>(statements.size() / 2);
        List<Form> noCopiesStatements = new ArrayList<>(statements.size() / 2);
        for (Form form : statements) {
            if (!set.contains(form)) {
                noCopiesStatements.add(form);
                set.add(form);
            } else {
                count++;
            }

        }
        statements = noCopiesStatements;

        return count;
    }



    public static int checkFile(String fileName) {
        return new Proof(fileName).check();
    }

    public Proof(List<Form> statements) {
        this(statements, null);
    }




    public Proof(String fileName) {


        statements = new ArrayList<>();
        assumptions = null;
        try (
                BufferedReader reader = new BufferedReader(new FileReader(fileName))
        ) {


            String firstString = reader.readLine();
            if (firstString.matches(".*" + PROVABLE_FROM_REGEX + ".+")) {
                String[] strings = firstString.split(HEADER_SPLIT_REGEX);
                assumptions = new ArrayList<>(strings.length);
                for (int i = 0; i < strings.length - 1; ++i) {
                    assumptions.add(FormParser.parse(strings[i]));
                }
                proving = FormParser.parse(strings[strings.length - 1]);


            } else {
                statements.add(FormParser.parse(firstString));
            }


            while (reader.ready()) {
                String readed = reader.readLine();
                statements.add(FormParser.parse(readed));
//            System.out.println(nextStatement.toString());
//            System.out.println(Integer.toString(linesReaded));
            }


        } catch (FormParser.ParserException e) {
            System.err.println("Exception on statement number " + statements.size());
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Proof(Proof proof) {
        this.statements = new ArrayList<>(proof.statements);
        this.assumptions = new ArrayList<>(proof.assumptions);
        this.proving = proof.proving;
    }


    public int check() throws IllegalStateException {

        List<Form> futureStatements = (assumptions == null) ? new ArrayList<Form>() : new ArrayList<>(assumptions);
        Set<Form> assumptionsSet = new HashSet<>();
        if (assumptions != null) {
            assumptionsSet.addAll(assumptions);
        }

        int statementsRead = 0;
        for (Form nextStatement : statements) {
            statementsRead++;
            if (AxiomsSystems.isAxiom(nextStatement)
                    || isModusPonensOf(nextStatement, futureStatements) != null
                    || assumptionsSet.contains(nextStatement)) {
                futureStatements.add(nextStatement);
            } else {
                System.out.println("standart fail " + (statementsRead ) + " " + nextStatement);
                return statementsRead;

            }
        }

        if (proving != null && !statements.get(statements.size() - 1).equals(proving)) {
            System.out.println("proving fail " + statementsRead); // todo
            return statementsRead + 1;
        }

        return 0; //means, that all is OK

    }


    public static ModusPonensPair isModusPonensOf(Form consequent, List<Form> statements) {

        for (ListIterator<Form> iteratorAntecedent = statements.listIterator(); iteratorAntecedent.hasNext(); ) {
            Form antecedent = iteratorAntecedent.next();
            if (antecedent instanceof BinaryNode
                    && ((BinaryNode) antecedent).operation == BinaryOperation.ENTAILMENT
                    && consequent.equals(((BinaryNode) antecedent).rightArgument)) {
                Form conditionalStatement = ((BinaryNode) antecedent).leftArgument;
                for (ListIterator<Form> iteratorContext = statements.listIterator(); iteratorContext.hasNext(); ) {
                    final Form context = iteratorContext.next();
                    if (context.equals(conditionalStatement)) {

//                                System.out.println("Statment " + Integer.toString(linesReaded) + " Modus Ponus "); ///////// /////////


                        return new ModusPonensPair(iteratorContext.nextIndex() - 1, iteratorAntecedent.nextIndex() - 1);

                    }
                }
            }
        }

        return null;
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

    public static Form getModusPonensConditionalStatement(Form consequent, List<Form> statements) {

        for (Form antecedent : statements) {
            if (antecedent.equals(consequent)) {
                return null;

            }
            if (antecedent instanceof BinaryNode
                    && ((BinaryNode) antecedent).operation == BinaryOperation.ENTAILMENT
                    && consequent.equals(((BinaryNode) antecedent).rightArgument)) {
                Form conditionalStatement = ((BinaryNode) antecedent).leftArgument;
                for (final Form context : statements) {
                    if (context.equals(consequent)) {
                        break;
                    }
                    if (context.equals(conditionalStatement)) {

//                                System.out.println("Statment " + Integer.toString(linesReaded) + " Modus Ponus "); ///////// /////////


                        return conditionalStatement;

                    }
                }
            }
        }

        return null;
    }

    public static Proof concat(Proof left, Proof right) {
        if (!left.proving.equals(right.proving)) {
            throw new IllegalArgumentException("provings mast be same to concat proofs");
        }
        if (left.assumptions.size() != right.assumptions.size()) {
            throw new IllegalArgumentException("different assumptions size");
        }

        int assumptionNumber = left.assumptions.size() - 1;
        Form leftAssumption = left.assumptions.get(assumptionNumber);
        Form rightAssumption = right.assumptions.get(assumptionNumber);
        Form assumption;
        if (leftAssumption instanceof UnaryNode
                && ((UnaryNode) leftAssumption).operation.equals(UnaryOperation.NEGATION)
                && ((UnaryNode) leftAssumption).argument.equals(rightAssumption)) {
            assumption = rightAssumption;
        } else if (rightAssumption instanceof UnaryNode
                && ((UnaryNode) rightAssumption).operation.equals(UnaryOperation.NEGATION)
                && ((UnaryNode) rightAssumption).argument.equals(leftAssumption)) {
            assumption = leftAssumption;
        } else {
            throw new IllegalArgumentException("illegal last assumptions");
        }

        Deduction leftDeduction = new Deduction(left);
        leftDeduction.apply();
        left = leftDeduction.getProof();
        Deduction rightDeduction = new Deduction(right);
        rightDeduction.apply();
        right = rightDeduction.getProof();
        left.statements.addAll(right.statements);
        left.statements.addAll(ProofBank.getProofByName("f|!f", assumption).statements);
        Form mpStatement = FormHelper.insert(AxiomsSystems.formsOfSystemsOfAxioms[7], assumption);
        left.statements.add(mpStatement);
        for (int i = 0; i < 3; i++) {
            mpStatement = ((BinaryNode) mpStatement).rightArgument;
            left.statements.add(mpStatement);

        }
        left.removeCopies();


        return left;

    }

    static class ModusPonensPair {

        public final int conditionalStatement;
        public final int antecedent;

        ModusPonensPair(int conditionalStatement, int antecedent) {
            this.conditionalStatement = conditionalStatement;
            this.antecedent = antecedent;
        }
    }
}
