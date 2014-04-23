package ru.skipor.MathLogic.Form.Logical;

import ru.skipor.MathLogic.Form.Form;
import ru.skipor.MathLogic.Form.Substitutions;
import ru.skipor.MathLogic.Form.Term.Variable;

import java.util.Set;

public class BinaryNode extends LogicalOperation {
    public final Form leftArgument;
    public final Form rightArgument;
    public final BinaryOperation operation;


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if ((leftArgument instanceof BinaryNode) && (((BinaryNode) leftArgument).operation.priority < operation.priority
                || ((BinaryNode) leftArgument).operation.priority == operation.priority
                && !((BinaryNode) leftArgument).operation.isLeftAssociativity)) {
            builder.append("(");
            builder.append(leftArgument.toString());
            builder.append(")");
        } else {
            builder.append(leftArgument.toString());
        }
        builder.append(operation.toString());
        if (rightArgument instanceof BinaryNode && (((BinaryNode) rightArgument).operation.priority < operation.priority
                || ((BinaryNode) rightArgument).operation.priority == operation.priority && !operation.isLeftAssociativity)) {
            builder.append("(");
            builder.append(rightArgument.toString());
            builder.append(")");
        } else {
            builder.append(rightArgument.toString());
        }

        return builder.toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BinaryNode)) return false;

        BinaryNode that = (BinaryNode) o;

        if (leftArgument != null ? !leftArgument.equals(that.leftArgument) : that.leftArgument != null) return false;
        if (operation != that.operation) return false;
        if (rightArgument != null ? !rightArgument.equals(that.rightArgument) : that.rightArgument != null)
            return false;

        return true;
    }

    protected int internalHashCode() {
        int result = leftArgument != null ? leftArgument.hashCode() : 0;
        result = 31 * result + (rightArgument != null ? rightArgument.hashCode() : 0);
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        return result;
    }

    @Override
    public Set<Variable> getFreeVariables() {
        Set<Variable> variableSet = leftArgument.getFreeVariables();
        variableSet.addAll(rightArgument.getFreeVariables());
        return variableSet;
    }

    public BinaryNode(Form leftArgument, Form rightArgument, BinaryOperation operation) {
        this.leftArgument = leftArgument;
        this.rightArgument = rightArgument;
        this.operation = operation;
        hashCode = internalHashCode();
    }


    @Override
    public boolean containsVariableAsFree(Variable variable) {
        return (leftArgument.containsVariableAsFree(variable) || rightArgument.containsVariableAsFree(variable));
    }

    @Override
    public boolean isFreeToSubstituteFor(Set<Variable> variablesInSubstitution, Variable variable) {
        return leftArgument.isFreeToSubstituteFor(variablesInSubstitution, variable)
                && rightArgument.isFreeToSubstituteFor(variablesInSubstitution, variable);
    }

    @Override
    public boolean equalsButSubstitutions(Form formWithSubstitutions, Substitutions substitutions) {
        if (formWithSubstitutions instanceof BinaryNode) {
            if(operation.equals(((BinaryNode) formWithSubstitutions).operation)) {
                return leftArgument.equalsButSubstitutions(((BinaryNode) formWithSubstitutions).leftArgument, substitutions)
                        && rightArgument.equalsButSubstitutions(((BinaryNode) formWithSubstitutions).rightArgument, substitutions);
            }
        }
        return false;
    }
}

