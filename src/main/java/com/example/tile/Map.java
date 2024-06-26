package com.example.tile;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.example.common.GamePanel;

public class Map extends TileManager {

    GamePanel gamePanel;
    BufferedImage worldMap[];
    public boolean miniMapOn = false;

    public Map(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        createWorldMap();
    }

    public void createWorldMap() {
        worldMap = new BufferedImage[gamePanel.maxMap];
        int worldMapWidth = gamePanel.tileSize * gamePanel.maxWorldCol;
        int worldMapHeight = gamePanel.tileSize * gamePanel.maxWorldRow;

        for (int i = 0; i < gamePanel.maxMap; i++) {
            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = (Graphics2D) worldMap[i].createGraphics();
            int col = 0;
            int row = 0;

            while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                int tileNumber = mapTileNumber[i][col][row];
                int x = gamePanel.tileSize * col;
                int y = gamePanel.tileSize * row;
                g2.drawImage(tile[tileNumber].image, x, y, null);
                col++;
                if (col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            
            g2.dispose();
        }
    }

    public void drawFullMapScreen(Graphics2D g2) {

        // Background color
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        // Draw map
        int width = 500;
        int height = 500;
        int x = gamePanel.screenWidth / 2 - width / 2;
        int y = gamePanel.screenHeight / 2 - height / 2;
        g2.drawImage(worldMap[gamePanel.currentMap], x, y, width, height, null);

        // Draw player
        double scale = (double) (gamePanel.tileSize * gamePanel.maxWorldCol) / width;
        int playerX = (int) (x + gamePanel.player.worldX / scale);
        int playerY = (int) (y + gamePanel.player.worldY / scale);
        int playerSize = (int) (gamePanel.tileSize / scale);
        g2.drawImage(gamePanel.player.down1, playerX, playerY, playerSize, playerSize, null);

        // Hint
        g2.setFont(gamePanel.ui.maruMonica.deriveFont(32F));
        g2.setColor(Color.white);
        g2.drawString("Press M to close", 750, 550);

    }

    public void drawMiniMap(Graphics2D g2) {

        if (miniMapOn == true) {

            // Draw map
            int width = 200;
            int height = 200;
            int x = gamePanel.screenWidth - width - 50;
            int y = 50;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8F));
            g2.drawImage(worldMap[gamePanel.currentMap], x, y, width, height, null);

            // Draw player
            double scale = (double) (gamePanel.tileSize * gamePanel.maxWorldCol) / width;
            int playerX = (int) (x + gamePanel.player.worldX / scale);
            int playerY = (int) (y + gamePanel.player.worldY / scale);
            int playerSize = (int) (gamePanel.tileSize / 3);
            g2.drawImage(gamePanel.player.down1, playerX - 6, playerY - 6, playerSize, playerSize, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

        }
    }
    
}
