package ru.skipor.MathLogicParser.Proof;

import ru.skipor.MathLogicParser.Form.Form;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 9/24/13
 * Time: 10:02 AM
 */
public class Statement {

    public final Form form;
    public int number;
    public String comment;

    public Statement(Form form, int number, String comment) {
        this.form = form;
        this.number = number;
        this.comment = comment;
    }
}
