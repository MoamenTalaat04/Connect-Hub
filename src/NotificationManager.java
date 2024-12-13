import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class NotificationManager {

    UserDatabase userDatabase;
    public NotificationManager() {
        this.userDatabase=UserDatabase.getInstance();
    }

    // Add Friend Request Notification
    public void addFriendRequestNotification(String toUserId, String fromUserId, String fromPhoto) {
        String currentDate = getCurrentDate();
        notificationData notification = new notificationData(
                fromUserId,
                fromPhoto,
                "Sent you a friend request",
                currentDate
        );
        addNotification(toUserId, notification);
    }

    // Add Promoted or Demoted Notification
    public void promotedOrDemotedFromGroupNotification(String toUserId, String groupID, String fromPhoto, boolean isPromoted) {
        String currentDate = getCurrentDate();
        String description = isPromoted ? "You have been promoted in the group" : "You have been demoted in the group";
        notificationData notification = new notificationData(
                groupID,
                fromPhoto,
                description,
                currentDate
        );
        addNotification(toUserId, notification);
    }

    // Add Post From Group Notification
    public void postFromGroupNotification(String toUserId, String groupID, String groupPhoto) {
        String currentDate = getCurrentDate();
        notificationData notification = new notificationData(
                groupID,
                groupPhoto,
                "New post in the group",
                currentDate
        );
        addNotification(toUserId, notification);
    }

    // Generic method to add a notification to a user
    private void addNotification(String toUserId, notificationData notification) {
        ArrayList<User> users = userDatabase.readUsersFromFile();
        boolean userFound = false; // Debugging flag
        for (User user : users) {
            if (user.getUserId().equals(toUserId)) {
                userFound = true;
                if (user.getNotification() == null) {
                    user.setNotification(new ArrayList<>());
                }
                user.getNotification().add(notification);
                System.out.println("Added notification to user: " + toUserId);
                break;
            }
        }
        if (!userFound) {
            System.err.println("User with ID \"" + toUserId + "\" not found in the file.");
            return;
        }
        userDatabase.saveUsersToFile(users); // Save updated users back to JSON
        System.out.println("User notifications saved successfully.");
    }

    // Delete Notification by sender
    public void deleteNotification(String userId, String from) {
        ArrayList<User> users = userDatabase.readUsersFromFile();
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                List<notificationData> notifications = user.getNotification();
                if (notifications == null || notifications.isEmpty()) {
                    System.out.println("No notifications found for user: " + userId);
                    return;
                }

                boolean removed = notifications.removeIf(n -> n.getfrom().equals(from));

                if (removed) {
                    userDatabase.saveUsersToFile(users);
                    System.out.println("Notification(s) from \"" + from + "\" deleted for user: " + userId);
                } else {
                    System.out.println("No notifications from \"" + from + "\" found for user: " + userId);
                }
                return;
            }
        }
        System.err.println("User with ID \"" + userId + "\" not found.");
    }

    // Utility method to get the current date and time
    private String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}
