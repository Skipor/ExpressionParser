import ru.ifmo.ctddef.Skipor.homeWork5.Form.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormParser { // tail recursive parser
    private final String expression;
    private String currentToken;
    private Matcher formMatcher;
    private static Matcher variableMatcher, constantMatcher;
    private static Pattern formPattern;

    static {
        String variableRegex = "\\w+",
                constantRegex = "\\d+",
                tokenRegex = "(?x)(" + variableRegex + "|" + constantRegex + "| [\\Q(+-/*\\E])";  //(?x)(\\w+ | \\d+ | [\\Q(+-/*\\E])
        formPattern = Pattern.compile(tokenRegex);
        variableMatcher = Pattern.compile(variableRegex).matcher("");
        constantMatcher = Pattern.compile(constantRegex).matcher("");
    }

    private static boolean tokenIsVariable(String token) {
        return variableMatcher.reset(token).matches();
    }

    private static boolean tokenIsConstant(String token) {
        return constantMatcher.reset(token).matches();
    }

    public FormParser(String expression) {
        this.expression = expression;
        formMatcher = formPattern.matcher(expression);
    }

    private void nextToken() {
        if (formMatcher.find()) {
            currentToken = formMatcher.group();
        } else {
            currentToken = "";
        }
    }

    private String extractExpressionInBrackets() { //begin is index of '(' which is begin of Form
        int begin = formMatcher.start();
        int end = begin;
        int count = 1;
        while (count > 0) { //find first ")" without pair
            end++;
            char currentChar = expression.charAt(end);
            if (currentChar == '(') {
                count++;
            }
            if (currentChar == ')') {
                count--;
            }
        }
        if (formMatcher.find(end + 1)) {
            currentToken = formMatcher.group();
        } else {
            currentToken = "";
        }
        return expression.substring(begin + 1, end);

    }

    public Form parse() {
        return summandParse();
    }

    private Form summandParse() {

        Form leftArgument = factorParse();
        while (currentToken.equals("+") || currentToken.equals("-")) {
            boolean isPlus = currentToken.equals("+");
            Form rightArgument = factorParse();
            if (isPlus) {
                leftArgument = new Plus(leftArgument, rightArgument);

            } else {
                leftArgument = new Minus(leftArgument, rightArgument);
            }
        }
        return leftArgument;

    }

    private Form factorParse() {
        Form leftArgument = identifierParse();
        while (currentToken.equals("*") || currentToken.equals("/")) {
            boolean isTimes = currentToken.equals("*");
            Form rightArgument = identifierParse();
            if (isTimes) {
                leftArgument = new Times(leftArgument, rightArgument);

            } else {
                leftArgument = new Division(leftArgument, rightArgument);
            }
        }
        return leftArgument;

    }

    private Form identifierParse() {
        nextToken();
        Form result;
        if (tokenIsConstant(currentToken)) {
            result = new Const(Integer.parseInt(currentToken));
            nextToken();
        } else if (tokenIsVariable(currentToken)) {
            result = new Variable(currentToken);
            nextToken();
        } else if (currentToken.equals("(")) {
            result = new FormParser(extractExpressionInBrackets()).parse();    // get next token
        } else {
            throw new IllegalArgumentException(
                    "Wrong identifier: \"" + currentToken + "\"  in " + expression
            );
        }
        //currentToken is BinaryOperation or empty string
        return result;
    }
}
