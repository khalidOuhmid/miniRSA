package rsa;
import java.math.BigInteger;
public class Algorithm {
    
    
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
                    if( e.mod(BigInteger.TWO).equals(BigInteger.ZERO)){
                        res = res.multiply(a).mod(n) 
                    }
                        a = a.multiply(a).mod(n);
                        e = e.divide(BigInteger.TWO);
                }
                return res;
    
        }
    
    }
    
    
}
