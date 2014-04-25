package ru.skipor.MathLogic.Proof;

import ru.skipor.MathLogic.Form.*;
import ru.skipor.MathLogic.Form.Checker.CounterexampleGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/10/14
 * Time: 3:51 PM
 */
public class ProofGenerator {


    Variable[] variables;
    int variablesCount;
    Form formToProof;
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

    static int getProofIndex(UnaryOperation
                                     unaryOperation, boolean argument) {

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
//    static Proof getProofFromArgument(Form formToProof) {
//        int index;
//        if (formToProof instanceof BinaryNode) {
//            return
//        }
//    }

    public static final int PROOFS_FROM_ARGS_CNT = 14;

    static {
        formArgumentProofs = new ArrayList<>(PROOFS_FROM_ARGS_CNT);
        for (int i = 0; i < PROOFS_FROM_ARGS_CNT; i++) {
            formArgumentProofs.add(null);
        }
        try {
            final String fileName = "resourceProofs/CustomNamedProofs.txt";
            InputStream inputStream = new FileInputStream(fileName);
//            InputStream inputStream = ProofGenerator.class.getClassLoader().getResourceAsStream(fileName);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
            ) {
                String currentLine;
                StringBuilder stringBuilder = new StringBuilder();
                while (true) {
                    currentLine = reader.readLine();
                    if (currentLine == null || currentLine.equals("")) {
                        if (stringBuilder != null) {
                            Proof proof = Proof.createProof(new StringReader(stringBuilder.toString()));
//                            assert (proof.check() == 0); // todo remove
                            Form proving = proof.proving;
                            int index;
                            if (proof.assumptions.size() == 2) {
                                if (proving instanceof UnaryNode) {
                                    proving = ((UnaryNode) proving).argument;
                                }
                                Form left = proof.assumptions.get(0);
                                Form right = proof.assumptions.get(1);
                                index = getProofIndex(((BinaryNode) proving).operation,
                                        !(left instanceof UnaryNode),
                                        !(right instanceof UnaryNode));
                            } else {
                                if (((UnaryNode) proving).argument instanceof UnaryNode) {
                                    proving = ((UnaryNode) proving).argument;
                                }
                                Form arg = proof.assumptions.get(0);
                                index = getProofIndex(((UnaryNode) proving).operation, !(arg instanceof UnaryNode));
                            }
                            proof.statements.removeAll(proof.assumptions);
                            formArgumentProofs.set(index, proof.statements);
                        }

                        if (currentLine == null) {
                            break;
                        } else {
                            stringBuilder = null;
                        }
                    } else {
                        if (stringBuilder == null) {
                            stringBuilder = new StringBuilder();
                        }

                        stringBuilder.append(currentLine).append("\n");
                    }
                }

                int count = 0;
                int i = 0;
//                for (List<Form> forms : formArgumentProofs) { //todo remove
//
//
//                    if (forms == null) {
//                        System.err.println(i);
//                        count++;
//                    }
//
//
//                    i++;
//                }
//                assert (count == 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Proof generate(Form form) {
        return new ProofGenerator(form).generate();
    }
    public Proof generate() {
        return generate(0);
    }
    public ProofGenerator(Form form) {
        if (CounterexampleGenerator.generate(form) != null) {
            throw new IllegalArgumentException("Expression has counterexample, and can not be proved");
        }
        this.formToProof = form;
        variables = FormHelper.getVariables(form);
        Arrays.sort(variables);
        variablesCount = variables.length;
    }

    private Proof generate(int currentVariableNumber) {
        if (currentVariableNumber < variablesCount) {
            variables[currentVariableNumber].setValue(true);
            Proof left = generate(currentVariableNumber + 1);
            variables[currentVariableNumber].setValue(false);
            Proof right = generate(currentVariableNumber + 1);
//            System.out.println("left");
//            System.out.println(left.toString(true));
//
//            System.out.println("rignt");
//            System.out.print(right.toString(true));
            return Proof.concat(left, right);
        } else {
            List<Form> statements = new ArrayList<>();
            formToProof.evaluate(null);
            for (Variable var : variables) {
                if (var.getValue()) {
                    statements.add(var);
                } else {
                    statements.add(new UnaryNode(var, UnaryOperation.NEGATION));
                }

            }
            ArrayList<Form> assumptions = new ArrayList<>(statements);

            addEvaluationProof(formToProof, statements);

            return Proof.createProof(statements, assumptions);
        }

    }

    private void addEvaluationProof(Form form, List<Form> statements) {
        if (form instanceof BinaryNode) {
            addEvaluationProof(((BinaryNode) form).leftArgument, statements);
            addEvaluationProof(((BinaryNode) form).rightArgument, statements);
            List<Form> proofList = formArgumentProofs.get(
                    getProofIndex(((BinaryNode) form).operation
                            , ((BinaryNode) form).leftArgument.getValue()
                            , ((BinaryNode) form).rightArgument.getValue())
            );

            for (Form statement : proofList) {
                statements.add(
                        FormHelper.insert(statement
                                , ((BinaryNode) form).leftArgument
                                , ((BinaryNode) form).rightArgument)
                );
            }

        } else if (form instanceof UnaryNode) {
            addEvaluationProof(((UnaryNode) form).argument, statements);
            List<Form> proofList = formArgumentProofs.get(
                    getProofIndex(((UnaryNode) form).operation
                            , ((UnaryNode) form).argument.getValue())
            );

            for (Form statement : proofList) {
                statements.add(
                        FormHelper.insert(statement
                                , ((UnaryNode) form).argument)
                );
            }

        } else if (form instanceof Variable) {
            return;
        } else {
            throw new IllegalArgumentException();
        }
    }


}
