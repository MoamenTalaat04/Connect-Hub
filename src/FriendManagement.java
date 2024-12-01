import java.util.*;

public class FriendManagement {
    private ArrayList<User> allUsers;// Store User objects, with userId as key

    public FriendManagement() {
        allUsers = new ArrayList<>();
    }
    // Send Friend Request
    public boolean sendFriendRequest(User sender, User receiver) {
      // Check if already friends or blocked
        if (sender.getFriends().contains(receiver.getUserId())) {
            return false;
        }
        if (receiver.getBlocked().contains(sender.getUserId())) {
            return false;
        }
        // Add to pending requests
        receiver.getPendingRequests().add(sender.getUserId());
        return true;
    }

    // Accept Friend Request
    public boolean acceptFriendRequest(User sender, User receiver) {
        // Check if there is a pending request
        if (!receiver.getPendingRequests().contains(sender.getUserId())) {
            System.out.println("No pending friend request from " + sender.getUserId());
            return false;
        }
        // Add to friends list
        receiver.getFriends().add(sender.getUserId());
        sender.getFriends().add(receiver.getUserId());
        // Remove from pending requests
        receiver.getPendingRequests().remove(sender.getUserId());
        return true;
    }

    // Remove Friend
    public boolean removeFriend(User user, User friend) {
        boolean removedFromUser = user.getFriends().remove(friend.getUserId());
        boolean removedFromFriend = friend.getFriends().remove(user.getUserId());
        if (removedFromUser && removedFromFriend) {
            return true;
        } else {
            return false;
        }
    }

    // Block User
    public boolean blockUser(User blocker, User blocked) {

        if (blocker.getBlocked().contains(blocked.getUserId())) {
            System.out.println(blocked.getUserId() + " is already blocked by " + blocker.getUserId());
            return false;
        }
        // Add to blocked list
        blocker.getBlocked().add(blocked.getUserId());
        // Remove from friends if present
        removeFriend(blocker, blocked);
        return true;

    }

    // Suggest Friends
    public ArrayList<User> suggestFriends(User user) {
        ArrayList<User> suggestedFriends = new ArrayList<>(allUsers);
        // Remove user, his friends, and blocked users
        suggestedFriends.remove(user); // Remove the user himself
        suggestedFriends.removeAll(user.getFriends()); // Remove friends
        suggestedFriends.removeAll(user.getBlocked()); // Remove blocked users

        return suggestedFriends;
    }

}
