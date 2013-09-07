package ru.ifmo.ctddef.Skipor.homeWork5;

import java.util.Map;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 28.05.13
 * Time: 13:21
 */
public class VariablesValues {
    private Map<String, Integer> values;

    public VariablesValues(Map<String, Integer> values) {
        this.values = values;
    }

    public Integer getValue(String name) {
        return values.get(name);
    }
}
