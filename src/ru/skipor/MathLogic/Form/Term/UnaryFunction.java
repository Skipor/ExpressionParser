package ru.skipor.MathLogic.Form.Term;

import java.util.Arrays;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/15/14
 * Time: 2:23 PM
 */
public class UnaryFunction extends Function {
    public UnaryFunction(String name, Term argument) {
        super(name, Arrays.asList(argument));
    }

    @Override
    public String toString() {
        Term argument = arguments.get(0);
        if (argument instanceof Variable || argument instanceof UnaryFunction) {
            return argument.toString().concat(name);
        } else {
            return new StringBuilder().append('(').append(arguments.get(0)).append(')').append(name).toString();
        }
    }
}
