import java.util.*;

public class Group {
    private String groupId;
    private String groupName;
    private String groupBio;
    private ArrayList<User> groupMembers;
    private ArrayList<User> groupAdmins;
    private User groupOwner;
    private String groupIconPath;
    private String groupCoverPath;

    //Constructor that creates group

    public Group(String groupId, String groupName, String groupBio, ArrayList<User> groupMembers, ArrayList<User> groupAdmins, User groupOwner, String groupIconPath, String groupCoverPath) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupBio = groupBio;
        this.groupMembers = groupMembers;
        this.groupAdmins = groupAdmins;
        this.groupOwner = groupOwner;
        this.groupIconPath = groupIconPath;
        this.groupCoverPath = groupCoverPath;
    }

    //Getters

    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupBio() {
        return groupBio;
    }

    public ArrayList<User> getGroupMembers() {
        return groupMembers;
    }

    public ArrayList<User> getGroupAdmins() {
        return groupAdmins;
    }

    public User getGroupOwner() {
        return groupOwner;
    }

    public String getGroupIconPath() {
        return groupIconPath;
    }

    public String getGroupCoverPath() {
        return groupCoverPath;
    }

    //Setters

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupBio(String groupBio) {
        this.groupBio = groupBio;
    }

    public void setGroupMembers(ArrayList<User> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public void setGroupAdmins(ArrayList<User> groupAdmins) {
        this.groupAdmins = groupAdmins;
    }

    public void setGroupOwner(User groupOwner) {
        this.groupOwner = groupOwner;
    }

    public void setGroupIconPath(String groupIconPath) {
        this.groupIconPath = groupIconPath;
    }

    public void setGroupCoverPath(String groupCoverPath) {
        this.groupCoverPath = groupCoverPath;
    }
}
