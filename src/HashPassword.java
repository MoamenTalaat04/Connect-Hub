import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {

    public static String hashPassword(String password)
            throws NoSuchAlgorithmException {
        MessageDigest encryptor = MessageDigest.getInstance("SHA-256");// encryptor is an object that we will use in order to encrypt the password string
        byte[] hashedBytes = encryptor.digest(password.getBytes()); // hashedBytes will store the encrypted password as bytes where each element in the array is an encrypted byte for a char. in the password
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hashedBytes) {
            stringBuilder.append(String.format("%02x", b)); // the stringBuilder will convert each byte in the array to 2 hexadecimal chars
        }
        return stringBuilder.toString(); // returns the string that holds the encrypted password
    }
}
