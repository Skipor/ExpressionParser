package ru.skipor.MathLogic.Form.Logical;

public enum BinaryOperation {

    ENTAILMENT("->", 0, false) {
    },

    DISJUNCTION("|", 1) {
    },

    CONJUNCTION("&", 2) {
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


}
