package ru.skipor.MathLogicParser.Proof;

import static junit.framework.Assert.*;
import org.junit.Test;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/6/14
 * Time: 2:54 PM
 */
public class ProofsTest {
    @Test
    public void testCheckFile() throws Exception {


        assertEquals(0,Proof.checkFile("testProofs/correct0.txt"));


    }
    @Test
    public void testCheckFile1() throws Exception {


        assertEquals(0,Proof.checkFile("testProofs/correct1.txt"));


    }
    @Test
            public void testCheckIncorrect() throws Exception {
        assertEquals(Proof.checkFile("testProofs/incorrect8831.txt"), 8831);
    }
}
