package ru.skipor.MathLogic.Form;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 9/24/13
 * Time: 8:00 AM
 */
public class VariableValues {
    Map<String, Boolean> values;

    public VariableValues() {
        values = new HashMap<>();
    }

    public Boolean getValue(String name) {
        return values.get(name);
    }

    public void putValue(String name, Boolean value) {
        values.put(name, value);
    }
}
