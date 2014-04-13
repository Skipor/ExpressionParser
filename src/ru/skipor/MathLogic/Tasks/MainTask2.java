package ru.skipor.MathLogic.Tasks;

import ru.skipor.MathLogic.Proof.Deduction;
import ru.skipor.MathLogic.Proof.Proof;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/6/14
 * Time: 7:42 PM
 */
public class MainTask2 {
    public static void main(String[] args) {
        if (args.length > 0) {
            final String inputFileName = args[0];
//            final String inputFileName = "testProofs/correctDeduction";
            Proof proof = Proof.createProof(inputFileName);
            int incorrectStatement = proof.check();
            if (incorrectStatement == 0) {
                Deduction deduction = new Deduction(proof);
                deduction.apply();
//                System.out.println(deduction.apply());
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFileName + ".out"))) {
                    writer.write(deduction.getProof().toString(true));
                    System.out.println("All is ok. See result in *.out file");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error in proof at statement number " + incorrectStatement);
            }

        } else {
            System.out.println("send input file name as first command line argument");
            System.exit(1);
        }

    }
}
