package ru.skipor.MathLogic.Form;

public interface Form {
    boolean equals(Object obj);

    boolean evaluate(VariableValues values);
    boolean getValue();
    String toString();



}


