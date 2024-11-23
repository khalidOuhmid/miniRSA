package main.java.com.example;

import java.math.BigInteger;

public class Certificat {

    /**
     * 
     * @param identite
     * @param e
     * @param n
     * @param clePrivee
     * @param modulo
     * @return
     */
    public static String genererCertificat(String identite, BigInteger e, BigInteger n,BigInteger clePrivee, BigInteger modulo) {
        String message = identite + ":" + e.toString() + ":" + n.toString();
        BigInteger hashMessage = HashUtil.hash(message);

        BigInteger signatureCA = Algorithm.puissanceModulaire(hashMessage, clePrivee, modulo);

        return message + ":" + signatureCA.toString();
    }

    /**
     * 
     * @param certificat
     * @param clePublique
     * @param moduloRSA
     * @return
     */
    public static boolean verifierCertificat(String certificat, BigInteger clePublique, BigInteger moduloRSA) {
        String[] parts = certificat.split(":");
        if (parts.length != 4) return false;

        String identity = parts[0];
        BigInteger e = new BigInteger(parts[1]);
        BigInteger n = new BigInteger(parts[2]);
        BigInteger signatureCA = new BigInteger(parts[3]);

        String messageOriginal = identity + ":" + e.toString() + ":" + n.toString();
        BigInteger hashOriginal = Hash.Hashage(messageOriginal);
        BigInteger hashDecriptee = Algorithm.puissanceModulaire(signatureCA, clePublique, moduloRSA);

        return hashOriginal.equals(hashDecriptee);
    }
}
