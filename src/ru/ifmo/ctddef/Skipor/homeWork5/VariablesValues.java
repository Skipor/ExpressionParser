package ru.ifmo.ctddef.Skipor.homeWork5;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vladimirskipor
 * Date: 28.05.13
 * Time: 13:21
 * To change this template use File | Settings | File Templates.
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
