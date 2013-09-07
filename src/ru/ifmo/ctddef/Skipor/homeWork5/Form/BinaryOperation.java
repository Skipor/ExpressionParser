package ru.ifmo.ctddef.Skipor.homeWork5.Form;

import ru.ifmo.ctddef.Skipor.homeWork5.VariablesValues;

public class BinaryOperation implements Form {
    private final Form leftArgument;
    private final Form rightArgument;
    private final boolean isLeftAssociativity;
    private final Operation operation;

    public BinaryOperation(Form leftArgument, Form rightArgument, Operation operation, boolean leftAssociativity) {
        this.leftArgument = leftArgument;
        this.rightArgument = rightArgument;
        this.operation = operation;
        this.isLeftAssociativity = leftAssociativity;
    }

    public BinaryOperation(Form leftArgument, Form rightArgument, Operation operation) {
        this(leftArgument, rightArgument, operation, true);
    }

    public Integer evaluate(VariablesValues arguments) throws FormEvaluationException {
        return operation.apply(leftArgument.evaluate(arguments), rightArgument.evaluate(arguments));
    }


}

