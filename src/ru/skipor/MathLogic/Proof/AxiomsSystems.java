package ru.skipor.MathLogic.Proof;

import ru.skipor.MathLogic.Form.Form;
import ru.skipor.MathLogic.Form.Logical.BinaryNode;
import ru.skipor.MathLogic.Form.Logical.BinaryOperation;
import ru.skipor.MathLogic.Form.Logical.QuantifierNode;
import ru.skipor.MathLogic.Form.Logical.QuantifierOperation;
import ru.skipor.MathLogic.Form.Parser.FormParser;
import ru.skipor.MathLogic.Form.Term.Constant;
import ru.skipor.MathLogic.Form.Term.Term;
import ru.skipor.MathLogic.Form.Term.UnaryFunction;
import ru.skipor.MathLogic.Form.Term.Variable;

import java.util.HashSet;
import java.util.Set;

public class AxiomsSystems {

    private static String errorMessage;

    public static String getErrorMessage() {
        return errorMessage;
    }

    private AxiomsSystems() {
    }

    public static final String[] systemsOfAxioms = {
            "A->B->A",
            "(A->B)->(A->B->C)->(A->C)",
            "A->B->A&B",
            "A&B->A",
            "A&B->B",
            "A->A|B",
            "B->A|B",
            "(A->C)->(B->C)->(A|B->C)",
            "(A->B)->(A->!B)->!A",
            "!!A->A"
    };

    public static final String[] notLogicAxioms = {
            "a = b -> a' = b'",
            "a = b -> a = c -> b = c",
            "a' = b' -> a = b",
            "!(a' = 0)",
            "a + b' = (a +b)'",
            "a + 0 = a",
            "a * 0 = 0",
            "a * b' = a * b + a"

    };
    public final static Form[] formsOfSystemsOfAxioms = new Form[10];
    public final static Set<Form> notLogicAxiomsSet = new HashSet<>();

    static {
        for (int i = 0; i < systemsOfAxioms.length; i++) {
            try {
                formsOfSystemsOfAxioms[i] = FormParser.parse(systemsOfAxioms[i]);
            } catch (FormParser.ParserException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        for (String axiom : notLogicAxioms) {
            notLogicAxiomsSet.add(Form.create(axiom));
        }
    }

    public static boolean isAxiom(Form statement) throws IllegalStateException {
        if (notLogicAxiomsSet.contains(statement)) {
            return true;
        }
        for (Form axiom : formsOfSystemsOfAxioms) {
            if (axiom.equalsButSubstitutions(statement) != null) {
                return true;
            }

        }

        if(isInductionAxiom(statement)) {
            return true;
        }
        if (statement instanceof BinaryNode
                && ((BinaryNode) statement).operation.equals(BinaryOperation.ENTAILMENT)) {
            final Form la = ((BinaryNode) statement).leftArgument;
            final Form ra = ((BinaryNode) statement).rightArgument;
            Form noSub, withSub;
            Variable variable;
            boolean result = false;
            if (la instanceof QuantifierNode && ((QuantifierNode) la).operation.equals(QuantifierOperation.UNIVERSAL)) {
                variable = ((QuantifierNode) la).boundingVariable;
                noSub = ((QuantifierNode) la).argument;
                withSub = ra;
                result |= check11and12(noSub, withSub, variable);
            }
            if (ra instanceof QuantifierNode && ((QuantifierNode) ra).operation.equals(QuantifierOperation.EXISTENTIAL)) {
                variable = ((QuantifierNode) ra).boundingVariable;
                noSub = ((QuantifierNode) ra).argument;
                withSub = la;
                result |= check11and12(noSub, withSub, variable);
            }
            if (!result) {
                errorMessage = null;
                return false;
            } else {
                return true;
            }


        }
        errorMessage = null;
        return false;


    }

    public static boolean check11and12(Form noSub, Form withSub, Variable variable) {
        if(!noSub.containsVariableAsFree(variable) && noSub.equals(withSub)){
            return true;
        }
        Term sub = noSub.getOnlySubstitution(withSub, variable);
//        System.out.println("No sub " + noSub);
//        System.out.println("With sub " + withSub);
//
//        System.out.println("Sub " + sub); // todo remove

        if (sub != null) {
            if(noSub.isFreeToSubstituteFor(sub, variable)) {
                return true;
            } else {
                errorMessage = "терм " + sub.toString() + " не свободен для подстановки в формулу " + noSub.toString() + " вместо переменной " + variable.toString() +".";
                return false;
            }
        } else {
//            errorMessage = null;
            return false;
        }
    }

    private static boolean isInductionAxiom(Form statement) {
        if (statement instanceof BinaryNode
                && ((BinaryNode) statement).operation.equals(BinaryOperation.ENTAILMENT)) {
            Form pattern = ((BinaryNode) statement).rightArgument;
            Form temp1 = ((BinaryNode) statement).leftArgument;
            if (temp1 instanceof BinaryNode
                    && ((BinaryNode) temp1).operation.equals(BinaryOperation.CONJUNCTION)) {

                final Form rightTemp1 = ((BinaryNode) temp1).rightArgument;
                if(rightTemp1 instanceof QuantifierNode
                        && ((QuantifierNode) rightTemp1).operation.equals(QuantifierOperation.UNIVERSAL)) {

                    Variable var = ((QuantifierNode) rightTemp1).boundingVariable;

                    final Term onlySubstitution = pattern.getOnlySubstitution(((BinaryNode) temp1).leftArgument, var);
//                    System.out.println("only sub is " + onlySubstitution); // todo remove
                    if (Constant.ZERO.equals(onlySubstitution)) {
                        Form temp2 = ((QuantifierNode) rightTemp1).argument;
                        if (temp2 instanceof BinaryNode
                                && ((BinaryNode) temp2).operation.equals(BinaryOperation.ENTAILMENT)) {
                            if(((BinaryNode) temp2).leftArgument.equals(pattern)) {
                                Form rightTemp2 = ((BinaryNode) temp2).rightArgument;
                                if (new UnaryFunction("'", var).equals(pattern.getOnlySubstitution(rightTemp2, var))) {
                                    return true;
                                }

                            }
                        }

                    }



                }



            }


        }
        return false;

    }

}
//

