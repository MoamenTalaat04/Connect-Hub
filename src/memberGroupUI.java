import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class memberGroupUI extends JFrame {
    private JPanel Cover;
    private JPanel addPosts;
    private JPanel properties;
    private JPanel members;
    private JButton leaveGroupButton;
    private JButton newsFeedButton;
    private JScrollPane PostContantScrollPane;
    private JTextField PostContantField;
    private JButton addPostButton;
    private JPanel PostsPanel;
    private JLabel bioText;
    private JPanel panel1;
    private JButton PostPhotoButton;
    private JLabel PostPhotoPathLable;
    private JScrollPane PostsScrollPane;
    private Group group;
    private User user;
    private GroupManagement groupManagement;
    DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public memberGroupUI(Group group, User user)
    {
        this.group = group;
        this.user = user;
        groupManagement = new GroupManagement(user);
        setContentPane(panel1);
        setTitle("Group Page");
        setSize(1300,1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        bioText.setText(group.getGroupBio());
        Cover.add(new JLabel(new ImageIcon(group.getGroupCoverPath())));
        loadPosts();
        PostContantField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                PostContantField.setText("");
            }
        });
        addPostButton.addActionListener(e -> {
            // Add a new post
            groupManagement.addPost(PostContantField.getText(), PostPhotoPathLable.getText(), group);
            PostPhotoPathLable.setText("");
            PostContantField.setText("");
            loadPosts();
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
        leaveGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                groupManagement.removeUserFromGroup(group, user.getUserId());
                new NewsFeedWindow(user);
                dispose();
            }
        });
        newsFeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewsFeedWindow(user);
                dispose();
            }
        });

    }
    private void loadPosts() {
        // Clear the PostsPanel
        PostsPanel.removeAll();
        PostsPanel.setLayout(new BoxLayout(PostsPanel, BoxLayout.Y_AXIS)); // Use vertical layout for PostsPanel

        // Get PostsPanel from the NewsFeed
        ArrayList<Posts> posts = group.getPosts();
        for (Posts post : posts) {
            JPanel postPanel = new JPanel(new BorderLayout());
            postPanel.setPreferredSize(new Dimension(1000, 150)); // Set size of post panel
            postPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Add border to post panel
            JLabel UsernameAndTimeLable = new JLabel(group.getGroupName() + " - " + post.getTimestamp().format(dtf));
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

}
