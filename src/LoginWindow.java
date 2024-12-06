import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class LoginWindow extends JFrame {
    private JPanel Container;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private String emailFormat="^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,6}$";

    public LoginWindow() throws IOException {
        setContentPane(Container);
        setTitle("Login");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        UserDatabase userDatabase = new UserDatabase();
        ArrayList<User> users=userDatabase.readUsersFromFile();
        Map<String,String> emailPasswordMap=userDatabase.readMapFromUsers();
        AccountManagement accountManagement = new AccountManagement(userDatabase);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                if (!email.matches(emailFormat)) JOptionPane.showMessageDialog(null, "Please Enter a Valid email address", "Invalid email address", JOptionPane.INFORMATION_MESSAGE);// error message -> invalid email format
                else if (!emailPasswordMap.containsKey(email))  JOptionPane.showMessageDialog(null, "No account with this email address exists", "Invalid email address", JOptionPane.INFORMATION_MESSAGE);
                else if (password.isEmpty() || password.equals("")) JOptionPane.showMessageDialog(null, "Please enter password", "Invalid Password", JOptionPane.INFORMATION_MESSAGE);
                else {
                    try {
                       if (HashPassword.hashPassword(password).equals(emailPasswordMap.get(email))) {
                            accountManagement.login(email,password);
                            //window to appear after login
                           User currentUser = null;
                            for (User user : users){
                                if (user.getEmail().equals(email)){
                                    currentUser=user;
                                    break;
                                }
                            }
                            currentUser.setStatus("Online");
                            new NewsFeedWindow(currentUser,users);
                            dispose();
                        }
                        else {JOptionPane.showMessageDialog(null, "please enter correct password", "Invalid Password", JOptionPane.INFORMATION_MESSAGE);}
                    } catch (NoSuchAlgorithmException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new SignUpWindow();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });
    }
}
