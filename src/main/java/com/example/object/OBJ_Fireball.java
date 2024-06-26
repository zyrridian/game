package com.example.object;

import java.awt.Color;

import com.example.common.GamePanel;
import com.example.entity.Entity;
import com.example.entity.Projectile;

public class OBJ_Fireball extends Projectile {

    GamePanel gamePanel;
    public static final String objName = "Fireball";

    public OBJ_Fireball(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        name = objName;
        speed = 5;
        maxLife = 80;
        life = maxLife;
        knockBackPower = 2;
        attack = 1;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectile/fireball_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/projectile/fireball_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/projectile/fireball_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/projectile/fireball_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/projectile/fireball_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/projectile/fireball_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/projectile/fireball_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/projectile/fireball_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if (user.mana >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }

    public void subtractResource(Entity user) {
        user.mana -= useCost;
    }

    public Color getParticleColor() {
        Color color = new Color(240, 50, 0);
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
