package es.deusto.spq.server.helper;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @class Hashing
 * @brief Provides hashing functionality.
 */
public class Hashing {

    /**
     * Hashes a string using SHA-512/256 algorithm.
     * @param s The string to be hashed.
     * @return The hashed string.
     * @throws RuntimeException If an error occurs during the hashing process.
     */
    public static String Hash(String s) {
        try {
            return DigestUtils.sha512_256Hex(s);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing string: ", e);
        }
    }
}