package ru.ifmo.ctddef.Skipor.homeWork5.Form;

import ru.ifmo.ctddef.Skipor.homeWork5.VariablesValues;

public class Const implements Form {
    private final int value;

    public Const(int value) {
        this.value = value;
    }

    public Integer evaluate(VariablesValues ignored) {
        return this.value;
    }
}
