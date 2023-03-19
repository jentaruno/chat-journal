package ui;

import javax.swing.Timer;

public class Main {
    // EFFECTS: displays splash screen, wait 2 seconds, then runs main menu
    public static void main(String[] args) {
        SplashPanel splash = new SplashPanel();
        Timer timer = new Timer(2000, e -> {
            splash.dispose();
            new MainApp();
        });
        timer.setRepeats(false);
        timer.start();
    }
}
