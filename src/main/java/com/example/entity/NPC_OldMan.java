package com.example.entity;

import java.util.Random;

import com.example.common.GamePanel;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gamePanel) {

        super(gamePanel);

        direction = "down";
        speed = 1;

        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 30;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        dialogueSet = -1;

        getImage();
        setDialogue();

    }

    public void getImage() {
        up1 = setup("/npc/oldman_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/oldman_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/oldman_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/oldman_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/oldman_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/oldman_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/oldman_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/oldman_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Hello, lad. What're you doing in\nthis island?";
        dialogues[0][1] = "What? You don't know?\nThat's strange.";
        dialogues[0][2] = "I used to be a great wizard\nbut now... I'm a bit too old for \ntaking an adventure.";
        dialogues[0][3] = "Well, good luck on you.";

        dialogues[1][0] = "Slimes? Yes, they're everywhere.";
        dialogues[1][1] = "But they're friendly, so don't kill\nthem. Or else...";
        dialogues[1][2] = "You will get consequences.";

        dialogues[2][0] = "I wonder how to open that door...";
    }

    public void setAction() {

        if (onPath) {
            
            // NPC path with goal
            // int goalCol = 12;
            // int goalRow = 9;
            
            // NPC path follow player
            int goalCol = (gamePanel.player.worldX + gamePanel.player.solidArea.x) / gamePanel.tileSize;
            int goalRow = (gamePanel.player.worldY + gamePanel.player.solidArea.y) / gamePanel.tileSize;

            searchPath(goalCol, goalRow);

        } else {

            actionLockCounter++;

            if (actionLockCounter == 120) { // Giving delay 2 second every movement

                Random random = new Random();
                int i = random.nextInt(100) + 1; // pick up a number from 1 to 100
        
                if (i <= 25) direction = "up";
                if (i > 25 && i <= 50) direction = "down";
                if (i > 50 && i <= 75) direction = "left";
                if (i > 75 && i <= 100) direction = "right";
                
                actionLockCounter = 0;
                
            }

        }
        
    }

    public void speak() {
        facePlayer();
        startDialogue(this, dialogueSet);
        dialogueSet++;
        if (dialogues[dialogueSet][0] == null) {
            dialogueSet = 0; // Dialogue will be replayed again
            // dialogueSet--; // Dialogue will be stuck in the end state
        }
    }
    
}
