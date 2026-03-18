import java.util.ArrayList;

public class ProfileManager {
    private User currentUser ;
    private UserDatabase userDatabase;
    private ArrayList<User> allUsers;
    private final com.connecthub.service.ProfileService profileService;

    public ProfileManager(User currentUser) {
        this.currentUser = currentUser;
        profileService = ConnectHubContext.factory().profileService();
        userDatabase=UserDatabase.getInstance();
        allUsers=userDatabase.readUsersFromFile();
    }

    public User getProfile(String userId) {
        ArrayList<User> profiles = allUsers;
        for (User profile : profiles) {
            if (profile.getUserId().equals(userId)) {
                return profile;
            }
        }
        return null;
    }

    public ArrayList<Posts> fetchPostsFromUser() {
        ArrayList<Posts> userposts = new ArrayList<>();
        for (com.connecthub.model.Posts post : profileService.fetchPostsFromUser(currentUser.getUserId())) {
            userposts.add(LegacyMapper.toLegacyPost(post));
        }
        return userposts;
    }
    public ArrayList<User>getAllUsers(){
        return allUsers;
    }
    public void setAllUsers(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public User getCurrentUser() {
        return currentUser;
    }

    public void fetchAllUsers(){
        setAllUsers(userDatabase.readUsersFromFile());
        setCurrentUser(getProfile(currentUser.getUserId()));
    }
}    