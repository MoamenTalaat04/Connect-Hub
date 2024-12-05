import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FriendManagementWindow extends JFrame {
    private JButton friendsButton1;
    private JButton blockedButton;
    private JButton profileButton;
    private JButton newsFeedButton;
    private JButton logOutButton;
    private JPanel Container;
    private JPanel NavigationPanel;
    private JPanel ButtonsManagmentPanel;
    private JScrollPane FriendsManagmentScrollPane;
    private JPanel FriendsManagmentPanal;
    private JButton refreshButton;
    private JButton receivedRequestsButton;
    private FriendManagement friendManagement;

    public FriendManagementWindow(User currentUser) {
        this.friendManagement = new FriendManagement(currentUser);
        setTitle("Friend Management");
        setSize(1300, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(Container);
        setLocationRelativeTo(null);

        loadFriendsList();
        friendsButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFriendsList();
            }
        });
        blockedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blockedFriendsList();
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFriendsList();
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentUser.setStatus("Offline");
                friendManagement.getUserDatabase().saveUsersToFile(friendManagement.getAllUsers());
                new LoginWindow;
                dispose();
            }
        });

        newsFeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new NewsFeedWindow(friendManagement.getCurrentUser());
                dispose();
            }
        });

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  new ProfileUI(friendManagement.getCurrentUser());
                dispose();
            }
        });

        receivedRequestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadReceivedRequestsList();
            }
        });
    }

    private void loadFriendsList() {
        FriendsManagmentPanal.removeAll();

        ArrayList<User> friends = friendManagement.getCurrentUser().getFriends();
        FriendsManagmentPanal.setLayout(new BoxLayout(FriendsManagmentPanal, BoxLayout.Y_AXIS));

        for (User friend : friends) {
            JPanel friendPanel = createFriendPanel(friend);
            FriendsManagmentPanal.add(friendPanel);
            FriendsManagmentPanal.add(Box.createVerticalStrut(10));
        }

        FriendsManagmentScrollPane.setViewportView(FriendsManagmentPanal);
        FriendsManagmentPanal.revalidate();
        FriendsManagmentPanal.repaint();
    }

    private void loadReceivedRequestsList() {
        FriendsManagmentPanal.removeAll();

        ArrayList<User> sentRequests = friendManagement.ReceivedRequestsForUser();
        FriendsManagmentPanal.setLayout(new BoxLayout(FriendsManagmentPanal, BoxLayout.Y_AXIS));

        for (User friend : sentRequests) {
            JPanel RequestsPanel = CreateReceivedRequestsPanel(friend);
            FriendsManagmentPanal.add(RequestsPanel);
            FriendsManagmentPanal.add(Box.createVerticalStrut(10));
        }

        FriendsManagmentScrollPane.setViewportView(FriendsManagmentPanal);
        FriendsManagmentPanal.revalidate();
        FriendsManagmentPanal.repaint();
    }



    private void blockedFriendsList() {
        FriendsManagmentPanal.removeAll();
        FriendsManagmentPanal.setLayout(new BoxLayout(FriendsManagmentPanal, BoxLayout.Y_AXIS));
        ArrayList<User> blockedUsers = friendManagement.getCurrentUser().getBlocked();

        for (User blocked : blockedUsers) {
            JPanel blockedPanel = createBlockedPanel(blocked);
            FriendsManagmentPanal.add(blockedPanel);
            FriendsManagmentPanal.add(Box.createVerticalStrut(10));
        }

        FriendsManagmentScrollPane.setViewportView(FriendsManagmentPanal);
        FriendsManagmentPanal.revalidate();
        FriendsManagmentPanal.repaint();
    }

    private JPanel createFriendPanel(User friend) {
        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new BorderLayout(10, 10));
        friendPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        friendPanel.setPreferredSize(new Dimension(900, 100));

        JLabel profilePictureLabel = createProfilePictureLabel(friend.getProfilePhotoPath());
        friendPanel.add(profilePictureLabel, BorderLayout.WEST);

        JLabel friendLabel = new JLabel(friend.getUsername());
        friendLabel.setFont(new Font("Arial", Font.BOLD, 16));
        friendPanel.add(friendLabel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        JButton sendRequestButton = new JButton("Remove Friend");
        sendRequestButton.addActionListener(e -> {
            if (friendManagement.removeFriend(friend)) {
                    JOptionPane.showMessageDialog(this, "Friend Removed.");
                    loadFriendsList();
                }
            });
        sendRequestButton.setAlignmentY(CENTER_ALIGNMENT);
        sendRequestButton.setVerticalAlignment(SwingConstants.CENTER);
        buttonsPanel.add(sendRequestButton);

        JButton blockButton = new JButton("Block");
        blockButton.addActionListener(e -> {
            if (friendManagement.blockUser(friend)) {
                JOptionPane.showMessageDialog(this, friend.getUsername() + " has been blocked.");
                loadFriendsList();
            }
        });
        blockedButton.setAlignmentY(CENTER_ALIGNMENT);
        blockedButton.setVerticalAlignment(SwingConstants.CENTER);
        buttonsPanel.add(blockButton);

        friendPanel.add(buttonsPanel, BorderLayout.EAST);

        return friendPanel;
    }

    private JPanel createBlockedPanel(User blocked) {
        JPanel blockedPanel = new JPanel();
        blockedPanel.setLayout(new BorderLayout(10, 10));
        blockedPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        blockedPanel.setPreferredSize(new Dimension(900, 100));

        JLabel profilePictureLabel = createProfilePictureLabel(blocked.getProfilePhotoPath());
        blockedPanel.add(profilePictureLabel, BorderLayout.WEST);

        JLabel blockedUserLabel = new JLabel(blocked.getUsername());
        blockedUserLabel.setFont(new Font("Arial", Font.BOLD, 16));
        blockedPanel.add(blockedUserLabel, BorderLayout.CENTER);

        JButton unblockButton = new JButton("Unblock");
        unblockButton.addActionListener(e -> {
            if (friendManagement.unblockUser(blocked)) {
                JOptionPane.showMessageDialog(this, blocked.getUsername() + " has been unblocked.");
                blockedFriendsList();
            }
        });
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonsPanel.add(unblockButton);

        blockedPanel.add(buttonsPanel, BorderLayout.EAST);

        return blockedPanel;
    }
    private JPanel CreateReceivedRequestsPanel(User friend) {
        JPanel RequestsPanel = new JPanel();
        RequestsPanel.setLayout(new BorderLayout(10, 10));
        RequestsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        RequestsPanel.setPreferredSize(new Dimension(900, 100));

        JLabel profilePictureLabel = createProfilePictureLabel(friend.getProfilePhotoPath());
        RequestsPanel.add(profilePictureLabel, BorderLayout.WEST);

        JLabel friendLabel = new JLabel(friend.getUsername());
        friendLabel.setFont(new Font("Arial", Font.BOLD, 16));
        RequestsPanel.add(friendLabel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        JButton acceptRequestButton = new JButton("Accept Request");
        acceptRequestButton.addActionListener(e -> {
            if (friendManagement.acceptFriendRequest(friend)) {
                JOptionPane.showMessageDialog(this, "Friend Request Accepted.");
                loadReceivedRequestsList();
            }
        });
        buttonsPanel.add(acceptRequestButton);

        JButton rejectRequestButton = new JButton("Reject Request");
        rejectRequestButton.addActionListener(e -> {
            if (friendManagement.rejectFriendRequest(friend)) {
                JOptionPane.showMessageDialog(this, "Friend Request Rejected.");
                loadReceivedRequestsList();
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
