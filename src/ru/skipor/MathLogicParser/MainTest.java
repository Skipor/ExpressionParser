package ru.skipor.MathLogicParser;

import ru.skipor.MathLogicParser.Form.Form;
import ru.skipor.MathLogicParser.Form.FormInserter;
import ru.skipor.MathLogicParser.Form.Variable;
import ru.skipor.MathLogicParser.Proof.Proof;
import ru.skipor.MathLogicParser.Proof.ProofBank;
import ru.skipor.MathLogicParser.Proof.ProofChecker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static ru.skipor.MathLogicParser.Form.FormInserter.*;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 9/24/13
 * Time: 1:27 PM
 */
public class MainTest {




        public static void main(String[] args) throws Exception {

            try(
                    BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
                    BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))
            ) {

                String base = "f->o->f&p|p";
                Form baseForm = FormParser.formParse(base);
                Form firstForm = FormParser.formParse(reader.readLine());
                Form secondForm = FormParser.formParse(reader.readLine());
                Form thirdForm = FormParser.formParse(reader.readLine());
//                writer.write(FormInserter.insert(baseForm, firstForm, secondForm, thirdForm).toString());
                writer.write(FormInserter.insert(baseForm, new Variable("B"), new Variable("C"), new Variable("D")).toString() + "\n");

                Proof testProof = new ProofBank().getProofByName("f->f", firstForm);
                for (Form form : testProof.statements) {
                    writer.write(form.toString() + '\n');

                }



            }

    }
}

