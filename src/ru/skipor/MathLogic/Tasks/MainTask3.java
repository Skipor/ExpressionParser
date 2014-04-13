package ru.skipor.MathLogic.Tasks;

import ru.skipor.MathLogic.Form.Checker.CounterexampleGenerator;
import ru.skipor.MathLogic.Form.Form;
import ru.skipor.MathLogic.Form.Parser.FormParser;
import ru.skipor.MathLogic.Form.Variable;
import ru.skipor.MathLogic.Proof.Proof;
import ru.skipor.MathLogic.Proof.ProofGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/13/14
 * Time: 9:19 PM
 */
public class MainTask3 {
    public static void main(String[] args) throws FormParser.ParserException {
        if (args.length > 0) {
            final String inputFileName = args[0];
//            final String inputFileName = "testProofs/correctDeduction";
            Proof proof = Proof.createProof(inputFileName);
            Form form = proof.proving;

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFileName + ".out"))) {
                List<Variable> counterExample = CounterexampleGenerator.generate(form);
                if (counterExample != null) {
                    writer.write("Высказывание ложно при ");
                    for (Iterator<Variable> iterator = counterExample.iterator(); iterator.hasNext(); ) {
                        Variable variable = iterator.next();
                        writer.write(variable.toString() + "=");
                        if (variable.getValue()) {
                            writer.write('И');
                        } else {
                            writer.write('Л');
                        }
                        if (iterator.hasNext()) {
                            writer.write(", ");
                        }
                    }


                } else {
                    Proof generated = ProofGenerator.generate(form);
                    writer.write(generated.toString(false));


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
