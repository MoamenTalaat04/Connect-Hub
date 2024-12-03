import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class myProfile extends JFrame {
private User user ;
private ProfileManager profileManager;
    private JPanel thePanel;
    private JButton myPostsButton;
    private JButton newsFeedButton;
    private JButton friendsButton;
    private JButton myStoriesButton;
    private JLabel profile;
    private JLabel Cover;

    public myProfile(String Id) {
    List<User> profiles = profileManager.loadProfiles();
    user=profileManager.getProfile(userID)//user ID hwa el GUI Entered by user;
    if(user==null) {
        JOptionPane.showMessageDialog(null, "No profile found");
        return;
    }
    setTitle("My Profile");
    setContentPane(thePanel);//rakz
    setSize(600,600);
    /*JScrollPane scrollpane = new JScrollPane(thePanel);
    scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    thePanel.add(scrollpane);*/



        friendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        newsFeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            //Hnd5l GUI el Newsfeed
            }
        });
        myStoriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.stories;
            }
        });
        myPostsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            user.posts
            }
        });
    }

}
