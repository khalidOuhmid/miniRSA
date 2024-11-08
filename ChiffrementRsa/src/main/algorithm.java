package main;

import java.math.BigInteger;

public class algorithm {
	
	public  BigInteger puissance(BigInteger a, BigInteger e, BigInteger n) {
        BigInteger result = BigInteger.ONE;
        while (e.compareTo(BigInteger.ZERO) > 0) {
            if (e.mod(BigInteger.TWO).equals(BigInteger.ONE)) {
                result = (result.multiply(a)).mod(n);
            }
            a = (a.multiply(a)).mod(n);
            e = e.divide(BigInteger.TWO);
        }
        return result;
    }
	
	public  BigInteger bezout(BigInteger a, BigInteger b) {
        BigInteger p = BigInteger.ONE, q = BigInteger.ZERO;
        BigInteger r = BigInteger.ZERO, s = BigInteger.ONE;
        while (!b.equals(BigInteger.ZERO)) {
            BigInteger quotient = a.divide(b);
            BigInteger temp = b;
            b = a.mod(b);
            a = temp;

            BigInteger newR = p.subtract(quotient.multiply(r));
            BigInteger newS = q.subtract(quotient.multiply(s));
            p = r; q = s;
            r = newR; s = newS;
        }
        return p; 
    }
	
	public  boolean testPremier(BigInteger n) {
        int[] bases = {2, 3, 5, 7, 11, 13};
        for (int base : bases) {
            if (!puissance(BigInteger.valueOf(base), n.subtract(BigInteger.ONE), n).equals(BigInteger.ONE)) {
                return false; 
            }
        }
        return true; 
    }
	

    public  BigInteger pgcd(BigInteger u, BigInteger v) {
        while (!v.equals(BigInteger.ZERO)) {
            BigInteger t = u;
            u = v;
            v = t.mod(v);
        }
        return u.abs();
    }
}
