package com.example.object;

import com.example.common.GamePanel;
import com.example.entity.Entity;

public class OBJ_Shield_Blue extends Entity {
    
    public static final String objName = "Blue Shield";

    public OBJ_Shield_Blue(GamePanel gamePanel) {
        super(gamePanel);
        type = type_shield;
        name = objName;
        down1 = setup("/objects/shield_blue", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 2;
        description = "[" + name + "]\nA shiny blue shield.";
        price = 250;
    }
    
}
