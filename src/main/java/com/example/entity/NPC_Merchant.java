package com.example.entity;

import com.example.common.GamePanel;
import com.example.object.OBJ_Axe;
import com.example.object.OBJ_Key;
import com.example.object.OBJ_Potion_Red;
import com.example.object.OBJ_Shield_Blue;
import com.example.object.OBJ_Shield_Wood;
import com.example.object.OBJ_Sword_Normal;

public class NPC_Merchant extends Entity {

    public NPC_Merchant(GamePanel gamePanel) {

        super(gamePanel);

        direction = "down";
        speed = 1;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setDialogue();
        setItems();

    }

    public void getImage() {
        up1 = setup("/npc/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/npc/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/npc/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/npc/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "He he, so you found me. I have some\ngood stuff. Do you want to trade?";
        dialogues[1][0] = "Come again, hehe!";
        dialogues[2][0] = "You need more coin to buy that!";
        dialogues[3][0] = "You cannot carry any more!";
        dialogues[4][0] = "You cannot sell an equipped item!";
    }

    public void setItems() {
        inventory.add(new OBJ_Potion_Red(gamePanel));
        inventory.add(new OBJ_Key(gamePanel));
        inventory.add(new OBJ_Sword_Normal(gamePanel));
        inventory.add(new OBJ_Axe(gamePanel));
        inventory.add(new OBJ_Shield_Wood(gamePanel));
        inventory.add(new OBJ_Shield_Blue(gamePanel));
    }

    public void speak() {
        facePlayer();
        gamePanel.gameState = GamePanel.TRADE_STATE;
        gamePanel.ui.npc = this;
    }
    
}
