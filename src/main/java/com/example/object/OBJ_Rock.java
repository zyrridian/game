package com.example.object;

import java.awt.Color;

import com.example.common.GamePanel;
import com.example.entity.Entity;
import com.example.entity.Projectile;

public class OBJ_Rock extends Projectile {

    GamePanel gamePanel;
    public static final String objName = "Rock";

    public OBJ_Rock(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        name = objName;
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
    }

    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if (user.ammo >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }

    public void subtractResource(Entity user) {
        user.ammo -= useCost;
    }

    public Color getParticleColor() {
        Color color = new Color(40, 50, 0);
        return color;
    }

    public int getParticleSize() {
        int size = 10;
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
