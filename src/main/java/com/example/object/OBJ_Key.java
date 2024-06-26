package com.example.object;

import com.example.common.GamePanel;
import com.example.entity.Entity;

public class OBJ_Key extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Key";

    public OBJ_Key(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        type = type_consumable;
        name = objName;
        down1 = setup("/objects/key", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nIt opens a door.";
        price = 100;
        stackable = true;
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "You use the " + name + " and open the door";
        dialogues[1][0] = "What are you doing?";
    }

    public boolean use(Entity entity) {
        int objIndex = getDetected(entity, gamePanel.obj, "Door");
        if (objIndex != 999) { // use the key
            startDialogue(this, 0);
            gamePanel.playSoundEffect(3);
            gamePanel.obj[gamePanel.currentMap][objIndex] = null;
            return true;
        } else {
            startDialogue(this, 1);
            return false;
        }
    }
}
