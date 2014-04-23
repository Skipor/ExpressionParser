package ru.skipor.MathLogic.Form.Term;

import java.util.Arrays;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/15/14
 * Time: 2:00 PM
 */
public class BinaryFunction extends Function {

    public BinaryFunction(String name, Term left, Term right) {
        super(name, Arrays.asList(left, right));
    }

    @Override
    public String toString() {
        return new StringBuilder().append('(').append(arguments.get(0)).append(name).append(arguments.get(1)).append(')').toString();
    }
}
