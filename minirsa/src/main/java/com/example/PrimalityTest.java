package main.java.com.example;

import java.math.BigInteger;
import java.security.SecureRandom;

public class PrimalityTest {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    
    /**
     * Effectue le test de primalité de Miller-Rabin.
     *
     * @param n Le nombre à tester
     * @param k Le nombre d'itérations (recommandé : 40 pour une très haute fiabilité)
     * @return true si n est probablement premier, false sinon
     */
    public static boolean isPrime(BigInteger n, int k) {
        // Vérifications préliminaires
        if (n == null || k <= 0) {
            throw new IllegalArgumentException("Paramètres invalides");
        }
        
        // Cas particuliers
        if (n.compareTo(BigInteger.TWO) < 0) return false;
        if (n.equals(BigInteger.TWO) || n.equals(BigInteger.valueOf(3))) return true;
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) return false;

        // Décomposition de n-1 en 2^s * d
        BigInteger nMoinsUn = n.subtract(BigInteger.ONE);
        BigInteger d = nMoinsUn;
        int s = 0;
        
        while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            d = d.divide(BigInteger.TWO);
            s++;
        }

        // Test de Miller-Rabin
        for (int i = 0; i < k; i++) {
            BigInteger a = generateRandomBase(n);
            if (!millerRabinTest(a, d, s, n)) {
                return false;
            }
        }

        return true;
    }

    private static boolean millerRabinTest(BigInteger a, BigInteger d, int s, BigInteger n) {
        BigInteger x = Algorithm.puissanceModulaire(a, d, n);
        
        if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))) {
            return true;
        }

        for (int r = 1; r < s; r++) {
            x = Algorithm.puissanceModulaire(x, BigInteger.TWO, n);
            if (x.equals(n.subtract(BigInteger.ONE))) {
                return true;
            }
            if (x.equals(BigInteger.ONE)) {
                return false;
            }
        }

        return false;
    }

    /**
     * Génère une base aléatoire pour le test dans l'intervalle [2, n-2]
     */
    private static BigInteger generateRandomBase(BigInteger n) {
        BigInteger result;
        do {
            result = new BigInteger(n.bitLength(), SECURE_RANDOM);
        } while (result.compareTo(BigInteger.TWO) < 0 || 
                result.compareTo(n.subtract(BigInteger.TWO)) > 0);
        
        return result;
    }

    /**
     * Version simplifiée pour les tests rapides avec un nombre d'itérations par défaut
     */
    public static boolean isPrime(BigInteger n) {
        return isPrime(n, 40); // 40 itérations pour une probabilité d'erreur négligeable
    }
}
