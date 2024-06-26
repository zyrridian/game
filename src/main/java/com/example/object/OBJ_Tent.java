package com.example.object;

import com.example.common.GamePanel;
import com.example.entity.Entity;

public class OBJ_Tent extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Tent";

    public OBJ_Tent(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_consumable;
        name = objName;
        down1 = setup("/objects/tent", gamePanel.tileSize, gamePanel.tileSize);
        description = "[Tent]\nYou can sleep until\nnext morning.";
        price = 300;
        stackable = true;
    }

    public boolean use(Entity entity) {
        gamePanel.gameState = GamePanel.SLEEP_STATE;
        gamePanel.playSoundEffect(15);
        gamePanel.player.life = gamePanel.player.maxLife;
        gamePanel.player.mana = gamePanel.player.maxMana;
        gamePanel.player.getSleepingImage(down1);
        return true;
    }
    
}
