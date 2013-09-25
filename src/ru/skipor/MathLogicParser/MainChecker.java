package ru.skipor.MathLogicParser;

import ru.skipor.MathLogicParser.Form.Form;
import ru.skipor.MathLogicParser.Proof.Proof;
import ru.skipor.MathLogicParser.Proof.ProofChecker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainChecker {


    public static void main(String[] args) throws Exception {

        try(
                BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
                BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))
        ) {

            List<Form> statements = new ArrayList<Form>();

            while (reader.ready()) {
                String readed = reader.readLine();
                statements.add(FormParser.formParse(readed));
//            System.out.println(nextStatement.toString());
//            System.out.println(Integer.toString(linesReaded));
            }

            int incorrectStatement = ProofChecker.check(new Proof(statements));

            if (incorrectStatement != 0) {
                writer.write("Доказательство некорректно начиная с высказывания № " + Integer.toString(incorrectStatement));
            } else {
                writer.write("Доказательство корректно.");
            }
        }


    }

}

