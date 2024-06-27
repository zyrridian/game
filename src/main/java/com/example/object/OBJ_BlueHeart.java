package com.example.object;

import com.example.common.GamePanel;
import com.example.entity.Entity;

public class OBJ_BlueHeart extends Entity {

    GamePanel gamePanel;
    public static final String objName = "Blue Heart";

    public OBJ_BlueHeart(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_pickupOnly;
        name = objName;
        down1 = setup("/objects/blueheart", gamePanel.tileSize, gamePanel.tileSize);

        setDialogues();
    }

    public void setDialogues() {
        dialogues[0][0] = "Kau mengambil sebuah berlian.";
        dialogues[0][1] = "Kau menemukan batu legendaris!";
    }

    public boolean use(Entity entity) {
        gamePanel.gameState = GamePanel.CUTSCENE_STATE;
        gamePanel.cManager.sceneNumber = gamePanel.cManager.ending;
        return true;
    }

}
