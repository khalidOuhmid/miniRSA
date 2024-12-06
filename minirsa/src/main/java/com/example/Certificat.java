package main.java.com.example;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Certificat {
    public static CertificatAutorite CA;

    /**
     * Génère un certificat pour un utilisateur.
     *
     * @param identite L'identité de l'utilisateur.
     * @param e L'exposant public de la clé RSA.
     * @param n Le modulo de la clé RSA.
     * @param clePrivee L'exposant privé de la clé RSA.
     * @param modulo Le modulo de la clé RSA.
     * @return Le certificat généré sous forme de chaîne de caractères.
     */
    public static String genererCertificat(String identite, BigInteger e, BigInteger n){
        // Construction de la partie non signée
        String nonSignedPart = identite + ":" + e.toString() + ":" + n.toString();
        
        // Utilisation directe de la méthode signer de la classe Signature
        BigInteger signature = Signature.signer(nonSignedPart, CA.getCaKeyPair().getPrivateKey(), CA.getCaKeyPair().getModulus());
        
        // Construction du certificat complet
        return nonSignedPart + ":" + signature.toString();
    }

    /**
     * Vérifie la validité d'un certificat.
     *
     * @param certificat Le certificat à vérifier.
     * @return `true` si le certificat est valide, `false` sinon.
     */
    public static boolean verifierCertificat(String certificat) {
        try {
            String[] parts = certificat.split(":");
            if (parts.length != 4) {
                return false;
            }
    
            // Reconstruction de la partie non signée
            String nonSignedPart = parts[0] + ":" + parts[1] + ":" + parts[2];
            BigInteger signature = new BigInteger(parts[3]);
            
            // Utilisation directe de la méthode verifier de la classe Signature
            return Signature.verifier(nonSignedPart, signature, CA.getCaKeyPair().getPublicKey(), CA.getCaKeyPair().getModulus());
            
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }
}