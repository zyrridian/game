package com.example.tile_interactive;

import java.awt.Graphics2D;

import com.example.common.GamePanel;
import com.example.entity.Entity;

public class InteractiveTile extends Entity {

    GamePanel gamePanel;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gamePanel, int col, int row) {
        super(gamePanel);
        this.gamePanel = gamePanel;
    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        return isCorrectItem;
    }

    public void playSoundEffect() {

    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;
        return tile;
    }

    public void update() {
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
    
    public void draw(Graphics2D g2) { // Remove invincible effect on trees
        
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        // Draw only the map that visible in camera
        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
            worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
            worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
            worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY
        ) {
            g2.drawImage(down1, screenX, screenY, null);
        }

    }
}
