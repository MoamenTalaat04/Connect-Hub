import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDatabase {
    private static UserDatabase instance;
    private final com.connecthub.repository.UserRepository userRepository;

    // Private constructor to prevent external instantiation
    private UserDatabase() {
        userRepository = ConnectHubContext.factory().userRepository();
    }

    // Public method to provide access to the single instance
    public static  UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }

    // Save a single user to file
    public void saveUserToFile(User user) {
        userRepository.save(LegacyMapper.toModernUser(user));
    }

    // Save a list of users to file
    public void saveUsersToFile(ArrayList<User> users) {
        userRepository.saveAll(LegacyMapper.toModernUsers(users));
    }

    // Load users from file
    public ArrayList<User> readUsersFromFile() {
        return LegacyMapper.toLegacyUsers(userRepository.findAll());
    }

    // Create a map from user email to hashed password
    public Map<String, String> readMapFromUsers() {
        ArrayList<User> usersList = readUsersFromFile();
        return usersList.stream()
                .collect(Collectors.toMap(User::getEmail, User::getHashedPassword));
    }
}
