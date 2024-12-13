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
    private JScrollPane MembersScrollPane;
    private JScrollPane PostsScrollPane;
    private Group group;
    private User user;
    private NewsFeed newsFeed;
    private GroupManagement groupManagement;
    DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public memberGroupUI(Group group, User user , NewsFeed newsFeed)
    {
        this.newsFeed = newsFeed;
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
        if (group.getGroupCoverPath() != null && !group.getGroupCoverPath().isEmpty()){
            Cover.add(new JLabel(new ImageIcon(group.getGroupCoverPath())));
        }
        else {
            Cover.add(new JLabel("No Image"));
        }
        loadPosts();
        loadMembers();
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
        if (groupManagement.isOwner(group,this.user)){
            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
            JButton deleteGroupButton = new JButton("Delete Group");
            deleteGroupButton.setFont(new Font("Arial", Font.PLAIN, 12));
            deleteGroupButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    groupManagement.deleteGroup(group);
                    dispose();
                }
            });
            buttonsPanel.add(deleteGroupButton);
            properties.add(buttonsPanel, BorderLayout.SOUTH);


        }



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
            if (groupManagement.isAdmin(group,this.user) || groupManagement.isOwner(group,this.user)){


                JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
                JButton deletePostButton = new JButton("Delete Post");
                deletePostButton.setFont(new Font("Arial", Font.PLAIN, 12));
                deletePostButton.addActionListener(e -> {
                    groupManagement.removePostFromGroup(group, post); // Delete the post
                    loadPosts(); // Reload the posts to update the UI after deletion
                });
                buttonsPanel.add(deletePostButton);
                postPanel.add(buttonsPanel, BorderLayout.EAST); // Add the button to the panel
            }
            PostsPanel.add(UsernameAndTimeLable); // Add username and time label
            PostsPanel.add(postPanel); // Add post panel to the PostsPanel
        }

        PostsScrollPane.setViewportView(PostsPanel); // Attach the panel to the scroll pane
        PostsPanel.revalidate();
        PostsPanel.repaint();
    }

    public void loadMembers()
    {
        members.removeAll();
        newsFeed.fetchAllUsers();
        members.setLayout(new BoxLayout(members, BoxLayout.Y_AXIS));
        ArrayList<String> usersId = group.getGroupMembersIds();
        ArrayList<User> users = newsFeed.getUsersById(usersId);
        for (User user  : users) {
            JPanel memberPanel = createMemberPanel(user);
            members.add(memberPanel);
            members.add(Box.createVerticalStrut(10));
        }
        MembersScrollPane.setViewportView(members);

    }

    private JPanel createMemberPanel(User user){
        JPanel MemberPanel = new JPanel();
        MemberPanel.setLayout(new BorderLayout(10, 10));
        MemberPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        MemberPanel.setPreferredSize(new Dimension(250, 190));

        JLabel profilePictureLabel = createProfilePictureLabel(user.getProfilePhotoPath());
        MemberPanel.add(profilePictureLabel, BorderLayout.WEST);

        JLabel friendLabel = new JLabel(user.getUsername());
        friendLabel.setFont(new Font("Arial", Font.BOLD, 16));
        MemberPanel.add(friendLabel, BorderLayout.CENTER);
        if (groupManagement.isAdmin(group,this.user) && !groupManagement.isOwner(group,user)){
            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
            JButton deleteMemberButton = new JButton("Kick");
            deleteMemberButton.setFont(new Font("Arial", Font.PLAIN, 12));
            deleteMemberButton.addActionListener(e -> {
                groupManagement.removeUserFromGroup(group, user.getUserId());
                loadMembers(); // Reload the members to update the UI after deletion
            });
            buttonsPanel.add(deleteMemberButton);
            MemberPanel.add(buttonsPanel, BorderLayout.EAST); // Add the button to the panel
        }
        else if (groupManagement.isOwner(group,this.user)){
            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
            JButton deleteMemberButton = new JButton("Kick");
            deleteMemberButton.setFont(new Font("Arial", Font.PLAIN, 12));
            deleteMemberButton.addActionListener(e -> {
                groupManagement.removeUserFromGroup(group, user.getUserId());
                loadMembers(); // Reload the members to update the UI after deletion
            });
            buttonsPanel.add(deleteMemberButton);

            if (groupManagement.isAdmin(group,user)){
            JButton demoteAdminButton = new JButton("Demote");
            demoteAdminButton.setFont(new Font("Arial", Font.PLAIN, 12));
            demoteAdminButton.addActionListener(e -> {
                groupManagement.demoteAdminToMember(group, user.getUserId());
                loadMembers(); // Reload the members to update the UI after deletion
            });
                buttonsPanel.add(demoteAdminButton);}
            else if (!groupManagement.isAdmin(group,user)){
                JButton promoteMemberButton = new JButton("Promote");
                promoteMemberButton.setFont(new Font("Arial", Font.PLAIN, 12));
                promoteMemberButton.addActionListener(e -> {
                    groupManagement.promoteMemberToAdmin(group, user.getUserId());
                    loadMembers(); // Reload the members to update the UI after deletion
                });
                buttonsPanel.add(promoteMemberButton);
            }
            MemberPanel.add(buttonsPanel, BorderLayout.EAST); // Add the button to the panel
        }
        return MemberPanel;
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
