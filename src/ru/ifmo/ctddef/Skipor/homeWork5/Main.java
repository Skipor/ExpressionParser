package ru.ifmo.ctddef.Skipor.homeWork5;

import ru.ifmo.ctddef.Skipor.homeWork5.Form.Form;
import ru.ifmo.ctddef.Skipor.homeWork5.Form.FormEvaluationException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParserException, FormEvaluationException {
//        int[] valuesOfArguments = {10, 20, 30};
//        final String[] namesOfArguments = {"x", "y", "z"};
//        final int numberOfArguments = namesOfArguments.length;
//        Map<String, Integer> arguments = new HashMap<>(numberOfArguments);
//        for (int i = 0; i < numberOfArguments; ++i) {
//            arguments.put(namesOfArguments[i], valuesOfArguments[i]);
//        }

        Scanner scanner = new Scanner(System.in);
        Form form;
        while (true) {
            System.out.print("Enter an expression: ");
            try {
                String expression = scanner.nextLine();
                form = FormParser.formParse(expression);
            } catch (ParserException e) {
                System.out.println(e.getMessage());
                System.out.println("Try one more.");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("Enter an variables and their vales: ");
            VariablesValues values;
            try {
                String expression = scanner.nextLine();
                values = FormParser.variablesParse(expression);
            } catch (ParserException e) {
                System.out.println(e.getMessage());
                System.out.println("Try one more.");
                continue;
            }

            try {
                System.out.println(form.evaluate(values));
            } catch (FormEvaluationException e) {
                System.out.println(e.getMessage());
                System.out.println("Try one more.");
                continue;
            }

        }

    }

}


////
////        StringBuilder sb = new StringBuilder();
////        for(int i = 0; i < args.length; i++) {
////            sb.append(args[i]);
////        }
////        String expression = sb.toString();
//        String expression = "((((0-10-10)))+(10-4+(x*(x+1)))/1+((z)-y)-(2)+y*y)^6";//  +  y/y + (z+y)/(z+y)";
////        String expression = "x - x";
//        Form form = FormParser.formParse(expression);
//        System.out.println(
//                form.evaluate(arguments)
//        );
//
//
//    }

//
//StringBuilder builder = new StringBuilder();
//for (int i = 0; i < args.length; i++) {
//        builder.append(args[i]);
//}
//        String expression = builder.toString();
//expression = "(((-0-10 - +10))) +(10 - 4 + (x * (x + 1)))/-1 + (((z) - y)) - (-2)^4 + y*y + 0 - 0"; // test
//Form form = FormParser.formParse(expression);
//form = form.simplify();
//System.out.println(form.toString());

            /*

            int[] valuesOfArguments = new int[numberOfArguments];
            int argumentIndex = 0;
            for (int i = 0; i < args.length; i++){
            String[] splitedArgs = args[i].split("\\s");
            for (int j = 0; j < splitedArgs.length; j++){
            if(splitedArgs[j].length() > 0){
            valuesOfArguments[argumentIndex++] =
            }
            }
            }
            */
