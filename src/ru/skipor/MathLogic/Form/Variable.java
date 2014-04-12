package ru.skipor.MathLogic.Form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Variable implements Form, Comparable<Variable> {
    public final String name;
    private boolean value;

    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public int compareTo(Variable o) {
        return name.compareTo(o.name);
    }

    public static class VariableSet {
        private HashMap<String, Variable> variableMap = new HashMap<>();

        public Variable getVariable(String name) {
            Variable variable = variableMap.get(name);
            if (variable == null) {
                variable = new Variable(name);
                variableMap.put(name, variable);
            }
            return variable;
        }

        List<Variable> getVariables() {
            return new ArrayList<>(variableMap.values());
        }


    }

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public boolean evaluate(VariableValues values) {
        if (values != null) {
            return values.getValue(name);
        } else {
            return value;
        }
    }

        @Override
        public boolean equals (Object o){
            if (this == o) return true;
            if (!(o instanceof Variable)) return false;

            Variable variable = (Variable) o;

            if (name != null ? !name.equals(variable.name) : variable.name != null) return false;

            return true;
        }

        @Override
        public int hashCode () {
            return name != null ? name.hashCode() : 0;
        }

        @Override
        public String toString () {
            return name;
        }

    }
