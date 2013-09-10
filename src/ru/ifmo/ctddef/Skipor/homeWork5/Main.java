package ru.ifmo.ctddef.Skipor.homeWork5;

import ru.ifmo.ctddef.Skipor.homeWork5.Form.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    static String[] systemsOfAxioms = {
            "F->O->F",
            "(F->O)->(F->O->P)->(F->P)",
            "F->O->F&O",
            "F&O->F",
            "F&O->O",
            "F->F|O",
            "O->F|O",
            "(F->P)->(O->P)->(F|O->P)",
            "(F->O)->(F->!O)->!F",
            "!!F->F"
    };
    static Form[] formsOfSystemsOfAxioms = new Form[10];

    static {
        for (int i = 0; i < systemsOfAxioms.length; i++) {
            try {
                formsOfSystemsOfAxioms[i] = FormParser.formParse(systemsOfAxioms[i]);

//                System.out.println(formsOfSystemsOfAxioms[i].toString());    ////////////////////////////

            } catch (ParserException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    static boolean isAxiom(Form statement) throws Exception {
        for (Form axiom : formsOfSystemsOfAxioms) {
            Map<String, Form> variablesValues = new HashMap<String, Form>(5);
            if (isAxiomPart(axiom, statement, variablesValues)) {
                return true;
            }
        }

        return false;


    }

    static boolean isAxiomPart(Form axiomPart, Form statementPart, Map<String, Form> variableValues) throws Exception {
        if (axiomPart instanceof Variable) {
            String variableName = axiomPart.toString();
            if (variableValues.containsKey(variableName)) {
                return statementPart.equals(variableValues.get(variableName));
            } else {
                variableValues.put(variableName, statementPart);
                return true;
            }
        }

        if (axiomPart instanceof UnaryNode) {
            if (statementPart instanceof UnaryNode) {
                return (((UnaryNode) axiomPart).operation.equals(((UnaryNode) statementPart).operation)
                        && isAxiomPart(((UnaryNode) axiomPart).argument, (((UnaryNode) statementPart).argument), variableValues));
            } else {
                return false;
            }
        }

        if (axiomPart instanceof BinaryNode) {
            if (statementPart instanceof BinaryNode) {
                return ((BinaryNode) axiomPart).operation.equals(((BinaryNode) statementPart).operation)
                        && isAxiomPart(((BinaryNode) axiomPart).leftArgument, ((BinaryNode) statementPart).leftArgument, variableValues)
                        && isAxiomPart(((BinaryNode) axiomPart).rightArgument, ((BinaryNode) statementPart).rightArgument, variableValues);

            } else {
                return false;
            }
        }
        throw new Exception("Surprising Form in axiom :))");
    }


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


            if (isAxiom(nextStatement)) {
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

