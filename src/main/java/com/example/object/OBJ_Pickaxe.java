package com.example.object;

import com.example.common.GamePanel;
import com.example.entity.Entity;

public class OBJ_Pickaxe extends Entity {
    
    public static final String objName = "Pickaxe";

    public OBJ_Pickaxe(GamePanel gamePanel) {
        super(gamePanel);
        type = type_pickaxe;
        name = objName;
        down1 = setup("/objects/pickaxe", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[Pickaxe]\nYou will dig it!";
        price = 75;
        knockBackPower = 10;
        motion1_duration = 10;
        motion2_duration = 20;
    }
    
}
