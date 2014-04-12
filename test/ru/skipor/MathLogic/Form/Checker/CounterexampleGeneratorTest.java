package ru.skipor.MathLogic.Form.Checker;

import org.junit.Test;
import ru.skipor.MathLogic.Form.Parser.FormParser;
import ru.skipor.MathLogic.Form.Variable;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/8/14
 * Time: 7:33 PM
 */

public class CounterexampleGeneratorTest {

    @Test
    public void testGenerate() throws Exception {

        assertEquals(null, CounterexampleGenerator.generate(FormParser.parse("(!B->!A)->(A->B)")));
        assertEquals(null, CounterexampleGenerator.generate(FormParser.parse("((C->(!B->(!A->(!((A&!A)->((B|C)&!A))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))))))->(C->((!B->(!A->(!((A&!A)->((B|C)&!A))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))))->((!B->((!A->(!((A&!A)->((B|C)&!A))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))))->((!A->((!((A&!A)->((B|C)&!A))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))->((!((A&!A)->((B|C)&!A))->((!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))->!((A&!A)->((B|C)&!A))))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))))->(!A->((!((A&!A)->((B|C)&!A))->((!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))->!((A&!A)->((B|C)&!A))))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))))))->(!B->((!A->((!((A&!A)->((B|C)&!A))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))->((!((A&!A)->((B|C)&!A))->((!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))->!((A&!A)->((B|C)&!A))))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))))->(!A->((!((A&!A)->((B|C)&!A))->((!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))->!((A&!A)->((B|C)&!A))))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))))))))->(C->((!B->((!A->(!((A&!A)->((B|C)&!A))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))))->((!A->((!((A&!A)->((B|C)&!A))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))->((!((A&!A)->((B|C)&!A))->((!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))->!((A&!A)->((B|C)&!A))))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))))->(!A->((!((A&!A)->((B|C)&!A))->((!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))->!((A&!A)->((B|C)&!A))))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))))))->(!B->((!A->((!((A&!A)->((B|C)&!A))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))->((!((A&!A)->((B|C)&!A))->((!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))->!((A&!A)->((B|C)&!A))))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))))->(!A->((!((A&!A)->((B|C)&!A))->((!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))->!((A&!A)->((B|C)&!A))))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))))))))")));
       List<Variable> variableList = CounterexampleGenerator.generate( FormParser.parse("(A->!B)->(!B->!A)"));

        Collections.sort(variableList);

        assertArrayEquals(new Variable[]{new Variable("A"), new Variable("B")}, variableList.toArray(new Variable[2]));




    }

    @Test
    public void testName() throws Exception {

    }
}
