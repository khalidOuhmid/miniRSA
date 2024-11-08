package main;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Chiffrement {
	
	
	 public BigInteger[] generateKeys(int bitLength) {
		 	algorithm algo = new algorithm();
	        SecureRandom random = new SecureRandom();
	        BigInteger p, q, n, phi, e, d;
	        do {
	            p = BigInteger.probablePrime(bitLength / 2, random);
	        } while (!algo.testPremier(p));
	        do {
	            q = BigInteger.probablePrime(bitLength / 2, random);
	        } while (!algo.testPremier(q));
	        
	        n = p.multiply(q); 
	        phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
	        
	        do {
	            e = new BigInteger(bitLength / 2, random);
	           
	        } while (!algo.pgcd(e, phi).equals(BigInteger.ONE));
	        
	        d = algo.bezout(e, phi).mod(phi);
	        
	        
	        return new BigInteger[]{e, d, n};
	 }
	 public  static BigInteger encrypt(BigInteger message, BigInteger e, BigInteger n) {
		 algorithm algo = new algorithm();
	        return algo.puissance(message, e, n);
	    }
	 
	 public  static BigInteger decrypt(BigInteger cipherText, BigInteger d, BigInteger n) {
		 algorithm algo = new algorithm();
	        return algo.puissance(cipherText, d, n);
	    }
		
	}
	

