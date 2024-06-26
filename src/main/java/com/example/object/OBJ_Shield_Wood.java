package com.example.object;

import com.example.common.GamePanel;
import com.example.entity.Entity;

public class OBJ_Shield_Wood extends Entity {

    public static final String objName = "Wood Shield";

    public OBJ_Shield_Wood(GamePanel gamePanel) {
        super(gamePanel);
        type = type_shield;
        name = objName;
        down1 = setup("/objects/shield_wood", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nMade by wood.";
        price = 35;
    }

}