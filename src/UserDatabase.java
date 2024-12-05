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
    public void saveUserToFile(User user) throws IOException {
        ArrayList<User> users = readUsersFromFile();
        users.add(user);
        mapper.writeValue(new File(userDatabaseFile), users);
    }
    public void saveUsersToFile(ArrayList<User> users) throws IOException {
        mapper.writeValue(new File(userDatabaseFile), users);
    }

    //load array list
    public ArrayList<User> readUsersFromFile() throws IOException {
        File file = new File(userDatabaseFile);
        if (!file.exists()) return new ArrayList<>();
        User[] usersArray = mapper.readValue(file, User[].class);
        return new ArrayList<>(Arrays.asList(usersArray));
    }

    public Map<String, String> readMapFromUsers() throws IOException {
        ArrayList<User> usersList = readUsersFromFile();

        return usersList.stream()
                .collect(Collectors.toMap(User::getEmail, User::getHashedPassword));
    }
}



