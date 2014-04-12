package ru.skipor.MathLogic.Tasks;

import ru.skipor.MathLogic.Proof.Proof;

import java.io.*;

public class MainTask1 {


    public static void main(String[] args) throws Exception {
        try(
                BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))
        ) {

            int incorrectStatement = Proof.checkFile("input.txt");
            if (incorrectStatement != 0) {
                writer.write("Доказательство некорректно начиная с высказывания № " + Integer.toString(incorrectStatement));
            } else {
                writer.write("Доказательство корректно.");
            }
        }


    }

}

