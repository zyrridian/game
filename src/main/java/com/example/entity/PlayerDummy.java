package com.example.entity;

import com.example.common.GamePanel;

public class PlayerDummy extends Entity {

    public static final String npcName = "Dummy";

    public PlayerDummy(GamePanel gamePanel) {
        super(gamePanel);
        
        name = npcName;
        getImage();
    }

    public void getImage() {
        up1 = setup("/player/zim_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/player/zim_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/player/zim_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/player/zim_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/player/zim_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/player/zim_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/player/zim_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/player/zim_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }
    
}
