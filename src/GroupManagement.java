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
    public void createGroup(String id,String name,String bio,String iconPath,String coverPath){
        Group group = new Group(id,name,bio,null,iconPath,coverPath);
    }
}
