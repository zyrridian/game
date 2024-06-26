package com.example.object;

import com.example.common.GamePanel;
import com.example.entity.Entity;

public class OBJ_Boots extends Entity {
    
    public static final String objName = "Boots";

    public OBJ_Boots(GamePanel gamePanel) {
        super(gamePanel);
        name = objName;
        down1 = setup("/objects/boots", gamePanel.tileSize, gamePanel.tileSize);
    }
}
