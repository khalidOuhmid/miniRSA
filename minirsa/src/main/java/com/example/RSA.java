package main.java.com.example;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA implements IRSA {

    // Chiffre le message avec la clé publique (e, n)
    public BigInteger chiffrer(BigInteger message, BigInteger e, BigInteger n) {
        // Vérifier que le message est plus petit que n
        if (message.compareTo(n) >= 0) {
            throw new IllegalArgumentException("Le message doit être plus petit que le modulo n");
        }
        return Algorithm.puissanceModulaire(message, e, n);
    }

    // Déchiffre le message avec la clé privée (d, n)
    public BigInteger dechiffrer(BigInteger messageChiffre, BigInteger d, BigInteger n) {
        return Algorithm.puissanceModulaire(messageChiffre, d, n);
    }

    // Méthode pour générer un grand nombre premier
    private static BigInteger generatePrime(int bitLength) {
        SecureRandom random = new SecureRandom();
        BigInteger prime;
        do {
            prime = BigInteger.probablePrime(bitLength, random);
        } while (!prime.isProbablePrime(100)); // Test de primalité plus rigoureux
        return prime;
    }
    
    // Méthode pour générer la clé RSA (public/private) avec un certain nombre de bits
    public static RSAKeyPair genererCle(int bitLength) {
        if (bitLength < 1024) {
            throw new IllegalArgumentException("La taille de clé doit être d'au moins 1024 bits");
        }

        BigInteger p, q, n, phiN, e, d;
        do {
            p = generatePrime(bitLength / 2);
            q = generatePrime(bitLength / 2);
            n = p.multiply(q);
            phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
            e = new BigInteger("65537"); // Exposant public standard
            d = Algorithm.bezout(e, phiN);
            
            // Vérifier que d est positif
            if (d.compareTo(BigInteger.ZERO) < 0) {
                d = d.add(phiN);
            }
        } while (d.equals(e)); // S'assurer que d ≠ e

        return new RSAKeyPair(e, d, n);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RSA Implementation";
    }
}
