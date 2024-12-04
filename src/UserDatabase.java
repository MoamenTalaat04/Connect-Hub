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
    private final String userDatabaseFile = "users.json";
    private final ObjectMapper mapper ;

    public UserDatabase() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    // save array list
    private void saveUserToFile(User user, String filePath) throws IOException {
        List<User> users = readUsersFromFile(filePath, User[].class);
        users.add(user);
        mapper.writeValue(new File(filePath), users);
    }

    //load array list
    private <T> List<T> readUsersFromFile(String filePath, Class<T[]> type) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) return new ArrayList<>();
        T[] usersArray = mapper.readValue(file, type);
        return new ArrayList<>(Arrays.asList(usersArray));
    }
    public Map<String, String> readMapFromUsers(String filePath) throws IOException {
        List<User> usersList = readUsersFromFile(filePath, User[].class);

        return usersList.stream()
                .collect(Collectors.toMap(User::getEmail, User::getHashedPassword));
    }
}



