package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import main.java.com.example.Certificat;
import main.java.com.example.RSA;
import main.java.com.example.RSAKeyPair;
import java.math.BigInteger;

public class CertificatTest {

    @Test
    public void testGenererCertificat() {
        // Génération des clés RSA
        RSAKeyPair keyPair = RSA.genererCle(1024);
        BigInteger clePublique = keyPair.getPublicKey();
        BigInteger clePrivee = keyPair.getPrivateKey();
        BigInteger modulo = keyPair.getModulus();
        
        // Cas 1 : Vérification avec des valeurs valides
        genererCertificatTest(
            "Alice",
            clePublique,
            modulo,
            clePrivee,
            modulo,
            "Alice:" + clePublique + ":" + modulo
        );

        // Cas 2 : Vérification avec une identité vide
        genererCertificatTest(
            "",
            clePublique,
            modulo,
            clePrivee,
            modulo,
            ":" + clePublique + ":" + modulo
        );
    }

    private void genererCertificatTest(String identite, BigInteger e, BigInteger n, 
                                    BigInteger clePrivee, BigInteger modulo, String expectedPart) {
        String certificat = Certificat.genererCertificat(identite, e, n);
        assertTrue(certificat.startsWith(expectedPart), "Le certificat ne commence pas par la partie attendue.");
        String[] parts = certificat.split(":");
        assertEquals(4, parts.length, "Le certificat doit contenir 4 parties.");
    }

    @Test
    public void testVerifierCertificat() {
        // Génération des clés RSA
        RSAKeyPair keyPair = RSA.genererCle(1024);
        BigInteger clePublique = keyPair.getPublicKey();
        BigInteger clePrivee = keyPair.getPrivateKey();
        BigInteger modulo = keyPair.getModulus();

        // Générer un certificat valide
        String certificatValide = Certificat.genererCertificat(
            "Alice", 
            clePublique, 
            modulo
        );

        // Cas 1 : Certificat valide
        verifierCertificatTest(
            certificatValide,
            true
        );

        // Cas 2 : Certificat invalide (mauvaise signature)
        String certificatInvalide = certificatValide.substring(0, certificatValide.lastIndexOf(':') + 1) + "1234";
        verifierCertificatTest(
            certificatInvalide,
            false
        );

        // Cas 3 : Certificat mal formé
        verifierCertificatTest(
            "Alice:" + clePublique + ":" + modulo,
            false
        );
    }

    private void verifierCertificatTest(String certificat, boolean expectedValid) {
        boolean isValid = Certificat.verifierCertificat(certificat);
        assertEquals(expectedValid, isValid);
    }
}