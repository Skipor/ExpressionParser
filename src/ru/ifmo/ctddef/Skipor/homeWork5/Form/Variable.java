package ru.ifmo.ctddef.Skipor.homeWork5.Form;

import ru.ifmo.ctddef.Skipor.homeWork5.VariablesValues;

public class Variable implements Form {
    public final String name;

    public Variable(String name) {
        this.name = name;
    }

    static class ValueIsNotFoundedException extends FormEvaluationException {
        public ValueIsNotFoundedException(String message) {
            super(message);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }

    public Integer evaluate(VariablesValues arguments) throws ValueIsNotFoundedException {
        Integer value = arguments.getValue(name);
        if (value == null) {
            throw new ValueIsNotFoundedException("There is no value for \'" + name + "\"");
        }
        return value;

    }
}
