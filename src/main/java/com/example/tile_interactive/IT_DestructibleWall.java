package com.example.tile_interactive;

import java.awt.Color;
import java.util.Random;

import com.example.common.GamePanel;
import com.example.entity.Entity;
import com.example.object.OBJ_Coin_Bronze;
import com.example.object.OBJ_Heart;
import com.example.object.OBJ_Mana_Crystal;

public class IT_DestructibleWall extends InteractiveTile {

    GamePanel gamePanel;

    public IT_DestructibleWall(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);

        this.gamePanel = gamePanel;
        this.worldX = gamePanel.tileSize * col;
        this.worldY = gamePanel.tileSize * row;

        down1 = setup("/tiles_interactive/destructiblewall", gamePanel.tileSize, gamePanel.tileSize);
        destructible = true;
        life = 3;
    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        if (entity.currentWeapon.type == type_pickaxe) isCorrectItem = true;
        return isCorrectItem;
    }

    public void playSoundEffect() {
        gamePanel.playSoundEffect(19);
    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;
        return tile;
    }

    public Color getParticleColor() {
        Color color = new Color(65, 65, 65);
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

    public void checkDrop() {
        
        // Cast a die
        int i = new Random().nextInt(100) + 1;

        // Set the monster drop
        if (i < 50) dropItem(new OBJ_Coin_Bronze(gamePanel));
        if (i >= 50 && i < 75) dropItem(new OBJ_Heart(gamePanel));
        if (i >= 75 && i < 100) dropItem(new OBJ_Mana_Crystal(gamePanel));

    }
    
}
