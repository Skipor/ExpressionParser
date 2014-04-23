package ru.skipor.MathLogic.Form.Term;

import java.util.ArrayList;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/15/14
 * Time: 2:27 PM
 */
public class Constant extends Function {
    public static final Constant ZERO = new Constant("0");


    public Constant(String name) {
        super(name, new ArrayList<Term>(0));
    }

    @Override
    public String toString() {
        return name;
    }
}
