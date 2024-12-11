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
    public void createGroup(String name,String bio,ArrayList<String> membersIds,ArrayList<String> adminsIds,String ownerId,String iconPath,String coverPath){
        Group group = new Group(getGroupId(),name,bio,membersIds,adminsIds,ownerId,iconPath,coverPath);
    }

    //adds a user to a given group
    public void addMemberToGroup(Group group,String userId){
        group.getGroupMembersIds().add(userId);
    }
    //removes user from
    public void removeUserFromGroup(Group group,User user){
    }


    //promotes member --to--> admin
    public void promoteMemberToAdmin(Group group,String userId){
        group.getGroupMembersIds().remove(userId);
        group.getGroupAdminsIds().add(userId);
    }
    //demotes admin --to--> member
    private void demoteAdminToMember(Group group,String userId){
        group.getGroupAdminsIds().remove(userId);
        group.getGroupMembersIds().add(userId);
    }

    private void removeMemberFromGroup(Group group,String userId){
        group.getGroupMembersIds().remove(userId);
    }
    private void removeAdminFromGroup(Group group,String userId){
        group.getGroupAdminsIds().remove(userId);
    }
    private void removeOwner(Group group){
        if (!group.getGroupAdminsIds().isEmpty())group.setGroupOwnerId(group.getGroupAdminsIds().get(0));
        else if(!group.getGroupMembersIds().isEmpty())group.setGroupOwnerId(group.getGroupMembersIds().get(0));
        else deleteGroup(group);
    }
    public void deleteGroup(Group group){
        ArrayList<Group> groups = groupDatabase.readGroupsFromFile();
        groups.remove(group);
        groupDatabase.saveGroupsToFile(groups);
    }
    public void addPostToGroup(Posts post){
        
    }
    public void removePostFromGroup(){

    }

}
