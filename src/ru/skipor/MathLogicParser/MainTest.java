package ru.skipor.MathLogicParser;

import ru.skipor.MathLogicParser.Form.Form;
import ru.skipor.MathLogicParser.Form.FormInserter;
import ru.skipor.MathLogicParser.Form.Variable;
import ru.skipor.MathLogicParser.Proof.Deduction;
import ru.skipor.MathLogicParser.Proof.Proof;
import ru.skipor.MathLogicParser.Proof.ProofBank;

import java.io.*;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 9/24/13
 * Time: 1:27 PM
 */
public class MainTest {




        public static void main(String[] args) throws Exception {
            int incorrectStatement = Proof.checkFile("input.txt");
            System.out.println(incorrectStatement);
            Deduction deduction = new Deduction(new Proof("testProofs/correctDeduction0.txt"));

            Proof proof = deduction.getFinal();
            assert (proof.check() == 0);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("testProofs/correct1.txt"))) {
                writer.write(proof.toString());


            }

    }
}

