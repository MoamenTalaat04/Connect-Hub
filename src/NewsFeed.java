import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class NewsFeed {
    private FriendManagement friendManagement;
    private MainContentCreation contentCreation;
    private User currentUser;
    private UserDatabase userDatabase;
    private ArrayList<User> allUsers;

    private GroupManagement groupManagement;



    public NewsFeed(User currentUser) {
        friendManagement =new FriendManagement(currentUser); ;
        contentCreation = new MainContentCreation();
        userDatabase = UserDatabase.getInstance();
        allUsers = userDatabase.readUsersFromFile();
        this.currentUser = friendManagement.getUserById(currentUser.getUserId());
        groupManagement = new GroupManagement(currentUser);

    }

    public void addPost(String content, String imagePath) {
        try {
            contentCreation.createPost(getCurrentUser().getUserId(), content, imagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void addStory(String content, String imagePath) {
        try {
            contentCreation.createStory(getCurrentUser().getUserId(), content, imagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Fetch posts from friends
    public ArrayList<Posts> fetchPostsFromFriends() {
        ArrayList<Posts> friendPosts = new ArrayList<>();
        ArrayList<Posts> allPosts = null;
        try {
            allPosts = contentCreation.readPosts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Filter posts to include only those from friends
        for (String friend : getCurrentUser().getFriends()) {
            for (Posts post : allPosts) {
                if (post.getAuthorId().equals(friend)) {
                    friendPosts.add(post);
                }
            }
        }
        // Sort posts by timestamp (latest first)
        friendPosts.sort(Comparator.comparing(Posts::getTimestamp).reversed());
        return friendPosts;
    }

    // Fetch stories from friends
    public ArrayList<Stories> fetchStoriesFromFriends() {
        ArrayList<Stories> friendStories = new ArrayList<>();
        ArrayList<Stories> allStories = null;
        try {
            allStories = contentCreation.readActiveStories();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Filter stories to include only those from friends
        for (String friend : getCurrentUser().getFriends()) {
            for (Stories story : allStories) {
                if (story.getAuthorId().equals(friend)) {
                    friendStories.add(story);
                }
            }
        }
        // Sort stories by timestamp (latest first)
        friendStories.sort(Comparator.comparing(Stories::getTimestamp).reversed());
        return friendStories;
    }

    // Suggest friends to the current user
    public ArrayList<User> suggestFriends() {
        return friendManagement.suggestFriends();  // Using FriendManagement to suggest friends
    }
    public ArrayList<String> fetchFriendStatus() {
        return friendManagement.FriendStatus();  // Using FriendManagement to fetch friend status
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
    public ArrayList<Group> fetchAllGroups(){
       return groupManagement.getAllGroups();
    }

    public GroupManagement getGroupManagement() {
        return groupManagement;
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

    public void addGroup(String groupName, String groupBio, String groupIconPath, String groupCoverPath) {
        groupManagement.createGroup(groupName, groupBio,new ArrayList<String>(),new ArrayList<String>(),new ArrayList<Posts>(),currentUser.getUserId(), groupIconPath, groupCoverPath);
    }
    public ArrayList<Group> GroupsOfUser(){
        return groupManagement.getMyGroups();
    }
    public ArrayList<Group> GroupsSuggested(){
        return groupManagement.Suggestions();
    }

    public Group getGroupById(String GroupId){
        for(Group group: groupManagement.getAllGroups() ){
            if (group.getGroupId().equals(GroupId))return group;
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
