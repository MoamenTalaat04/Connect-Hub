package com.connecthub.factory;

import com.connecthub.events.EventBus;
import com.connecthub.events.SimpleEventBus;
import com.connecthub.repository.ContentRepository;
import com.connecthub.repository.ConversationRepository;
import com.connecthub.repository.GroupRepository;
import com.connecthub.repository.NotificationRepository;
import com.connecthub.repository.UserRepository;
import com.connecthub.repository.json.JsonContentRepository;
import com.connecthub.repository.json.JsonConversationRepository;
import com.connecthub.repository.json.JsonGroupRepository;
import com.connecthub.repository.json.JsonNotificationRepository;
import com.connecthub.repository.json.JsonUserRepository;
import com.connecthub.service.AccountService;
import com.connecthub.service.ChatService;
import com.connecthub.service.FriendService;
import com.connecthub.service.GroupService;
import com.connecthub.service.NewsFeedService;
import com.connecthub.service.NotificationService;
import com.connecthub.service.ProfileService;
import com.connecthub.service.impl.DefaultAccountService;
import com.connecthub.service.impl.DefaultChatService;
import com.connecthub.service.impl.DefaultFriendService;
import com.connecthub.service.impl.DefaultGroupService;
import com.connecthub.service.impl.DefaultNewsFeedService;
import com.connecthub.service.impl.DefaultNotificationService;
import com.connecthub.service.impl.DefaultProfileService;
import com.connecthub.service.impl.NotificationEventSubscriber;

public class ConnectHubFactory {
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;
    private final GroupRepository groupRepository;
    private final ConversationRepository conversationRepository;
    private final NotificationRepository notificationRepository;
    private final EventBus eventBus;

    private final AccountService accountService;
    private final FriendService friendService;
    private final NewsFeedService newsFeedService;
    private final ProfileService profileService;
    private final GroupService groupService;
    private final ChatService chatService;
    private final NotificationService notificationService;

    public ConnectHubFactory() {
        this.userRepository = new JsonUserRepository("users.json");
        this.contentRepository = new JsonContentRepository("posts.json", "stories.json");
        this.groupRepository = new JsonGroupRepository("groups.json");
        this.conversationRepository = new JsonConversationRepository("conversations.json");
        this.notificationRepository = new JsonNotificationRepository("notifications.json");
        this.eventBus = new SimpleEventBus();

        this.notificationService = new DefaultNotificationService(notificationRepository);
        this.friendService = new DefaultFriendService(userRepository, eventBus);
        this.accountService = new DefaultAccountService(userRepository);
        this.newsFeedService = new DefaultNewsFeedService(contentRepository, userRepository, friendService, eventBus);
        this.profileService = new DefaultProfileService(userRepository, contentRepository);
        this.groupService = new DefaultGroupService(groupRepository, userRepository, eventBus);
        this.chatService = new DefaultChatService(conversationRepository, eventBus);

        NotificationEventSubscriber subscriber = new NotificationEventSubscriber(notificationService);
        eventBus.subscribe("chat.message.sent", subscriber);
        eventBus.subscribe("friend.request.sent", subscriber);
        eventBus.subscribe("group.joined", subscriber);
        eventBus.subscribe("post.created", subscriber);
    }

    public UserRepository userRepository() {
        return userRepository;
    }

    public AccountService accountService() {
        return accountService;
    }

    public FriendService friendService() {
        return friendService;
    }

    public NewsFeedService newsFeedService() {
        return newsFeedService;
    }

    public ProfileService profileService() {
        return profileService;
    }

    public GroupService groupService() {
        return groupService;
    }

    public ChatService chatService() {
        return chatService;
    }

    public NotificationService notificationService() {
        return notificationService;
    }
}
