import javax.swing.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;

public class LoginWindow extends JFrame {
    private JPanel Container;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private final UserDatabase userDatabase;
    private final AccountManagement accountManagement;

    public LoginWindow() throws IOException {
        setContentPane(Container);
        setTitle("Login");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        userDatabase = UserDatabase.getInstance();
        accountManagement = new AccountManagement(userDatabase);

        loginButton.addActionListener(e -> handleLogin());
        signupButton.addActionListener(e -> openSignUp());
    }

    private void handleLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        Map<String, String> emailPasswordMap = userDatabase.readMapFromUsers();

        if (!com.connecthub.util.ValidationUtils.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please Enter a Valid email address", "Invalid email address", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (!emailPasswordMap.containsKey(email)) {
            JOptionPane.showMessageDialog(this, "No account with this email address exists", "Invalid email address", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter password", "Invalid Password", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            if (!HashPassword.hashPassword(password).equals(emailPasswordMap.get(email)) || !accountManagement.login(email, password)) {
                JOptionPane.showMessageDialog(this, "please enter correct password", "Invalid Password", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            ArrayList<User> users = userDatabase.readUsersFromFile();
            User currentUser = findUserByEmail(users, email);
            if (currentUser == null) {
                JOptionPane.showMessageDialog(this, "Unable to locate user profile.", "Login Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            currentUser.setStatus("Online");
            userDatabase.saveUsersToFile(users);
            new NewsFeedWindow(currentUser);
            dispose();
        } catch (NoSuchAlgorithmException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private User findUserByEmail(ArrayList<User> users, String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    private void openSignUp() {
        try {
            new SignUpWindow();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        dispose();
    }
}
