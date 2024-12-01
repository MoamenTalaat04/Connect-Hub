import java.util.*;

public class FriendManagement {
    protected Map<String, List<String>> friendsMap = new HashMap<>();//userID, friendsIDs
    protected Map<String, List<String>> blockedMap = new HashMap<>();//userID, blockedIDs
    protected ArrayList<String> users = new ArrayList<>(); // all users





    public void sendFriendRequest(String sender, String receiver) {
        if (friendsMap.containsKey(sender) && friendsMap.get(sender).contains(receiver)) {
           // System.out.println("Already friends.");
            return;
        }
        if (blockedMap.containsKey(receiver) && blockedMap.get(receiver).contains(sender)) {
           // System.out.println("You are blocked by the receiver.");
             return;
        }
        //System.out.println(sender + " sent a friend request to " + receiver);
        //pending request should be implemented

    }


    public void acceptFriendRequest(String sender, String receiver) {
        if (!users.contains(sender) || !users.contains(receiver)) {
            //System.out.println("Either sender or receiver does not exist.");
            return;
        }
        if (friendsMap.containsKey(receiver) && friendsMap.get(receiver).contains(sender)) {
            //System.out.println("Already friends.");
            return;
        }

        friendsMap.get(receiver).add(sender);
        friendsMap.get(sender).add(receiver);
        //System.out.println(receiver + " accepted " + sender + "'s friend request.");
    }

    public boolean removeFriend(String user, String friend) {
        return friendsMap.containsKey(user) && friendsMap.get(user).remove(friend) && friendsMap.get(friend).remove(user); // remove friend from user's friend list(returns true if removed successfully)

    }

    public void blockUser(String blocker, String blocked) {

        if (blockedMap.get(blocker).contains(blocked)) {
            System.out.println(blocked + " is already blocked by " + blocker);
            return;
        }
        blockedMap.get(blocker).add(blocked);
        removeFriend(blocker, blocked);

    }

    public List<String> suggestFriends(String userId) {
        if (!users.contains(userId)) {
            System.out.println("User does not exist.");
            return new ArrayList<>();
        }

        List<String> suggestedFriends = new ArrayList<>(users);
        //remove friends and blocked users
        if (friendsMap.containsKey(userId)) {
            suggestedFriends.removeAll(friendsMap.get(userId));
        }
        if (blockedMap.containsKey(userId)) {
            suggestedFriends.removeAll(blockedMap.get(userId));
        }
        suggestedFriends.remove(userId); // remove the user itself

        return suggestedFriends;
    }

}
