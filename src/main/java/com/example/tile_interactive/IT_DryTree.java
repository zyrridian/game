package com.example.tile_interactive;

import java.awt.Color;

import com.example.common.GamePanel;
import com.example.entity.Entity;

public class IT_DryTree extends InteractiveTile{

    GamePanel gamePanel;

    public IT_DryTree(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);

        this.gamePanel = gamePanel;
        this.worldX = gamePanel.tileSize * col;
        this.worldY = gamePanel.tileSize * row;

        down1 = setup("/tiles_interactive/drytree", gamePanel.tileSize, gamePanel.tileSize);
        destructible = true;
        life = 2;
    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        if (entity.currentWeapon.type == type_axe) isCorrectItem = true;
        return isCorrectItem;
    }

    public void playSoundEffect() {
        gamePanel.playSoundEffect(12);
    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = new IT_Trunk(gamePanel, worldX / gamePanel.tileSize, worldY / gamePanel.tileSize);
        return tile;
    }

    public Color getParticleColor() {
        Color color = new Color(65, 50, 30);
        return color;
    }

    public int getParticleSize() {
        int size = 6; // 6 pixels
        return size;
    }

    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }
    
}
