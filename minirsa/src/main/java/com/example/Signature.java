package main.java.com.example;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Signature {
    /**
     * Signe un message en utilisant une clé privée.
     *
     * @param message  Le message à signer.
     * @param clePrive La clé privée utilisée pour signer.
     * @param modulo   Le module RSA.
     * @return La signature numérique sous forme d'un BigInteger.
     */
    public static BigInteger signer(String message, BigInteger clePrive, BigInteger modulo) {
        // Calculer l'empreinte numérique du message
        BigInteger hash = Hash.Hashage(message);
        
        // Vérifier que le hash est plus petit que le modulo
        if (hash.compareTo(modulo) >= 0) {
            hash = hash.mod(modulo);
        }

        // Signer l'empreinte avec la clé privée
        return Algorithm.puissanceModulaire(hash, clePrive, modulo);
    }

    /**
     * Vérifie une signature numérique en utilisant une clé publique.
     *
     * @param message      Le message original.
     * @param signature    La signature numérique à vérifier.
     * @param publicKey    La clé publique utilisée pour vérifier.
     * @param modulus      Le module RSA.
     * @return true si la signature est valide, false sinon.
     */
    public static boolean verifier(String message, BigInteger signature, BigInteger publicKey, BigInteger modulus) {
        // Calculer l'empreinte numérique du message original
        BigInteger hashOriginal = Hash.Hashage(message);
        
        // S'assurer que le hash est dans le bon intervalle
        if (hashOriginal.compareTo(modulus) >= 0) {
            hashOriginal = hashOriginal.mod(modulus);
        }

        // Déchiffrer la signature avec la clé publique
        BigInteger hashDechiffre = Algorithm.puissanceModulaire(signature, publicKey, modulus);

        // Comparer les empreintes
        return hashOriginal.equals(hashDechiffre);
    }
}