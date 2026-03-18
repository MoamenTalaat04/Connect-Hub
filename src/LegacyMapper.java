import java.util.ArrayList;
import java.util.List;

public final class LegacyMapper {
    private LegacyMapper() {
    }

    public static User toLegacyUser(com.connecthub.model.User user) {
        if (user == null) {
            return null;
        }
        return new User(
                user.getUserId(),
                new ArrayList<>(safeList(user.getBlocked())),
                new ArrayList<>(safeList(user.getPendingRequests())),
                new ArrayList<>(safeList(user.getFriends())),
                user.getStatus(),
                user.getDob(),
                user.getUsername(),
                user.getEmail(),
                user.getHashedPassword(),
                user.getCoverPhotoPath(),
                user.getBio(),
                user.getProfilePhotoPath()
        );
    }

    public static com.connecthub.model.User toModernUser(User user) {
        if (user == null) {
            return null;
        }
        return new com.connecthub.model.User(
                user.getUserId(),
                new ArrayList<>(safeList(user.getBlocked())),
                new ArrayList<>(safeList(user.getPendingRequests())),
                new ArrayList<>(safeList(user.getFriends())),
                user.getStatus(),
                user.getDob(),
                user.getUsername(),
                user.getEmail(),
                user.getHashedPassword(),
                user.getCoverPhotoPath(),
                user.getBio(),
                user.getProfilePhotoPath()
        );
    }

    public static ArrayList<User> toLegacyUsers(List<com.connecthub.model.User> users) {
        ArrayList<User> result = new ArrayList<>();
        for (com.connecthub.model.User user : users) {
            result.add(toLegacyUser(user));
        }
        return result;
    }

    public static ArrayList<com.connecthub.model.User> toModernUsers(List<User> users) {
        ArrayList<com.connecthub.model.User> result = new ArrayList<>();
        for (User user : users) {
            result.add(toModernUser(user));
        }
        return result;
    }

    public static Posts toLegacyPost(com.connecthub.model.Posts post) {
        return new Posts(post.getContent(), post.getAuthorId(), post.getContentId(), post.getImagePath(), post.getTimestamp());
    }

    public static Stories toLegacyStory(com.connecthub.model.Stories story) {
        Stories legacy = new Stories(story.getContent(), story.getAuthorId(), story.getContentId(), story.getImagePath(), story.getTimestamp());
        legacy.setExpired(story.isExpired());
        return legacy;
    }

    public static com.connecthub.model.Posts toModernPost(Posts post) {
        return new com.connecthub.model.Posts(post.getContent(), post.getAuthorId(), post.getContentId(), post.getImagePath(), post.getTimestamp());
    }

    public static com.connecthub.model.Stories toModernStory(Stories story) {
        com.connecthub.model.Stories modern = new com.connecthub.model.Stories(story.getContent(), story.getAuthorId(), story.getContentId(), story.getImagePath(), story.getTimestamp());
        modern.setExpired(story.isExpired());
        return modern;
    }

    private static List<String> safeList(List<String> values) {
        return values == null ? new ArrayList<>() : values;
    }
}
