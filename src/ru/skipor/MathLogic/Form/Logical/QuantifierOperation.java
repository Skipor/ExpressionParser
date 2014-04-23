package ru.skipor.MathLogic.Form.Logical;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/15/14
 * Time: 12:45 PM
 */
public enum QuantifierOperation {
    UNIVERSAL("@") {
    },

    EXISTENTIAL("?") {

    },;


    public final String token;

    private QuantifierOperation(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return token;
    }
}
