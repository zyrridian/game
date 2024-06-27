package com.example.object;

import com.example.common.GamePanel;
import com.example.entity.Entity;

public class OBJ_Sword_Normal extends Entity {
    
    public static final String objName = "Normal Sword";

    public OBJ_Sword_Normal(GamePanel gamePanel) {
        super(gamePanel);
        type = type_sword;
        name = objName;
        down1 = setup("/objects/sword_normal", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 10;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nAn old sword.";
        price = 20;
        knockBackPower = 2;
        motion1_duration = 5;
        motion2_duration = 25;
    }

}