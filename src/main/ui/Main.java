package ui;

import javax.swing.*;
import java.awt.*;

public class Main {
    static MainApp mainApp;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(640, 480));
            SplashPanel splashPanel = new SplashPanel();
            frame.add(splashPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            splashPanel.repaint();

            // Display the splash screen for 3 seconds
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Close the splash screen and show the main application window
            frame.setVisible(false);
            frame.remove(splashPanel);
            mainApp = new MainApp();
            frame.pack();
            frame.setLocationRelativeTo(null); // Center the frame on the screen
        });
    }
}
