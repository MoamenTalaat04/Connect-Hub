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
    private ArrayList<Posts> posts;

    //Constructor that creates group
    public Group(){}
    public Group(String groupName, String groupId, ArrayList<String> groupMembersIds, String groupBio, ArrayList<String> groupAdminsIds, String groupOwnerId, String groupIconPath, ArrayList<Posts> posts, String groupCoverPath) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.groupMembersIds = groupMembersIds;
        this.groupBio = groupBio;
        this.groupAdminsIds = groupAdminsIds;
        this.groupOwnerId = groupOwnerId;
        this.groupIconPath = groupIconPath;
        this.posts = posts;
        this.groupCoverPath = groupCoverPath;
    }

    //Getters

    public String getGroupName() {
        return groupName;
    }

    public String getGroupId() {
        return groupId;
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

    public ArrayList<Posts> getPosts() {
        return posts;
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

    public void setPosts(ArrayList<Posts> posts) {
        this.posts = posts;
    }
}
