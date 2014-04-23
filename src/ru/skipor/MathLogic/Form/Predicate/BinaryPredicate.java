package ru.skipor.MathLogic.Form.Predicate;

import ru.skipor.MathLogic.Form.Term.Term;

import java.util.Arrays;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/15/14
 * Time: 1:35 PM
 */
public class BinaryPredicate extends Predicate{


    public BinaryPredicate(String name, Term left, Term right) {
        super(name, Arrays.asList(left, right));
    }

    @Override
    public String toString() {
        return new StringBuilder().append('(').append(arguments.get(0)).append(name).append(arguments.get(1)).append(')').toString();
    }
}
