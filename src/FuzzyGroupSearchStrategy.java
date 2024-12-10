import java.util.ArrayList;
import java.util.stream.Collectors;
import org.apache.commons.text.similarity.LevenshteinDistance;
public class FuzzyGroupSearchStrategy implements SearchStrategy {
    private ArrayList<Group> allGroups;

    public FuzzyGroupSearchStrategy(ArrayList<Group> groups) {
        this.allGroups = groups;
    }

    @Override
    public ArrayList<Group> search(String query) {
        LevenshteinDistance levenshtein = new LevenshteinDistance();
        return allGroups.stream()
                .filter(group -> levenshtein.apply(group.getName().toLowerCase(), query.toLowerCase()) <= 3)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
