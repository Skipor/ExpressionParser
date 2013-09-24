package ru.skipor.MathLogicParser.Form;

public class BinaryNode implements Form {
    public final Form leftArgument;
    public final Form rightArgument;
    public final BinaryOperation operation;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if ((leftArgument instanceof BinaryNode) && (((BinaryNode) leftArgument).operation.priority < operation.priority
                || ((BinaryNode) leftArgument).operation.priority == operation.priority
                && !((BinaryNode) leftArgument).operation.isLeftAssociativity)) {
            builder.append("(");
            builder.append(leftArgument.toString());
            builder.append(")");
        } else {
            builder.append(leftArgument.toString());
        }
        builder.append(operation.toString());
        if (rightArgument instanceof BinaryNode && (((BinaryNode) rightArgument).operation.priority < operation.priority
                || ((BinaryNode) rightArgument).operation.priority == operation.priority && !operation.isLeftAssociativity)) {
            builder.append("(");
            builder.append(rightArgument.toString());
            builder.append(")");
        } else {
            builder.append(rightArgument.toString());
        }

        return builder.toString();

    }

    @Override
    public boolean evaluate(VariableValues values) {
        return operation.apply(leftArgument.evaluate(values), rightArgument.evaluate(values));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BinaryNode)) return false;

        BinaryNode that = (BinaryNode) o;

        if (leftArgument != null ? !leftArgument.equals(that.leftArgument) : that.leftArgument != null) return false;
        if (operation != that.operation) return false;
        if (rightArgument != null ? !rightArgument.equals(that.rightArgument) : that.rightArgument != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = leftArgument != null ? leftArgument.hashCode() : 0;
        result = 31 * result + (rightArgument != null ? rightArgument.hashCode() : 0);
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        return result;
    }

    public BinaryNode(Form leftArgument, Form rightArgument, BinaryOperation operation) {
        this.leftArgument = leftArgument;
        this.rightArgument = rightArgument;
        this.operation = operation;
    }


}

