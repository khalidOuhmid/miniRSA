package main.java.com.example;

import java.math.BigInteger;

public class RsaTextConverter {
    /**
     * Convertit une chaîne de caractères en BigInteger
     */
    private static BigInteger strToInt(String message) {
        BigInteger result = BigInteger.ZERO;
        BigInteger base = BigInteger.ONE;
        
        for (char c : message.toCharArray()) {
            BigInteger value = BigInteger.valueOf((int) c);
            result = result.add(value.multiply(base));
            base = base.multiply(BigInteger.valueOf(256));
        }
        return result;
    }

    /**
     * Convertit un BigInteger en chaîne de caractères
     */
    private static String intToStr(BigInteger number) {
        StringBuilder result = new StringBuilder();
        BigInteger base = BigInteger.valueOf(256);
        
        while (!number.equals(BigInteger.ZERO)) {
            BigInteger[] divAndRem = number.divideAndRemainder(base);
            result.append((char) divAndRem[1].intValue());
            number = divAndRem[0];
        }
        
        return result.toString();
    }

    /**
     * Chiffre un message texte
     */
    public static BigInteger chiffrer(String message, BigInteger e, BigInteger n) {
        BigInteger messageInt = strToInt(message);
        if (messageInt.compareTo(n) >= 0) {
            throw new IllegalArgumentException("Message trop long pour ce modulo");
        }
        return Algorithm.puissanceModulaire(messageInt, e, n);
    }

    /**
     * Déchiffre un message chiffré en texte
     */
    public static String dechiffrer(BigInteger messageChiffre, BigInteger d, BigInteger n) {
        BigInteger messageInt = Algorithm.puissanceModulaire(messageChiffre, d, n);
        return intToStr(messageInt);
    }

    /**
     * Signe un message texte
     */
    public static BigInteger signer(String message, BigInteger clePrivee, BigInteger modulo) {
        BigInteger hash = Hash.Hashage(message);
        return Algorithm.puissanceModulaire(hash.mod(modulo), clePrivee, modulo);
    }

    /**
     * Vérifie la signature d'un message texte
     */
    public static boolean verifier(String message, BigInteger signature, 
                                 BigInteger clePublique, BigInteger modulo) {
        BigInteger hashOriginal = Hash.Hashage(message);
        BigInteger hashDechiffre = Algorithm.puissanceModulaire(signature, clePublique, modulo);
        return hashOriginal.mod(modulo).equals(hashDechiffre);
    }
}
