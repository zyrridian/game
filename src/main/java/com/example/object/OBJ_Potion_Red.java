package com.example.object;

import com.example.common.GamePanel;
import com.example.entity.Entity;

public class OBJ_Potion_Red extends Entity {
    
    GamePanel gamePanel;
    public static final String objName = "Red Potion";

    public OBJ_Potion_Red(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        type = type_consumable;
        name = objName;
        value = 5;
        down1 = setup("/objects/potion_red", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nHeals your life by " + value + ".";
        price = 25;
        stackable = true;
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "You drink the " + name + "! Your\n" + "life has been recovered by " + value + ".";
    }

    public boolean use(Entity entity) {
        startDialogue(this, 0);
        entity.life += value;
        gamePanel.playSoundEffect(2);
        return true;
    }

}
