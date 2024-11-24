package main.java.com.example;

import java.math.BigInteger;

public class RSAKeyPair {
    private final BigInteger e;  // Clé publique
    private final BigInteger d;  // Clé privée
    private final BigInteger n;  // Modulo

    /**
     * Constructeur pour créer une paire de clés RSA
     * @param e exposant public
     * @param d exposant privé
     * @param n modulo
     * @throws IllegalArgumentException si les paramètres sont invalides
     */
    public RSAKeyPair(BigInteger e, BigInteger d, BigInteger n) {
        // Vérifications de validité
        if (e == null || d == null || n == null) {
            throw new IllegalArgumentException("Les paramètres ne peuvent pas être null");
        }
        if (e.compareTo(BigInteger.ZERO) <= 0 || d.compareTo(BigInteger.ZERO) <= 0 || n.compareTo(BigInteger.ZERO) <= 0) {
            throw new IllegalArgumentException("Les paramètres doivent être positifs");
        }
        if (e.equals(d)) {
            throw new IllegalArgumentException("La clé publique ne peut pas être égale à la clé privée");
        }

        this.e = e;
        this.d = d;
        this.n = n;
    }

    /**
     * @return la clé publique
     */
    public BigInteger getPublicKey() {
        return e;
    }

    /**
     * @return la clé privée
     */
    public BigInteger getPrivateKey() {
        return d;
    }

    /**
     * @return le modulo
     */
    public BigInteger getModulus() {
        return n;
    }

    @Override
    public String toString() {
        return String.format("RSAKeyPair{e=%s, n=%s}", e.toString(), n.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RSAKeyPair other = (RSAKeyPair) obj;
        return e.equals(other.e) && n.equals(other.n);
    }

    @Override
    public int hashCode() {
        return 31 * e.hashCode() + n.hashCode();
    }
}
