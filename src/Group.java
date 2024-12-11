import java.util.*;

public class Group {
    private String groupId;
    private String groupName;
    private String groupBio;
    private ArrayList<User> groupMembers;
    private String groupIconPath;
    private String groupCoverPath;

    //Constructor that creates group
    public Group(String groupId, String groupName, String groupBio, ArrayList<User> groupMembers, String groupIconPath, String groupCoverPath) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupBio = groupBio;
        this.groupMembers = groupMembers;
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

    public String getGroupIconPath() {
        return groupIconPath;
    }

    public String getGroupCoverPath() {
        return groupCoverPath;
    }
    //Setters

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupBio(String groupBio) {
        this.groupBio = groupBio;
    }

    public void setGroupMembers(ArrayList<User> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public void setGroupIconPath(String groupIconPath) {
        this.groupIconPath = groupIconPath;
    }

    public void setGroupCoverPath(String groupCoverPath) {
        this.groupCoverPath = groupCoverPath;
    }
}
