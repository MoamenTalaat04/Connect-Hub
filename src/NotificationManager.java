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
    ArrayList<User> allUsers;
    public NotificationManager() {
        this.userDatabase=UserDatabase.getInstance();
        this.allUsers = userDatabase.readUsersFromFile();
    }

    // Add Friend Request Notification
    public void addFriendRequestNotification(String toUserId, String fromUserId, String fromPhoto) {
        fetchAllUsers();
        String currentDate = getCurrentDate();
        notificationData notification = new notificationData(fromUserId, fromPhoto, "Sent you a friend request", currentDate);
        addNotification(toUserId, notification);
    }

    // Add Promoted or Demoted Notification
    public void promotedOrDemotedFromGroupNotification(String toUserId, String groupID, String fromPhoto, boolean isPromoted) {
        fetchAllUsers();
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
        fetchAllUsers();
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
        fetchAllUsers();
        for (User user : allUsers) {
            if (user.getUserId().equals(toUserId)) {
                if (user.getNotification() == null) {
                    user.setNotification(new ArrayList<>());
                }
                    user.getNotification().add(notification);
                    userDatabase.saveUsersToFile(allUsers);
                    System.out.println("Added notification to user: " + toUserId);
                    return;
            }
        }
        userDatabase.saveUsersToFile(allUsers); // Save updated users back to JSON
        System.out.println("User notifications saved successfully.");
    }

    // Delete Notification by sender
    public void deleteNotification(notificationData notification, String userId) {
        fetchAllUsers();
        ArrayList<User> users = userDatabase.readUsersFromFile();
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                ArrayList<notificationData> notifications = user.getNotification();
                for (notificationData n : notifications) {
                    if (n.getDate().equals(notification.getDate())) {
                        notifications.remove(n);
                        userDatabase.saveUsersToFile(users);
                        return;
                    }
                }

            }
        }
    }

    // Utility method to get the current date and time
    private String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }
    public void fetchAllUsers(){
        this.allUsers = userDatabase.readUsersFromFile();
    }

    public notificationData getNotificationByDate(String userId, String fromUserId, String description) {
        fetchAllUsers();
        for (User user : allUsers) {
            if (user.getUserId().equals(userId)) {
                ArrayList<notificationData> notifications = user.getNotification();
                for (notificationData notification : notifications) {
                    if (notification.getfrom().equals(fromUserId) && notification.getDescription().equals(description)) {
                        return notification;
                    }
                }
            }
        }
        return null;
    }
}
