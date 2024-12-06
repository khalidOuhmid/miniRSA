package main.java.com.example;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import java.math.BigInteger;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.KeyPair;

public class CertificatAutorite {
    private TextArea messageArea;
    private RSAKeyPair keyPair;
    private String caCertificat;
    private boolean running;

    public CertificatAutorite(TextArea messageArea) {
        this.messageArea = messageArea;
        this.running = true;
        Certificat.CA = this;
        initializeCA();
    }
    public CertificatAutorite(){
        this.running = true;
        Certificat.CA = this;
        initializeCA();
    }

    private void initializeCA() {
    
            this.keyPair = RSA.genererCle(2048);

            this.caCertificat = Certificat.genererCertificat("CA MiniRSA", 
                this.keyPair.getPublicKey(), 
                this.keyPair.getModulus());

        
        
    }

    public RSAKeyPair getCaKeyPair() {
        return keyPair;
    }

    public String getCaCertificat() {
        return caCertificat;
    }

    public void arreter() {
        running = false;
    }
}