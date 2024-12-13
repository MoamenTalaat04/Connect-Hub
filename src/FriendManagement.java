import java.util.*;

public class FriendManagement {
    private ArrayList<User> allUsers;  // Store User objects, with userId as key
    private User currentUser;  // The current user
    private UserDatabase userDatabase;
    private NotificationManager notificationManager;

    public FriendManagement(User currentUser) {
        this.userDatabase =UserDatabase.getInstance();
        this.allUsers = userDatabase.readUsersFromFile();
        this.currentUser = getUserById(currentUser.getUserId());
        this.notificationManager = new NotificationManager();
    }

    // Send Friend Request
    public boolean sendFriendRequest(User receiver) {
        fetchAllUsers();
        receiver = getUserById(receiver.getUserId());
        // Check if already friends or blocked
        if (getCurrentUser().getFriends().contains(receiver.getUserId())) {
            return false;
        }
        if (receiver.getBlocked().contains(getCurrentUser().getUserId())) {
            return false;
        }
        // Add to pending requests
        getUserById(receiver.getUserId()).getPendingRequests().add(getCurrentUser().getUserId());
        userDatabase.saveUsersToFile(allUsers);
        notificationManager.addFriendRequestNotification(receiver.getUserId(), getCurrentUser().getUserId(), getCurrentUser().getProfilePhotoPath());
        return true;
    }

    public ArrayList<User> SentRequestsFromUser() {
        fetchAllUsers();
        ArrayList<User> sentRequests = new ArrayList<>();
        for(User user : allUsers)
        {
            if(user.getPendingRequests().contains(getCurrentUser().getUserId()))
            {
                sentRequests.add(user);
            }
        }
        return sentRequests;
    }
    public ArrayList<User> ReceivedRequestsForUser() {
        return getUsersById(getCurrentUser().getPendingRequests());
    }

    // Accept Friend Request
    public boolean acceptFriendRequest(User sender) {
        fetchAllUsers();
        sender = getUserById(sender.getUserId());
        // Check if there is a pending request
        if (!getCurrentUser().getPendingRequests().contains(sender.getUserId())) {
            System.out.println("No pending friend request from " + sender.getUserId());
            return false;
        }
        // Add to friends list
        getCurrentUser().getFriends().add(sender.getUserId());
        sender.getFriends().add(getCurrentUser().getUserId());
        // Remove from pending requests
        getCurrentUser().getPendingRequests().remove(sender.getUserId());
        userDatabase.saveUsersToFile(allUsers);
        notificationManager.deleteNotification(notificationManager.getNotificationByDate(getCurrentUser().getUserId(),sender.getUserId(),"Sent you a friend request"),getCurrentUser().getUserId());
        return true;
    }

    public boolean rejectFriendRequest(User sender) {
        fetchAllUsers();
        sender = getUserById(sender.getUserId());
        if (getCurrentUser().getPendingRequests().remove(sender.getUserId())) {
            userDatabase.saveUsersToFile(allUsers);
            notificationManager.deleteNotification(notificationManager.getNotificationByDate(getCurrentUser().getUserId(),sender.getUserId(),"Sent you a friend request"),getCurrentUser().getUserId());
            return true;
        }
        return false;
    }

    // Remove Friend
    public boolean removeFriend(User friend) {
        fetchAllUsers();
        friend = getUserById(friend.getUserId());
        boolean removedFromCurrentUser = getCurrentUser().getFriends().remove(friend.getUserId());
        boolean removedFromFriend = friend.getFriends().remove(getCurrentUser().getUserId());
        userDatabase.saveUsersToFile(allUsers);
        return removedFromCurrentUser && removedFromFriend;
    }
    // Block User
    public boolean blockUser(User blocked) {
        fetchAllUsers();
        blocked = getUserById(blocked.getUserId());
        if (getCurrentUser().getBlocked().contains(blocked.getUserId())) {
            System.out.println(blocked.getUserId() + " is already blocked by " + getCurrentUser().getUserId());
            return false;
        }
        // Add to blocked list
        getCurrentUser().getBlocked().add(blocked.getUserId());
        // Remove from friends if present
        removeFriend(blocked);
        userDatabase.saveUsersToFile(allUsers);
        return true;
    }

    public boolean unblockUser(User unblocked) {
        fetchAllUsers();
        unblocked = getUserById(unblocked.getUserId());
        if (getCurrentUser().getBlocked().remove(unblocked.getUserId())) {
            userDatabase.saveUsersToFile(allUsers);
            return true;
        }
        return false;
    }
    public boolean cancelFriendRequest(User receiver) {
        fetchAllUsers();
        receiver = getUserById(receiver.getUserId());
        if (receiver.getPendingRequests().remove(getCurrentUser().getUserId())) {
            userDatabase.saveUsersToFile(allUsers);
            notificationManager.deleteNotification(notificationManager.getNotificationByDate(receiver.getUserId(), currentUser.getUserId(), "Sent you a friend request"),getCurrentUser().getUserId());
            return true;
        }
        return false;
    }

    // Suggest Friends
    public ArrayList<User> suggestFriends() {
        fetchAllUsers();
        ArrayList<User> suggestedFriends = new ArrayList<>(allUsers);
        // Remove current user, his friends, and blocked users
        suggestedFriends.remove(getCurrentUser()); // Remove the current user himself
        suggestedFriends.removeAll(getUsersById(getCurrentUser().getFriends())); // Remove friends
        suggestedFriends.removeAll(getUsersById(getCurrentUser().getBlocked())); // Remove blocked users

        return suggestedFriends;
    }

    public ArrayList<String> FriendStatus() {
        fetchAllUsers();
        ArrayList<String> friendStatus = new ArrayList<>();
        for (User friend : getUsersById(getCurrentUser().getFriends())) {
            friendStatus.add(friend.getUsername() + " is " + friend.getStatus());
        }
        return friendStatus;
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
