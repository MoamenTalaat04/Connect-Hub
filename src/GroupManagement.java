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
    private String getGroupId(){
        return String.valueOf(groupDatabase.readGroupsFromFile().size()+1);
    }
    public void createGroup(String name,String bio,ArrayList<User> members,User owner,ArrayList<User> admins,String iconPath,String coverPath){
        Group group = new Group(getGroupId(),name,bio,members,admins,owner,iconPath,coverPath);
    }
    public void addUserToGroup(Group group,User user){
        group.getGroupMembers().add(user);
    }
    public void removeUserFromGroup(Group group,User user){
        group.getGroupMembers().
    }
    public void addAdmin(){

    }
    public void removeAdmin(){

    }
    public void deleteGroup(){

    }
    public void updateGroupInfo(){

    }
    public void addPostToGroup(){

    }
    public void removePostFromGroup(){

    }

}
