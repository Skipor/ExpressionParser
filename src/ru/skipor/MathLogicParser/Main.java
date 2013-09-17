package ru.skipor.MathLogicParser;

import ru.skipor.MathLogicParser.Form.BinaryNode;
import ru.skipor.MathLogicParser.Form.BinaryOperation;
import ru.skipor.MathLogicParser.Form.Form;
import ru.skipor.MathLogicParser.Proof.AxiomsSystems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws Exception {


        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        PrintWriter writer = new PrintWriter(new FileWriter("output.txt"));
        List<Form> statements = new ArrayList<Form>();
        int linesReaded = 0;


        boolean proofIsCorrect = true;

        read:
        while (reader.ready()) {
            linesReaded++;
            String readed = reader.readLine();
            Form nextStatement = FormParser.formParse(readed);
//            System.out.println(nextStatement.toString());
//            System.out.println(Integer.toString(linesReaded));


            for (Form statement : statements) {                       //Modus Ponus
                if (statement instanceof BinaryNode && ((BinaryNode) statement).operation == BinaryOperation.ENTAILMENT) {
                    if (nextStatement.equals(((BinaryNode) statement).rightArgument)) {
                        Form leftTerm = ((BinaryNode) statement).leftArgument;
                        for (Form context : statements) {
                            if (context.equals(leftTerm)) {
                                statements.add(nextStatement);

//                                System.out.println("Statment " + Integer.toString(linesReaded) + " Modus Ponus "); ///////// /////////


                                continue read;

                            }
                        }
                    }
                }
            }


            if (AxiomsSystems.isAxiom(nextStatement)) {
                statements.add(nextStatement);
            } else {
                writer.write("Доказательство некорректно начиная с высказывания № " + Integer.toString(linesReaded));
                proofIsCorrect = false;
                break;
            }
        }

        if (proofIsCorrect) {
            writer.write("Доказательство корректно.");
        }

        writer.close();


    }

}

