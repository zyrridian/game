package com.example.object;

import com.example.common.GamePanel;
import com.example.entity.Entity;

public class OBJ_DoorIron extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Iron Door";

    public OBJ_DoorIron(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_obstacle;
        name = objName;
        down1 = setup("/objects/door_iron", gamePanel.tileSize, gamePanel.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "It wont't budge!";
    }

    public void interact() {
        startDialogue(this, 0);
    }
    
}
