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
    public boolean equals(Object obj) {
        if (obj instanceof BinaryNode) {
            return (operation.equals(((BinaryNode)obj).operation))
                    && (leftArgument.equals(((BinaryNode)obj).leftArgument))
                    && (rightArgument.equals(((BinaryNode)obj).rightArgument));
        }  else {
            return false;
        }
    }

    public BinaryNode(Form leftArgument, Form rightArgument, BinaryOperation operation) {
        this.leftArgument = leftArgument;
        this.rightArgument = rightArgument;
        this.operation = operation;
    }


}

