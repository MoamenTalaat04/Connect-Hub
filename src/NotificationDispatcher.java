import com.connecthub.model.Notification;

import java.util.ArrayList;

public class NotificationDispatcher {
    private final com.connecthub.service.NotificationService notificationService;

    public NotificationDispatcher() {
        this.notificationService = ConnectHubContext.factory().notificationService();
    }

    public ArrayList<Notification> getUserNotifications(User user) {
        return new ArrayList<>(notificationService.forUser(user.getUserId()));
    }

    public void markAllRead(User user) {
        notificationService.markAllRead(user.getUserId());
    }
}
