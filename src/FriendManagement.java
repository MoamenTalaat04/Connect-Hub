import java.util.*;

public class FriendManagement {
    private ArrayList<User> allUsers;  // Store User objects, with userId as key
    private User currentUser;  // The current user
    private UserDatabase userDatabase;

    public FriendManagement(User currentUser) {
        this.currentUser = currentUser;  // Initialize currentUser in the constructor
        this.userDatabase = new UserDatabase();
        this.allUsers = userDatabase.readUsersFromFile();
    }

    // Send Friend Request
    public boolean sendFriendRequest(User receiver) {
        // Check if already friends or blocked
        if (currentUser.getFriends().contains(receiver)) {
            return false;
        }
        if (receiver.getBlocked().contains(currentUser)) {
            return false;
        }
        // Add to pending requests
        receiver.getPendingRequests().add(currentUser);
        userDatabase.saveUsersToFile(allUsers);
        return true;
    }

    public ArrayList<User> SentRequestsFromUser() {
        ArrayList<User> sentRequests = new ArrayList<>();
        for(User user : allUsers)
        {
            if(user.getPendingRequests().contains(currentUser))
            {
                sentRequests.add(user);
            }
        }
        return sentRequests;
    }
    public ArrayList<User> ReceivedRequestsForUser() {
        return currentUser.getPendingRequests();
    }

    // Accept Friend Request
    public boolean acceptFriendRequest(User sender) {
        // Check if there is a pending request
        if (!currentUser.getPendingRequests().contains(sender)) {
            System.out.println("No pending friend request from " + sender.getUserId());
            return false;
        }
        // Add to friends list
        currentUser.getFriends().add(sender);
        sender.getFriends().add(currentUser);
        // Remove from pending requests
        currentUser.getPendingRequests().remove(sender);
        userDatabase.saveUsersToFile(allUsers);
        return true;
    }

    public boolean rejectFriendRequest(User sender) {
        if (currentUser.getPendingRequests().remove(sender)) {
            userDatabase.saveUsersToFile(allUsers);
            return true;
        }
        return false;
    }

    // Remove Friend
    public boolean removeFriend(User friend) {
        boolean removedFromCurrentUser = currentUser.getFriends().remove(friend);
        boolean removedFromFriend = friend.getFriends().remove(currentUser);
        userDatabase.saveUsersToFile(allUsers);
        return removedFromCurrentUser && removedFromFriend;
    }
    public User getCurrentUser() {
        return currentUser;
    }

    // Block User
    public boolean blockUser(User blocked) {
        if (currentUser.getBlocked().contains(blocked)) {
            System.out.println(blocked.getUserId() + " is already blocked by " + currentUser.getUserId());
            return false;
        }
        // Add to blocked list
        currentUser.getBlocked().add(blocked);
        // Remove from friends if present
        removeFriend(blocked);
        userDatabase.saveUsersToFile(allUsers);
        return true;
    }

    public boolean unblockUser(User unblocked) {
        if (currentUser.getBlocked().remove(unblocked)) {
            userDatabase.saveUsersToFile(allUsers);
            return true;
        }
        return false;
    }
    public boolean cancelFriendRequest(User receiver) {
        if (receiver.getPendingRequests().remove(currentUser)) {
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
        suggestedFriends.removeAll(currentUser.getFriends()); // Remove friends
        suggestedFriends.removeAll(currentUser.getBlocked()); // Remove blocked users

        return suggestedFriends;
    }

    public ArrayList<String> FriendStatus() {
        ArrayList<String> friendStatus = new ArrayList<>();
        for (User friend : currentUser.getFriends()) {
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
}
