package ru.skipor.MathLogic.Tasks;

import ru.skipor.MathLogic.Form.Parser.FormParser;
import ru.skipor.MathLogic.Proof.Deduction;
import ru.skipor.MathLogic.Proof.Proof;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/13/14
 * Time: 9:19 PM
 */
public class MainTask4 {
    public static void main(String[] args) throws FormParser.ParserException {
        if (args.length > 0) {
            final String inputFileName = args[0];
//
            try (
                    BufferedWriter writer = new BufferedWriter(new FileWriter(inputFileName + ".out"))
            ) {


                Proof proof = Proof.createProof(inputFileName);
                int incorrectStatement = proof.check();
                if (incorrectStatement != 0) {
                    writer.write(Proof.getLastErrorMessage());
                } else {
                    Deduction deduction = new Deduction(proof);
                    if (deduction.apply()) {
                        writer.write(deduction.getProof().toString(true));
                    } else {
                        StringBuilder builder = new StringBuilder("Вывод некорректен начиная с формулы номер ").append(deduction.getErrorStatement());
                        String additionalError = deduction.getErrorMessage();
                        if (additionalError == null) {
                            additionalError = deduction.getErrorMessage();
                        }
                        if (additionalError != null) {
                            builder.append(": ");
                            builder.append(additionalError);
                        }
                        writer.write(builder.toString());
                    }
                }
                System.out.println("All is ok. See result in *.out file");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Usage: task_number file_name");
        }

    }


}
