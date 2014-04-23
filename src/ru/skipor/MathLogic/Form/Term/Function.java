package ru.skipor.MathLogic.Form.Term;

import ru.skipor.MathLogic.Form.Substitutions;

import java.util.*;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/15/14
 * Time: 1:59 PM
 */
public class Function extends Term {
    public final List<Term> arguments;

    public Function(String name, List<Term> arguments) {
        super(name);
        this.arguments = arguments;
        hashCode = internalHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Function)) return false;

        Function function = (Function) o;

        if (!arguments.equals(function.arguments)) return false;
        return name.equals(function.name);

    }

    @Override
    protected int internalHashCode(){
        int result = arguments.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {

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

    @Override
    public Set<Variable> getVariables() {
        Set<Variable> variableSet = new HashSet<>();
        for (Term argument : arguments) {
            variableSet.addAll(argument.getVariables());
        }
        return (variableSet);

    }

    @Override
    public boolean containsVariable(Variable variable) {
        for (Term argument : arguments) {
            if (argument.containsVariable(variable)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equalsButSubstitutions(Term termWithSubstitutions, Substitutions substitutions) {
        if (termWithSubstitutions instanceof Function
                && ((Function) termWithSubstitutions).arguments.size() == arguments.size()
            && name.equals(((Function) termWithSubstitutions).name)) {

            Iterator<Term> subArgumentIterator = ((Function) termWithSubstitutions).arguments.iterator();
            for (Term argument : arguments) {
                Term subArgument = subArgumentIterator.next();
                if (!argument.equalsButSubstitutions(subArgument, substitutions)) {
                    return false;
                }
            }

            return true;

        }
        return false;
    }
}
