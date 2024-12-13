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

public class GroupDatabase {
    private static GroupDatabase instance;
    private final String groupDatabaseFile = "groups.json";
    private final ObjectMapper mapper;

    //using singleton design principle --> to insure the uniqueness of the database ;)
    //to prevent conflects from having multi-instance of group database XD
    // Private constructor to prevent external instantiation
    private GroupDatabase() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    //Public method to create Single instance (if an instance does not exist)
    //returns the single instance itself (if it does exist)
    public static GroupDatabase getInstance(){
        if (instance == null){
            instance =new GroupDatabase();
        }
        return instance;
    }
    // Saves a single Group to file ;)
    public void saveGroupToFile(Group group) {
        try {
            ArrayList<Group> groups = readGroupsFromFile();
            groups.add(group);
            mapper.writeValue(new File(groupDatabaseFile), groups);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // Save a list of Groups to file
    public void saveGroupsToFile(ArrayList<Group> groups) {
        try {
            mapper.writeValue(new File(groupDatabaseFile), groups);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // Load Groups from file
    public ArrayList<Group> readGroupsFromFile() {
        try {
            File file = new File(groupDatabaseFile);
            if (!file.exists()) return new ArrayList<>();
            Group[] groupsArray = mapper.readValue(file, Group[].class);
            return new ArrayList<>(Arrays.asList(groupsArray ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    

}
