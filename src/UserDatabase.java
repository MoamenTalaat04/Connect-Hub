import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Map;

public class UserDatabase {
    private final String userDatabaseFile = "users.json";
    private final ObjectMapper mapper ;
    private ArrayList<User> users;
    private Map accValidation;//for login only

    public UserDatabase(String userDatabaseFile) {
        this.userDatabaseFile = userDatabaseFile;
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

}
