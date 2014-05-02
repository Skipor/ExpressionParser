package ru.skipor.MathLogic.Form.Parser;

import ru.skipor.MathLogic.Form.Form;
import ru.skipor.MathLogic.Form.Logical.BinaryNode;
import ru.skipor.MathLogic.Form.Logical.QuantifierNode;
import ru.skipor.MathLogic.Form.Logical.QuantifierOperation;
import ru.skipor.MathLogic.Form.Logical.UnaryNode;
import ru.skipor.MathLogic.Form.Predicate.BinaryPredicate;
import ru.skipor.MathLogic.Form.Predicate.Predicate;
import ru.skipor.MathLogic.Form.Term.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.skipor.MathLogic.Form.Logical.BinaryOperation.*;
import static ru.skipor.MathLogic.Form.Logical.UnaryOperation.NEGATION;

public class FormParser { // tail recursive parser
    private final String expression;
    private String currentToken;
    private final Matcher formMatcher;
    private final static Matcher variableMatcher, predicateMatcher;
    private final static Pattern formPattern;


    public static final String CORRECT_EXIT_TOKEN = "";
    public static final String ERROR_TOKEN = "error";
    private Term savedTerm = null;

    static {

        String variableRegex = "[a-z][0-9]*", constantRegex = "0", predicateRegex = "[A-Z][0-9]*", operationRegex = "[|&!(),+=*'@?]|->", tokenRegex = "(?x)("
                + predicateRegex
//                + "|" + functionRegex
                + "|" + constantRegex
                + "|" + operationRegex +
                "|" + variableRegex + "|.??$)";
        //.??$ empty string, if it is end of expression. ',',  "" and ')' are exit tokens
        formPattern = Pattern.compile(tokenRegex);
        variableMatcher = Pattern.compile(variableRegex).matcher(CORRECT_EXIT_TOKEN);
        predicateMatcher = Pattern.compile(predicateRegex).matcher(CORRECT_EXIT_TOKEN);

    }


    public static List<Form> parseForms(String expressions) throws ParserException {
        FormParser parser = new FormParser(expressions);
        List<Form> results = new ArrayList<>();

        do {
            results.add(parser.formParse());
//            if (!(parser.currentToken.equals(CORRECT_EXIT_TOKEN) || parser.currentToken.equals(","))) {
//                throw new UnexpectedTokenFoundedException(parser);
//            }


        } while (!parser.currentToken.equals(CORRECT_EXIT_TOKEN) && !parser.currentToken.equals(" "));
        return results;


    }

    public static Form parse(String expression) throws ParserException {
        FormParser parser = new FormParser(expression);
        Form result = parser.formParse();
        if (!parser.currentToken.equals(CORRECT_EXIT_TOKEN)) {
            if (parser.currentToken.equals(")")) {
                throw new IllegalExpressionFormatException("Unexpected ')' founded in \"" + expression + "\""
                        + " at " + String.valueOf(parser.formMatcher.start()));
            } else {
                throw new UnexpectedTokenFoundedException(parser);
            }
        }
        return result;
    }

