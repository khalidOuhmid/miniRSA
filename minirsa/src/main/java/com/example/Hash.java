package main.java.com.example;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class Hash {
    /**
     * Fonction pour générer une empreinte à partir de l'algorithme SHA-256
     * @param message message à partir duquel on va générer l'empreinte
     * @return l'empreinte
     */
    public static BigInteger Hashage(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Utiliser UTF-8 de manière explicite pour garantir la cohérence
            byte[] digest = md.digest(message.getBytes(StandardCharsets.UTF_8));
            
            // Convertir le hash en hexadécimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            // Convertir la chaîne hexadécimale en BigInteger
            return new BigInteger(hexString.toString(), 16);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul du hash", e);
        }
    }
}
