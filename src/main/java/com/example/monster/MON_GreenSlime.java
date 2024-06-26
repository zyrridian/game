package com.example.monster;

import java.util.Random;

import com.example.common.GamePanel;
import com.example.entity.Entity;
import com.example.object.OBJ_Coin_Bronze;
import com.example.object.OBJ_Heart;
import com.example.object.OBJ_Mana_Crystal;
import com.example.object.OBJ_Rock;

public class MON_GreenSlime extends Entity {

    GamePanel gamePanel;

    public MON_GreenSlime(GamePanel gamePanel) {

        super(gamePanel);

        this.gamePanel = gamePanel;

        type = type_monster;
        name = "Green Slime";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 5;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 2;
        projectile = new OBJ_Rock(gamePanel);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        
    }

    public void getImage() {
        up1 = setup("/monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setAction() { // Monster simple ai algorithm

        // if (onPath) { 

        //     // Check if it stops chasing
        //     checkStopChasingOrNot(gamePanel.player, 15, 100);

        //     // Search the direction to go
        //     searchPath(getGoalCol(gamePanel.player), getGoalRow(gamePanel.player));
            
        // } else {

        //     // Check if it starts chasing
        //     checkStartChasingOrNot(gamePanel.player, 5, 100);

            // Get a random direction
            getRandomDirection(120);

        // }

    }

    public void damageReaction() {
        actionLockCounter = 0;
        direction = gamePanel.player.direction;
        // onPath = true;
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
