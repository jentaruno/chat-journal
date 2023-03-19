package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SplashPanel extends JPanel {

    private Image splashImage;

    public SplashPanel() {
        try {
            BufferedImage image = ImageIO.read(new File("chat-journal-splash_640x480.png"));
            splashImage = Toolkit.getDefaultToolkit().createImage(image.getSource());
        } catch (IOException e) {
            System.out.println("Error loading splash image!");
        }
        setPreferredSize(new Dimension(splashImage.getWidth(null), splashImage.getHeight(null)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = (getWidth() - splashImage.getWidth(null)) / 2;
        int y = (getHeight() - splashImage.getHeight(null)) / 2;
        g.drawImage(splashImage, x, y, null);
    }
}