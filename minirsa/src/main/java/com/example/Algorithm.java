package main.java.com.example;
import java.math.BigInteger;

import javax.swing.ButtonGroup;
public class Algorithm {

        /**
         *  Méthode  pour effectuer l'exponentiation modulaire.
         *  
         * @param a La base.
         * @param e exposant L'exposant.
         * @param n modulo Le modulo.
         * @return Le résultat de (base^exposant) % modulo. 
         */
        public static BigInteger puissanceModulaire(BigInteger a, BigInteger e, BigInteger n) {
            if (n.compareTo(BigInteger.ZERO) <= 0) {
                throw new IllegalArgumentException("Le modulo n doit être positif.");
            }
        
            // Initialisation du résultat à 1
            BigInteger res = BigInteger.ONE;
            a = a.mod(n);
            while (e.compareTo(BigInteger.ZERO) > 0) {
                if (e.mod(BigInteger.TWO).equals(BigInteger.ONE)) {
                    res = res.multiply(a).mod(n);
                }
                a = a.multiply(a).mod(n);
        
                e = e.divide(BigInteger.TWO);
            }
        
            return res;
        }
        

        /**
         * 
         * @param u
         * @param v
         * @return
         */
        public static BigInteger pgcd(BigInteger u ,BigInteger v){

            while(!v.equals(BigInteger.ZERO)){
                BigInteger t  = u;
                u = v;
                v = t.mod(v);
            }
            return u.abs();
        }

        public static BigInteger bezout(BigInteger a, BigInteger b) {
            BigInteger x0 = BigInteger.ONE;
            BigInteger x1 = BigInteger.ZERO;
        
            while (!b.equals(BigInteger.ZERO)) {
                BigInteger quotient = a.divide(b);
                BigInteger remainder = a.mod(b);
        
                a = b;
                b = remainder;
        
                // Mise à jour des coefficients de Bézout
                BigInteger tempX = x0.subtract(quotient.multiply(x1));
                x0 = x1;
                x1 = tempX;
            }
        
            return x0; // Retourne le coefficient associé à "a"
        }
    
    
}
