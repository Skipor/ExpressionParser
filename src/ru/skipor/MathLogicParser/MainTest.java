package ru.skipor.MathLogicParser;

import ru.skipor.MathLogicParser.Proof.Deduction;
import ru.skipor.MathLogicParser.Proof.Proof;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 9/24/13
 * Time: 1:27 PM
 */
public class MainTest {


    public static void main(String[] args) throws Exception {
//            int incorrectStatement = Proof.checkFile("input.txt");
//            System.out.println(incorrectStatement);
        Deduction deduction = new Deduction(new Proof("testProofs/correctDeduction0.txt"));

        Proof proof = deduction.getFinal();
        assert (proof.check() == 0);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("testProofs/correct1.txt"))) {
            writer.write(proof.toString());


        }
//            ProofBank proofBank = new ProofBank();
//            Set<Form> assumptionSet = new HashSet<>();
//            Form alpha = FormParser.formParse("A&!A"), currentStatement = FormParser.formParse("")
//
//            if (assumptionSet.contains(currentStatement) || AxiomsSystems.isAxiom(currentStatement)) {
//                futureStatements.addAll(proofBank.getProofByName("o->f", currentStatement, alpha).statements);
//            } else if (currentStatement.equals(alpha)) {
//                futureStatements.addAll(proofBank.getProofByName("f->f", alpha).statements);
//            } else {
//                Form antecedent = Proof.getModusPonensConditionalStatement(currentStatement,
//                        statements);
//                if (antecedent != null) {
//
//                    futureStatements.addAll(proofBank.getProofByName("f->p", alpha, antecedent, currentStatement).statements);
//                } else {
//                    System.out.println(statements.indexOf(currentStatement));
//                    for (Form st : futureStatements) {
//                        System.out.println(st);
//                    }
//
//                    throw new IllegalStateException(currentStatement.toString());
//
////                        return false;
//                }


//            }

    }
}

