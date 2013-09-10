package ru.skipor.MathLogicParser.Form;

public class UnaryNode implements Form {
    public final Form argument;
    public final UnaryOperation operation;


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UnaryNode) {
            return (operation == ((UnaryNode)obj).operation) && argument.equals(((UnaryNode)obj).argument);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (argument instanceof Variable) {
            return operation.toString() + argument.toString();
        } else {
            return operation.toString() + "(" + argument.toString() + ")";
        }
    }

    public UnaryNode(Form argument, UnaryOperation operation) {
        this.argument = argument;
        this.operation = operation;
    }
}

