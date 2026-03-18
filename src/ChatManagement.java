import com.connecthub.model.Message;

import java.util.ArrayList;

public class ChatManagement {
    private final com.connecthub.service.ChatService chatService;

    public ChatManagement() {
        this.chatService = ConnectHubContext.factory().chatService();
    }

    public Message sendDirectMessage(User sender, User receiver, String text) {
        return chatService.sendMessage(sender.getUserId(), receiver.getUserId(), text);
    }

    public ArrayList<Message> getConversation(User userA, User userB) {
        return new ArrayList<>(chatService.getChatHistory(userA.getUserId(), userB.getUserId()));
    }
}
