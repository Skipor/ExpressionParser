package ru.skipor.MathLogic.Proof;

import ru.skipor.MathLogic.Form.BinaryNode;
import ru.skipor.MathLogic.Form.Form;
import ru.skipor.MathLogic.Form.UnaryNode;
import ru.skipor.MathLogic.Form.Variable;
import ru.skipor.MathLogic.Form.Parser.FormParser;

import java.util.HashMap;
import java.util.Map;

public class AxiomsSystems {

    private AxiomsSystems() {
    }

    public static final String[] systemsOfAxioms = {
            "f->o->f",
            "(f->o)->(f->o->p)->(f->p)",
            "f->o->f&o",
            "f&o->f",
            "f&o->o",
            "f->f|o",
            "o->f|o",
            "(f->p)->(o->p)->(f|o->p)",
            "(f->o)->(f->!o)->!f",
            "!!f->f"
    };
    public static Form[] formsOfSystemsOfAxioms = new Form[10];

    static {
        for (int i = 0; i < systemsOfAxioms.length; i++) {
            try {
                formsOfSystemsOfAxioms[i] = FormParser.parse(systemsOfAxioms[i]);

//                System.out.println(formsOfSystemsOfAxioms[i].toString());    ////////////////////////////

            } catch (FormParser.ParserException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public static boolean isAxiom(Form statement) throws IllegalStateException{
        for (Form axiom : formsOfSystemsOfAxioms) {
            Map<String, Form> variablesValues = new HashMap<String, Form>(5);
            if (isAxiomPart(axiom, statement, variablesValues)) {
                return true;
            }
        }

        return false;
    }

    protected static boolean isAxiomPart(Form axiomPart, Form statementPart, Map<String, Form> variableValues) throws IllegalStateException {
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
        throw new IllegalStateException("Surprising ru.skipor.MathLogic.Form in axiom :))");
    }
}

