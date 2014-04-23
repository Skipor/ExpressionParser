package ru.skipor.MathLogic.Form.Term;

import ru.skipor.MathLogic.Form.Substitutions;

import java.util.HashSet;
import java.util.Set;

public class Variable extends Term implements Comparable<Variable> {


    @Override
    public int compareTo(Variable o) {
        return name.compareTo(o.name);
    }


    public Variable(String name) {
        super(name);
        hashCode = internalHashCode();
    }

    @Override
    public Set<Variable> getVariables() {
        Set<Variable> set = new HashSet<>(2);
        set.add(this);
        return set;
    }

    @Override
    public boolean containsVariable(Variable variable) {
        return name.equals(variable.name);
    }

    @Override
    public boolean equalsButSubstitutions(Term termWithSubstitutions, Substitutions substitutions) {
        if (equals(termWithSubstitutions)) {
            return true;
        } else if (!substitutions.isBounded(this)) {
            return substitutions.setOrCompare(this, termWithSubstitutions);
        }

        return false;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variable)) return false;

        Variable variable = (Variable) o;

        return !(name != null ? !name.equals(variable.name) : variable.name != null);

    }

    @Override
    protected int internalHashCode(){
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

}