    public static Term parseTerm(String expression) throws ParserException {
        FormParser parser = new FormParser(expression);
        parser.nextToken();
        Term result = parser.termParse();
        if (!parser.currentToken.equals(CORRECT_EXIT_TOKEN)) {
            if (parser.currentToken.equals(")")) {
                throw new IllegalExpressionFormatException("Unexpected ')' founded in \"" + expression + "\""
                        + " at " + String.valueOf(parser.formMatcher.start()));
            } else {
                throw new UnexpectedTokenFoundedException(parser);
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

    private boolean tokenIsPredicate() {
        return predicateMatcher.reset(currentToken).matches();
    }


    private void nextToken() {
        formMatcher.find();
        currentToken = formMatcher.group();
    }


    private Form formParse() throws ParserException {

        Form result = disjunctionParse();
        if (currentToken.equals("->")) {
            result = new BinaryNode(result, formParse(), ENTAILMENT);
        }
        return result;
    }

    private Form disjunctionParse() throws ParserException {
        Form leftArgument = conjunctionParse();
        while (currentToken.equals("|")) {
            Form rightArgument = conjunctionParse();
            leftArgument = new BinaryNode(leftArgument, rightArgument, DISJUNCTION);
        }
        return leftArgument;
    }

    private Form conjunctionParse() throws ParserException {
        Form leftArgument = unaryParse();
        while (currentToken.equals("&")) {
            Form rightArgument = unaryParse();
            leftArgument = new BinaryNode(leftArgument, rightArgument, CONJUNCTION);
        }
        return leftArgument;
    }


    private Form unaryParse() throws ParserException {
        nextToken();
        Form result;
        if (currentToken.equals("(")) {
            result = formParse();
            if (result == null) { // there is term in brackets, not form
                result = predicateParse();
            } else {
                if (!currentToken.equals(")")) {
                    if (currentToken.equals(CORRECT_EXIT_TOKEN)) {
                        throw new MatchingBracketIsNotFoundedException(this);

                    } else {
                        throw new UnexpectedTokenFoundedException(this);
                    }
                }
                nextToken();
            }
        } else if (currentToken.equals("!")) {
            result = new UnaryNode(unaryParse(), NEGATION);
        } else if (currentToken.equals(QuantifierOperation.EXISTENTIAL.token)
                || currentToken.equals(QuantifierOperation.UNIVERSAL.token)) {
            QuantifierOperation operation = currentToken.equals(QuantifierOperation.EXISTENTIAL.token)
                    ? QuantifierOperation.EXISTENTIAL
                    : QuantifierOperation.UNIVERSAL;
            nextToken();
            if (tokenIsVariable()) {

                result = new QuantifierNode(operation, new Variable(currentToken), unaryParse());
            } else {
                throw new IllegalExpressionFormatException("No variable after quantifier in \"" + expression
                        + "\"" + " at " + String.valueOf(formMatcher.start(formMatcher.start())));
            }

        } else {
            result = predicateParse();
        }
        //currentToken is UnaryNode or exit token.
        return result;
    }

    private Predicate predicateParse() throws ParserException {
        if (tokenIsPredicate()) {
            String name = currentToken;
            List<Term> terms = new ArrayList<>(4);
            nextToken();
            if (currentToken.equals("(")) {
                do {
                    nextToken();
//                    if(currentToken.equals(")")) break;
                    terms.add(termParse());
                } while (currentToken.equals(","));
                if (currentToken.equals(")")) {
                    nextToken();
                } else {
                    throw new MatchingBracketIsNotFoundedException(this);
                }
            }
            return new Predicate(name, terms);
        }

        Term left = termParse();
//        System.out.println(left);
//        System.out.println(currentToken);
        if (currentToken.equals("=")) {

            nextToken();
            return new BinaryPredicate("=", left, termParse());

        } else if (currentToken.equals(")")) {

            savedTerm = left;
            currentToken = ERROR_TOKEN;
            return null;


        } else {
            throw new UnexpectedTokenFoundedException(this);
        }

    }

    private Term termParse() throws ParserException {
        Term leftArgument = summandParse();
        while (currentToken.equals("+")) {
            nextToken();
            leftArgument = new BinaryFunction("+", leftArgument, summandParse());
        }
        return leftArgument;
    }

    private Term summandParse() throws ParserException {
        Term leftArgument = factorParse();
        while (currentToken.equals("*")) {
            nextToken();
            leftArgument = new BinaryFunction("*", leftArgument, factorParse());
        }
        return leftArgument;
    }

    private Term factorParse() throws ParserException {
        Term result;
        if (currentToken.equals("(")) {
            nextToken();
            result = termParse();
//            System.out.println(result);
//            System.out.println(currentToken);
            if (currentToken.equals(")")) {
                nextToken();
//                System.out.println(currentToken);
            } else {
                throw new MatchingBracketIsNotFoundedException(this);
            }
        } else if (tokenIsVariable()) {
            String name = currentToken;
            nextToken();
            if (currentToken.equals("(")) {
                List<Term> terms = new ArrayList<>();
                do {
                    nextToken();
                    if (currentToken.equals(")")) break; // if no arguments
                    terms.add(termParse());
                } while (currentToken.equals(","));
                nextToken();
                result = new Function(name, terms);

            } else {
                result = new Variable(name);
            }
        } else if (currentToken.equals("0")) {
            result = Constant.ZERO;
            nextToken();
        } else if (currentToken.equals(ERROR_TOKEN)) {
            result = savedTerm;

            savedTerm = null;
            nextToken();


        } else {

            throw new UnexpectedTokenFoundedException(this);
        }

        while (currentToken.equals("'")) {

            nextToken();
            result = new UnaryFunction("'", result);
        }

        return result;


    }


    public static class ParserException extends Exception {
        public ParserException(String message) {
            super(message);
        }

        public ParserException(String message, Exception cause) {
            super(message, cause);
        }
    }


    static class IllegalExpressionFormatException extends ParserException {
        public IllegalExpressionFormatException(String message) {
            super(message);
        }
    }

    static class MatchingBracketIsNotFoundedException extends IllegalExpressionFormatException {
        public MatchingBracketIsNotFoundedException(FormParser parser) {
            super("Matching bracket is not founded in \"" + parser.expression + "\""
                    + " at " + String.valueOf(parser.formMatcher.start()));
        }
    }

    static class UnexpectedTokenFoundedException extends ParserException {
        public UnexpectedTokenFoundedException(String message) {
            super(message);
        }

        public UnexpectedTokenFoundedException(FormParser parent) {
            this("Unexpected token \"" + parent.currentToken + "\" founded in \"" + parent.expression
                    + "\" at " + String.valueOf(parent.formMatcher.start()));
        }
    }
}
