import java.util.*;

public class NewsFeed {
    private final FriendManagement friendManagement;
    private User currentUser;
    private final UserDatabase userDatabase;
    private ArrayList<User> allUsers;
    private final com.connecthub.service.NewsFeedService newsFeedService;



    public NewsFeed(User currentUser) {
        friendManagement =new FriendManagement(currentUser);
        userDatabase = UserDatabase.getInstance();
        newsFeedService = ConnectHubContext.factory().newsFeedService();
        allUsers = userDatabase.readUsersFromFile();
        this.currentUser = friendManagement.getUserById(currentUser.getUserId());


    }

    public void addPost(String content, String imagePath) {
        newsFeedService.addPost(getCurrentUser().getUserId(), content, imagePath);

    }

    public void addStory(String content, String imagePath) {
        newsFeedService.addStory(getCurrentUser().getUserId(), content, imagePath);
    }

    // Fetch posts from friends
    public ArrayList<Posts> fetchPostsFromFriends() {
        ArrayList<Posts> friendPosts = new ArrayList<>();
        for (com.connecthub.model.Posts post : newsFeedService.fetchPostsFromFriends(getCurrentUser().getUserId())) {
            friendPosts.add(LegacyMapper.toLegacyPost(post));
        }
        return friendPosts;
    }

    // Fetch stories from friends
    public ArrayList<Stories> fetchStoriesFromFriends() {
        ArrayList<Stories> friendStories = new ArrayList<>();
        for (com.connecthub.model.Stories story : newsFeedService.fetchStoriesFromFriends(getCurrentUser().getUserId())) {
            friendStories.add(LegacyMapper.toLegacyStory(story));
        }
        return friendStories;
    }

    // Suggest friends to the current user
    public ArrayList<User> suggestFriends() {
        return LegacyMapper.toLegacyUsers(newsFeedService.suggestFriends(getCurrentUser().getUserId()));
    }
    public ArrayList<String> fetchFriendStatus() {
        return new ArrayList<>(newsFeedService.fetchFriendStatus(getCurrentUser().getUserId()));
    }

    public String getUsernameByID(String UserID){
        for(User user :getUsersById( getCurrentUser().getFriends())){
            if (user.getUserId().equals(UserID)){
                return user.getUsername();
            }
        }
        return null;
    }
    public User getCurrentUser() {
        return getUserById(currentUser.getUserId());
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setAllUsers(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
    }
    public void fetchAllUsers(){
        setAllUsers(userDatabase.readUsersFromFile());
        setCurrentUser(getUserById(getCurrentUser().getUserId()));
        friendManagement.fetchAllUsers();
    }



    public FriendManagement getFriendManagement() {
        return friendManagement;
    }
    public UserDatabase getUserDatabase() {
        return userDatabase;
    }
    public ArrayList<User> getAllUsers() {
        return allUsers;
    }
    public User getUserById(String UserId){
        for(User user: allUsers ){
            if (user.getUserId().equals(UserId))return user;
        }
        return null;

    }
    public ArrayList<User> getUsersById(ArrayList<String> UsersId){
        ArrayList<User> Users =new ArrayList<>();
        for(String userId: UsersId ){
            Users.add(getUserById(userId));
        }
        return Users;

    }


}
