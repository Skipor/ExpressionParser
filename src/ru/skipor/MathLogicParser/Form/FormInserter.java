package ru.skipor.MathLogicParser.Form;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 9/24/13
 * Time: 9:21 AM
 */
public class FormInserter {



    public static final String[] argumentOrder = {"f", "o", "p", "z"};
    static final Map<String, Form> arguments = new HashMap<>(argumentOrder.length);

    public static Form insert(Form base, Form ... inputs) {
        if (inputs.length > argumentOrder.length) {
            throw new IllegalArgumentException("Too many inputs. At most " + Integer.toString(argumentOrder.length) + " needed. ");
        }
        arguments.clear();
        for (int i = 0; i < inputs.length; i++) {
            FormInserter.arguments.put(argumentOrder[i], inputs[i]);
        }

        return insert(base);


    }

    private static Form insert(Form base) {
        if (base instanceof Variable) {
            Form result = arguments.get(((Variable) base).name);
            if (result != null) {
                return result;
            } else {
                throw new IllegalArgumentException("No form founded for " + ((Variable) base).name);
            }
        } else if (base instanceof BinaryNode) {
            return new BinaryNode(
                    insert(((BinaryNode) base).leftArgument),
                    insert(((BinaryNode) base).rightArgument),
                    ((BinaryNode) base).operation);
        } else if (base instanceof UnaryNode) {
            return new UnaryNode(((UnaryNode) base).argument, ((UnaryNode) base).operation);
        } else {
            throw new IllegalArgumentException("Unexpected Form subclass");
        }
    }
}
