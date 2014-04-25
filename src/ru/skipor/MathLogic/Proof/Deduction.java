package ru.skipor.MathLogic.Proof;

import ru.skipor.MathLogic.Form.Form;

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
            Proof.ModusPonensFounder modusPonensFounder = new Proof.ModusPonensFounder(statements.size());

            Set<Form> assumptionSet = new HashSet<>(assumptions);
            assumptionSet.remove(alpha);
            List<Form> futureStatements = new ArrayList<>();
            for (Form currentStatement : statements) {
                if (assumptionSet.contains(currentStatement) || AxiomsSystems.isAxiom(currentStatement)) {
                    futureStatements.addAll(ProofBank.getProofByName("o->f", currentStatement, alpha).statements);
                } else if (currentStatement.equals(alpha)) {
                    futureStatements.addAll(ProofBank.getProofByName("f->f", alpha).statements);
                } else {
                    Form antecedent = modusPonensFounder.getModusPonensConditionalStatement(currentStatement);
                    if (antecedent != null) {

                        futureStatements.addAll(ProofBank.getProofByName("f->p", alpha, antecedent, currentStatement).statements);
                    } else {
//                        System.out.println(statements.indexOf(currentStatement));
//                        for (Form st : futureStatements) {
//                            System.out.println(st);
//                        }

                        throw new IllegalStateException(currentStatement.toString());

//                        return false;
                    }


                }
                modusPonensFounder.add(currentStatement);
            }
            assumptions.remove(alphaIndex);
            statements = futureStatements;




//            assert (getProof().check() == 0); // todo: remove
            return true;


        }
        return false;

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