package ru.ifmo.ctddef.Skipor.homeWork5.Form;

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
