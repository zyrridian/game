package com.example.entity;

import java.util.Random;

import com.example.common.GamePanel;

public class NPC_OldMan extends Entity {

    public static final String npcName = "Old Man";

    public NPC_OldMan(GamePanel gamePanel) {
        super(gamePanel);

        name = npcName;
        direction = "left";
        speed = 1;

        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 30;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        // dialogueSet = -1;

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
        dialogues[0][0] = "Selamat datang, petualang.";
        dialogues[0][1] = "Kau pasti kebingungan karena tiba-tiba\nberada di tempat yang tidak dikenal.";
        dialogues[0][2] = "Aku adalah penyihir terhebat di masa lalu.";
        dialogues[0][3] = "Tapi sekarang...";
        dialogues[0][4] = "Kekuatanku sudah hilang.";
        dialogues[0][5] = "..........";
        dialogues[0][6] = "Itulah sebabnya banyak monster yang\nberkeliaran di sekitar sini.";
        dialogues[0][7] = "Kau adalah satu-satunya harapanku,\npetualang. Tolong kalahkan monster\npenunggu gua di utara.";
        dialogues[0][8] = "Bawalah kapak di dalam peti itu beserta\nlentera di sebelahnya. Kau akan sangat\nmembutuhkannya.";
        dialogues[0][9] = "Selamat berjuang!";

        dialogues[1][0] = "Jika kau kelelahan, minumlah air di danau";
        dialogues[1][1] = "Energimu akan pulih kembali.";

        dialogues[2][0] = "Legenda mengatakan, ada sebuah perisai\nmerah yang sangat kuat tersembunyi di\npulau ini.";
        dialogues[2][1] = "Namun kurasa kau tidak membutuhkannya\nuntuk dapat mengalahkan monster\npenunggu gua.";
        dialogues[2][2] = "Aku sudah melihat petualang lain sepertimu\nyang memiliki nyawa sejumlah\nnegatif sembilan.";
        dialogues[2][3] = "Dan dia masih hidup.";
        dialogues[2][4] = "Benar-benar mengerikan...";

        dialogues[3][0] = "Berhentilah berbicara denganku.";
    }

    public void setAction() {

        if (onPath) {
            
            // NPC path with goal
            // int goalCol = 12;
            // int goalRow = 9;
            
            // NPC path follow player
            int goalCol = (gamePanel.player.worldX + gamePanel.player.solidArea.x) / gamePanel.tileSize;
            int goalRow = (gamePanel.player.worldY + gamePanel.player.solidArea.y) / gamePanel.tileSize;
            // if (nextCol == goalCol && nextRow == goalRow) onPath = false;


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
            // dialogueSet = 0; // Dialogue will be replayed again
            dialogueSet--; // Dialogue will be stuck in the end state
        }
    }
    
}
