package com.example.tile_interactive;

import com.example.common.GamePanel;

public class IT_Trunk extends InteractiveTile {
    
    GamePanel gamePanel;

    public IT_Trunk(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);

        this.gamePanel = gamePanel;
        this.worldX = gamePanel.tileSize * col;
        this.worldY = gamePanel.tileSize * row;

        down1 = setup("/tiles_interactive/trunk", gamePanel.tileSize, gamePanel.tileSize);

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

}
