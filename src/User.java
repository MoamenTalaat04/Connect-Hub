import java.util.*;
public class User {
    private String userId;
    private String profilePhotoPath;
    private String coverPhotoPath;
    private String bio;
    private String hashedPassword;
    private String email;
    private String username;
    private String status;
    private String dob;
    private ArrayList<String> friends;
    private ArrayList<String> blocked;
    private ArrayList<String> pendingRequests;
    private ArrayList<notificationData>notification;
    public User() {
    }
    public User(String userId, ArrayList<String> blocked, ArrayList<String> pendingRequests, ArrayList<String> friends, String status,String dob,String username, String email, String hashedPassword, String coverPhotoPath, String bio, String profilePhotoPath,ArrayList<notificationData>notifications) {

        this.userId = userId;
        this.blocked = blocked != null ? blocked : new ArrayList<>();
        this.pendingRequests = pendingRequests != null ? pendingRequests : new ArrayList<>();

        this.friends = friends != null ? friends : new ArrayList<>();
        this.status = status;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.coverPhotoPath = coverPhotoPath;
        this.bio = bio;
        this.dob=dob;
        this.profilePhotoPath = profilePhotoPath;
        this.notification=notifications!= null ? notifications : new ArrayList<notificationData>();

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCoverPhotoPath() {
        return coverPhotoPath;
    }

    public void setCoverPhotoPath(String coverPhotoPath) {
        this.coverPhotoPath = coverPhotoPath;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public ArrayList<String> getBlocked() {
        return blocked;
    }

    public void setBlocked(ArrayList<String> blocked) {
        this.blocked = blocked;
    }

    public ArrayList<String> getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(ArrayList<String> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
    public ArrayList<notificationData> getNotification() {
        return notification;
    }

    public void setNotification(ArrayList<notificationData> notifications){
        this.notification=notifications;
    }
    public void addNotification(notificationData notification){
        if (this.notification==null){
            this.notification=new ArrayList<>();
        }
        this.notification.add(notification);
    }
}

