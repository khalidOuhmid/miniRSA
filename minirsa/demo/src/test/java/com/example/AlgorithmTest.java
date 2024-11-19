package com.example;
import static org.junit.Assert.assertEquals;
import java.math.BigInteger;
import org.junit.Test;

public class AlgorithmTest {

    @Test
    public void TestPuissance() {
        // Appel du test avec les valeurs 65, 17, 3233
        PuissanceTest(65, 17, 3233);
    }

    /**
     * Teste l'exponentiation modulaire.
     * 
     * @param message       Le message à chiffrer ou déchiffrer
     * @param exposantPublic Utilisé pour chiffrer le message
     * @param moduleRsa     Produit des deux nombres premiers (p × q)
     */
    public void PuissanceTest(int message, int exposantPublic, int moduleRsa) {
        BigInteger a = BigInteger.valueOf(message);
        BigInteger e = BigInteger.valueOf(exposantPublic);
        BigInteger n = BigInteger.valueOf(moduleRsa);
        BigInteger expectedResult = new BigInteger("2790");

        BigInteger result = Algorithm.puissance(a, e, n);

        assertEquals(expectedResult, result);
    }
}
