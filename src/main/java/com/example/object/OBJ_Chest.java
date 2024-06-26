package com.example.object;

import com.example.common.GamePanel;
import com.example.entity.Entity;

public class OBJ_Chest extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Chest";

    public OBJ_Chest(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_obstacle;
        name = objName;
        image = setup("/objects/chest", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("/objects/chest_opened", gamePanel.tileSize, gamePanel.tileSize);
        down1 = image;
        collision = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void setLoot(Entity loot) {
        this.loot = loot;
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "You open the chest and find a " + loot.name + "!" + "\n... But you cannot carry any more!";
        dialogues[1][0] = "You open the chest and find a " + loot.name + "!" + "\nYou obtain the " + loot.name + "!";
        dialogues[2][0] = "It's empty.";
    }

    public void interact() {
        if (!opened) {
            gamePanel.playSoundEffect(3);
            if (!gamePanel.player.canObtainItem(loot)) {
                startDialogue(this, 0);
            } else {
                startDialogue(this, 1);
                down1 = image2;
                opened = true;
            }
        } else {
            startDialogue(this, 2);
        }
    }

}
