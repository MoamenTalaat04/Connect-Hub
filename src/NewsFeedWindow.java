import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NewsFeedWindow extends JFrame {
    private JPanel panel1;
    private JPanel NavigationPanel;
    private JPanel FriendStatusPanel;
    private JPanel NewsFeedPanel;
    private JButton profileButton;
    private JButton friendsButton;
    private JButton logOutButton;
    private JButton refreshButton;
    private JSeparator NewsFeedNavigationSeparator;
    private JSeparator NewsFeedFriendStatusSeparator;
    private JScrollPane PostsScrollPane;
    private JScrollPane StoriesScrollPane;
    private JScrollPane FriendStatusScrollPane;
    private JPanel PostsPanel;
    private JPanel StoriesPanel;
    private JButton postButton;
    private JTextField PostContantField;
    private JTextField StoryContantField;
    private JScrollPane PostContantScrollPane;
    private JScrollPane StoryContantScrollPane;
    private JButton storyButton;
    private JButton PostPhotoButton;
    private JButton StoryPhotoButton;
    private JLabel PostPhotoPathLable;
    private JLabel StoryPhotoPathLable;
    private JScrollPane FriendSuggestionsScrollPane;
    private JPanel FriendSuggestionsPanel;

    private NewsFeed newsFeed;
    DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public NewsFeedWindow(User currentUser) {
        this.newsFeed = new NewsFeed(currentUser);
        setContentPane(panel1);
        setTitle("News Feed");
        setSize(1300, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


        // Load initial data
        loadFriendStatus();
        loadStories();
        loadPosts();
        loadFriendSuggestions();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                newsFeed.fetchAllUsers();
                newsFeed.getCurrentUser().setStatus("Offline");
                newsFeed.getUserDatabase().saveUsersToFile(newsFeed.getAllUsers());
            }
        });

        refreshButton.addActionListener(e -> {
            loadFriendStatus();
            loadStories();
            loadPosts();
            loadFriendSuggestions();
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

    private void loadStories() {
        StoriesPanel.removeAll();
        StoriesPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Use horizontal layout for stories

        ArrayList<Stories> stories = newsFeed.fetchStoriesFromFriends();
        for (Stories story : stories) {
            JPanel storyPanel = new JPanel();
            storyPanel.setPreferredSize(new Dimension(120, 200));
            storyPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

            JLabel Username = new JLabel(newsFeed.getUsernameByID(story.getAuthorId()));
            JLabel Time = new JLabel(story.getTimestamp().format(dtf));
            Time.setFont(new Font("Arial", Font.PLAIN, 12));
            Time.setAlignmentX(Component.CENTER_ALIGNMENT);
            Username.setFont(new Font("Arial", Font.PLAIN, 12));
            Username.setAlignmentX(Component.LEFT_ALIGNMENT);
            Username.setVerticalAlignment(SwingConstants.TOP);
            JLabel storyLabel = new JLabel( story.getContent());
            storyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            storyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            storyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            // If the story has an image, add it
            if (story.getImagePath() != null && !story.getImagePath().isEmpty()) {
                ImageIcon storyImage = new ImageIcon(story.getImagePath());
                Image scaledImage = storyImage.getImage().getScaledInstance(100, 120, Image.SCALE_SMOOTH); // Scale image
                storyLabel.setIcon(new ImageIcon(scaledImage));
                storyLabel.setHorizontalTextPosition(SwingConstants.CENTER);
                storyLabel.setVerticalTextPosition(SwingConstants.TOP);
                storyLabel.setIconTextGap(10);

            }
            storyPanel.add(Username);
            storyPanel.add(Time);
            storyPanel.add(storyLabel);
            StoriesPanel.add(storyPanel);
        }

        StoriesScrollPane.setViewportView(StoriesPanel);

    }
    private void loadFriendSuggestions() {
        newsFeed.fetchAllUsers();
        FriendSuggestionsPanel.removeAll();
        FriendSuggestionsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5)); // Align buttons to the right with spacing); // Use vertical layout
        ArrayList<User> friendSuggestions = newsFeed.suggestFriends();

        for (User friend : friendSuggestions) {
            JPanel friendPanel = new JPanel();
            friendPanel.setLayout(new BorderLayout(5, 5)); // Add spacing between components
            friendPanel.setPreferredSize(new Dimension(250, 190)); // Adjust size of each suggestion panel
            friendPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 1), // Add border
                    BorderFactory.createEmptyBorder(10, 10, 10, 10) // Add padding
            ));

            // Add friend profile picture (if available)
            JLabel profilePictureLabel;
            if (friend.getProfilePhotoPath() != null && !friend.getProfilePhotoPath().isEmpty()) {
                ImageIcon profilePicture = new ImageIcon(friend.getProfilePhotoPath());
                Image scaledImage = profilePicture.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Scale image
                profilePictureLabel = new JLabel(new ImageIcon(scaledImage));
            } else {
                profilePictureLabel = new JLabel("No Image");
                profilePictureLabel.setHorizontalAlignment(SwingConstants.CENTER);
                profilePictureLabel.setPreferredSize(new Dimension(100, 100));
                profilePictureLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            }
            friendPanel.add(profilePictureLabel, BorderLayout.WEST);

            // Add friend name at the top
            JLabel friendLabel = new JLabel(friend.getUsername());
            friendLabel.setFont(new Font("Arial", Font.BOLD, 14));
            friendLabel.setHorizontalAlignment(SwingConstants.LEFT);
            friendPanel.add(friendLabel, BorderLayout.CENTER);

            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

            if (newsFeed.getFriendManagement().SentRequestsFromUser().contains(newsFeed.getFriendManagement().getUserById(friend.getUserId()))) {
                JButton cancelRequestButton = new JButton("Cancel Request");
                cancelRequestButton.setFont(new Font("Arial", Font.PLAIN, 12));
                cancelRequestButton.addActionListener(e -> {
                    boolean success = newsFeed.getFriendManagement().cancelFriendRequest(newsFeed.getFriendManagement().getUserById(friend.getUserId()));
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Friend Request Cancelled to " + friend.getUsername());
                        loadFriendSuggestions(); // Refresh suggestions after cancelling
                    } else {
                        JOptionPane.showMessageDialog(this, "Unable to Cancel Friend Request to " + friend.getUsername());
                    }
                });
                buttonsPanel.add(cancelRequestButton);
            } else {
                JButton sendRequestButton = new JButton("Send Request");
                sendRequestButton.setFont(new Font("Arial", Font.PLAIN, 12));
                sendRequestButton.addActionListener(e -> {
                    boolean success = newsFeed.getFriendManagement().sendFriendRequest(newsFeed.getFriendManagement().getUserById(friend.getUserId()));
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Friend Request Sent to " + newsFeed.getFriendManagement().getUserById(friend.getUserId()).getUsername());
                        loadFriendSuggestions(); // Refresh suggestions after sending
                    } else {
                        JOptionPane.showMessageDialog(this, "Unable to Send Friend Request to " + newsFeed.getFriendManagement().getUserById(friend.getUserId()).getUsername());
                    }
                });
                buttonsPanel.add(sendRequestButton);
            }

            JButton blockButton = new JButton("Block");
            blockButton.setFont(new Font("Arial", Font.PLAIN, 12));
            blockButton.addActionListener(e -> {
                boolean success = newsFeed.getFriendManagement().blockUser(newsFeed.getFriendManagement().getUserById(friend.getUserId()));
                if (success) {
                    JOptionPane.showMessageDialog(this, newsFeed.getFriendManagement().getUserById(friend.getUserId()).getUsername() + " has been blocked.");
                    loadFriendSuggestions(); // Refresh suggestions after blocking
                } else {
                    JOptionPane.showMessageDialog(this, "Unable to Block " + newsFeed.getFriendManagement().getUserById(friend.getUserId()).getUsername());
                }
            });
            buttonsPanel.add(blockButton);

            friendPanel.add(buttonsPanel, BorderLayout.SOUTH);

            FriendSuggestionsPanel.add(friendPanel);
            FriendSuggestionsPanel.add(Box.createVerticalStrut(10));
        }

        FriendSuggestionsScrollPane.setViewportView(FriendSuggestionsPanel);

    }





    private void loadPosts () {
            PostsPanel.removeAll();
            PostsPanel.setLayout(new BoxLayout(PostsPanel, BoxLayout.Y_AXIS)); // Use vertical layout for posts

            // Get posts from the NewsFeed
            ArrayList<Posts> posts = newsFeed.fetchPostsFromFriends();
            for (Posts post : posts) {
                JPanel postPanel = new JPanel(new BorderLayout());
                postPanel.setPreferredSize(new Dimension(1200, 150));
                postPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                JLabel UsernameAndTimeLable = new JLabel(newsFeed.getUsernameByID(post.getAuthorId()) + " - " + post.getTimestamp().format(dtf));
                UsernameAndTimeLable.setFont(new Font("Arial", Font.PLAIN, 12));
                UsernameAndTimeLable.setAlignmentX(Component.LEFT_ALIGNMENT);
                UsernameAndTimeLable.setHorizontalTextPosition(SwingConstants.LEFT);
                UsernameAndTimeLable.setVerticalAlignment(SwingConstants.TOP);
                JLabel postLabel = new JLabel(post.getContent());
                postLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                postLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                // If the post has an image, add it
                if (post.getImagePath() != null && !post.getImagePath().isEmpty()) {

                    ImageIcon postImage = new ImageIcon(post.getImagePath());
                    Image scaledImage = postImage.getImage().getScaledInstance(600, 300, Image.SCALE_SMOOTH); // Scale image
                    postPanel.add(new JLabel(new ImageIcon(scaledImage)), BorderLayout.SOUTH);
                }

                postPanel.add(postLabel, BorderLayout.CENTER);
                PostsPanel.add(UsernameAndTimeLable);
                PostsPanel.add(postPanel);
            }

            PostsScrollPane.setViewportView(PostsPanel);

        }
}
