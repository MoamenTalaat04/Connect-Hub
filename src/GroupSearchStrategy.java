import java.util.ArrayList;
import java.util.stream.Collectors;

public class GroupSearchStrategy implements SearchStrategy {
    private ArrayList<Group> allGroups;

    public GroupSearchStrategy(ArrayList<Group> groups) {
        this.allGroups = groups;
    }

    @Override
    public ArrayList<Group> search(String query) {
        return allGroups.stream()
                .filter(group -> group.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
