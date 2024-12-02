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
    private ArrayList<User> friends;
    private ArrayList<User> blocked;
    private ArrayList<User> pendingRequests;

    public User(String userId, ArrayList<User> blocked, ArrayList<User> pendingRequests, ArrayList<User> friends, String status, String username, String email, String hashedPassword, String coverPhotoPath, String bio, String profilePhotoPath) {

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
        this.profilePhotoPath = profilePhotoPath;
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

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public ArrayList<User> getBlocked() {
        return blocked;
    }

    public void setBlocked(ArrayList<User> blocked) {
        this.blocked = blocked;
    }

    public ArrayList<User> getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(ArrayList<User> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }
}

