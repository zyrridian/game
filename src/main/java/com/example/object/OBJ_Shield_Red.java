package com.example.object;

import com.example.common.GamePanel;
import com.example.entity.Entity;

public class OBJ_Shield_Red extends Entity {
    
    public static final String objName = "Red Shield";

    public OBJ_Shield_Red(GamePanel gamePanel) {
        super(gamePanel);
        type = type_shield;
        name = objName;
        down1 = setup("/objects/shield_red", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 100;
        description = "[" + name + "]\nA legendary red shield.";
        price = 2500;
    }

}
