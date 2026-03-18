import java.util.*;

public class FriendManagement {
    private ArrayList<User> allUsers;
    private User currentUser;
    private final UserDatabase userDatabase;
    private final com.connecthub.service.FriendService friendService;

    public FriendManagement(User currentUser) {
        this.userDatabase = UserDatabase.getInstance();
        this.friendService = ConnectHubContext.factory().friendService();
        this.allUsers = userDatabase.readUsersFromFile();
        this.currentUser = getUserById(currentUser.getUserId());
    }

    // Send Friend Request
    public boolean sendFriendRequest(User receiver) {
        boolean result = friendService.sendFriendRequest(getCurrentUser().getUserId(), receiver.getUserId());
        fetchAllUsers();
        return result;
    }

    public ArrayList<User> SentRequestsFromUser() {
        return LegacyMapper.toLegacyUsers(friendService.sentRequests(getCurrentUser().getUserId()));
    }
    public ArrayList<User> ReceivedRequestsForUser() {
        return LegacyMapper.toLegacyUsers(friendService.receivedRequests(getCurrentUser().getUserId()));
    }

    // Accept Friend Request
    public boolean acceptFriendRequest(User sender) {
        boolean result = friendService.acceptFriendRequest(getCurrentUser().getUserId(), sender.getUserId());
        fetchAllUsers();
        return result;
    }

    public boolean rejectFriendRequest(User sender) {
        boolean result = friendService.rejectFriendRequest(getCurrentUser().getUserId(), sender.getUserId());
        fetchAllUsers();
        return result;
    }

    // Remove Friend
    public boolean removeFriend(User friend) {
        boolean result = friendService.removeFriend(getCurrentUser().getUserId(), friend.getUserId());
        fetchAllUsers();
        return result;
    }
    // Block User
    public boolean blockUser(User blocked) {
        boolean result = friendService.blockUser(getCurrentUser().getUserId(), blocked.getUserId());
        fetchAllUsers();
        return result;
    }

    public boolean unblockUser(User unblocked) {
        boolean result = friendService.unblockUser(getCurrentUser().getUserId(), unblocked.getUserId());
        fetchAllUsers();
        return result;
    }
    public boolean cancelFriendRequest(User receiver) {
        boolean result = friendService.cancelFriendRequest(getCurrentUser().getUserId(), receiver.getUserId());
        fetchAllUsers();
        return result;
    }

    // Suggest Friends
    public ArrayList<User> suggestFriends() {
        return LegacyMapper.toLegacyUsers(friendService.suggestions(getCurrentUser().getUserId()));
    }

    public ArrayList<String> FriendStatus() {
        return new ArrayList<>(friendService.friendStatus(getCurrentUser().getUserId()));
    }

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public UserDatabase getUserDatabase() {
        return userDatabase;
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
    public ArrayList<User> getFriendsById(ArrayList<String> UsersId){
        ArrayList<User> Users =new ArrayList<>();
        for(String userId: UsersId ){
            Users.add(getUserById(userId));
        }
        return Users;

    }
    public void setAllUsers(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
    }
   public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public void fetchAllUsers(){
        setAllUsers(userDatabase.readUsersFromFile());
        setCurrentUser(getUserById(currentUser.getUserId()));
    }
    public User getCurrentUser() {
        return getUserById(currentUser.getUserId());
    }
}
