import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    public NewsFeedWindow(NewsFeed newsFeed) {
        this.newsFeed = newsFeed;
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
        refreshButton.addActionListener(e -> {
            loadFriendStatus();
            loadStories();
            loadPosts();
            loadFriendSuggestions();
        });

        profileButton.addActionListener(e -> {
            // Open the ProfileWindow
          //  new ProfileWindow(newsFeed.getCurrentUser().getUserId());
        });

        friendsButton.addActionListener(e -> {
            // Open the FriendManagementWindow
            //new FriendManagementWindow(newsFeed.getFriendManagement());
        });

        logOutButton.addActionListener(e -> {
            // Close the NewsFeedWindow
            //new LoginWindow();
            dispose();
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
                // Set the image path in the PostContantField
                PostPhotoPathLable.setText(imagePath);
            }
        });

        StoryPhotoButton.addActionListener(e -> {
            // Open a file chooser to select a photo
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // Get the selected file
                String imagePath = fileChooser.getSelectedFile().getAbsolutePath();
                // Set the image path in the StoryContantField
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
        // Clear the FriendStatusPanel
        FriendStatusPanel.removeAll();
        FriendStatusPanel.setLayout(new BoxLayout(FriendStatusPanel, BoxLayout.Y_AXIS)); // Use vertical layout

        // Get friend statuses from the NewsFeed
        ArrayList<String> friends = newsFeed.fetchFriendStatus();
        for (String friend : friends) {
            JLabel friendLabel = new JLabel(friend);
            friendLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for better readability
            friendLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align text
            FriendStatusPanel.add(friendLabel);
            FriendStatusPanel.add(Box.createVerticalStrut(10)); // Add space between friends
        }

        FriendStatusScrollPane.setViewportView(FriendStatusPanel); // Attach the panel to the scroll pane
        FriendStatusPanel.revalidate();
        FriendStatusPanel.repaint();
    }

    private void loadStories() {
        // Clear the StoriesPanel
        StoriesPanel.removeAll();
        StoriesPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Use horizontal layout for stories

        // Get stories from the NewsFeed
        ArrayList<Stories> stories = newsFeed.fetchStoriesFromFriends();
        for (Stories story : stories) {
            JPanel storyPanel = new JPanel();
            storyPanel.setPreferredSize(new Dimension(120, 200)); // Set story size
            storyPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2)); // Add border to story

            JLabel Username = new JLabel(newsFeed.getUsernameByID(story.getAuthorId()));
            JLabel Time = new JLabel(story.getTimestamp().format(dtf));
            Time.setFont(new Font("Arial", Font.PLAIN, 12));
            Time.setAlignmentX(Component.CENTER_ALIGNMENT);
            Username.setFont(new Font("Arial", Font.PLAIN, 12)); // Set font for username and time
            Username.setAlignmentX(Component.LEFT_ALIGNMENT); // Center align text
            Username.setVerticalAlignment(SwingConstants.TOP); // Align to the top
            JLabel storyLabel = new JLabel( story.getContent());
            storyLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align text
            storyLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for story content
            storyLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align text
            // If the story has an image, add it
            if (story.getImagePath() != null && !story.getImagePath().isEmpty()) {
                ImageIcon storyImage = new ImageIcon(story.getImagePath());
                Image scaledImage = storyImage.getImage().getScaledInstance(100, 120, Image.SCALE_SMOOTH); // Scale image
                storyLabel.setIcon(new ImageIcon(scaledImage)); // Set image as icon
                storyLabel.setHorizontalTextPosition(SwingConstants.CENTER); // Center align text
                storyLabel.setVerticalTextPosition(SwingConstants.TOP); // Align text to the bottom
                storyLabel.setIconTextGap(10); // Add spacing between image and text

            }
            storyPanel.add(Username); // Add username and time label
            storyPanel.add(Time);
            storyPanel.add(storyLabel); // Add story content to the story panel
            StoriesPanel.add(storyPanel); // Add story panel to the StoriesPanel
        }

        StoriesScrollPane.setViewportView(StoriesPanel); // Attach the panel to the scroll pane
        StoriesPanel.revalidate();
        StoriesPanel.repaint();
    }
    private void loadFriendSuggestions() {
        // Clear the FriendSuggestionsPanel
        FriendSuggestionsPanel.removeAll();
        FriendSuggestionsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5)); // Align buttons to the right with spacing); // Use vertical layout
        // Get friend suggestions from the NewsFeed
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
            friendLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Use bold font for better readability
            friendLabel.setHorizontalAlignment(SwingConstants.LEFT); // Align text to the left
            friendPanel.add(friendLabel, BorderLayout.CENTER);

            // Create buttons panel for actions
            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5)); // Align buttons to the right with spacing

            if (newsFeed.getFriendManagement().SentRequests().contains(friend)) {
                // "Cancel Friend Request" button
                JButton cancelRequestButton = new JButton("Cancel Request");
                cancelRequestButton.setFont(new Font("Arial", Font.PLAIN, 12));
                cancelRequestButton.addActionListener(e -> {
                    boolean success = newsFeed.getFriendManagement().cancelFriendRequest(friend);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Friend Request Cancelled to " + friend.getUsername());
                        loadFriendSuggestions(); // Refresh suggestions after cancelling
                    } else {
                        JOptionPane.showMessageDialog(this, "Unable to Cancel Friend Request to " + friend.getUsername());
                    }
                });
                buttonsPanel.add(cancelRequestButton);
            } else {
                // "Send Friend Request" button
                JButton sendRequestButton = new JButton("Send Request");
                sendRequestButton.setFont(new Font("Arial", Font.PLAIN, 12));
                sendRequestButton.addActionListener(e -> {
                    boolean success = newsFeed.getFriendManagement().sendFriendRequest(friend);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Friend Request Sent to " + friend.getUsername());
                        loadFriendSuggestions(); // Refresh suggestions after sending
                    } else {
                        JOptionPane.showMessageDialog(this, "Unable to Send Friend Request to " + friend.getUsername());
                    }
                });
                buttonsPanel.add(sendRequestButton);
            }

            // "Block" button
            JButton blockButton = new JButton("Block");
            blockButton.setFont(new Font("Arial", Font.PLAIN, 12));
            blockButton.addActionListener(e -> {
                boolean success = newsFeed.getFriendManagement().blockUser(friend);
                if (success) {
                    JOptionPane.showMessageDialog(this, friend.getUsername() + " has been blocked.");
                    loadFriendSuggestions(); // Refresh suggestions after blocking
                } else {
                    JOptionPane.showMessageDialog(this, "Unable to Block " + friend.getUsername());
                }
            });
            buttonsPanel.add(blockButton);

            // Add buttons panel to the friend panel
            friendPanel.add(buttonsPanel, BorderLayout.SOUTH);

            // Add friend panel to the main suggestions panel
            FriendSuggestionsPanel.add(friendPanel);
            FriendSuggestionsPanel.add(Box.createVerticalStrut(10)); // Add space between friend panels
        }

        // Attach the FriendSuggestionsPanel to the scroll pane
        FriendSuggestionsScrollPane.setViewportView(FriendSuggestionsPanel);
        FriendSuggestionsPanel.revalidate();
        FriendSuggestionsPanel.repaint();
    }





    private void loadPosts () {
            // Clear the PostsPanel
            PostsPanel.removeAll();
            PostsPanel.setLayout(new BoxLayout(PostsPanel, BoxLayout.Y_AXIS)); // Use vertical layout for posts

            // Get posts from the NewsFeed
            ArrayList<Posts> posts = newsFeed.fetchPostsFromFriends();
            for (Posts post : posts) {
                JPanel postPanel = new JPanel(new BorderLayout());
                postPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Add border to post panel
                JLabel UsernameAndTimeLable = new JLabel(newsFeed.getUsernameByID(post.getAuthorId()) + " - " + post.getTimestamp().format(dtf));
                UsernameAndTimeLable.setFont(new Font("Arial", Font.PLAIN, 12)); // Set font for username and time
                UsernameAndTimeLable.setAlignmentX(Component.LEFT_ALIGNMENT); // Center align text
                UsernameAndTimeLable.setVerticalAlignment(SwingConstants.TOP); // Align to the top
                JLabel postLabel = new JLabel(post.getContent());
                postLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for post content
                postLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Left align text

                // If the post has an image, add it
                if (post.getImagePath() != null && !post.getImagePath().isEmpty()) {

                    ImageIcon postImage = new ImageIcon(post.getImagePath());
                    Image scaledImage = postImage.getImage().getScaledInstance(600, 300, Image.SCALE_SMOOTH); // Scale image
                    postPanel.add(new JLabel(new ImageIcon(scaledImage)), BorderLayout.SOUTH); // Add image at the bottom
                }

                postPanel.add(postLabel, BorderLayout.CENTER); // Add content to the center
                PostsPanel.add(UsernameAndTimeLable); // Add username and time label
                PostsPanel.add(postPanel); // Add post panel to the PostsPanel
            }

            PostsScrollPane.setViewportView(PostsPanel); // Attach the panel to the scroll pane
            PostsPanel.revalidate();
            PostsPanel.repaint();
        }



    public static void main(String[] args) {
        // Sample data for testing
        User friend1 = new User("2", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "online", "Friend1", "friend1@gmail.com", "hashedpassword", null, "This is friend 1.", null);

        User friend2 = new User("3", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "offline", "Friend2", "friend2@gmail.com", "hashedpassword", null, "This is friend 2.", "C:\\Users\\mohammed adel\\Desktop\\WhatsApp Image 2024-12-04 at 15.26.09_ee4260ee.jpg");

        User friend3 = new User("3", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "offline", "Friend2", "friend2@gmail.com", "hashedpassword", null, "This is friend 2.", "C:\\Users\\mohammed adel\\Desktop\\WhatsApp Image 2024-12-04 at 15.26.09_ee4260ee.jpg");

        User friend4 = new User("3", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "offline", "Friend2", "friend2@gmail.com", "hashedpassword", null, "This is friend 2.", "C:\\Users\\mohammed adel\\Desktop\\WhatsApp Image 2024-12-04 at 15.26.09_ee4260ee.jpg");

        User friend5 = new User("3", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "offline", "Friend2", "friend2@gmail.com", "hashedpassword", null, "This is friend 2.", "C:\\Users\\mohammed adel\\Desktop\\WhatsApp Image 2024-12-04 at 15.26.09_ee4260ee.jpg");
        User friend6 = new User("3", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "offline", "Friend2", "friend2@gmail.com", "hashedpassword", null, "This is friend 2.", "C:\\Users\\mohammed adel\\Desktop\\WhatsApp Image 2024-12-04 at 15.26.09_ee4260ee.jpg");
       // ArrayList<User> pending = new ArrayList<>();
        //pending.add(friend2);
        User currentUser = new User("1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "online", "CurrentUser", "currentuser@gmail.com", "hashedpassword", null, "This is the current user.", null);
       // friend2.getPendingRequests().add(currentUser);


        currentUser.getFriends().add(friend1);

        ArrayList<User> allUsers = new ArrayList<>();
        allUsers.add(currentUser);
        allUsers.add(friend1);
        allUsers.add(friend2);
        allUsers.add(friend3);
        allUsers.add(friend4);
        allUsers.add(friend5);
        allUsers.add(friend6);


        FriendManagement friendManagement = new FriendManagement(currentUser, allUsers);
        NewsFeed newsFeed = new NewsFeed(friendManagement);


        try {
            // Set FlatMacDarkLaf as the Look and Feel
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch the IntelliJ form window
        SwingUtilities.invokeLater(() -> {
            NewsFeedWindow window = new NewsFeedWindow(newsFeed);
            window.setVisible(true);
        });
    }

}
