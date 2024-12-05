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
    private FriendManagment friend;
    private AccountManagment accountManagment;
    UserDatabase userDatabase;
    private MainContentCreation content;
    private JPanel thePanel;
    private JButton uploadprofile;
    private JButton uploadcover;
    private JButton uploadbio;
    private JLabel profile;
    private JLabel Cover;
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

    public myProfile(User user) {
        this.friend= new FriendManagment(user);
        this.accountManagment=new AccountManagment;
        this.profileManager=new ProfileManager();
        this.user=user;
        loadnewdata();
        List <User> profiles = accountManagment.readUsersFromFile();  //nadiiiim
        setTitle("My Profile");
        setContentPane(thePanel);
        setSize(1300,1000);
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
                    AccountManagment.saveUserToFile;   // hnnady method el save beta3et nadim
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
            AccountManagment.saveUserToFile();// el save beta3et nadim
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
                    AccountManagment.saveUserToFile();//beta3et nadiiim
                loadnewdata();
                }
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadnewdata();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            user.setStatus("Offline");
            AccountManagment.saveUserToFile();//beta3et nadiiim

                dispose();
            }
        });

        myFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new FriendWindow(user);
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
       if (user.getProfilePhotoPath()==null){
           profile=new JLabel(,,,,,);// put the default image if the user didnt upload a photo
       }else{
           ImageIcon profilePicture = new ImageIcon(user.getProfilePhotoPath());
           Image scaledProfileImage = profilePicture.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Scale image
           profile=new JLabel((new ImageIcon(scaledProfileImage))) ;


       }
        if (user.getCoverPhotoPath()==null) {
        Cover=new JLabel(.......);// put the default image if the user didnt upload a photo
        }
       else{
           ImageIcon coverPicture = new ImageIcon(user.getCoverPhotoPath());
            Image scaledcoverPicture= coverPicture.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            Cover= new JLabel(new ImageIcon(scaledcoverPicture));
        }

        Bio= new JLabel(user.getBio());
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
