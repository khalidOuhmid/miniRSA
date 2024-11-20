package com.example;
import static org.junit.Assert.assertEquals;
import java.math.BigInteger;
import org.junit.Test;

import main.java.com.example.Algorithm;

public class AlgorithmTest {

    @Test
    public void TestPuissance() {
        // Appel du test avec les valeurs 65, 17, 3233
        PuissanceTest(65, 17, 3233,2790);
    }

    /**ls
     * Teste l'exponentiation modulaire.
     * 
     * @param message       Le message à chiffrer ou déchiffrer
     * @param exposantPublic Utilisé pour chiffrer le message
     * @param moduleRsa     Produit des deux nombres premiers (p × q)
     */
    public void PuissanceTest(int message, int exposantPublic, int moduleRsa, int expt) {
        BigInteger a = BigInteger.valueOf(message);
        BigInteger e = BigInteger.valueOf(exposantPublic);
        BigInteger n = BigInteger.valueOf(moduleRsa);
        BigInteger expectedResult = BigInteger.valueOf(expt);

        BigInteger result = Algorithm.puissance(a, e, n);

        assertEquals(expectedResult, result);
    }
}
