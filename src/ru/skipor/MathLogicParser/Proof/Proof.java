package ru.skipor.MathLogicParser.Proof;

import ru.skipor.MathLogicParser.Form.BinaryNode;
import ru.skipor.MathLogicParser.Form.BinaryOperation;
import ru.skipor.MathLogicParser.Form.Form;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 9/15/13
 * Time: 11:20 PM
 */
public class Proof {
    public List<Form> statements;
    public List<Form> statementsSystem;     // can be null


    public Proof(List<Form> statements, List<Form> statementsSystem) {
        this.statements = statements;
        this.statementsSystem = statementsSystem;
    }

    public Proof(List<Form> statements) {
        this(statements, null);
    }

    public Proof(Proof proof) {
        this.statements = new ArrayList<>(proof.statements);
        this.statementsSystem = new ArrayList<>(proof.statementsSystem);
    }


    public int check() throws IllegalStateException {

        List<Form> futureStatements = (statementsSystem == null) ? new ArrayList<Form>() : new ArrayList<>(statementsSystem);
        int statementsRead = 0;
        for (Form nextStatement : statements) {
            statementsRead++;
            if (AxiomsSystems.isAxiom(nextStatement) || isModusPonensOf(nextStatement,futureStatements) != null) {
                futureStatements.add(nextStatement);
            } else {
                return statementsRead;
            }
        }

        return 0; //means, that all is OK

    }


    public static ModusPonensPair isModusPonensOf(Form consequent, List<Form> statements) {

        for (ListIterator<Form> iteratorAntecedent = statements.listIterator(); iteratorAntecedent.hasNext();) {
            Form antecedent = iteratorAntecedent.next();
            if (antecedent instanceof BinaryNode
                    && ((BinaryNode) antecedent).operation == BinaryOperation.ENTAILMENT
                    && consequent.equals(((BinaryNode) antecedent).rightArgument)) {
                Form conditionalStatement = ((BinaryNode) antecedent).leftArgument;
                for (ListIterator<Form> iteratorContext = statements.listIterator(); iteratorContext.hasNext();) {
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

    static class ModusPonensPair {

        public final int conditionalStatement;
        public final int antecedent;

        ModusPonensPair(int conditionalStatement, int antecedent) {
            this.conditionalStatement = conditionalStatement;
            this.antecedent = antecedent;
        }
    }
}
