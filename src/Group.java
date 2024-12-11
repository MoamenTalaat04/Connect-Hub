import java.util.*;

public class Group {
    private String groupId;
    private String groupName;
    private String groupBio;
    private ArrayList<String> groupMembersIds;
    private ArrayList<String> groupAdminsIds;
    private String groupOwnerId;
    private String groupIconPath;
    private String groupCoverPath;

    //Constructor that creates group

    public Group(String groupId, String groupName, String groupBio, ArrayList<String> groupMembersIds, ArrayList<String> groupAdminsIds, String groupOwnerId, String groupIconPath, String groupCoverPath) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupBio = groupBio;
        this.groupMembersIds = groupMembersIds;
        this.groupAdminsIds = groupAdminsIds;
        this.groupOwnerId = groupOwnerId;
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

    public ArrayList<String> getGroupMembersIds() {
        return groupMembersIds;
    }

    public ArrayList<String> getGroupAdminsIds() {
        return groupAdminsIds;
    }

    public String getGroupOwnerId() {
        return groupOwnerId;
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

    public void setGroupMembersIds(ArrayList<String> groupMembersIds) {
        this.groupMembersIds = groupMembersIds;
    }

    public void setGroupAdminsIds(ArrayList<String> groupAdminsIds) {
        this.groupAdminsIds = groupAdminsIds;
    }

    public void setGroupOwnerId(String groupOwnerId) {
        this.groupOwnerId = groupOwnerId;
    }

    public void setGroupIconPath(String groupIconPath) {
        this.groupIconPath = groupIconPath;
    }

    public void setGroupCoverPath(String groupCoverPath) {
        this.groupCoverPath = groupCoverPath;
    }
}
