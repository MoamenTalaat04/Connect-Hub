import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDatabase {
    private static UserDatabase instance;
    private final String userDatabaseFile = "users.json";
    private final ObjectMapper mapper;

    // Private constructor to prevent external instantiation
    private UserDatabase() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
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
        try {
            ArrayList<User> users = readUsersFromFile();
            users.add(user);
            mapper.writeValue(new File(userDatabaseFile), users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Save a list of users to file
    public void saveUsersToFile(ArrayList<User> users) {
        try {
            mapper.writeValue(new File(userDatabaseFile), users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Load users from file
    public ArrayList<User> readUsersFromFile() {
        try {
            File file = new File(userDatabaseFile);
            if (!file.exists()) return new ArrayList<>();
            User[] usersArray = mapper.readValue(file, User[].class);
            return new ArrayList<>(Arrays.asList(usersArray));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Create a map from user email to hashed password
    public Map<String, String> readMapFromUsers() {
        ArrayList<User> usersList = readUsersFromFile();
        return usersList.stream()
                .collect(Collectors.toMap(User::getEmail, User::getHashedPassword));
    }
}
