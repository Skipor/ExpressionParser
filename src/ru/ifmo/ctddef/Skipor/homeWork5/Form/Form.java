package ru.ifmo.ctddef.Skipor.homeWork5.Form;

import ru.ifmo.ctddef.Skipor.homeWork5.VariablesValues;

public interface Form {
    Integer evaluate(VariablesValues arguments) throws FormEvaluationException;
}
