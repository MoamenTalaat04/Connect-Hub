import com.connecthub.model.Group;

import java.util.ArrayList;

public class GroupManagement {
    private final com.connecthub.service.GroupService groupService;

    public GroupManagement() {
        this.groupService = ConnectHubContext.factory().groupService();
    }

    public Group createGroup(User owner, String name, String description) {
        return groupService.createGroup(owner.getUserId(), name, description);
    }

    public boolean joinGroup(String groupId, User user) {
        return groupService.joinGroup(groupId, user.getUserId());
    }

    public boolean leaveGroup(String groupId, User user) {
        return groupService.leaveGroup(groupId, user.getUserId());
    }

    public ArrayList<User> viewMembers(String groupId) {
        return LegacyMapper.toLegacyUsers(groupService.getMembers(groupId));
    }

    public boolean postInGroup(String groupId, User author, String content, String imagePath) {
        return groupService.addPost(groupId, author.getUserId(), content, imagePath);
    }
}
