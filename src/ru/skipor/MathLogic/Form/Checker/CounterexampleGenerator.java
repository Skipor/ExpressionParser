package ru.skipor.MathLogic.Form.Checker;

import ru.skipor.MathLogic.Form.*;
import ru.skipor.MathLogic.Proof.Proof;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/8/14
 * Time: 4:58 PM
 */
public class CounterexampleGenerator {


    private final Form form;
    private final Variable[] variables;
    private final boolean[] values;
    private final int variablesCount;




    public static List<Variable> generate(Form form) {
        return new CounterexampleGenerator(form).generate();
    }

    public CounterexampleGenerator(Form form) {
        this.form = form;
        variables = FormHelper.getVariables(form);
        variablesCount = variables.length;
        values = new boolean[variablesCount];

    }


    List<Variable> generate() {
        for (int i = 0; i < variablesCount; i++) {
            setValue(i, false);
        }
        int p = variablesCount - 1;
        while (true) {
            boolean result = form.evaluate(null);
//            for (boolean b : values) {
//                System.out.print(b);
//                System.out.print(' ');
//            }
//
//            System.out.println(" result : " + result);
            if (result) {

                while (p >= 0 && values[p]) {
                    p--;
                }
                if (p < 0) {
                    return null;
                }
                setValue(p, true);

                while (p < (variablesCount - 1)) {
                    p++;
                    setValue(p, false);
                }

            } else {

                return Arrays.asList(variables);
            }
        }

    }

    private void setValue(int index, boolean value) {
        variables[index].setValue(value);
        values[index] = value;
    }
}
