package ru.skipor.MathLogic.Form.Logical;

import ru.skipor.MathLogic.Form.Form;
import ru.skipor.MathLogic.Form.Substitutions;
import ru.skipor.MathLogic.Form.Term.Variable;

import java.util.Set;

public class UnaryNode extends LogicalOperation {
    public final Form argument;
    public final UnaryOperation operation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnaryNode)) return false;

        UnaryNode unaryNode = (UnaryNode) o;

        if (argument != null ? !argument.equals(unaryNode.argument) : unaryNode.argument != null) return false;
        return operation == unaryNode.operation;

    }



    @Override
    protected int internalHashCode(){
        int result = argument != null ? argument.hashCode() : 0;
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if (argument instanceof UnaryNode) {
            return operation.toString() + argument.toString();
        } else {
            return operation.toString() + "(" + argument.toString() + ")";
        }
    }

    public UnaryNode(Form argument, UnaryOperation operation) {
        this.argument = argument;
        this.operation = operation;
        hashCode = internalHashCode();
    }
    @Override
    public Set<Variable> getFreeVariables() {
        return argument.getFreeVariables();
    }

    @Override
    public boolean containsVariableAsFree(Variable variable) {
         return argument.containsVariableAsFree(variable);
    }

    @Override
    public boolean isFreeToSubstituteFor(Set<Variable> variablesInSubstitution, Variable variable) {
        return argument.isFreeToSubstituteFor(variablesInSubstitution, variable);
    }

    @Override
    public boolean equalsButSubstitutions(Form formWithSubstitutions, Substitutions substitutions) {
        if (formWithSubstitutions instanceof UnaryNode && operation.equals(((UnaryNode) formWithSubstitutions).operation)) {
            return argument.equalsButSubstitutions(((UnaryNode) formWithSubstitutions).argument, substitutions);
        }
        return false;
    }
}

