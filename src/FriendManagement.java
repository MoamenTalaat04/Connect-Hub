import java.util.*;

public class FriendManagement {
    private ArrayList<User> allUsers;  // Store User objects, with userId as key
    private User currentUser;  // The current user
    private UserDatabase userDatabase;

    public FriendManagement(User currentUser,ArrayList<User> allUsers) {
        this.currentUser = currentUser;  // Initialize currentUser in the constructor
        this.userDatabase = new UserDatabase();
        this.allUsers =allUsers;
    }

    // Send Friend Request
    public boolean sendFriendRequest(User receiver) {
        // Check if already friends or blocked
        if (currentUser.getFriends().contains(receiver.getUserId())) {
            return false;
        }
        if (receiver.getBlocked().contains(currentUser.getUserId())) {
            return false;
        }
        // Add to pending requests
        receiver.getPendingRequests().add(currentUser.getUserId());
        userDatabase.saveUsersToFile(allUsers);
        return true;
    }

    public ArrayList<User> SentRequestsFromUser() {
        ArrayList<User> sentRequests = new ArrayList<>();
        for(User user : allUsers)
        {
            if(user.getPendingRequests().contains(currentUser.getUserId()))
            {
                sentRequests.add(user);
            }
        }
        return sentRequests;
    }
    public ArrayList<User> ReceivedRequestsForUser() {
        return getUsersById(currentUser.getPendingRequests());
    }

    // Accept Friend Request
    public boolean acceptFriendRequest(User sender) {
        // Check if there is a pending request
        if (!currentUser.getPendingRequests().contains(sender.getUserId())) {
            System.out.println("No pending friend request from " + sender.getUserId());
            return false;
        }
        // Add to friends list
        currentUser.getFriends().add(sender.getUserId());
        sender.getFriends().add(currentUser.getUserId());
        // Remove from pending requests
        currentUser.getPendingRequests().remove(sender.getUserId());
        userDatabase.saveUsersToFile(allUsers);
        return true;
    }

    public boolean rejectFriendRequest(User sender) {
        if (currentUser.getPendingRequests().remove(sender.getUserId())) {
            userDatabase.saveUsersToFile(allUsers);
            return true;
        }
        return false;
    }

    // Remove Friend
    public boolean removeFriend(User friend) {
        boolean removedFromCurrentUser = currentUser.getFriends().remove(friend.getUserId());
        boolean removedFromFriend = friend.getFriends().remove(currentUser.getUserId());
        userDatabase.saveUsersToFile(allUsers);
        return removedFromCurrentUser && removedFromFriend;
    }
    public User getCurrentUser() {
        return currentUser;
    }

    // Block User
    public boolean blockUser(User blocked) {
        if (currentUser.getBlocked().contains(blocked.getUserId())) {
            System.out.println(blocked.getUserId() + " is already blocked by " + currentUser.getUserId());
            return false;
        }
        // Add to blocked list
        currentUser.getBlocked().add(blocked.getUserId());
        // Remove from friends if present
        removeFriend(blocked);
        userDatabase.saveUsersToFile(allUsers);
        return true;
    }

    public boolean unblockUser(User unblocked) {
        if (currentUser.getBlocked().remove(unblocked.getUserId())) {
            userDatabase.saveUsersToFile(allUsers);
            return true;
        }
        return false;
    }
    public boolean cancelFriendRequest(User receiver) {
        if (receiver.getPendingRequests().remove(currentUser.getUserId())) {
            userDatabase.saveUsersToFile(allUsers);
            return true;
        }
        return false;
    }

    // Suggest Friends
    public ArrayList<User> suggestFriends() {
        ArrayList<User> suggestedFriends = new ArrayList<>(allUsers);
        // Remove current user, his friends, and blocked users
        suggestedFriends.remove(currentUser); // Remove the current user himself
        suggestedFriends.removeAll(getUsersById(currentUser.getFriends())); // Remove friends
        suggestedFriends.removeAll(getUsersById(currentUser.getBlocked())); // Remove blocked users

        return suggestedFriends;
    }

    public ArrayList<String> FriendStatus() {
        ArrayList<String> friendStatus = new ArrayList<>();
        for (User friend : getUsersById(currentUser.getFriends())) {
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

}
