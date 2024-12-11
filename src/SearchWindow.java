import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchWindow extends JFrame {
    private JPanel panel1;
    private JPanel NavigationPanel;
    private JButton profileButton;
    private JButton friendsButton;
    private JButton logOutButton;
    private JButton backButton;
    private JPanel NewsFeedPanel;
    private JSeparator NewsFeedNavigationSeparator;
    private JSeparator NewsFeedFriendStatusSeparator;
    private JScrollPane PostContantScrollPane;
    private JTextField PostContantField;
    private JButton postButton;
    private JScrollPane StoryContantScrollPane;
    private JTextField StoryContantField;
    private JButton storyButton;
    private JButton StoryPhotoButton;
    private JButton PostPhotoButton;
    private JLabel PostPhotoPathLable;
    private JLabel StoryPhotoPathLable;
    private JScrollPane FriendStatusScrollPane;
    private JPanel FriendStatusPanel;
    private JTextField SearchField;
    private JButton searchButton;
    private JCheckBox groupsCheckBox;
    private JCheckBox usersCheckBox;
    private JScrollPane ResultsScrollPane;
    private JPanel ResultsPanel;
    private NewsFeed newsFeed ;
    private Timer timer;


    public SearchWindow(NewsFeed newsFeed) {
        this.newsFeed=newsFeed;
        setContentPane(panel1);
        setTitle("News Feed");
        setSize(1300, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        loadFriendStatus();



        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                newsFeed.fetchAllUsers();
                newsFeed.getCurrentUser().setStatus("Offline");
                newsFeed.getUserDatabase().saveUsersToFile(newsFeed.getAllUsers());
            }
        });

        backButton.addActionListener(e -> {
            new NewsFeedWindow(newsFeed.getCurrentUser());
            dispose();
        });

        profileButton.addActionListener(e -> {
            new myProfile(newsFeed.getCurrentUser());
            dispose();
        });

        friendsButton.addActionListener(e -> {
            new FriendManagementWindow(newsFeed.getCurrentUser());
            dispose();
        });

        logOutButton.addActionListener(e -> {
            try {

                newsFeed.fetchAllUsers();
                newsFeed.getCurrentUser().setStatus("Offline");
                newsFeed.getUserDatabase().saveUsersToFile(newsFeed.getAllUsers());
                new LoginWindow();
                dispose();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        postButton.addActionListener(e -> {
            // Add a new post
            newsFeed.addPost(PostContantField.getText(), PostPhotoPathLable.getText());
            PostPhotoPathLable.setText("");
            PostContantField.setText("");
        });

        storyButton.addActionListener(e -> {
            // Add a new story
            if(StoryContantField.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Story content cannot be empty.");
                return;
            }
            newsFeed.addStory(StoryContantField.getText(), StoryPhotoPathLable.getText());
            StoryPhotoPathLable.setText("");
            StoryContantField.setText("");
        });

        PostPhotoButton.addActionListener(e -> {
            // Open a file chooser to select a photo
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // Get the selected file
                String imagePath = fileChooser.getSelectedFile().getAbsolutePath();
                // Set the image path in the PostContentField
                PostPhotoPathLable.setText(imagePath);
            }
        });

        StoryPhotoButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String imagePath = fileChooser.getSelectedFile().getAbsolutePath();
                StoryPhotoPathLable.setText(imagePath);
            }
        });

        PostContantField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                PostContantField.setText("");
            }
        });

        StoryContantField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                StoryContantField.setText("");
            }
        });

        searchButton.addActionListener(e -> performSearch());
        SearchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                SearchField.setText("");
            }
        });


        SearchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                handleDebouncedSearch();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performSearch();
                }
            }
        });
    }
    private void performSearch() {
        String query = SearchField.getText().trim();
        ResultsPanel.removeAll();

        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search query.");
            return;
        }

        newsFeed.fetchAllUsers();
        ArrayList<Object> allResults = new ArrayList<>();
        ArrayList<Object> Suggestions = new ArrayList<>();
        boolean isFuzzySearch = false;

        // Normal search strategies
        if (usersCheckBox.isSelected()) {
            SearchStrategy userStrategy = new UserSearchStrategy(newsFeed.getAllUsers());
            allResults.addAll(userStrategy.search(query));
        }
        if (groupsCheckBox.isSelected()) {
            SearchStrategy groupStrategy = new GroupSearchStrategy(newsFeed.getAllGroups());
            allResults.addAll(groupStrategy.search(query));
        }

        // If no results, perform fuzzy search
        if (allResults.isEmpty()) {
            isFuzzySearch = true;
            if (usersCheckBox.isSelected()) {
                SearchStrategy fuzzyUserStrategy = new FuzzyUserSearchStrategy(newsFeed.getAllUsers());
                Suggestions.addAll(fuzzyUserStrategy.search(query));
            }
            if (groupsCheckBox.isSelected()) {
                SearchStrategy fuzzyGroupStrategy = new FuzzyGroupSearchStrategy(newsFeed.getAllGroups());
                Suggestions.addAll(fuzzyGroupStrategy.search(query));
            }
        }

        // Display results
        if (allResults.isEmpty()) {
            ResultsPanel.add(new JLabel("No results found."));
        } else {
            if (!Suggestions.isEmpty()) {
                ResultsPanel.add(new JLabel("Did you mean:"));
                for (Object suggestion : Suggestions) {
                    if (suggestion instanceof User) {
                        if(suggestion.equals(newsFeed.getCurrentUser()))
                        {
                             continue;
                        }
                        else if (newsFeed.getCurrentUser().getFriends().contains(suggestion)) {
                            createFriendPanel((User) suggestion);
                        }else {
                            createUserPanel((User) suggestion);
                        }
                    } else if (suggestion instanceof Group) {
                         createGroupPanel((Group)suggestion);
                    }
                }
            }
            for (Object results : allResults) {
                if (results instanceof User) {
                    if(results.equals(newsFeed.getCurrentUser()))
                    {
                        continue;
                    }
                    else if (newsFeed.getCurrentUser().getFriends().contains(results)) {
                        createFriendPanel((User) results);
                    }else {
                        createUserPanel((User) results);
                    }
                } else if (results instanceof Group) {
                    createGroupPanel((Group)results);
                }
            }
        }
        ResultsScrollPane.setViewportView(ResultsPanel);
        ResultsPanel.revalidate();
        ResultsPanel.repaint();
    }

    private void handleDebouncedSearch() {
        if (timer != null) {
            timer.cancel(); // Cancel existing timer
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                performSearch();
            }
        }, 500); // Delay of 500ms
    }


    private void loadFriendStatus() {
        FriendStatusPanel.removeAll();
        FriendStatusPanel.setLayout(new BoxLayout(FriendStatusPanel, BoxLayout.Y_AXIS)); // Use vertical layout
        newsFeed.fetchAllUsers();
        ArrayList<String> friends = newsFeed.fetchFriendStatus();
        for (String friend : friends) {
            JLabel friendLabel = new JLabel(friend);
            friendLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            friendLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            FriendStatusPanel.add(friendLabel);
            FriendStatusPanel.add(Box.createVerticalStrut(10));
        }

        FriendStatusScrollPane.setViewportView(FriendStatusPanel);

    }
    private JPanel createUserPanel(User user){
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout(10, 10));
        userPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        userPanel.setMaximumSize(new Dimension(1200, 190));

        JLabel profilePictureLabel = createProfilePictureLabel(user.getProfilePhotoPath());
        userPanel.add(profilePictureLabel, BorderLayout.WEST);

        JLabel userLabel = new JLabel(user.getUsername());
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userPanel.add(userLabel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        if (newsFeed.getFriendManagement().SentRequestsFromUser().contains(newsFeed.getFriendManagement().getUserById(user.getUserId()))) {
            JButton cancelRequestButton = new JButton("Cancel Request");
            cancelRequestButton.setFont(new Font("Arial", Font.PLAIN, 12));
            cancelRequestButton.addActionListener(e -> {
                boolean success = newsFeed.getFriendManagement().cancelFriendRequest(newsFeed.getFriendManagement().getUserById(user.getUserId()));
                if (success) {
                    JOptionPane.showMessageDialog(this, "Friend Request Cancelled to " + user.getUsername());
                    performSearch(); // Refresh after cancelling
                } else {
                    JOptionPane.showMessageDialog(this, "Unable to Cancel Friend Request to " + user.getUsername());
                }
            });
            buttonsPanel.add(cancelRequestButton);
        } else {
            JButton sendRequestButton = new JButton("Send Request");
            sendRequestButton.setFont(new Font("Arial", Font.PLAIN, 12));
            sendRequestButton.addActionListener(e -> {
                boolean success = newsFeed.getFriendManagement().sendFriendRequest(newsFeed.getFriendManagement().getUserById(user.getUserId()));
                if (success) {
                    JOptionPane.showMessageDialog(this, "Friend Request Sent to " + newsFeed.getFriendManagement().getUserById(user.getUserId()).getUsername());
                    performSearch(); // Refresh after cancelling
                } else {
                    JOptionPane.showMessageDialog(this, "Unable to Send Friend Request to " + newsFeed.getFriendManagement().getUserById(user.getUserId()).getUsername());
                }
            });
            buttonsPanel.add(sendRequestButton);
        }

        JButton blockButton = new JButton("Block");
        blockButton.addActionListener(e -> {
            if (newsFeed.getFriendManagement().blockUser(user)) {
                JOptionPane.showMessageDialog(this, user.getUsername() + " has been blocked.");
            }
        });
        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.addActionListener(e -> {
            new myProfile(user);
            dispose();
        });
        buttonsPanel.add(blockButton);
        userPanel.add(buttonsPanel, BorderLayout.EAST);
        return userPanel;
    }

    private JPanel createFriendPanel(User friend) {
        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new BorderLayout(10, 10));
        friendPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        friendPanel.setMaximumSize(new Dimension(1200, 190));

        JLabel profilePictureLabel = createProfilePictureLabel(friend.getProfilePhotoPath());
        friendPanel.add(profilePictureLabel, BorderLayout.WEST);

        JLabel friendLabel = new JLabel(friend.getUsername());
        friendLabel.setFont(new Font("Arial", Font.BOLD, 16));
        friendPanel.add(friendLabel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        JButton sendRequestButton = new JButton("Remove Friend");
        sendRequestButton.addActionListener(e -> {
            if (newsFeed.getFriendManagement().removeFriend(friend)) {
                JOptionPane.showMessageDialog(this, "Friend Removed.");
                performSearch();
            }
        });
        sendRequestButton.setAlignmentY(CENTER_ALIGNMENT);
        sendRequestButton.setVerticalAlignment(SwingConstants.CENTER);
        buttonsPanel.add(sendRequestButton);

        JButton blockButton = new JButton("Block");
        blockButton.addActionListener(e -> {
            if (newsFeed.getFriendManagement().blockUser(friend)) {
                JOptionPane.showMessageDialog(this, friend.getUsername() + " has been blocked.");
                performSearch();
            }
        });

        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.addActionListener(e -> {
            new myProfile(friend);
            dispose();
        });
        buttonsPanel.add(blockButton);
        friendPanel.add(buttonsPanel, BorderLayout.EAST);
        return friendPanel;
    }

     private JPanel createGroupPanel(Group group) {
        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new BorderLayout(10, 10));
        groupPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        groupPanel.setMaximumSize(new Dimension(1200, 190));
         Label profilePictureLabel = createProfilePictureLabel(group.getGroupPhotoPath());
         groupPanel.add(profilePictureLabel, BorderLayout.WEST);

        JLabel groupLabel = new JLabel(group.getGroupName());
        groupLabel.setFont(new Font("Arial", Font.BOLD, 16));
        groupPanel.add(groupLabel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
      if(!group.getGroupMembersIds().contains(newsFeed.getCurrentUser().getUserId())&& !group.getGroupAdminsIds().contains(newsFeed.getCurrentUser().getUserId())&&!group.getGroupOwnerId(newsFeed.getCurrentUser().getUserId())) {
          JButton joinButton = new JButton("Join");
          joinButton.addActionListener(e -> {
              if (newsFeed.getGroupManagement().addUserToGroup(group,newsFeed.getCurrentUser().getUserId())) {
                  JOptionPane.showMessageDialog(this, "Joined " + group.getGroupName());
                  performSearch();
              }
          });
          buttonsPanel.add(joinButton);
      }else {
        JButton leaveButton = new JButton("Leave");
        leaveButton.addActionListener(e -> {
            if (newsFeed.getGroupManagement().removeUserFromGroup(group,newsFeed.getCurrentUser().getUserId())) {
                JOptionPane.showMessageDialog(this, "Left " + group.getGroupName());
                performSearch();
            }
        });
        buttonsPanel.add(leaveButton);
        }
         JButton viewGroupButton = new JButton("View Group");
         viewGroupButton.addActionListener(e -> {
             new GroupWindow(group);
             dispose();
         });

        groupPanel.add(buttonsPanel, BorderLayout.EAST);
        return groupPanel;
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
    private void createUIComponents() {
        // TODO: place custom component creation code here
        groupsCheckBox.setSelected(!usersCheckBox.isSelected());
    }
}
