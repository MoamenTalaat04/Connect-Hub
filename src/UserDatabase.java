import com.fasterxml.jackson.databind.ObjectMapper;

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
    private void saveArrayListToFile(User user, String filePath) throws IOException {
        List<User> users = readArrayListFromFile(filePath, User[].class);
        users.add(user);
        mapper.writeValue(new File(filePath), users);
    }

    //load array list
    private  <T> List<T> readArrayListFromFile(String filePath, Class<T[]> type) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) return new ArrayList<>();
        T[] usersArray = mapper.readValue(file, type);
        return new ArrayList<>(Arrays.asList(usersArray));
    }
    public Map<String, String> readMapFromUsers(String filePath) throws IOException {
        List<User> usersList = readArrayListFromFile(filePath, User[].class);

        return usersList.stream()
                .collect(Collectors.toMap(User::getEmail, User::getHashedPassword));
    }
}
