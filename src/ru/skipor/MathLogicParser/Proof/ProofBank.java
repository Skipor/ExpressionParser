package ru.skipor.MathLogicParser.Proof;

import ru.skipor.MathLogicParser.Form.Form;
import ru.skipor.MathLogicParser.Form.FormInserter;
import ru.skipor.MathLogicParser.FormParser;
import ru.skipor.MathLogicParser.ParserException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    public static final String NAMED_PROOFS_FILE_NAME = "resourceProofs/namedProofs.txt";

    static {
        try (BufferedReader reader = new BufferedReader(new FileReader(NAMED_PROOFS_FILE_NAME))) {
            List<Form> statements = new ArrayList<>();
            String lastStatement = "";
            String nextStatement;
            while (reader.ready()) {
                nextStatement = reader.readLine();
                if (!nextStatement.equals("")) {
                    statements.add(FormParser.formParse(nextStatement));
                    lastStatement = nextStatement;
                } else {
                    proofsByNames.put(lastStatement, new Proof(statements));
                    statements = new ArrayList<>();
                }
            }
//            System.out.println(lastStatement);
            proofsByNames.put(lastStatement, new Proof(statements));

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public  Proof getProofByName(String name, Form ... arguments) {
        List<Form> statements = new ArrayList<>();
        Proof proof = proofsByNames.get(name);
        if (proof == null) {
            throw  new IllegalStateException("No proof founded for " + name);
        }
        List<Form> initialStatements = proof.statements;

        for (Form statement : initialStatements) {
            statements.add(FormInserter.insert(statement, arguments));
        }
        return new Proof(statements);
    }



}
