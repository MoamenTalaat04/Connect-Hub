import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private JPanel posts;
    private JLabel bioText;
    private JLabel groupName;
    private JPanel panel1;
    private JButton PostPhotoButton;
    private JLabel PostPhotoPathLable;

    public memberGroupUI(Group group, User user)
    {
        setContentPane(panel1);
        setTitle("Group Page");
        setSize(1300, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        groupName.setText(group.getGroupName());
        PostContantField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                PostContantField.setText("");
            }
        });
        addPostButton.addActionListener(e -> {
            // Add a new post
            group.addPost(PostContantField.getText(), PostPhotoPathLable.getText());
            PostPhotoPathLable.setText("");
            PostContantField.setText("");
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
                group.removeMember(user);
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
}
