package ru.skipor.MathLogicParser.Proof;

import ru.skipor.MathLogicParser.Form.Form;
import ru.skipor.MathLogicParser.FormParser;
import ru.skipor.MathLogicParser.ParserException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
    private static Map<String, Proof> proofsByNames = new HashMap<String, Proof>();
    private static Map<Form, Proof> proofsByForms = new HashMap<Form, Proof>();
    public static final String NAMED_POOFS_FILE_NAME = "namedProofs.txt";

    static {
        try (BufferedReader reader = new BufferedReader(new FileReader(NAMED_POOFS_FILE_NAME))) {
            List<Form> statements = new ArrayList<>();
            String nextStatement = "";
            while (reader.ready()) {
                nextStatement = reader.readLine();
                if (!nextStatement.equals("")) {
                    statements.add(FormParser.formParse(nextStatement));
                } else {
                    proofsByNames.put(nextStatement, new Proof(statements));
                    statements = new ArrayList<>();
                }
            }
            proofsByNames.put(nextStatement, new Proof(statements));

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }



}
