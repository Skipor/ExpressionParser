package ru.skipor.MathLogic.Form;

import ru.skipor.MathLogic.Form.Predicate.Predicate;
import ru.skipor.MathLogic.Form.Term.Term;
import ru.skipor.MathLogic.Form.Term.Variable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Substitutions {

    private final Map<Variable, Term> variableTermMap;
    private final Map<Predicate, Form> predicateFormMap;
    private final Set<Variable> boundedVariables;

    public Map<Variable, Term> getVariableSubsitutions() {
        return variableTermMap;
    }

    public Map<Predicate, Form> getPredicateSubstitutions() {
        return predicateFormMap;
    }

    public Substitutions() {
        variableTermMap = new HashMap<>();
        predicateFormMap = new HashMap<>();
        boundedVariables = new HashSet<>();
    }

    public boolean addBounded(Variable variable) {
        return boundedVariables.add(variable);

    }
    public boolean isBounded(Variable variable) {
        return boundedVariables.contains(variable);
    }

    public void removeBounded(Variable variable) {
        boundedVariables.remove(variable);
    }


    public boolean setOrCompare(Variable variable, Term subtitution) {
        Term contains = variableTermMap.get(variable);
        if (contains == null) {
            variableTermMap.put(variable, subtitution);
            return true;
        } else {
            return contains.equals(subtitution);
        }


    }

    public boolean setOrCompare(Predicate predicate, Form subtitution) {
        Form contains = predicateFormMap.get(predicate);
        if (contains == null) {
            predicateFormMap.put(predicate, subtitution);
            return true;
        } else {
            return contains.equals(subtitution);
        }



    }

    @Override
    public String toString() {
        return "Substitutions{" +
                "variableTermMap=" + variableTermMap +
                ", predicateFormMap=" + predicateFormMap +
                ", boundedVariables=" + boundedVariables +
                '}';
    }
}
