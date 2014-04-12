package ru.skipor.MathLogic.Tasks;

import ru.skipor.MathLogic.Form.Form;
import ru.skipor.MathLogic.Form.Parser.FormParser;
import ru.skipor.MathLogic.Proof.Proof;
import ru.skipor.MathLogic.Proof.ProofBank;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/10/14
 * Time: 12:52 PM
 */
public class Gen {

    public static void main(String[] args) throws Exception{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resourceProofs/cp.out.txt"))) {

            StringBuilder builder = new StringBuilder();
            Form f = FormParser.parse("f");
            Form fOrNotf = FormParser.parse("f|!f");

            builder.append("f->f|!f").append("\n");
            builder.append(ProofBank.getProofByName("(f->o)->(!o->!f)", f, fOrNotf)).append("\n");
            builder.append("!(f|!f)->!f").append("\n");

            Form notf = FormParser.parse("!f");
            builder.append("!f->f|!f").append("\n");
            builder.append(ProofBank.getProofByName("(f->o)->(!o->!f)", notf, fOrNotf)).append("\n");
            builder.append("!(f|!f)->!!f").append("\n");
            writer.write(builder.toString());
        }

        Proof proof = new Proof("resourceProofs/cp.out.txt");
        assert (proof.check() == 0);
    }
}
