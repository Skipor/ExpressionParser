package ru.skipor.MathLogic.Proof;

import org.junit.Test;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/6/14
 * Time: 7:11 PM
 */
import static org.junit.Assert.*;
public class DeductionTest {
    @Test
    public void testGetFinal() throws Exception {

        Deduction deduction = new Deduction(Proof.createProof("testProofs/correctDeduction0.txt"));
        Proof proof = deduction.getFinal();
        assertEquals(0, proof.check());

    }
    @Test
    public void testGetFinal1() throws Exception {

        Deduction deduction = new Deduction(Proof.createProof("testProofs/correct1.txt"));
        Proof proof = deduction.getFinal();
        assertEquals(0, proof.check());

    }
}
