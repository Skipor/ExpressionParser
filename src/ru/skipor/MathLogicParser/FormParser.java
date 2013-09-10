package ru.skipor.MathLogicParser;

import ru.skipor.MathLogicParser.Form.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.skipor.MathLogicParser.Form.BinaryOperation.*;
import static ru.skipor.MathLogicParser.Form.UnaryOperation.*;

public class FormParser { // tail recursive parser
    private String expression;
    private String currentToken;
    private Matcher formMatcher;
    private static Matcher variableMatcher;
    private static Pattern formPattern;


    static {

        String variableRegex = "[A-Z]",
                operationRegex = "[|&!()] | ->",
                tokenRegex = "(?x)(" + operationRegex + "|" + variableRegex + " | .??$)";
        //.??$ empty string, if it is end of expression. "" and ')' are exit tokens
        formPattern = Pattern.compile(tokenRegex);
        variableMatcher = Pattern.compile(variableRegex).matcher("");
//        formPattern = Pattern.compile("[A-Z]");
//        variableMatcher = Pattern.compile("[A-Z]").matcher("");
    }

    static class IllegalExpressionFormatException extends ParserException {
        public IllegalExpressionFormatException(String message) {
            super(message);
        }
    }

    static class UnknownTokenFoundedException extends ParserException {
        public UnknownTokenFoundedException(String message) {
            super(message);
        }
    }


    public static Form formParse(String expression) throws ParserException {
        FormParser parser = new FormParser(expression);
        Form result = parser.entailmentParse();
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

    private void nextToken() {
        formMatcher.find();
        currentToken = formMatcher.group();
    }


    private Form entailmentParse() throws ParserException {

        Form result = disjunctionParse();
        if (currentToken.equals("->")) {
            result = new BinaryNode(result, entailmentParse(), ENTAILMENT);
        }

        return result;


    }
    private Form disjunctionParse() throws ParserException {
        Form leftArgument = conjunctionParse();

        while (currentToken.equals("|")){

            Form rightArgument = conjunctionParse();

            leftArgument = new BinaryNode(leftArgument, rightArgument, DISJUNCTION);
        }
        return leftArgument;
    }

    private Form conjunctionParse() throws ParserException {

        Form leftArgument = identifierParse();

        while (currentToken.equals("&")){

            Form rightArgument = identifierParse();

            leftArgument = new BinaryNode(leftArgument, rightArgument, CONJUNCTION);
        }
        return leftArgument;
    }



    private Form identifierParse() throws ParserException {
        nextToken();
        Form result;
        if (tokenIsVariable()) {
            result = new Variable(currentToken);
            nextToken();
        } else if (currentToken.equals("(")) {
            result = entailmentParse();
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
       } else if (currentToken.equals("!")) {
            result = new UnaryNode(identifierParse(), NEGATION);
        } else {
            throw new IllegalExpressionFormatException(
                    "Unexpected identifier found: \"" + currentToken + "\" in \"" + expression + "\""
                            + " at " + String.valueOf(formMatcher.start())
            );
        }
        //currentToken is UnaryNode or exit token.
        return result;
    }
}