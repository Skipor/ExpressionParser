package ru.ifmo.ctddef.Skipor.homeWork5.Form;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public enum Operation {


    PLUS("+", 0) {
        public Integer apply(Integer leftArgument, Integer rightArgument) throws FormOverflowException {
            int res = leftArgument + rightArgument;
            if (res - leftArgument != rightArgument) {
                throw new FormOverflowException("Overflow: " + String.valueOf(leftArgument) + " + "
                        + String.valueOf(rightArgument) + " != " + String.valueOf(res));

            }

            return res;
        }
    },

    MINUS("-", 0) {
        public Integer apply(Integer leftArgument, Integer rightArgument) throws FormOverflowException {
            int res = leftArgument - rightArgument;
            if (rightArgument + res != leftArgument) {
                throw new FormOverflowException("Overflow: " + String.valueOf(leftArgument) + " - "
                        + String.valueOf(rightArgument) + " != " + String.valueOf(res));

            }
            return res;
        }
    },

    TIMES("*", 1) {
        public Integer apply(Integer leftArgument, Integer rightArgument) throws FormOverflowException {
            int res = leftArgument * rightArgument;
            if (res / leftArgument != rightArgument) {
                throw new FormOverflowException("Overflow: " + String.valueOf(leftArgument) + " * "
                        + String.valueOf(rightArgument) + " != " + String.valueOf(res));

            }
            return res;
        }
    },

    DIVIDE("/", 1) {
        public Integer apply(Integer leftArgument, Integer rightArgument) throws FormDBZException {
            if (rightArgument == 0) {
                throw new FormDBZException("division by zero");
            }
            return leftArgument / rightArgument;
        }
    },

    MOD("%", 1) {
        public Integer apply(Integer leftArgument, Integer rightArgument) {
            return leftArgument % rightArgument;
        }
    },


    POWER("^", 2) {
        public Integer apply(Integer leftArgument, Integer rightArgument) throws FormOverflowException {
            long res = Math.round(Math.pow((double) leftArgument, (double) rightArgument));
            if (Math.abs(rightArgument) != Math.round(Math.log(res) / Math.abs(Math.log(Math.abs((double) leftArgument)))) || Math.abs(res) > Integer.MAX_VALUE) {
                throw new FormOverflowException("Overflow: " + String.valueOf(leftArgument) + " ^ "
                        + String.valueOf(rightArgument) + " != " + String.valueOf((int) res));
            }
            return (int) res;
        }
    },;

    class FormOverflowException extends FormEvaluationException {

        public FormOverflowException(String message) {
            super(message);
        }
    }

    class FormDBZException extends FormEvaluationException {

        public FormDBZException(String message) {
            super(message);
        }
    }

    public static final int MAX_PRIORITY = 2;
    public static final int MIN_PRIORITY = 0;
    public final String token;
    public final int priority;
    private static final Map<String, Operation> MAP;

    static {
        MAP = new HashMap<>();
        for (Operation op : Operation.values()) {
            MAP.put(op.token, op);
        }
    }

    private Operation(String token, int priority) {
        this.token = token;
        this.priority = priority;
    }

    public static Operation getOperation(String token) {
        return MAP.get(token);
    }

    public static Iterator<String> getKeySetIterator() {
        return MAP.keySet().iterator();
    }

    //    public static boolean containsOperation(String token) {
//        return MAP.containsKey(token);
//    }
    public abstract Integer apply(Integer leftArgument, Integer rightArgument) throws FormEvaluationException;
}
