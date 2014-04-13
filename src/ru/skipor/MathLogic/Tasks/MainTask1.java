package ru.skipor.MathLogic.Tasks;

import ru.skipor.MathLogic.Proof.Proof;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class MainTask1 {


    public static void main(String[] args) throws Exception {


        if (args.length > 0) {
            final String inputFileName = args[0];

            try (
                    BufferedWriter writer = new BufferedWriter(new FileWriter(inputFileName + ".out"))
            ) {


                int incorrectStatement = Proof.checkFile(inputFileName);
                if (incorrectStatement != 0) {
                    writer.write("Доказательство некорректно начиная с высказывания № " + Integer.toString(incorrectStatement));
                } else {
                    writer.write("Доказательство корректно.");
                }
            }
            System.out.println("All is ok. See result in *.out file");

        } else {
            System.out.println("Usage: task_number file_name");
        }

    }
}


