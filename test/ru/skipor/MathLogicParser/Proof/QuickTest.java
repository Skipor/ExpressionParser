package ru.skipor.MathLogicParser.Proof;

import org.junit.Test;
import ru.skipor.MathLogicParser.Form.Form;
import ru.skipor.MathLogicParser.Form.FormInserter;
import ru.skipor.MathLogicParser.Form.Variable;
import ru.skipor.MathLogicParser.FormParser;

import static org.junit.Assert.assertEquals;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/6/14
 * Time: 1:31 PM
 */
public class QuickTest {
    @Test
    public void testInsert() throws Exception {

//        assertEquals();
        String base = "f->o->f&p|p";
        Form baseForm = FormParser.formParse(base);
        Form insertedForm = FormInserter.insert(baseForm, new Variable("B"), new Variable("C"), new Variable("D"));
        assertEquals(insertedForm, FormParser.formParse("B->C->B&D|D"));


    }

    @Test
    public void testProofBank() throws Exception {
        Proof testProof = new ProofBank().getProofByName("f->f", new Variable("X"));
        assertEquals(testProof.check(), 0);
        assertEquals(testProof.statements.get(testProof.statements.size() - 1), FormParser.formParse("X->X"));
    }

    @Test
    public void isAxiom() throws Exception {
        assertEquals(true, AxiomsSystems.isAxiom(FormParser.formParse("(A->A|!A)->(!(A|!A)->(A->A|!A))")));
    }


}
