package ru.skipor.MathLogic.Proof;

import org.junit.Assert;
import org.junit.Test;
import ru.skipor.MathLogic.Form.Form;
import ru.skipor.MathLogic.Form.Parser.FormParser;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/13/14
 * Time: 7:49 PM
 */
public class ProofGeneratorTest {
    @Test
    public void testGenerate() throws Exception {
        final Form form = FormParser.parse("A&!A->(B|C)&!A");
//        final Form form = FormParser.parse("!!A->A");
        Proof proof = ProofGenerator.generate(form);
        Assert.assertEquals(0, proof.check());

    }
}
