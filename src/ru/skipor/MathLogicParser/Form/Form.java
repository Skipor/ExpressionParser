package ru.skipor.MathLogicParser.Form;

import java.util.Map;

public interface Form {
    boolean equals(Object obj);

    boolean evaluate(VariableValues values);
    String toString();


}


