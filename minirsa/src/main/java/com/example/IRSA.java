package main.java.com.example;

import java.math.BigInteger;

public interface IRSA {

     /**
     * Chiffre un message avec la clé publique.
     *
     * @param message Le message à chiffrer.
     * @param e       L'exposant public.
     * @param n       Le module RSA.
     * @return Le message chiffré.
     */
    BigInteger chiffrer(BigInteger message, BigInteger e, BigInteger n);


     /**
     * Déchiffre un message avec la clé privée.
     *
     * @param messageChiffre Le message chiffré.
     * @param d              L'exposant privé.
     * @param n              Le module RSA.
     * @return Le message déchiffré.
     */
    BigInteger dechiffrer(BigInteger messageChiffre, BigInteger d, BigInteger n);

    
}
