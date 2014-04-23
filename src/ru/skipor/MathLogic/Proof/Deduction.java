package ru.skipor.MathLogic.Proof;

import ru.skipor.MathLogic.Form.Form;
import ru.skipor.MathLogic.Form.Logical.BinaryNode;
import ru.skipor.MathLogic.Form.Logical.BinaryOperation;
import ru.skipor.MathLogic.Form.Logical.QuantifierNode;
import ru.skipor.MathLogic.Form.Term.Variable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 9/15/13
 * Time: 11:27 PM
 */
public class Deduction {
    private List<Form> assumptions;
    private List<Form> statements;
    private String errorMessage;
    private int errorStatement;

    public int getErrorStatement() {
        return errorStatement;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    //    private static ProofBank proofBank = new ProofBank();

    public Deduction(Proof proof) {
//        assert (proof.check() == 0); // todo: remove
        this.assumptions = proof.assumptions;
        this.statements = proof.statements;
    }


    public boolean apply() {
//        System.out.println("apply outer"); //todo
        if (!ready()) {
//            System.out.println("apply inner"); //todo

            final int alphaIndex = assumptions.size() - 1;
            Form alpha = assumptions.get(alphaIndex);
            Set<Variable> freeAlphaVariableSet = alpha.getFreeVariables();
            RulesChecker modusPonensFounder = new RulesChecker(statements.size());

            Set<Form> assumptionSet = new HashSet<>(assumptions);
            assumptionSet.remove(alpha);
            List<Form> futureStatements = new ArrayList<>();
            for (Form currentStatement : statements) {
                if (assumptionSet.contains(currentStatement) || AxiomsSystems.isAxiom(currentStatement)) {
                    futureStatements.addAll(ProofBank.getProofByName("B->A", currentStatement, alpha).statements);
                } else if (currentStatement.equals(alpha)) {
                    futureStatements.addAll(ProofBank.getProofByName("A->A", alpha).statements);
                } else {
                    Form antecedent;
                    if ((antecedent = modusPonensFounder.getModusPonensConditionalStatement(currentStatement)) != null) {
                        futureStatements.addAll(ProofBank.getProofByName("A->C", alpha, antecedent, currentStatement).statements);
                    } else {
                        Variable ruleVariable;
                        if (modusPonensFounder.isExistentialIntroduction(currentStatement)) {
                            BinaryNode binaryNode = ((BinaryNode) currentStatement);
                            QuantifierNode quantifierNode = (QuantifierNode) binaryNode.leftArgument;
                            ruleVariable = quantifierNode.boundingVariable;
                            Form la = quantifierNode.argument;
                            Form ra = binaryNode.rightArgument;
                            futureStatements.addAll(ProofBank.getProofByName("A->C->B", la, ra, alpha).statements);
                            futureStatements.add(new BinaryNode(
                                    quantifierNode
                                    , new BinaryNode(
                                    alpha,
                                    ra,
                                    BinaryOperation.ENTAILMENT
                            )
                                    , BinaryOperation.ENTAILMENT
                            ));
                            futureStatements.addAll(ProofBank.getProofByName("A->C->B", alpha, ra, quantifierNode).statements);

                        } else if (modusPonensFounder.isUniversalIntroduction(currentStatement)) {
                            BinaryNode binaryNode = ((BinaryNode) currentStatement);
                            QuantifierNode quantifierNode = (QuantifierNode) binaryNode.rightArgument;
                            ruleVariable = quantifierNode.boundingVariable;
                            Form ra = quantifierNode.argument;
                            Form la = binaryNode.leftArgument;
                            futureStatements.addAll(ProofBank.getProofByName("C&A->B", la, ra, alpha).statements);
                            futureStatements.add(new BinaryNode(
                                    new BinaryNode(
                                            alpha
                                            , la
                                            , BinaryOperation.CONJUNCTION
                                    )
                                    , quantifierNode
                                    , BinaryOperation.ENTAILMENT
                            ));
                            futureStatements.addAll(ProofBank.getProofByName("A->(B->C)", alpha, la, quantifierNode).statements);

                        } else {
                            errorMessage = AxiomsSystems.getErrorMessage();
                            if (errorMessage == null) {
                                errorStatement = statements.indexOf(currentStatement) + 1;
                                errorMessage = modusPonensFounder.getLastErrorMessage();
                            }
                            return false;
//                        System.out.println(statements.indexOf(currentStatement));
//                        for (Form st : futureStatements) {
//                            System.out.println(st);
//                        }

//                            throw new IllegalStateException(currentStatement.toString());


//                        return false;
                        }
                        if (freeAlphaVariableSet.contains(ruleVariable)) {
                            errorStatement = statements.indexOf(currentStatement) + 1;
                            errorMessage = "используется правило с квантором по переменной " + ruleVariable
                                    + " входящей свободно в допущение " + alpha + ".";
                            return false;
                        }
                        modusPonensFounder.resetErrorMessage();

                    }
                }


                modusPonensFounder.add(currentStatement);
            }
            assumptions.remove(alphaIndex);
            statements = futureStatements;
        }


//            assert (getProof().check() == 0); // todo: remove
        return true;


    }


    public boolean ready() {
        return (assumptions == null || assumptions.isEmpty());
    }


    public Proof getProof() {
        return Proof.createProof(statements, assumptions);
    }

    public Proof getFinal() {
        while (true) {
            if (!(apply())) break;

        }
        return Proof.createProof(statements, assumptions);
    }
}
