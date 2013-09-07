package ru.ifmo.ctddef.Skipor.homeWork5.Form;

import ru.ifmo.ctddef.Skipor.homeWork5.VariablesValues;

public class BinaryOperation implements Form {
    private final Form leftArgument;
    private final Form rightArgument;
    private final boolean isLeftA
    private final Operation operation;

    public BinaryOperation(Form leftArgument, Form rightArgument, Operation operation) {
        this.leftArgument = leftArgument;
        this.rightArgument = rightArgument;
        this.operation = operation;
    }

    public Integer evaluate(VariablesValues arguments) throws FormEvaluationException {
        return operation.apply(leftArgument.evaluate(arguments), rightArgument.evaluate(arguments));
    }


}

