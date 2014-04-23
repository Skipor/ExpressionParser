package ru.skipor.MathLogic.Form.Term;

import ru.skipor.MathLogic.Form.Substitutions;

import java.util.Set;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/15/14
 * Time: 11:50 AM
 */
public abstract class Term {
    public final String name;
    protected int hashCode;

    protected Term(String name) {
        this.name = name;
    }


    public abstract Set<Variable> getVariables();

    public abstract boolean containsVariable(Variable variable);

    public abstract boolean equalsButSubstitutions(Term termWithSubstitutions, Substitutions substitutions);
    protected abstract int internalHashCode();
    @Override
    public final int hashCode() {
//        if (hashCode == 0) {
//            hashCode = internalHashCode();
//        }
        return hashCode;

    }
}
