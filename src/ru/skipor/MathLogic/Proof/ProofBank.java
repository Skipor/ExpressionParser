package ru.skipor.MathLogic.Proof;

import ru.skipor.MathLogic.Form.Form;
import ru.skipor.MathLogic.Form.FormHelper;
import ru.skipor.MathLogic.Form.Parser.FormParser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* User: Vladimir Skipor
* Email: vladimirskipor@gmail.com
* Date: 9/16/13
* Time: 12:04 AM
*/
public class ProofBank {
    private static Map<String, Proof> proofsByNames = new HashMap<>();
    private static final String NAMED_PROOFS_FILE_NAME = "resourceProofs/namedProofs.txt";

    static {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(NAMED_PROOFS_FILE_NAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        InputStream inputStream = ProofBank.class.getClassLoader().getResourceAsStream(NAMED_PROOFS_FILE_NAME);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            List<Form> statements = new ArrayList<>();
            String lastStatement = "";
            String nextStatement;
            while (reader.ready()) {
                nextStatement = reader.readLine();
                if (!nextStatement.equals("")) {
                    statements.add(FormParser.parse(nextStatement));
                    lastStatement = nextStatement;
                } else {
                    proofsByNames.put(lastStatement, Proof.createProof(statements));
                    statements = new ArrayList<>();
                }
            }
//            System.out.println(lastStatement);
            proofsByNames.put(lastStatement, Proof.createProof(statements));

        } catch (IOException | FormParser.ParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static Proof getProofByName(String name, Form... arguments) {
        List<Form> statements = new ArrayList<>();
        Proof proof = proofsByNames.get(name);
        if (proof == null) {
            throw  new IllegalStateException("No proof founded for " + name);
        }
        List<Form> initialStatements = proof.statements;

        for (Form statement : initialStatements) {
            statements.add(FormHelper.insert(statement, arguments));
        }
        return Proof.createProof(statements);
    }



}
