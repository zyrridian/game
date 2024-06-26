package com.example.tile_interactive;

import com.example.common.GamePanel;

public class IT_MetalPlate extends InteractiveTile {

    GamePanel gamePanel;
    public static final String itName = "Metal Plate";

    public IT_MetalPlate(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);

        this.gamePanel = gamePanel;
        this.worldX = gamePanel.tileSize * col;
        this.worldY = gamePanel.tileSize * row;

        name = itName;
        down1 = setup("/tiles_interactive/metalplate", gamePanel.tileSize, gamePanel.tileSize);

        // No collision
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
