package db.connection;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthenticationBD {

    public boolean validatePassword(String password, String hash, String salt) {
        try {
            return MessageDigest.isEqual(generateHash(password, salt).getBytes("UTF-8"), hash.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("Cannot validate password", e);
            throw new RuntimeException("Suppress exception", e);
        }
    }

    private static String md5Custom(String st) { // ленивое хеширование md5
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }
}
