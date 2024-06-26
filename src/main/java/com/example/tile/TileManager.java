package com.example.tile;

import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.example.common.GamePanel;
import com.example.common.UtilityTool;

import java.awt.*;

public class TileManager {
    
    GamePanel gamePanel;
    public Tile[] tile;
    public int mapTileNumber[][][];
    boolean drawPath = true;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        // Read tile data file
        InputStream iStream = getClass().getResourceAsStream("/maps/tiledata.txt");
        BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));

        // Getting tile names and collision info from the file
        String line;

        try {
            while ((line = bReader.readLine()) != null) {
                fileNames.add(line);
                collisionStatus.add(bReader.readLine());
            }    
            bReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Initialize the tile array based on the fileNames size
        tile = new Tile[fileNames.size()];
        getTileImage();

        // Get the maxWorldCol and maxWorldRow
        iStream = getClass().getResourceAsStream("/maps/worldmap.txt");
        bReader = new BufferedReader(new InputStreamReader(iStream));

        try {
            String line2 = bReader.readLine();
            String maxTile[] = line2.split(" ");
            gamePanel.maxWorldCol = maxTile.length;
            gamePanel.maxWorldRow = maxTile.length;
            mapTileNumber = new int[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];
            bReader.close();
        } catch (IOException e) {
            System.out.println("Exception!");
        }

        // loadMap("/maps/worldmap.txt", 0);
        loadMap("/maps/map.txt", 0);
        loadMap("/maps/indoor01.txt", 1);
        loadMap("/maps/dungeon01.txt", 2);
        loadMap("/maps/dungeon02.txt", 3);
        loadMap("/maps/map02.txt", 4);
        loadMap("/maps/map03.txt", 5);

    }

    public void getTileImage() {
        
        for (int i = 0; i < fileNames.size(); i++) {
            String fileName;
            boolean collision;

            // Get a file name
            fileName = fileNames.get(i);

            // Get a collision status
            if (collisionStatus.get(i).equals("true")) {
                collision = true;
            } else {
                collision = false;
            }

            // Setup
            setup(i, fileName, collision);
        }

    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool utilityTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName));
            tile[index].image = utilityTool.scaleImage(tile[index].image, gamePanel.tileSize, gamePanel.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int map) {

        try {

            // Read .txt map file
            InputStream InputStream = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(InputStream));

            int col = 0;
            int row = 0;

            while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                
                String line = bufferedReader.readLine();
                
                while (col < gamePanel.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int number = Integer.parseInt(numbers[col]);
                    mapTileNumber[map][col][row] = number;
                    col++;
                }

                if (col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
                 
            }

            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {

            int tileNumber = mapTileNumber[gamePanel.currentMap][worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            // Draw only the map that visible in camera
            if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY
            ) {
                g2.drawImage(tile[tileNumber].image, screenX, screenY, null);
            }
            
            worldCol++;

            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }

        }

        // Debug to draw path finding algorithm
        if (drawPath) {
            g2.setColor(new Color(255, 0, 0, 70));
            for (int i = 0; i < gamePanel.pathFinder.pathList.size(); i++) {
                int worldX = gamePanel.pathFinder.pathList.get(i).col * gamePanel.tileSize;
                int worldY = gamePanel.pathFinder.pathList.get(i).row * gamePanel.tileSize;
                int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
                int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
                g2.fillRect(screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);
            }
        }

    }

}
