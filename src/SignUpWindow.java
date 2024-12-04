import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class SignUpWindow {
    private JPanel Container;
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JFormattedTextField yyyy;
    private JButton addProfilePictureButton;
    private JButton profileCoverButton;
    private JPasswordField confirmPasswordField;
    private JButton signupButton;
    private JButton loginButton;
    private JTextField dd;
    private JTextField mm;
    private JTextArea bioField;
    private String emailFormat="^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,6}$";
    private String usernameFormat ="^[a-zA-Z][a-zA-Z0-9._]{2,14}$";

    private String profilePicPath=null;
    private String profileCoverPath=null;

    public SignUpWindow() {
        AccountManagement accountManagement = new AccountManagement(new UserDatabase());
        String email=emailField.getText();
        String username=usernameField.getText();
        String password= Arrays.toString(passwordField.getPassword());
        String day=dd.getText();
        String month=mm.getText();
        String year=yyyy.getText();
        String bio=bioField.getText();

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (!email.matches(emailFormat)) JOptionPane.showMessageDialog(null, "Please Enter a Valid email", "Invalid email", JOptionPane.INFORMATION_MESSAGE);// error message -> invalid email format
                else if () return; //error message -> an existing account linked to this email
                else if (username.equals(null) || !username.matches(usernameFormat))  JOptionPane.showMessageDialog(null, "Please Enter a Valid Username", "Invalid username", JOptionPane.INFORMATION_MESSAGE);
                else if ()
            }
        });
    }
}
