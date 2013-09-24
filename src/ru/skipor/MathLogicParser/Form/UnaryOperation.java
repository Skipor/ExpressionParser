package ru.skipor.MathLogicParser.Form;

public enum UnaryOperation {

    NEGATION("!") {
        @Override
        public boolean apply(boolean argument) {
            return !argument;
        }
    },;


    public final String token;

    private UnaryOperation(String token) {
        this.token = token;
    }

    abstract public boolean apply(boolean argument);
    @Override
    public String toString() {
        return token;
    }
}
