package com.example.object;

import com.example.common.GamePanel;
import com.example.entity.Entity;

public class OBJ_Axe extends Entity {

    public static final String objName = "Kapak";

    public OBJ_Axe(GamePanel gamePanel) {
        super(gamePanel);
        type = type_axe;
        name = objName;
        down1 = setup("/objects/axe", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[Kapak]\nSedikit berkarat,\nuntuk memotong kayu";
        price = 75;
        knockBackPower = 10;
        motion1_duration = 20;
        motion2_duration = 40;
    }
}
