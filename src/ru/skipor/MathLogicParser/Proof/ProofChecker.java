package ru.skipor.MathLogicParser.Proof;

import ru.skipor.MathLogicParser.Form.BinaryNode;
import ru.skipor.MathLogicParser.Form.BinaryOperation;
import ru.skipor.MathLogicParser.Form.Form;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 9/17/13
 * Time: 9:16 AM
 */
public class ProofChecker {

    public static int check(Proof proof) throws IllegalStateException{

        List<Form> statements = (proof.statementsSystem == null) ? new ArrayList<Form>() : new ArrayList<Form>(proof.statementsSystem);
        int statementsRead = 0;
        next:for (Form nextStatement : proof.statements) {
            statementsRead++;

           for (Form statement : statements) {                       //Modus Ponus
                if (statement instanceof BinaryNode
                        && ((BinaryNode) statement).operation == BinaryOperation.ENTAILMENT
                        && nextStatement.equals(((BinaryNode) statement).rightArgument)) {
                    Form leftTerm = ((BinaryNode) statement).leftArgument;
                    for (Form context : statements) {
                        if (context.equals(leftTerm)) {
                            statements.add(nextStatement);

//                                System.out.println("Statment " + Integer.toString(linesReaded) + " Modus Ponus "); ///////// /////////


                            continue next;

                        }
                    }
                }
            }


            if (AxiomsSystems.isAxiom(nextStatement)) {
                statements.add(nextStatement);
            } else {
                return statementsRead;
            }
        }

        return 0; //means, that all is OK

    }
}
