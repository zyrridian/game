package com.example.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {

    GamePanel gamePanel;

    public Config(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void saveConfig() {

        try {
            
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("config.txt"));

            // Fullscreen
            if (gamePanel.fullscreenOn) bufferedWriter.write("On");
            if (!gamePanel.fullscreenOn) bufferedWriter.write("Off");
            bufferedWriter.newLine();

            // Music volume
            bufferedWriter.write(String.valueOf(gamePanel.music.volumeScale));
            bufferedWriter.newLine();

            // SFX volume
            bufferedWriter.write(String.valueOf(gamePanel.sfx.volumeScale));
            bufferedWriter.newLine();

            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void loadConfig() {
        
        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader("config.txt"));
            String s = bufferedReader.readLine();

            // Fullscreen
            if (s.equals("On")) {
                gamePanel.fullscreenOn = true;
            }
            if (s.equals("Off")) {
                gamePanel.fullscreenOn = false;
            }

            // Music volume
            s = bufferedReader.readLine();
            gamePanel.music.volumeScale = Integer.parseInt(s);

            // SFX volume
            s = bufferedReader.readLine();
            gamePanel.sfx.volumeScale = Integer.parseInt(s);

            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
