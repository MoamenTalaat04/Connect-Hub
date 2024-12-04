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
    private ContentCreation content;
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

    public myProfile(String Id) {
    List<User> profiles = profileManager.();//nadiiiim
    user=profileManager.getProfile(userID); //user ID hwa el GUI Entered by user;
    if(user==null) {
        JOptionPane.showMessageDialog(null, "No profile found");
        return;
    }
    setTitle("My Profile");
    setContentPane(thePanel);//rakz
    setSize(600,600);




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
                    profileManager.save;// hnnady method el save beta3et nadim

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
            profileManager.saveProfiles();// el save beta3et nadim
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
                    profileManager.saveProfiles();//beta3et nadiiim

                }
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Gui nadiiim
                lll
            }
        });
        myFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                friend
            }
        });
        newsFeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewsFeed(friend); // gui abodeif
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

}
