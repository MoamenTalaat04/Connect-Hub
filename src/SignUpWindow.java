import javax.swing.*;
import java.io.IOException;
import java.util.Map;

public class SignUpWindow extends JFrame {
    private JPanel Container;
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JFormattedTextField yyyy;
    private JPasswordField confirmPasswordField;
    private JButton signupButton;
    private JButton loginButton;
    private JTextField dd;
    private JTextField mm;
    private JTextArea bioField;
    private String profilePicPath=null;
    private String profileCoverPath=null;
    private final UserDatabase userDatabase;
    private final AccountManagement accountManagement;

    public SignUpWindow() throws IOException {
        setContentPane(Container);
        setTitle("Signup");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        userDatabase = UserDatabase.getInstance();
        accountManagement = new AccountManagement(userDatabase);

        signupButton.addActionListener(e -> handleSignUp());
        loginButton.addActionListener(e -> openLogin());
    }

    private void handleSignUp() {
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String day = dd.getText();
        String month = mm.getText();
        String year = yyyy.getText();
        String bio = bioField.getText();

        Map<String, String> emailPasswordMap = userDatabase.readMapFromUsers();
        if (!com.connecthub.util.ValidationUtils.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please Enter a Valid email address", "Invalid email address", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (emailPasswordMap.containsKey(email)) {
            JOptionPane.showMessageDialog(this, "An account with this email address already exists, Please Enter a Valid email address", "Invalid email address", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (!com.connecthub.util.ValidationUtils.isValidUsername(username)) {
            JOptionPane.showMessageDialog(this, "Please Enter a Valid Username", "Invalid username", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (!com.connecthub.util.ValidationUtils.isStrongPassword(password)) {
            JOptionPane.showMessageDialog(this, "Password must be at least 8 chars and include uppercase, lowercase, digit, and special character.", "Invalid Password", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Password and confirm password does not match\nplease rewrite password correctly", "Password does not match", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (!com.connecthub.util.ValidationUtils.isValidDate(year, month, day)) {
            JOptionPane.showMessageDialog(this, "Please Enter a Valid date of birth", "Invalid date of birth", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (accountManagement.signUp(email, username, password, year + "/" + month + "/" + day, bio, profilePicPath, profileCoverPath)) {
            openLogin();
            return;
        }
        JOptionPane.showMessageDialog(this, "Error in registration process", "Error 404", JOptionPane.INFORMATION_MESSAGE);
    }

    private void openLogin() {
        try {
            new LoginWindow();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        dispose();
    }
}
