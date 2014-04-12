package ru.skipor.MathLogic.Proof;

import ru.skipor.MathLogic.Form.Checker.CounterexampleGenerator;
import ru.skipor.MathLogic.Form.Form;
import ru.skipor.MathLogic.Form.FormHelper;
import ru.skipor.MathLogic.Form.Variable;

import java.util.Arrays;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/10/14
 * Time: 3:51 PM
 */
public class ProofGenerator {


    Variable[] variables;
    int variablesCount;
    Form form;
    public Proof generate(Form form) {
        if (CounterexampleGenerator.generate(form) != null) {
            throw new IllegalArgumentException("Expression has counterexample, and can not be proved");
        }
        this.form = form;
        variables = FormHelper.getVariables(form);
        Arrays.sort(variables);
        variablesCount = variables.length;

        Proof result = generate(0);


        return result;



    }

    private Proof generate(int currentVariableNumber) {
        if (currentVariableNumber < variablesCount) {
            variables[currentVariableNumber].setValue(true);
            Proof left = generate(currentVariableNumber + 1);
            variables[currentVariableNumber].setValue(false);
            Proof right = generate(currentVariableNumber + 1);
            return Proof.concat(left, right);
        } else {
            return null; // todo

        }
    }


}
