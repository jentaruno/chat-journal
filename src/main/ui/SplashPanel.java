package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SplashPanel extends JPanel {

    private Image splashImage;

    public SplashPanel() {
        try {
            // Load the image from the project root folder
            BufferedImage image = ImageIO.read(new File("chat-journal-splash.png"));
            splashImage = Toolkit.getDefaultToolkit().createImage(image.getSource());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(splashImage.getWidth(null), splashImage.getHeight(null)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the image in the center of the panel
        int x = (getWidth() - splashImage.getWidth(null)) / 2;
        int y = (getHeight() - splashImage.getHeight(null)) / 2;
        g.drawImage(splashImage, x, y, null);
    }
}