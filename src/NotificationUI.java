import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NotificationUI extends JFrame {
    DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private JScrollPane scroll;
    private JPanel panel;
    private JPanel Container;
    private NewsFeed newsfeed;
    private NotificationManager notificationManager;

    public NotificationUI(NewsFeed newsfeed) {
    this.newsfeed=newsfeed;
    this.notificationManager=new NotificationManager();
    setContentPane(Container);
    setTitle("Notification");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setVisible(true);
    setLocationRelativeTo(null);
    setSize(700,550);
    loadNoificationonPanel();

    }
    public void loadNoificationonPanel() {
        panel.removeAll();
        newsfeed.fetchAllUsers();
        ArrayList<notificationData> notifications = newsfeed.getCurrentUser().getNotification();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        for (notificationData notification : notifications) {
            if(notification.getDescription().equals("Sent you a friend request")) {
                panel.add(CreateReceivedRequestsPanel(notification));
                panel.add(Box.createVerticalStrut(10));
            } else if(notification.getDescription().equals("You have been promoted in the group") || notification.getDescription().equals("You have been demoted in the group")) {
                panel.add(createPromotionOrDemotionPanel(notification));
                panel.add(Box.createVerticalStrut(10));

            } else if (notification.getDescription().equals("New post in the group")) {
                panel.add(createNewPostPanel(notification));
                panel.add(Box.createVerticalStrut(10));

            }

        }
        scroll.setViewportView(panel);
        panel.revalidate();
        panel.repaint();
    }
    private JPanel createNewPostPanel(notificationData notification) {
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BorderLayout(10, 10));
        postPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        postPanel.setMaximumSize(new Dimension(400, 190));

        JLabel profilePictureLabel = createProfilePictureLabel(notification.getPhotopath());
        postPanel.add(profilePictureLabel, BorderLayout.WEST);

        JLabel postLabel = new JLabel(newsfeed.getGroupById(notification.getfrom()).getGroupName() + notification.getDescription());
        postLabel.setFont(new Font("Arial", Font.BOLD, 16));
        postPanel.add(postLabel, BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        JButton viewGroupButton = new JButton("View Group");
        viewGroupButton.setFont(new Font("Arial", Font.PLAIN, 12));
        viewGroupButton.addActionListener(e -> {
            notificationManager.deleteNotification(notification,newsfeed.getCurrentUser().getUserId());
            // new memberGroupUI(newsfeed.getGroupById(notification.getfrom()), newsfeed.getCurrentUser(), newsfeed);
            dispose();
        });
        buttonsPanel.add(viewGroupButton);
        postPanel.add(buttonsPanel, BorderLayout.EAST);
        return postPanel;
    }


    private JPanel createPromotionOrDemotionPanel(notificationData notification) {
        JPanel promotionPanel = new JPanel();
        promotionPanel.setLayout(new BorderLayout(10, 10));
        promotionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        promotionPanel.setMaximumSize(new Dimension(400, 190));

        JLabel profilePictureLabel = createProfilePictureLabel(notification.getPhotopath());
        promotionPanel.add(profilePictureLabel, BorderLayout.WEST);

        JLabel promotionLabel = new JLabel(newsfeed.getGroupById(notification.getfrom()).getGroupName() + notification.getDescription());
        promotionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        promotionPanel.add(promotionLabel, BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        JButton viewGroupButton = new JButton("View Group");
        viewGroupButton.setFont(new Font("Arial", Font.PLAIN, 12));
        viewGroupButton.addActionListener(e -> {
            notificationManager.deleteNotification(notification,newsfeed.getCurrentUser().getUserId());
           // new memberGroupUI(newsfeed.getGroupById(notification.getfrom()), newsfeed.getCurrentUser(), newsfeed);
            dispose();
        });
        buttonsPanel.add(viewGroupButton);
        promotionPanel.add(buttonsPanel, BorderLayout.EAST);
        return promotionPanel;
    }

    private JPanel CreateReceivedRequestsPanel(notificationData notification ){
        JPanel RequestsPanel = new JPanel();
        RequestsPanel.setLayout(new BorderLayout(10, 10));
        RequestsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        RequestsPanel.setMaximumSize(new Dimension(400, 190));

        JLabel profilePictureLabel = createProfilePictureLabel(notification.getPhotopath());
        RequestsPanel.add(profilePictureLabel, BorderLayout.WEST);

        JLabel friendLabel = new JLabel(newsfeed.getUsernameByID(notification.getfrom())+"Sent You a Friend Request");
        friendLabel.setFont(new Font("Arial", Font.BOLD, 16));
        RequestsPanel.add(friendLabel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        JButton acceptRequestButton = new JButton("Accept Request");
        acceptRequestButton.addActionListener(e -> {
            if (newsfeed.getFriendManagement().acceptFriendRequest(newsfeed.getFriendManagement().getUserById(notification.getfrom()))) {
                JOptionPane.showMessageDialog(this, "Friend Request Accepted.");
                loadNoificationonPanel();
            }
        });
        buttonsPanel.add(acceptRequestButton);

        JButton rejectRequestButton = new JButton("Reject Request");
        rejectRequestButton.addActionListener(e -> {
            if (newsfeed.getFriendManagement().rejectFriendRequest(newsfeed.getFriendManagement().getUserById(notification.getfrom()))) {
                JOptionPane.showMessageDialog(this, "Friend Request Rejected.");
               loadNoificationonPanel();
            }
        });
        buttonsPanel.add(rejectRequestButton);

        RequestsPanel.add(buttonsPanel, BorderLayout.EAST);

        return RequestsPanel;
    }

    private JLabel createProfilePictureLabel(String profilePhotoPath) {
        JLabel profilePictureLabel;
        if (profilePhotoPath != null && !profilePhotoPath.isEmpty()) {
            ImageIcon profilePicture = new ImageIcon(profilePhotoPath);
            Image scaledImage = profilePicture.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            profilePictureLabel = new JLabel(new ImageIcon(scaledImage));
        } else {
            profilePictureLabel = new JLabel("No Image");
            profilePictureLabel.setHorizontalAlignment(SwingConstants.CENTER);
            profilePictureLabel.setPreferredSize(new Dimension(100, 100));
            profilePictureLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        }
        return profilePictureLabel;
    }
}
