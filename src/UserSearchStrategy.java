import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserSearchStrategy implements SearchStrategy {
    private ArrayList<User> allUsers;

    public UserSearchStrategy(ArrayList<User> users) {
        this.allUsers = users;
    }

    @Override
    public ArrayList<User> search(String query) {
        return allUsers.stream()
                .filter(user -> user.getUsername().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
