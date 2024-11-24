package main.java.com.example;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Utilisateur extends Thread {
    private String nom;
    private RSAKeyPair keyPair;
    private TextArea  messageArea;
    private BlockingQueue<Message> messagesRecus;
    private boolean running;

    public Utilisateur(String nom, TextArea messageArea){
        this.nom = nom;
        this.keyPair = RSA.genererCle(1024);


    }
}
