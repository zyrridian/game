package com.example;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.example.common.GamePanel;

public class Main {

    public static JFrame window;

    public static void main(String[] args) throws Exception {
        
        window = new JFrame("Echoes of Eternity");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        new Main().setIcon();

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if (gamePanel.fullscreenOn) {
            window.setUndecorated(true);
        }


        window.pack(); // Fit to the size of subclass

        window.setVisible(true);
        window.setLocationRelativeTo(null);
 
        gamePanel.setupGame();
        gamePanel.startGameThread();

    }

    public void setIcon() {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("player/boy_down_1.png"));
        window.setIconImage(icon.getImage());
    }

}
