package com.example.entity;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.example.common.GamePanel;
import com.example.object.OBJ_DoorIron;
import com.example.tile_interactive.IT_MetalPlate;
import com.example.tile_interactive.InteractiveTile;

public class NPC_BigRock extends Entity {

    public static final String npcName = "Big Rock";

    public NPC_BigRock(GamePanel gamePanel) {

        super(gamePanel);

        name = npcName;
        direction = "down";
        speed = 4;

        solidArea = new Rectangle();
        solidArea.x = 2;
        solidArea.y = 6;
        solidArea.width = 44;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        dialogueSet = -1;

        getImage();
        setDialogue();

    }

    public void getImage() {
        up1 = setup("/npc/bigrock", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/bigrock", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/bigrock", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/bigrock", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/bigrock", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/bigrock", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/bigrock", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/bigrock", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "It's a giant rock.";
    }

    public void setAction() {

    }

    public void update() {

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

    public void move(String direction) {
        this.direction = direction;

        checkCollision();

        if (!collisionOn) {
            switch (direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }
        detectPlate();
    }

    public void detectPlate() {
        ArrayList<InteractiveTile> plateList = new ArrayList<>();
        ArrayList<Entity> rockList = new ArrayList<>();
        // Create a plate list
        for (int i = 0; i < gamePanel.iTile[1].length; i++) {
            if (gamePanel.iTile[gamePanel.currentMap][i] != null &&
                gamePanel.iTile[gamePanel.currentMap][i].name != null &&
                gamePanel.iTile[gamePanel.currentMap][i].name.equals(IT_MetalPlate.itName)
            ) {
                plateList.add(gamePanel.iTile[gamePanel.currentMap][i]);
            }
        }
        // Create a rock list
        for (int i = 0; i < gamePanel.npc[1].length; i++) {
            if (gamePanel.npc[gamePanel.currentMap][i] != null &&
                gamePanel.npc[gamePanel.currentMap][i].name != null &&
                gamePanel.npc[gamePanel.currentMap][i].name.equals(NPC_BigRock.npcName)
            ) {
                rockList.add(gamePanel.npc[gamePanel.currentMap][i]);
            }
        }
        int count = 0;
        // Scan the plate list
        for (int i = 0; i < plateList.size(); i++) {
            int xDistance = Math.abs(worldX - plateList.get(i).worldX);
            int yDistance = Math.abs(worldY - plateList.get(i).worldY);
            int distance = Math.max(xDistance, yDistance);
            if (distance < 8) {
                if (linkedEntity == null) {
                    linkedEntity = plateList.get(i);
                    gamePanel.playSoundEffect(3);   
                }
            } else {
                if (linkedEntity == plateList.get(i)) {
                    linkedEntity = null;
                }
            }
        }
        // Scan the rock list
        for (int i = 0; i < rockList.size(); i++) {
            // Count the rock on the plate
            if (rockList.get(i).linkedEntity != null) {
                count++;
            }
        }
        // If all the rocks are on the plate, the iron door opens
        if (count == rockList.size()) {
            for (int i = 0; i < gamePanel.obj[1].length; i++) {
                if (gamePanel.obj[gamePanel.currentMap][i] != null &&
                    gamePanel.obj[gamePanel.currentMap][i].name.equals(OBJ_DoorIron.objName)
                ) {
                    gamePanel.obj[gamePanel.currentMap][i] = null;
                    gamePanel.playSoundEffect(20);
                }
            }
        }
    }

}
