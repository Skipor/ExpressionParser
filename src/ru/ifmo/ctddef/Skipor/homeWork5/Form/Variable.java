package ru.ifmo.ctddef.Skipor.homeWork5.Form;

public class Variable implements Form {
    public final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variable) {
            return name == ((Variable) obj).name;

        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return name;
    }

    static class ValueIsNotFoundedException extends Exception {
        public ValueIsNotFoundedException(String message) {
            super(message);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }

}
