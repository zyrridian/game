package com.example.monster;

import java.util.Random;

import com.example.common.GamePanel;
import com.example.entity.Entity;
import com.example.object.OBJ_Coin_Bronze;
import com.example.object.OBJ_Heart;
import com.example.object.OBJ_Mana_Crystal;
public class MON_Orc extends Entity {

    GamePanel gamePanel;

    public MON_Orc(GamePanel gamePanel) {

        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_monster;
        name = "Orc";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 10;
        life = maxLife;
        attack = 8;
        defense = 2;
        exp = 10;
        knockBackPower = 5;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;
        motion1_duration = 40;
        motion2_duration = 85;

        getImage();
        getAttackImage();
        
    }

    public void getImage() {
        up1 = setup("/monster/orc_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/monster/orc_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/monster/orc_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/monster/orc_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/monster/orc_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/monster/orc_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/monster/orc_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/monster/orc_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void getAttackImage() {
        attackUp1 = setup("/monster/orc_attack_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackUp2 = setup("/monster/orc_attack_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown1 = setup("/monster/orc_attack_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown2 = setup("/monster/orc_attack_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackLeft1 = setup("/monster/orc_attack_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackLeft2 = setup("/monster/orc_attack_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight1 = setup("/monster/orc_attack_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight2 = setup("/monster/orc_attack_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
    }

    public void setAction() { // Monster simple ai algorithm

        if (onPath) { 

            // Check if it stops chasing
            checkStopChasingOrNot(gamePanel.player, 15, 100);

            // Search the direction to go
            searchPath(getGoalCol(gamePanel.player), getGoalRow(gamePanel.player));

        } else {

            // Check if it starts chasing
            checkStartChasingOrNot(gamePanel.player, 5, 100);

            // Get a random direction
            getRandomDirection(120);

        }

        // Check if it attacks
        if (!attacking) {
            checkAttackOrNot(30, gamePanel.tileSize * 4, gamePanel.tileSize); // Pass small number in rate if you want the monster to be aggressive
        }

    }

    public void damageReaction() {
        actionLockCounter = 0;
        // direction = gamePanel.player.direction;
        onPath = true;
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
