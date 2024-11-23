package main.java.com.example;

import java.math.BigInteger;

public class Signature {
    
    public static BigInteger signer(String message, BigInteger clePrive, BigInteger modulo) {
        BigInteger hash = Hash.Hashage(message);
        return Algorithm.puissanceModulaire(hash,clePrive,modulo);
    }

    public static boolean verifier(String message, BigInteger signature, BigInteger publicKey, BigInteger modulus) {
        BigInteger hashOriginal = HashUtil.hash(message);
        BigInteger hashDechiffre = puissanceModulaire(signature, publicKey, modulus); 
        return hashOriginal.equals(hashDechiffre);
    }
    
}
