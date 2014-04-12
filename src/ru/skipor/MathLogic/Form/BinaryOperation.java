package ru.skipor.MathLogic.Form;

public enum BinaryOperation {

    ENTAILMENT("->", 0, false) {
        @Override
        public boolean apply(boolean leftArgument, boolean rightArgument) {
            return (!leftArgument || rightArgument);
        }
    },

    DISJUNCTION("|", 1) {
        @Override
        public boolean apply(boolean leftArgument, boolean rightArgument) {
            return leftArgument || rightArgument;
        }
    },

    CONJUNCTION("&", 2) {
        @Override
        public boolean apply(boolean leftArgument, boolean rightArgument) {
            return leftArgument && rightArgument;
        }
    },
;


    public final String token;
    public final int priority;
    public final boolean isLeftAssociativity;


    private BinaryOperation(String token, int priority, boolean isLeftAssociativity) {
        this.isLeftAssociativity = isLeftAssociativity;
        this.token = token;
        this.priority = priority;
    }

    private BinaryOperation(String token, int priority) {
        this(token, priority, true);
    }

    @Override
    public String toString() {
        return token;
    }

    abstract public boolean apply(boolean leftArgument, boolean rightArgument);


}
