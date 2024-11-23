package main.java.com.example;

import java.math.BigInteger;
import java.util.function.BiConsumer;

public class RSA implements IRSA{

    public BigInteger chiffrer(BigInteger message, BigInteger e, BigInteger n){
        return Algorithm.puissanceModulaire(message, e, n);
    }


    public BigInteger dechiffrer(BigInteger messageChiffre, BigInteger e, BigInteger n){
        return Algorithm.puissanceModulaire(messageChiffre,e,n);
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }



}
