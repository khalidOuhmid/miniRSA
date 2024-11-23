package main.java.com.example;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Hash {

    /**
     * Fonction pour générer une empreinte  à partir de l'algorithme SHA-256
     * @param message message à partir duquel on va générer l'empreinte
     * @return l'empreinte
     */
    public static BigInteger Hashage(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(message.getBytes());
            return new BigInteger(1, digest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
