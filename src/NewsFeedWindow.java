import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NewsFeedWindow extends JFrame {
    private JPanel panel1; // Main panel from IntelliJ Form
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

    private NewsFeed newsFeed;
    DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public NewsFeedWindow(NewsFeed newsFeed) {
        this.newsFeed = newsFeed;
        setContentPane(panel1);
        setTitle("News Feed");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


        // Load initial data
        loadFriendStatus();
        loadStories();
        loadPosts();
        refreshButton.addActionListener(e -> {
            loadFriendStatus();
            loadStories();
            loadPosts();
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
            storyPanel.setPreferredSize(new Dimension(120, 280)); // Set story size
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
                storyLabel.setIcon(storyImage); // Set image as icon
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

    private void loadPosts() {
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
            JLabel postLabel = new JLabel( post.getContent());
            postLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for post content
            postLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Left align text

            // If the post has an image, add it
            if (post.getImagePath() != null && !post.getImagePath().isEmpty()) {
                ImageIcon postImage = new ImageIcon(post.getImagePath());
                JLabel imageLabel = new JLabel(postImage);
                postPanel.add(imageLabel, BorderLayout.SOUTH); // Add image at the bottom
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
        User currentUser = new User("1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "online", "CurrentUser", "currentuser@gmail.com", "hashedpassword", null, "This is the current user.", null);
        User friend1 = new User("2", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "online", "Friend1", "friend1@gmail.com", "hashedpassword", null, "This is friend 1.", null);
        User friend2 = new User("3", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "offline", "Friend2", "friend2@gmail.com", "hashedpassword", null, "This is friend 2.", null);

        currentUser.getFriends().add(friend1);
        currentUser.getFriends().add(friend2);
        ArrayList<User> allUsers = new ArrayList<>();
        allUsers.add(currentUser);
        allUsers.add(friend1);
        allUsers.add(friend2);

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
