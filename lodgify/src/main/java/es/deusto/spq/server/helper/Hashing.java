package es.deusto.spq.server.helper;

import org.apache.commons.codec.digest.DigestUtils;

public class Hashing {
    public static String Hash(String s) {
        try {
            return DigestUtils.sha512_256Hex(s);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing string: ", e);
        }
    }
}