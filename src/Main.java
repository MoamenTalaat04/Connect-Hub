import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

import static java.awt.SystemColor.window;

public class Main {
    public static void main(String[] args) throws IOException {

        try {
            // Set FlatMacDarkLaf as the Look and Feel
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch the IntelliJ form window
        SwingUtilities.invokeLater(() -> {
            LoginWindow window = null;
            try {
                window = new LoginWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            window.setVisible(true);
        });
    }

}
