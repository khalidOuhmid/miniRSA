package main.java.com.example;
import java.math.BigInteger;
public class Algorithm {

        /**
         * 
         * @param a
         * @param e
         * @param n
         * @return
         */
        public static BigInteger puissance(BigInteger a , BigInteger e, BigInteger n){
                BigInteger res = BigInteger.ONE;
                while(e.compareTo(BigInteger.ZERO) > 0){
                    if( e.mod(BigInteger.TWO).equals(BigInteger.ONE)){
                        res = res.multiply(a).mod(n);
                    }
                        a = a.multiply(a).mod(n);
                        e = e.divide(BigInteger.TWO);
                }
                return res;
    
        }
        public static BigInteger pgcd(BigInteger u ,BigInteger v){

            while(!v.equals(BigInteger.ZERO)){
                BigInteger t  = u;
                u = v;
                v = t.mod(v);
            }
            return u.abs();
        }
    
    
}
