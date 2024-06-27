package com.example.object;

import com.example.common.GamePanel;
import com.example.entity.Entity;

public class OBJ_Lantern extends Entity {
    
    public static final String objName = "Lentera";

    public OBJ_Lantern(GamePanel gamePanel) {
        super(gamePanel);
        
        type = type_light;
        name = objName;
        down1 = setup("/objects/lantern", gamePanel.tileSize, gamePanel.tileSize);
        description = "[Lentera]\nMenerangi\nsekitarmu.";
        price = 200;
        lightRadius = 300;
    }
    
}
