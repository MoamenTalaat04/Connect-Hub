import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NotificationUI extends JFrame {
    DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private JScrollPane scroll;
    private JPanel panel;
    private NewsFeed newsfeed;
    public NotificationUI(NewsFeed newsfeed , NotificationManager notificationManager) {
    this.newsfeed=newsfeed;
    setContentPane(panel);
    setTitle("Notification");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setVisible(true);
    setLocationRelativeTo(null);
    setSize(450,550);
    loadNoificationonPanel();

    }
    public void loadNoificationonPanel() {
        panel.removeAll();
        ArrayList<notificationData> notifications = newsfeed.getCurrentUser().getNotification();

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        for (notificationData notification : notifications) {
            if (notification.getDescription().equals("Sent you a friend request")) {
                JPanel notificationPanel = CreateReceivedRequestsPanel(notification);
                panel.add(notificationPanel);
                panel.add(Box.createVerticalStrut(10));

        }

    }
        scroll.setViewportView(panel);
        panel.revalidate();
        panel.repaint();
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

        JLabel friendLabel = new JLabel(notification.getfrom()+"Sent You a Friend Request");
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
