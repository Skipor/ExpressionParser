package ru.skipor.MathLogic.Form.Predicate;


import ru.skipor.MathLogic.Form.Form;
import ru.skipor.MathLogic.Form.Substitutions;
import ru.skipor.MathLogic.Form.Term.Term;
import ru.skipor.MathLogic.Form.Term.Variable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Predicate extends Form {
    public final List<Term> arguments;
    public final String name;

    public Predicate(String name, List<Term> arguments) {
        this.arguments = arguments;
        this.name = name;
        hashCode = internalHashCode();
    }



    @Override
    public Set<Variable> getFreeVariables() {
        Set<Variable> variableSet = new HashSet<>();
        for (Term argument : arguments) {
            variableSet.addAll(argument.getVariables());
        }
        return (variableSet);

    }

    @Override
    public boolean containsVariableAsFree(Variable variable) {
        for (Term argument : arguments) {
            if (argument.containsVariable(variable)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isFreeToSubstituteFor(Set<Variable> variablesInSubstitution, Variable variable) {
        return true;
    }

    @Override
    public boolean equalsButSubstitutions(Form formWithSubstitutions, Substitutions substitutions) {
        if (formWithSubstitutions instanceof Predicate
                && ((Predicate) formWithSubstitutions).arguments.size() == arguments.size()
                && name.equals(((Predicate) formWithSubstitutions).name)) {

            Iterator<Term> subArgumentIterator = ((Predicate) formWithSubstitutions).arguments.iterator();
            for (Term argument : arguments) {
                Term subArgument = subArgumentIterator.next();
                if (!argument.equalsButSubstitutions(subArgument, substitutions)) {
                    return false;
                }
            }

            return true;

        }

        if (arguments.size() == 0) {
            return substitutions.setOrCompare(this, formWithSubstitutions);
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Predicate)) return false;

        Predicate predicate = (Predicate) o;

        if (arguments != null ? !arguments.equals(predicate.arguments) : predicate.arguments != null) return false;
        if (name != null ? !name.equals(predicate.name) : predicate.name != null) return false;

        return true;
    }
    @Override
    protected int internalHashCode(){
        int result = arguments != null ? arguments.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if (arguments.size() == 0) {
            return name;
        } else {
            StringBuilder builder = new StringBuilder(name);
            builder.append('(');
            for (Iterator<Term> iterator = arguments.iterator(); iterator.hasNext(); ) {
                Term term = iterator.next();
                builder.append(term.toString());
                if (iterator.hasNext()) {
                    builder.append(',');
                }

            }
            builder.append(')');

            return builder.toString();

        }

    }
}


