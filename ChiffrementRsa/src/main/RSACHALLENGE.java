package main;

import java.math.BigInteger;

public class RSACHALLENGE {
	  // Fonction pour factoriser N en p et q
    public static BigInteger[] factorizeN(BigInteger N) {
        BigInteger p = BigInteger.TWO;
        while (p.compareTo(N.sqrt()) <= 0) {
            if (N.mod(p).equals(BigInteger.ZERO)) {
                BigInteger q = N.divide(p);
                return new BigInteger[]{p, q};
            }
            p = p.add(BigInteger.ONE);
        }
        return null;
    }

    // Calcul de l'inverse modulaire de e mod phi
    public static BigInteger modInverse(BigInteger e, BigInteger phi) {
        return e.modInverse(phi);
    }

    // Déchiffrement avec la clé privée d
    public static BigInteger decrypt(BigInteger c, BigInteger d, BigInteger N) {
        return c.modPow(d, N);
    }

    // Calcul du PGCD
    public static BigInteger gcd(BigInteger a, BigInteger b) {
        return a.gcd(b);
    }

}
