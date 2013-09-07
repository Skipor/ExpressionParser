package ru.ifmo.ctddef.Skipor.homeWork5;

import ru.ifmo.ctddef.Skipor.homeWork5.Form.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.ifmo.ctddef.Skipor.homeWork5.Form.Operation.*;

public class FormParser { // tail recursive parser
    private String expression;
    private String currentToken;
    private Matcher formMatcher;
    private static Matcher variableMatcher;
    private static Matcher constantMatcher;
    private static Matcher valuesMatcher;
    private static Pattern formPattern;


    static {
        StringBuilder binaryOperationBuilder = new StringBuilder();
        Iterator<String> iterator = getKeySetIterator();
        while (iterator.hasNext()) {
            binaryOperationBuilder.append("\\Q");
            binaryOperationBuilder.append(iterator.next());
            binaryOperationBuilder.append("\\E");
            binaryOperationBuilder.append('|');
        }
        String variableRegex = "[a-z_A-Z]+",
                constantRegex = "[0-9]+",//"\\d+(\\.\\d+)?",
                operationRegex = "[-+()]",
                binaryOperationRegex = binaryOperationBuilder.toString(), // empty, or have '|' at the end
                tokenRegex = "(?x)(" + operationRegex + "|" + binaryOperationRegex + variableRegex + "|" + constantRegex + " | .??$)",
                valuesRegex = variableRegex + "|" + "[+-]?" + constantRegex;
        //.??$ empty string, if it is end of expression. "" and ')' are exit tokens
        formPattern = Pattern.compile(tokenRegex);
        variableMatcher = Pattern.compile(variableRegex).matcher("");
        constantMatcher = Pattern.compile(constantRegex).matcher("");
        valuesMatcher = Pattern.compile(valuesRegex).matcher("");
    }

    static class IllegalExpressionFormatException extends ParserException {
        public IllegalExpressionFormatException(String message) {
            super(message);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }

    static class UnknownTokenFoundedException extends ParserException {
        public UnknownTokenFoundedException(String message) {
            super(message);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }

    static class WrongVariableFormatException extends ParserException {
        public WrongVariableFormatException(String message, Exception cause) {
            super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
        }

        public WrongVariableFormatException(String message) {
            super(message);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }

    public static VariablesValues variablesParse(String expression) throws ParserException {
        Map<String, Integer> variablesValues = new HashMap<>();
        valuesMatcher.reset(expression);
        while (valuesMatcher.find()) {
            String name = valuesMatcher.group();
            if (!valuesMatcher.find()) {
                throw new WrongVariableFormatException("No value, for " + name + " founded.");
            }

            String value = valuesMatcher.group();

            try {
                variablesValues.put(name, Integer.valueOf(value));
            } catch (NumberFormatException e) {
                throw new WrongVariableFormatException("Wrong value format.", e);
            }

        }

        return new VariablesValues(variablesValues);
    }

    public static Form formParse(String expression) throws ParserException {
        FormParser parser = new FormParser(expression);
        Form result = parser.operationParse(MIN_PRIORITY);
        if (!parser.currentToken.equals("")) {
            if (parser.currentToken.equals(")")) {
                throw new IllegalExpressionFormatException("Unexpected ')' founded in \"" + expression + "\""
                        + " at " + String.valueOf(parser.formMatcher.start()));
            } else {
                throw new UnknownTokenFoundedException("Unexpected token \"" + parser.currentToken + "\" founded in \"" + expression
                        + "\" at " + String.valueOf(parser.formMatcher.start()));
            }
        }
        return result;
    }

    private FormParser(String expression) {
        this.expression = expression;
        formMatcher = formPattern.matcher(expression);
    }

    private boolean tokenIsVariable() {
        return variableMatcher.reset(currentToken).matches();
    }

    private boolean tokenIsConstant() {
        return constantMatcher.reset(currentToken).matches();
    }

    private void nextToken() {
        formMatcher.find();
        currentToken = formMatcher.group();
    }

    private Form operationParse(int priority) throws ParserException {
        Form leftArgument;
        if (priority != MAX_PRIORITY) {
            leftArgument = operationParse(priority + 1);
        } else {
            leftArgument = identifierParse();
        }
        for (Operation operation = getOperation(currentToken);
             operation != null && operation.priority == priority;
             operation = getOperation(currentToken)) {

            Form rightArgument;
            if (priority != MAX_PRIORITY) {
                rightArgument = operationParse(priority + 1);
            } else {
                rightArgument = identifierParse();
            }
            leftArgument = new BinaryOperation(leftArgument, rightArgument, operation);
        }
        return leftArgument;
    }


    private Form identifierParse() throws ParserException {
        nextToken();
        Form result;
        if (tokenIsConstant()) {
            result = new Const(Integer.valueOf(currentToken));
            nextToken();
        } else if (tokenIsVariable()) {
            result = new Variable(currentToken);
            nextToken();
        } else if (currentToken.equals("(")) {
            result = operationParse(MIN_PRIORITY);
            if (!currentToken.equals(")")) {
                if (currentToken.equals("")) {
                    throw new IllegalExpressionFormatException("Matching bracket is not founded in \"" + expression + "\""
                            + " at " + String.valueOf(formMatcher.start()));

                } else {
                    throw new UnknownTokenFoundedException("Unexpected token \"" + currentToken + "\" founded in \"" + expression
                            + "\" at " + String.valueOf(formMatcher.start()));
                }
            }
            nextToken();
        } else if (currentToken.equals("-")) {
            result = new BinaryOperation(new Const(-1), operationParse(MAX_PRIORITY - 1), TIMES);
        } else if (currentToken.equals("+")) {
            result = identifierParse();
        } else {
            throw new IllegalExpressionFormatException(
                    "Unexpected identifier found: \"" + currentToken + "\" in \"" + expression + "\""
                            + " at " + String.valueOf(formMatcher.start())
            );
        }
        //currentToken is BinaryOperation or exit token.
        return result;
    }
}
