import java.util.ArrayList;
import java.util.stream.Collectors;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class FuzzyUserSearchStrategy implements SearchStrategy {
    private ArrayList<User> allUsers;

    public FuzzyUserSearchStrategy(ArrayList<User> users) {
        this.allUsers = users;
    }

    @Override
    public ArrayList<User> search(String query) {
        LevenshteinDistance levenshtein = new LevenshteinDistance();
        return allUsers.stream()
                .filter(user -> levenshtein.apply(user.getUsername().toLowerCase(), query.toLowerCase()) <= 3)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
