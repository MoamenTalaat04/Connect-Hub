import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

//this class handles the operations related to the group class
public class GroupManagement {
    private  GroupDatabase groupDatabase;
    private  String currentUserId;
    private MainContentCreation contentCreation;
    private ArrayList<Group> allGroups;
    private NotificationManager notificationManager ;
    //constructor method
    //takes a GroupDatabase as an argument and creates and object of GroupManagement that holds that object
    //GroupManagement object will contain the groups inside itself
    //we will construct an instance of GroupManagement when ever we want to make an operation related to groups
    public GroupManagement(User currentUser) {
        this.groupDatabase = GroupDatabase.getInstance();
        this.currentUserId=currentUser.getUserId();
        this.contentCreation  = new MainContentCreation();
        this.notificationManager = new NotificationManager();
    }

    //generates an id for the group
    private String getGroupId(){
        return String.valueOf(groupDatabase.readGroupsFromFile().size()+1);
    }

    //creates a new group with the given data
    public void createGroup(String name,String bio,ArrayList<String> membersIds,ArrayList<String> adminsIds,ArrayList<Posts> posts,String ownerId,String iconPath,String coverPath){
        Group group = new Group(name,getGroupId(),membersIds,bio,adminsIds,ownerId,iconPath,posts,coverPath);
        groupDatabase.saveGroupToFile(group);
    }

    //adds a user to a given group
    public void addMemberToGroup(Group group,String userId){
        fetchAllGroups();
        Group g = getMyGroupVersion(group);
        if (!g.getGroupMembersIds().contains(userId)) {
            g.getGroupMembersIds().add(userId);
            groupDatabase.saveGroupsToFile(allGroups);
    }}
    //checks the user's type and calls the proper deletion method
    //incase that the user we want to remove is owner and there is no other user in the group this method will delete the group
    public void removeUserFromGroup(Group group,String userId){
        fetchAllGroups();
        Group g = getMyGroupVersion(group);
        if(g.getGroupOwnerId().equals(userId)) removeOwner(group);
        else if(g.getGroupAdminsIds().contains(userId))removeAdminFromGroup(g,userId);
        else if (g.getGroupMembersIds().contains(userId))removeMemberFromGroup(g,userId);
        groupDatabase.saveGroupsToFile(allGroups);
    }


    //promotes member --to--> admin
    public void promoteMemberToAdmin(Group group,String userId){
        fetchAllGroups();
        Group g = getMyGroupVersion(group);
        if(!g.getGroupAdminsIds().contains(userId)){
            g.getGroupMembersIds().remove(userId);
            g.getGroupAdminsIds().add(userId);
            notificationManager.promotedOrDemotedFromGroupNotification(userId,g.getGroupId(),g.getGroupIconPath(),true);
            groupDatabase.saveGroupsToFile(allGroups);
        }

    }
    //demotes admin --to--> member
    private void demoteAdminToMember(Group group,String userId){
        fetchAllGroups();
        Group g = getMyGroupVersion(group);
        g.getGroupAdminsIds().remove(userId);
        g.getGroupMembersIds().add(userId);
        groupDatabase.saveGroupsToFile(allGroups);
        notificationManager.promotedOrDemotedFromGroupNotification(userId,g.getGroupId(),g.getGroupIconPath(),false);
    }
    //these methods (the next three) are private methods used to delete different type of user
    //removes normal member
    private void removeMemberFromGroup(Group group,String userId){
        fetchAllGroups();
        Group g = getMyGroupVersion(group);
        g.getGroupMembersIds().remove(userId);
        groupDatabase.saveGroupsToFile(allGroups);
    }
    //removes admin
    private void removeAdminFromGroup(Group group,String userId){
        fetchAllGroups();
        Group g = getMyGroupVersion(group);
        g.getGroupAdminsIds().remove(userId);
        groupDatabase.saveGroupsToFile(allGroups);
    }
    //removes Owner
    private void removeOwner(Group group){
        fetchAllGroups();
        Group g = getMyGroupVersion(group);
        if (!g.getGroupAdminsIds().isEmpty())g.setGroupOwnerId(g.getGroupAdminsIds().get(0));
        else if(!g.getGroupMembersIds().isEmpty())g.setGroupOwnerId(g.getGroupMembersIds().get(0));
        else deleteGroup(g);
        groupDatabase.saveGroupsToFile(allGroups);

    }
    //method used to delete the Group
    public void deleteGroup(Group group){
        fetchAllGroups();
        Group g = getMyGroupVersion(group);
        ArrayList<Group> groups = groupDatabase.readGroupsFromFile();
        if(groups.contains(g)){
            groups.remove(g);
            groupDatabase.saveGroupsToFile(allGroups);
        }
    }
    //method used to add a post to the group
    public void addPost(String content, String imagePath,Group group) {
        try {
            fetchAllGroups();
            Group g = getMyGroupVersion(group);
            g.getPosts().add(contentCreation.createPost(this.currentUserId, content, imagePath));
            groupDatabase.saveGroupsToFile(allGroups);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //method used to delete a post from the group
    public void removePostFromGroup(Group group,Posts post){
        fetchAllGroups();
        Group g = getMyGroupVersion(group);
        g.getPosts().remove(post);
        try {
            ArrayList<Posts> posts = contentCreation.readPosts();
            for(Posts p : posts){
             if(p.getContentId().equals(post.getContentId())){
                 posts.remove(p);
                 contentCreation.saveContentToFile(posts);
                 break;
             }
            }
            groupDatabase.saveGroupsToFile(allGroups);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Group> getMyGroups(){
        fetchAllGroups();
        ArrayList<Group> myGroups = new ArrayList<>();
        for (Group g : allGroups){
            if(g.getGroupMembersIds().contains(currentUserId) || g.getGroupAdminsIds().contains(currentUserId) || g.getGroupOwnerId().equals(currentUserId))myGroups.add(g);
        }
        return myGroups;

    }

    public ArrayList<Group> Suggestions(){
        fetchAllGroups();
        ArrayList<Group> suggestions = new ArrayList<>();
        for (Group g : allGroups){
            if(!g.getGroupMembersIds().contains(currentUserId) && !g.getGroupAdminsIds().contains(currentUserId) && !g.getGroupOwnerId().equals(currentUserId))suggestions.add(g);
        }
        return suggestions;
    }

   private void fetchAllGroups(){
        try {
            allGroups = groupDatabase.readGroupsFromFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

   private Group getMyGroupVersion(Group group){
        for (Group g : allGroups){
            if(g.getGroupId().equals(group.getGroupId()))return g;
        }
        return null;
   }

   public ArrayList<Posts> getGroupPosts(Group group){
        fetchAllGroups();
        Group g = getMyGroupVersion(group);
        return g.getPosts();
   }
   public ArrayList<Group> getAllGroups(){
        fetchAllGroups();
        return allGroups;
   }

}
