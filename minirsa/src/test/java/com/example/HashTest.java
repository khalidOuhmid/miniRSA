package com.example;
import main.java.com.example.Hash;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

public class HashTest {

    @Test
    public void testHashage() {
        // Message d'exemple
        String message = "Alice:65537:3233";

        // Calculer le hash attendu (valeur connue)
        BigInteger expectedHash = new BigInteger(
            "6d5d8baa50f2eba23807acd8f0f78e168e237170b60b3f9f941a530572a2880a", 16);

        // Calculer le hash avec la méthode Hashage
        BigInteger actualHash = Hash.Hashage(message);

        // Vérifier que les deux hashes correspondent
        assertEquals(expectedHash, actualHash, "Le hash calculé ne correspond pas au hash attendu.");
    }
}