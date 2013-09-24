package ru.skipor.MathLogicParser.Proof;

import ru.skipor.MathLogicParser.Form.Form;

import java.util.List;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 9/15/13
 * Time: 11:20 PM
 */
public class Proof {
    public List<Form> statements;
    public List<Form> statementsSystem;     // can be null


    public Proof( List<Form> statements, List<Form> statementsSystem) {
        this.statementsSystem = statementsSystem;
        this.statements = statements;
    }

    public Proof(List<Form> statements) {
        this(statements, null);
    }

}
