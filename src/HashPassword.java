import java.security.NoSuchAlgorithmException;

public class HashPassword {

    public static String hashPassword(String password)
            throws NoSuchAlgorithmException {
        return com.connecthub.util.PasswordHasher.hash(password);
    }
}
