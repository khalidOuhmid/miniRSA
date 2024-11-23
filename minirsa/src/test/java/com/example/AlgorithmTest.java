package com.example;
import static org.junit.Assert.assertEquals;
import java.math.BigInteger;
import org.junit.Test;

import main.java.com.example.Algorithm;

public class AlgorithmTest {

    @Test
    public void TestPuissanceModulaire() {
        // Test 1 : 65^17 % 3233 = 2790
        PuissanceModulaireTest(65, 17, 3233,2790);

        // Test 2 : 5^3 % 13 = 8
        PuissanceModulaireTest(5, 3, 13,8);

        // Test 3 : Cas limite avec base = 1 (1^100 % n = 1)
        PuissanceModulaireTest(1, 100, 3233,1);

         // Test 4 : Cas limite avec exposant = 0 (a^0 % n = 1)
         PuissanceModulaireTest(1234, 0, 3233,1);
    }

    @Test
    public void TestPgcd(){
        // Cas 1 : PGCD(48, 18) = 6
        PgcdTest(48,  18, 6);

        // Cas 2 : PGCD(101, 103) = 1 (nombres premiers entre eux)
        PgcdTest(101,  103, 1);

        // Cas 3 : PGCD(0, 25) = 25
        PgcdTest(0,  25, 25);

        // Cas 4 : PGCD(25, 0) = 25
        PgcdTest(25,  0, 25);

         // Cas 5 : PGCD(0, 0) = 0
        PgcdTest(0,  0, 0);
        
    }

    @Test
    public void TestBezout(){
        // Cas 1 : Bezout(17, 3120) -> coefficient associé à 17 est -367
        BezoutTest(17, 3120, -367);

        // Cas 2 : Bezout(48, 18) -> coefficient associé à 48 est -1
        BezoutTest(48, 18, -1);
        
    }

    /**
     * Teste l'exponentiation modulaire.
     * 
     * @param message       Le message à chiffrer ou déchiffrer
     * @param exposantPublic Utilisé pour chiffrer le message
     * @param moduleRsa     Produit des deux nombres premiers (p × q)
     * @param expt résultat attendu
     */
    public void PuissanceModulaireTest(int message, int exposantPublic, int moduleRsa, int expt) {
        BigInteger a = BigInteger.valueOf(message);
        BigInteger e = BigInteger.valueOf(exposantPublic);
        BigInteger n = BigInteger.valueOf(moduleRsa);
        BigInteger expectedResult = BigInteger.valueOf(expt);

        BigInteger result = Algorithm.puissanceModulaire(a, e, n);

        assertEquals(expectedResult, result);
    }


    /**
     * 
     * @param premierEntier
     * @param deuxiemeEntier
     * @param expt résultat attendu
     */
    public void PgcdTest(int premierEntier, int deuxiemeEntier ,int expt){
        BigInteger u = BigInteger.valueOf(premierEntier);
        BigInteger v = BigInteger.valueOf(deuxiemeEntier);
        BigInteger expectedResult = BigInteger.valueOf(expt);

        BigInteger result = Algorithm.pgcd(u, v);
        assertEquals(expectedResult,result);


    }
       /**
     * Teste la fonction de Bézout.
     * 
     * @param a    Le premier nombre
     * @param b    Le deuxième nombre
     * @param expt Le résultat attendu (coefficient de Bézout associé à `a`)
     */
    public void BezoutTest(int a, int b, int expt) {
        BigInteger A = BigInteger.valueOf(a);
        BigInteger B = BigInteger.valueOf(b);
        BigInteger expectedResult = BigInteger.valueOf(expt);

        BigInteger result = Algorithm.bezout(A, B);

        assertEquals(expectedResult, result);
    }
}
