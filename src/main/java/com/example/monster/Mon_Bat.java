package com.example.monster;

import java.util.Random;

import com.example.common.GamePanel;
import com.example.entity.Entity;
import com.example.object.OBJ_Coin_Bronze;
import com.example.object.OBJ_Heart;
import com.example.object.OBJ_Mana_Crystal;

public class MON_Bat extends Entity {
    
    GamePanel gamePanel;

    public MON_Bat(GamePanel gamePanel) {

        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_monster;
        name = "Bat";
        defaultSpeed = 4;
        speed = defaultSpeed;
        maxLife = 7;
        life = maxLife;
        attack = 7;
        defense = 0;
        exp = 7;

        solidArea.x = 3;
        solidArea.y = 15;
        solidArea.width = 42;
        solidArea.height = 21;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        
    }

    public void getImage() {
        up1 = setup("/monster/bat_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/monster/bat_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/monster/bat_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/monster/bat_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/monster/bat_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/monster/bat_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/monster/bat_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/monster/bat_down_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setAction() { // Monster simple ai algorithm
        getRandomDirection(10);
    }

    public void damageReaction() {
        actionLockCounter = 0;
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
