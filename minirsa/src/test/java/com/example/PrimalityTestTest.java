package test.java.com.example;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import main.java.com.example.PrimalityTest;
import java.math.BigInteger;

import org.junit.jupiter.api.Test;

public class PrimalityTestTest {

    @Test
    public void testIsPrime() {
        assertTrue(PrimalityTest.isPrime(new BigInteger("61"), 10)); // Nombre premier
        assertFalse(PrimalityTest.isPrime(new BigInteger("100"), 10)); // Nombre composite
    }
}