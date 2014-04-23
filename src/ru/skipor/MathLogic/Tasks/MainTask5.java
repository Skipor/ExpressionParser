package ru.skipor.MathLogic.Tasks;

import ru.skipor.MathLogic.Form.Parser.FormParser;
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
public class MainTask5 {
    public static void main(String[] args) throws FormParser.ParserException {
        if (args.length > 0) {
            final String inputFileName = args[0];
//
            try (
                    BufferedWriter writer = new BufferedWriter(new FileWriter(inputFileName + ".out"))
            ) {


                int incorrectStatement = Proof.checkFile(inputFileName);
                if (incorrectStatement != 0) {
                    writer.write(Proof.getLastErrorMessage());
                } else {
                    writer.write("Доказательство корректно.");
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
