package test.java.com.example;

import org.junit.jupiter.api.RepeatedTest;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import main.java.com.example.Signature;
import main.java.com.example.RSA;
import main.java.com.example.RSAKeyPair;
import main.java.com.example.Hash;
import java.math.BigInteger;

public class SignatureTest {

    @RepeatedTest(100) // Exécute le test 100 fois
    public void testSignerEtVerifier() {
        // Génération des clés RSA
        RSA rsa = new RSA();
        RSAKeyPair keyPair = RSA.genererCle(1024);
        BigInteger clePub = keyPair.getPublicKey();
        BigInteger clePrive = keyPair.getPrivateKey();
        BigInteger modulo = keyPair.getModulus();
        
        // Exemple de message
        String message = "Ceci est un message important.";

        // Calcul de l'empreinte du message
        BigInteger hashMessage = Hash.Hashage(message);
        System.out.println("Empreinte du message : " + hashMessage);

        // Signer le message
        BigInteger signature = Signature.signer(message, clePrive, modulo);
        System.out.println("Signature générée : " + signature);

        // Vérifier la signature
        boolean estValide = Signature.verifier(message, signature, clePub, modulo);
        System.out.println("Signature valide : " + estValide);
        
        assertTrue(estValide, "La signature devrait être valide.");

        // Test avec un message modifié
        String messageModifie = "Ceci est un message différent.";
        boolean estValideModifie = Signature.verifier(messageModifie, signature, clePub, modulo);
        assertFalse(estValideModifie, "La signature ne devrait pas être valide pour un message modifié.");
    }
}
