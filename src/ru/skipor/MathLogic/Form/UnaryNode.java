package ru.skipor.MathLogic.Form;

public class UnaryNode implements Form {
    public final Form argument;
    public final UnaryOperation operation;
    private boolean value;

    @Override
    public boolean getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnaryNode)) return false;

        UnaryNode unaryNode = (UnaryNode) o;

        if (argument != null ? !argument.equals(unaryNode.argument) : unaryNode.argument != null) return false;
        if (operation != unaryNode.operation) return false;

        return true;
    }

    @Override
    public boolean evaluate(VariableValues values) {
        return value = operation.apply(argument.evaluate(values));
    }

    @Override
    public int hashCode() {
        int result = argument != null ? argument.hashCode() : 0;
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        return result;
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

