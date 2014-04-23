package ru.skipor.MathLogic.Form.Logical;

import ru.skipor.MathLogic.Form.Form;
import ru.skipor.MathLogic.Form.Substitutions;
import ru.skipor.MathLogic.Form.Term.Variable;

import java.util.Set;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/15/14
 * Time: 12:39 PM
 */
public class QuantifierNode extends LogicalOperation {
    public final Form argument;
    public final QuantifierOperation operation;
    public final Variable boundingVariable;



    @Override
    public String toString() {

            return operation.toString() + boundingVariable.toString() + "(" + argument.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuantifierNode)) return false;

        QuantifierNode that = (QuantifierNode) o;

        if (argument != null ? !argument.equals(that.argument) : that.argument != null) return false;
        if (boundingVariable != null ? !boundingVariable.equals(that.boundingVariable) : that.boundingVariable != null)
            return false;
        return operation == that.operation;

    }

    @Override
    protected int internalHashCode() {
        int result = argument != null ? argument.hashCode() : 0;
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        result = 31 * result + (boundingVariable != null ? boundingVariable.hashCode() : 0);
        return result;
    }

    @Override
    public Set<Variable> getFreeVariables() {
        Set<Variable> variableSet = argument.getFreeVariables();
        variableSet.remove(boundingVariable);
        return variableSet;
    }

    public QuantifierNode(QuantifierOperation operation, Variable boundingVariable, Form argument) {
        this.argument = argument;
        this.operation = operation;
        this.boundingVariable = boundingVariable;
        hashCode = internalHashCode();
    }

    @Override
    public boolean containsVariableAsFree(Variable variable) {
        return !variable.equals(boundingVariable) && argument.containsVariableAsFree(variable);
    }

    @Override
    public boolean isFreeToSubstituteFor(Set<Variable> variablesInSubstitution, Variable variable) {
        if (boundingVariable.equals(variable)) {
            return true;
        } else {
            if (variablesInSubstitution.contains(boundingVariable)) {
                return !argument.containsVariableAsFree(variable);
            } else {
                return argument.isFreeToSubstituteFor(variablesInSubstitution, variable);
            }
        }
    }

    @Override
    public boolean equalsButSubstitutions(Form formWithSubstitutions, Substitutions substitutions) {
        if (formWithSubstitutions instanceof QuantifierNode
                && operation.equals(((QuantifierNode) formWithSubstitutions).operation)
                && boundingVariable.equals(((QuantifierNode) formWithSubstitutions).boundingVariable)) {
            boolean removeAfterCheck = substitutions.addBounded(boundingVariable);
            boolean result = argument.equalsButSubstitutions(((QuantifierNode) formWithSubstitutions).argument, substitutions);
            if (removeAfterCheck) {
                substitutions.removeBounded(boundingVariable);
            }
            return result;
        }

        return false;
    }
}
