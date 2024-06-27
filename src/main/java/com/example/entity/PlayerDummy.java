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
        up1 = setup("/player/boy_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/player/boy_up_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/player/boy_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/player/boy_left_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/player/boy_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/player/boy_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/player/boy_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/player/boy_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogues() {
        dialogues[0][0] = "Dimana aku? Ini bukan desaku...";
        dialogues[0][1] = "...";
    }
    
}
