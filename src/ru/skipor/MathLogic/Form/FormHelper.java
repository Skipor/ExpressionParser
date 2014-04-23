package ru.skipor.MathLogic.Form;

import ru.skipor.MathLogic.Form.Logical.BinaryNode;
import ru.skipor.MathLogic.Form.Logical.UnaryNode;
import ru.skipor.MathLogic.Form.Predicate.Predicate;

import java.util.HashMap;
import java.util.Map;

/**
* User: Vladimir Skipor
* Email: vladimirskipor@gmail.com
* Date: 9/24/13
* Time: 9:21 AM
*/
public class FormHelper {


//public static void putAllVariables(Form form, Set<Predicate> formSet) {
//        if (form instanceof BinaryNode) {
//
//            putAllVariables(((BinaryNode) form).leftArgument, formSet);
//            putAllVariables(((BinaryNode) form).rightArgument, formSet);
//
//        } else if (form instanceof UnaryNode) {
//            putAllVariables(((UnaryNode) form).argument, formSet);
//        } else if (form instanceof Predicate) {
//            formSet.add((Predicate) form);
//        } else {
//            throw new IllegalArgumentException();
//        }
//    }

//    public static Variable[] getVariables(Form form) {
//        Set<Predicate> formSet = new HashSet<>();
//        putAllVariables(form, formSet);
//        return formSet.toArray(new Variable[formSet.size()]);
//    }

    public static final String[] argumentOrder = {"A", "B", "C", "D"};
    static final Map<String, Form> arguments = new HashMap<>(argumentOrder.length);

    public static Form insert(Form base, Form... inputs) {
        if (inputs.length > argumentOrder.length) {
            throw new IllegalArgumentException("Too many inputs. At most " + Integer.toString(argumentOrder.length) + " needed. ");
        }
        arguments.clear();
        for (int i = 0; i < inputs.length; i++) {
            FormHelper.arguments.put(argumentOrder[i], inputs[i]);
        }

        return insert(base);


    }

    private static Form insert(Form base) {
        if (base instanceof Predicate) {
            Form result = arguments.get(((Predicate) base).name);
            if (result != null) {
                return result;
            } else {
                throw new IllegalArgumentException("No form founded for " + ((Predicate) base).name);
            }
        } else if (base instanceof BinaryNode) {
            return new BinaryNode(
                    insert(((BinaryNode) base).leftArgument),
                    insert(((BinaryNode) base).rightArgument),
                    ((BinaryNode) base).operation);
        } else if (base instanceof UnaryNode) {
            return new UnaryNode(insert(((UnaryNode) base).argument), ((UnaryNode) base).operation);
        } else {
            throw new IllegalArgumentException("Unexpected Form subclass");
        }
    }
}
