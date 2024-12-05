import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class myProfile extends JFrame {
private User user ;
private ProfileManager profileManager;
    DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private FriendManagement friend;
    private UserDatabase userDatabase;
    private MainContentCreation content;
    private JPanel thePanel;
    private JButton uploadprofile;
    private JButton uploadcover;
    private JButton uploadbio;
    private JLabel Bio;
    private JPanel postsPanel;
    private JScrollPane scrollpane;
    private JPanel postsContent;
    private JPanel newpanel;
    private JButton refreshButton;
    private JButton logoutButton;
    private JButton myFriendsButton;
    private JButton newsFeedButton;
    private JScrollPane FriendStatusScrollPane;
    private JPanel FriendStatusPanel;
    private JPanel CoverPanel;
    private JPanel ProfilePanel;

    public myProfile(User user) {
        this.friend= new FriendManagement(user);
        this.userDatabase=new UserDatabase();
        this.profileManager=new ProfileManager(user);
        this.user=user;
        loadnewdata();
        loadPosts();
        ArrayList <User> profiles = userDatabase.readUsersFromFile();
        setTitle("My Profile");
        setContentPane(thePanel);
        setSize(900,600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        uploadprofile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path=chooseFile();
                if(path==null){
                    JOptionPane.showMessageDialog(null, "No photo was uploaded");
                    return;

                }
                else{
                    user.setProfilePhotoPath(path);
                    userDatabase.saveUserToFile(user);
                    loadnewdata();
                }
            }
        });


        uploadcover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        String path=chooseFile();
        if(path==null){
            JOptionPane.showMessageDialog(null, "No photo was uploaded");
            return;

        }
        else{
            user.setCoverPhotoPath(path);
            userDatabase.saveUserToFile(user);
            loadnewdata();
        }
            }
        });
        uploadbio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newBio= JOptionPane.showInputDialog(null, "Enter Bio");
                if(newBio==null){
                    JOptionPane.showMessageDialog(null, "No bio was uploaded");
                }
                else{
                    user.setBio(newBio);
                    userDatabase.saveUserToFile(user);
                    loadnewdata();
                }
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadnewdata();
                loadPosts();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            user.setStatus("Offline");
                userDatabase.saveUserToFile(user);

                dispose();
            }
        });

        myFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new FriendManagementWindow(user);
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

    private String chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }
    private void loadPosts() {
        // Clear the PostsPanel
        postsContent.removeAll();
        postsContent.setLayout(new BoxLayout(postsContent, BoxLayout.Y_AXIS)); // Use vertical layout for posts

        // Get posts from the NewsFeed
        ArrayList<Posts> posts = profileManager.fetchPostsFromUser();
        for (Posts post : posts) {
            JPanel postPanel = new JPanel(new BorderLayout());
            postPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Add border to post panel
            JLabel UsernameAndTimeLable = new JLabel(user.getUsername() + " - " + post.getTimestamp().format(dtf));
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
            postsContent.add(UsernameAndTimeLable); // Add username and time label
            postsContent.add(postPanel); // Add post panel to the PostsPanel
        }

        scrollpane.setViewportView(postsContent); // Attach the panel to the scroll pane
        postsContent.revalidate();
        postsContent.repaint();
    }
    private void loadnewdata(){
        ProfilePanel.removeAll();
        CoverPanel.removeAll();
        Bio.setText("");
        ProfilePanel.setLayout(new BorderLayout());
        CoverPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ProfilePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        CoverPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
       if (user.getProfilePhotoPath()==null){
          JLabel profile=new JLabel("No Image");// put the default image if the user didnt upload a photo
           profile.setHorizontalAlignment(SwingConstants.LEFT);
           profile.setPreferredSize(new Dimension(100,100));
           profile.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
           ProfilePanel.add(profile,BorderLayout.CENTER);
       }else{
           ImageIcon profilePicture = new ImageIcon(user.getProfilePhotoPath());
           Image scaledProfileImage = profilePicture.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Scale image
          JLabel profile =new JLabel((new ImageIcon(scaledProfileImage))) ;
           ProfilePanel.add(profile,BorderLayout.CENTER);
       }

       if (user.getCoverPhotoPath()==null) {
           JLabel Cover=new JLabel("No Image");// put the default image if the user didnt upload a photo
            Cover.setHorizontalAlignment(SwingConstants.LEFT);
            Cover.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
           CoverPanel.add(Cover,BorderLayout.CENTER);
        }
       else{
           ImageIcon coverPicture = new ImageIcon(user.getCoverPhotoPath());
            Image scaledcoverPicture= coverPicture.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel Cover= new JLabel(new ImageIcon(scaledcoverPicture));
            CoverPanel.add(Cover,BorderLayout.CENTER);
        }
        Bio.setText(user.getBio());
    }



    private void loadFriendStatus() {
        // Clear the FriendStatusPanel
        FriendStatusPanel.removeAll();
        FriendStatusPanel.setLayout(new BoxLayout(FriendStatusPanel, BoxLayout.Y_AXIS)); // Use vertical layout

        ArrayList<String> friends = friend.FriendStatus();
        for (String friend : friends) {
            JLabel friendLabel = new JLabel(friend);
            friendLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for better readability
            friendLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align text
            FriendStatusPanel.add(friendLabel);
            FriendStatusPanel.add(Box.createVerticalStrut(10)); // Add space between friends
        }

        FriendStatusScrollPane.setViewportView(FriendStatusPanel); // Attach the panel to the scroll pane

   }

}
