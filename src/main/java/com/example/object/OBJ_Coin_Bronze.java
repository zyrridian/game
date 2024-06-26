package com.example.object;

import com.example.common.GamePanel;
import com.example.entity.Entity;

public class OBJ_Coin_Bronze extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Bronze Coin";

    public OBJ_Coin_Bronze(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        type = type_pickupOnly;
        name = objName;
        value = 1;
        down1 = setup("/objects/coin_bronze", gamePanel.tileSize, gamePanel.tileSize);
    }

    public boolean use(Entity entity) {
        gamePanel.playSoundEffect(1);
        gamePanel.ui.addMessage("Coin +" + value);
        gamePanel.player.coin += value;
        return true;
    }
    
}
