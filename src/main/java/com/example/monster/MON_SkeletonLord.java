package com.example.monster;

import java.util.Random;

import com.example.common.GamePanel;
import com.example.data.Progress;
import com.example.entity.Entity;
import com.example.object.OBJ_Coin_Bronze;
import com.example.object.OBJ_DoorIron;
import com.example.object.OBJ_Heart;
import com.example.object.OBJ_Mana_Crystal;

public class MON_SkeletonLord extends Entity {
    
    GamePanel gamePanel;
    public static final String monName = "Skeleton Lord";

    public MON_SkeletonLord(GamePanel gamePanel) {

        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_monster;
        boss = true;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 200;
        life = maxLife;
        attack = 10;
        defense = 50;
        exp = 5000;
        knockBackPower = 15;
        sleep = true;

        int size = gamePanel.tileSize * 5;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48 * 2;
        solidArea.height = size - 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 170;
        attackArea.height = 170;
        motion1_duration = 25;
        motion2_duration = 50;

        getImage();
        getAttackImage();
        setDialogue();
        
    }

    public void getImage() {

        int i = 5;

        if (!inRage) {
            up1 = setup("/monster/skeletonlord_up_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            up2 = setup("/monster/skeletonlord_up_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
            down1 = setup("/monster/skeletonlord_down_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            down2 = setup("/monster/skeletonlord_down_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
            left1 = setup("/monster/skeletonlord_left_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            left2 = setup("/monster/skeletonlord_left_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
            right1 = setup("/monster/skeletonlord_right_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            right2 = setup("/monster/skeletonlord_right_2", gamePanel.tileSize * i, gamePanel.tileSize * i);    
        }

        if (inRage) {
            up1 = setup("/monster/skeletonlord_phase2_up_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            up2 = setup("/monster/skeletonlord_phase2_up_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
            down1 = setup("/monster/skeletonlord_phase2_down_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            down2 = setup("/monster/skeletonlord_phase2_down_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
            left1 = setup("/monster/skeletonlord_phase2_left_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            left2 = setup("/monster/skeletonlord_phase2_left_2", gamePanel.tileSize * i, gamePanel.tileSize * i);
            right1 = setup("/monster/skeletonlord_phase2_right_1", gamePanel.tileSize * i, gamePanel.tileSize * i);
            right2 = setup("/monster/skeletonlord_phase2_right_2", gamePanel.tileSize * i, gamePanel.tileSize * i);    
        }
        
    }

    public void getAttackImage() {

        int i = 5;
        
        if (!inRage) {
            attackUp1 = setup("/monster/skeletonlord_attack_up_1", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackUp2 = setup("/monster/skeletonlord_attack_up_2", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackDown1 = setup("/monster/skeletonlord_attack_down_1", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackDown2 = setup("/monster/skeletonlord_attack_down_2", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackLeft1 = setup("/monster/skeletonlord_attack_left_1", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
            attackLeft2 = setup("/monster/skeletonlord_attack_left_2", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
            attackRight1 = setup("/monster/skeletonlord_attack_right_1", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
            attackRight2 = setup("/monster/skeletonlord_attack_right_2", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);   
        }

        if (inRage) {
            attackUp1 = setup("/monster/skeletonlord_phase2_attack_up_1", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackUp2 = setup("/monster/skeletonlord_phase2_attack_up_2", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackDown1 = setup("/monster/skeletonlord_phase2_attack_down_1", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackDown2 = setup("/monster/skeletonlord_phase2_attack_down_2", gamePanel.tileSize * i, gamePanel.tileSize * i * 2);
            attackLeft1 = setup("/monster/skeletonlord_phase2_attack_left_1", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
            attackLeft2 = setup("/monster/skeletonlord_phase2_attack_left_2", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
            attackRight1 = setup("/monster/skeletonlord_phase2_attack_right_1", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
            attackRight2 = setup("/monster/skeletonlord_phase2_attack_right_2", gamePanel.tileSize * i * 2, gamePanel.tileSize * i);
        }

    }

    public void setDialogue() {
        dialogues[0][0] = "No one can steal my treasure!";
        dialogues[0][1] = "You will die here!";
        dialogues[0][2] = "WELCOME TO MY DOOM!";
    }

    public void setAction() { // Monster simple ai algorithm

        // If skeleton hp decrease to 50% and not in rage, make it rage
        if (!inRage && life < maxLife / 2) {
            inRage = true;
            getImage();
            getAttackImage();
            defaultSpeed *= 2;
            speed = defaultSpeed;
            attack *= 3;
        }

        // If player close to skeleton, move towards player, otherwise move randomly
        if (getTileDistance(gamePanel.player) < 10) { 
            moveTowardPlayer(60);
        } else {
            getRandomDirection(120);
        }

        // Check if it attacks
        if (!attacking) {
            checkAttackOrNot(60, gamePanel.tileSize * 7, gamePanel.tileSize * 5);
        }

    }

    public void damageReaction() {
        actionLockCounter = 0;
        // direction = gamePanel.player.direction;
        onPath = true;
    }

    public void checkDrop() {

        gamePanel.bossBattleOn = false;
        Progress.skeletonLordDefeated = true;
        
        // Restore the previous music
        gamePanel.stopMusic();
        gamePanel.playMusic(7);

        // Remove the iron doors
        for (int i = 0; i < gamePanel.obj[1].length; i++) {
            if (gamePanel.obj[gamePanel.currentMap][i] != null && gamePanel.obj[gamePanel.currentMap][i].name.equals(OBJ_DoorIron.objName)) {
                gamePanel.playSoundEffect(20);
                gamePanel.obj[gamePanel.currentMap][i] = null;
            }
        }
        
        // Cast a die
        int i = new Random().nextInt(100) + 1;

        // Set the monster drop
        if (i < 50) dropItem(new OBJ_Coin_Bronze(gamePanel));
        if (i >= 50 && i < 75) dropItem(new OBJ_Heart(gamePanel));
        if (i >= 75 && i < 100) dropItem(new OBJ_Mana_Crystal(gamePanel));

    }

}
