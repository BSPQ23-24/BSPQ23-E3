package es.deusto.spq.server.helper;

import static org.junit.Assert.assertEquals;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class HashingTest {

    @Test
    public void testHash_Success() {
        String input = "testString";
        String expectedHash = DigestUtils.sha512_256Hex(input);

        String actualHash = Hashing.Hash(input);

        assertEquals(expectedHash, actualHash);
    }

    @Test(expected = RuntimeException.class)
    public void testHash_Exception() {
        @SuppressWarnings("unused")
        String input = "testString";

        // Forzar una excepción pasando una cadena nula
        Hashing.Hash(null);
    }
}