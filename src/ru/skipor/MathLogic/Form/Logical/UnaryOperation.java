package ru.skipor.MathLogic.Form.Logical;

public enum UnaryOperation {

    NEGATION("!") {
    },;


    public final String token;

    private UnaryOperation(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return token;
    }
}
