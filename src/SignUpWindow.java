import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class SignUpWindow extends JFrame {
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
    private String emailFormat="^[a-zA-Z][\\w.-]*@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,6}$";
    private String usernameFormat ="^[a-zA-Z][a-zA-Z0-9._]{2,14}$";
    private String profilePicPath=null;
    private String profileCoverPath=null;

    public SignUpWindow() throws IOException {
        setContentPane(Container);
        setTitle("Signup");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        UserDatabase userDatabase = new UserDatabase();
        ArrayList<User> users=userDatabase.readUsersFromFile();
        Map<String,String> emailPasswordMap=userDatabase.readMapFromUsers();

        AccountManagement accountManagement = new AccountManagement(userDatabase);



        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email=emailField.getText();
                String username=usernameField.getText();
                String password= new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                String day=dd.getText();
                String month=mm.getText();
                String year=yyyy.getText();
                String bio=bioField.getText();
                int parsedYear = 0, parsedMonth = 0, parsedDay = 0;
                boolean isValidDate = true;


                if (!email.matches(emailFormat)) JOptionPane.showMessageDialog(null, "Please Enter a Valid email address", "Invalid email address", JOptionPane.INFORMATION_MESSAGE);// error message -> invalid email format
                else if (emailPasswordMap.containsKey(email)) JOptionPane.showMessageDialog(null, "An account with this email address already exists, Please Enter a Valid email address", "Invalid email address", JOptionPane.INFORMATION_MESSAGE); //error message -> an existing account linked to this email
                else if (!username.matches(usernameFormat))   JOptionPane.showMessageDialog(null, "Please Enter a Valid Username", "Invalid username", JOptionPane.INFORMATION_MESSAGE);
                else if (password.length() < 8) {
                    JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long.", "Invalid Password", JOptionPane.INFORMATION_MESSAGE);
                } else if (!password.matches(".*[a-z].*")) {
                    JOptionPane.showMessageDialog(null, "Password must contain at least one lowercase letter.", "Invalid Password", JOptionPane.INFORMATION_MESSAGE);
                } else if (!password.matches(".*[A-Z].*")) {
                    JOptionPane.showMessageDialog(null, "Password must contain at least one uppercase letter.", "Invalid Password", JOptionPane.INFORMATION_MESSAGE);
                } else if (!password.matches(".*\\d.*")) {
                    JOptionPane.showMessageDialog(null, "Password must contain at least one digit.", "Invalid Password", JOptionPane.INFORMATION_MESSAGE);
                } else if (!password.matches(".*[@$!%*?&].*")) {
                    JOptionPane.showMessageDialog(null, "Password must contain at least one special character (e.g., !@#$%^&*).", "Invalid Password", JOptionPane.INFORMATION_MESSAGE);
                }
                else if (!password.equals(confirmPassword))   JOptionPane.showMessageDialog(null, "Password and confirm password does not match\nplease rewrite password correctly", "Password does not match", JOptionPane.INFORMATION_MESSAGE);
                else {
                   try {
                       parsedYear = Integer.parseInt(year);
                       parsedMonth = Integer.parseInt(month);
                       parsedDay = Integer.parseInt(day);
                   } catch (NumberFormatException ex) {
                       isValidDate = false;
                       //JOptionPane.showMessageDialog(null, "Please enter valid numeric values for the date of birth (year, month, day).", "Invalid date", JOptionPane.INFORMATION_MESSAGE);
                   }
                 if (!(1900<=parsedYear ) || !(parsedYear<=2024))    JOptionPane.showMessageDialog(null, "Please Enter a Valid year for date of birth", "Invalid date of birth", JOptionPane.INFORMATION_MESSAGE);
                else if (!(1<=parsedMonth)||!(parsedMonth<=12))      JOptionPane.showMessageDialog(null, "Please Enter a Valid month for date of birth", "Invalid date of birth", JOptionPane.INFORMATION_MESSAGE);
                else if (!(1<=parsedDay)||!(parsedDay<=31))          JOptionPane.showMessageDialog(null, "Please Enter a Valid day for date of birth", "Invalid date of birth", JOptionPane.INFORMATION_MESSAGE);
                else if (accountManagement.signUp(email,username,password,year+"/"+month+"/"+day,bio,profilePicPath,profileCoverPath)) {
                    System.out.println("User signed up successfully");
                     try {
                         new LoginWindow();
                     } catch (IOException ex) {
                         throw new RuntimeException(ex);
                     }
                     dispose();
                 }
                else JOptionPane.showMessageDialog(null, "Error in registration process", "Error 404", JOptionPane.INFORMATION_MESSAGE);
            }}
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    new LoginWindow();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();

            }
        });
    }
}
