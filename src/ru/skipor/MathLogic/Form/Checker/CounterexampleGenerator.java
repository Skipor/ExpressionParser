package ru.skipor.MathLogic.Form.Checker;

import ru.skipor.MathLogic.Form.*;
import ru.skipor.MathLogic.Proof.Proof;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/8/14
 * Time: 4:58 PM
 */
public class CounterexampleGenerator {


    private final Form form;
    private final Variable[] variables;
    private final boolean[] values;
    private final int variablesCount;
    public static final List<List<Form>> formArgumentProofs;


    static int getProofIndex(BinaryOperation binaryOperation, boolean left, boolean right) {
        int index = 0;
        switch (binaryOperation)
        {
//            case ENTAILMENT: index |=0;
//                break;
            case DISJUNCTION: index|=0x4;
                break;
            case CONJUNCTION: index|=0x8;
                break;

        }
        if (left) {
            index|=0x2;
        }
        if (right) {
            index|=0x1;
        }
        return index;
    }

    static int getProofIndex(UnaryOperation unaryOperation, boolean argument) {

        int index = 0;
//        switch (unaryOperation) {
//            case NEGATION:
                index |= 0xC;
//                break;
//        }
        if (argument) {
            index|=1;
        }
        return index;
    }
//    static Proof getProofFromArgument(Form form) {
//        int index;
//        if (form instanceof BinaryNode) {
//            return
//        }
//    }

    static {
        formArgumentProofs = new ArrayList<>(14);
        try {
            try (BufferedReader binaryReader = new BufferedReader(new FileReader("binaryCustomNamedProofs.txt"));
                 BufferedReader unaryReader = new BufferedReader(new FileReader(("unaryCustomNamedProofs.txt")))) {
                ArrayList<Form> proof = null;
                Set<Form> argumetns = new HashSet<>(4);
                String currentLine = null;
                while ((currentLine = binaryReader.readLine()) != null) {
                    if (currentLine == "") {
                        proof = null;
                        argumetns.clear();
                        continue;
                    }
                    if (proof == null) {
                        proof = new ArrayList<>();
                        String[] header = currentLine.split(Proof.HEADER_SPLIT_REGEX);


                    }

                }




            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static List<Variable> generate(Form form) {
        return new CounterexampleGenerator(form).generate();
    }

    public CounterexampleGenerator(Form form) {
        this.form = form;
        variables = FormHelper.getVariables(form);
        variablesCount = variables.length;
        values = new boolean[variablesCount];

    }


    List<Variable> generate() {
        for (int i = 0; i < variablesCount; i++) {
            setValue(i, false);
        }
        int p = variablesCount - 1;
        while (true) {
            boolean result = form.evaluate(null);
//            for (boolean b : values) {
//                System.out.print(b);
//                System.out.print(' ');
//            }
//
//            System.out.println(" result : " + result);
            if (result) {

                while (p >= 0 && values[p]) {
                    p--;
                }
                if (p < 0) {
                    return null;
                }
                setValue(p, true);

                while (p < (variablesCount - 1)) {
                    p++;
                    setValue(p, false);
                }

            } else {

                return Arrays.asList(variables);
            }
        }

    }

    private void setValue(int index, boolean value) {
        variables[index].setValue(value);
        values[index] = value;
    }
}
