import java.util.ArrayList;

//this class handles the operations related to the group class
public class GroupManagement {
    private  GroupDatabase groupDatabase;
    //constructor method
    //takes a GroupDatabase as an argument and creates and object of GroupManagement that holds that object
    //GroupManagement object will contain the groups inside itself
    //we will construct an instance of GroupManagement when ever we want to make an operation related to groups
    public GroupManagement(GroupDatabase groupDatabase) {
        this.groupDatabase = groupDatabase;
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
        if (!group.getGroupMembersIds().contains(userId)) {
        group.getGroupMembersIds().add(userId);
        saveChanges();
    }}
    //checks the user's type and calls the proper deletion method
    //incase that the user we want to remove is owner and there is no other user in the group this method will delete the group
    public void removeUserFromGroup(Group group,String userId){
        if(group.getGroupOwnerId().equals(userId)) removeOwner(group);
        else if(group.getGroupAdminsIds().contains(userId))removeAdminFromGroup(group,userId);
        else if (group.getGroupMembersIds().contains(userId))removeMemberFromGroup(group,userId);
    }


    //promotes member --to--> admin
    public void promoteMemberToAdmin(Group group,String userId){
        if(!group.getGroupAdminsIds().contains(userId)){
            group.getGroupMembersIds().remove(userId);
            group.getGroupAdminsIds().add(userId);
            saveChanges();
        }
    }
    //demotes admin --to--> member
    private void demoteAdminToMember(Group group,String userId){
        group.getGroupAdminsIds().remove(userId);
        group.getGroupMembersIds().add(userId);
        saveChanges();
    }
    //these methods (the next three) are private methods used to delete different type of user
    //removes normal member
    private void removeMemberFromGroup(Group group,String userId){
        group.getGroupMembersIds().remove(userId);
        saveChanges();
    }
    //removes admin
    private void removeAdminFromGroup(Group group,String userId){
        group.getGroupAdminsIds().remove(userId);
        saveChanges();
    }
    //removes Owner
    private void removeOwner(Group group){
        if (!group.getGroupAdminsIds().isEmpty())group.setGroupOwnerId(group.getGroupAdminsIds().get(0));
        else if(!group.getGroupMembersIds().isEmpty())group.setGroupOwnerId(group.getGroupMembersIds().get(0));
        else deleteGroup(group);
        saveChanges();
    }
    //method used to delete the Group
    public void deleteGroup(Group group){
        ArrayList<Group> groups = groupDatabase.readGroupsFromFile();
        if(groups.contains(group)){
            groups.remove(group);
            groupDatabase.saveGroupsToFile(groups);
        }
    }
    //method used to add a post to the group
    public void addPostToGroup(Group group,Posts post){
        group.getPosts().add(post);
        saveChanges();
    }
    //method used to delete a post from the group
    public void removePostFromGroup(Group group,Posts post){
        group.getPosts().remove(post);
        saveChanges();
    }
    //method to save any changes made to the group instantly
    //to make sure any changes made is visible to other user
    private void saveChanges() {
        groupDatabase.saveGroupsToFile(groupDatabase.readGroupsFromFile());
    }

}
