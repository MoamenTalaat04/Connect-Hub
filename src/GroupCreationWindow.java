import javax.swing.*;
import java.io.IOException;

public class GroupCreationWindow extends JFrame {
    private JPanel Container;
    private JTextField groupNameTextField;
    private JButton createButton;
    private JButton selectPhotoButton;
    private JButton selectPhotoButton1;
    private JTextField bioTextField;
    private JTextField groupProfileTextField;
    private JTextField groupCoverTextField;

    public GroupCreationWindow(NewsFeed newsFeed) {
     setContentPane(Container);
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        selectPhotoButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                groupProfileTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        selectPhotoButton1.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                groupCoverTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        createButton.addActionListener(e -> {
            if(groupNameTextField.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please fill the name filled fields");
                return;
            }
            newsFeed.addGroup(groupNameTextField.getText(), bioTextField.getText(), groupProfileTextField.getText(), groupCoverTextField.getText());
            JOptionPane.showMessageDialog(null, "Group created successfully");
            dispose();
        });

    }
}




