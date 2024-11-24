package main.java.com.example;

import java.math.BigInteger;

public class Certificat {
    public static String genererCertificat(String identite, BigInteger e, BigInteger n, 
                                         BigInteger clePrivee, BigInteger modulo) {
        // Construction de la partie non signée
        String nonSignedPart = identite + ":" + e.toString() + ":" + n.toString();
        
        // Utilisation directe de la méthode signer de la classe Signature
        BigInteger signature = Signature.signer(nonSignedPart, clePrivee, modulo);
        
        // Construction du certificat complet
        return nonSignedPart + ":" + signature.toString();
    }

    public static boolean verifierCertificat(String certificat, BigInteger clePublique, 
                                           BigInteger moduloRSA) {
        try {
            String[] parts = certificat.split(":");
            if (parts.length != 4) {
                return false;
            }

            // Reconstruction de la partie non signée
            String nonSignedPart = parts[0] + ":" + parts[1] + ":" + parts[2];
            BigInteger signature = new BigInteger(parts[3]);
            
            // Utilisation directe de la méthode verifier de la classe Signature
            return Signature.verifier(nonSignedPart, signature, clePublique, moduloRSA);
            
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }
}
