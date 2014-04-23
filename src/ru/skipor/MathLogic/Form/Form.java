package ru.skipor.MathLogic.Form;

import ru.skipor.MathLogic.Form.Parser.FormParser;
import ru.skipor.MathLogic.Form.Term.Term;
import ru.skipor.MathLogic.Form.Term.Variable;

import java.util.Set;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/15/14
 * Time: 1:11 PM
 */
public abstract class Form {
    protected int hashCode = 0;


    public static Form create(String formString) {
        try {
            return FormParser.parse(formString);
        } catch (FormParser.ParserException e) {
            e.printStackTrace();
        }
        return null;
    }
    protected abstract int internalHashCode();

    @Override
    public final int hashCode() {
//        if (hashCode == 0) {
//            hashCode = internalHashCode();
//        }
        return hashCode;

    }


    public abstract Set<Variable> getFreeVariables();
    public abstract boolean  containsVariableAsFree(Variable variable);
    public boolean isFreeToSubstituteFor(Term term, Variable variable) {
        return isFreeToSubstituteFor(term.getVariables(), variable);
    }

    public abstract boolean isFreeToSubstituteFor(Set<Variable> variablesInSubstitution, Variable variable);

    public Substitutions equalsButSubstitutions(Form formWithSubstitutions) {
        Substitutions substitutions = new Substitutions();
        if (equalsButSubstitutions(formWithSubstitutions, substitutions)) {
            return substitutions;
        } else {
            return  null;
        }
    }


    public abstract boolean equalsButSubstitutions(Form formWithSubstitutions, Substitutions substitutions);

    public Term getOnlySubstitution(Form formWithSubstitution, Variable variable) {
        Substitutions substitutions = equalsButSubstitutions(formWithSubstitution);
        if (substitutions != null) {
            if (substitutions.getVariableSubsitutions().size() == 1 && substitutions.getPredicateSubstitutions().isEmpty()) {
                return substitutions.getVariableSubsitutions().get(variable);
            }

        }

        return null;


    }





}


