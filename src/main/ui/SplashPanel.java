package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JWindow;

public class SplashPanel extends JWindow {
    private Image splashImage;

    // EFFECTS: load splash image from the project root folder
    public SplashPanel() {
        try {
            BufferedImage image = ImageIO.read(new File("chat-journal-splash.png"));
            splashImage = Toolkit.getDefaultToolkit().createImage(image.getSource());
            splashImage = splashImage.getScaledInstance(640, 480, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.out.println("Failed to load image");
            splashImage = null;
        }

        setPreferredSize(new Dimension(640, 480));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: draw image in the center of the window
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(splashImage, 0, 0, null);
    }
}