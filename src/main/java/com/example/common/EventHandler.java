package com.example.common;

import com.example.data.Progress;
import com.example.entity.Entity;

public class EventHandler {

    GamePanel gamePanel;
    EventRect eventRect[][][];
    Entity eventMaster;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        eventMaster = new Entity(gamePanel);
        eventRect = new EventRect[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        int map = 0;
        int col = 0;
        int row = 0;
        while (map < gamePanel.maxMap && col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
            col++;
            if (col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
                if (row == gamePanel.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
        setDialogue();
    }

    public void setDialogue() {
        eventMaster.dialogues[0][0] = "You fall into a pit!";
        eventMaster.dialogues[1][0] = "You drink the water.\nYour life has been recovered.\n(The progress has been saved)";
        eventMaster.dialogues[1][1] = "Damn, that's a good water.";
    }

    public void checkEvent() {

        // Check if the player character is more than 1 tile away from the last event
        int xDistance = Math.abs(gamePanel.player.worldX - previousEventX);
        int yDistance = Math.abs(gamePanel.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        
        if (distance > gamePanel.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (hit(0, 27, 16, "right")) damagePit(27, 16, GamePanel.DIALOGUE_STATE);
            else if (hit(0, 23, 12, "up")) healingPool(23, 12, GamePanel.DIALOGUE_STATE);
            else if (hit(1, 12, 9, "up")) speak(gamePanel.npc[1][0]);
            else if (hit(0, 10, 39, "any")) teleport(1, 12, 13, GamePanel.INDOOR_AREA);
            else if (hit(1, 12, 13, "any")) teleport(0, 10, 39, GamePanel.OUTSIDE_AREA);
            else if (hit(0, 12, 9, "any")) teleport(2, 9, 41, GamePanel.DUNGEON_AREA); // To the dungeon
            else if (hit(2, 9, 41, "any")) teleport(0, 12, 9, GamePanel.OUTSIDE_AREA); // To the to outside
            else if (hit(2, 8, 7, "any")) teleport(3, 26, 41, GamePanel.DUNGEON_AREA); // To B2
            else if (hit(3, 26, 41, "any")) teleport(2, 8, 7, GamePanel.DUNGEON_AREA); // To B1
            else if (hit(3, 25, 27, "any")) skeletonLord();
        }

    }

    public boolean hit(int map, int col, int row, String reqDirection) {
        
        boolean hit = false;
        
        if (map == gamePanel.currentMap) {

            gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
            gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;
            eventRect[map][col][row].x = col * gamePanel.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gamePanel.tileSize + eventRect[map][col][row].y;
    
            if (gamePanel.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                if (gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;
                    previousEventX = gamePanel.player.worldX;
                    previousEventY = gamePanel.player.worldY;
    
                }
            }
    
            gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
            gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;

        }
        
        return hit;

    }

    public void damagePit(int col, int row, int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.playSoundEffect(1);
        eventMaster.startDialogue(eventMaster, 0);
        gamePanel.player.life -= 1;
        eventRect[0][col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool(int col, int row, int gameState) {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = gameState;
            gamePanel.player.attackCanceled = true;
            gamePanel.playSoundEffect(2);
            eventMaster.startDialogue(eventMaster, 1);
            gamePanel.player.life = gamePanel.player.maxLife;
            gamePanel.player.mana = gamePanel.player.maxMana;
            gamePanel.assetSetter.setMonster(); // optional for testing purpose. reset monster
            gamePanel.saveLoad.save();
        }
    }

    public void teleport(int map, int col, int row, int area) {
        gamePanel.gameState = GamePanel.TRANSITION_STATE;
        gamePanel.nextArea = area;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        gamePanel.playSoundEffect(14);
    }

    public void speak(Entity entity) {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = GamePanel.DIALOGUE_STATE;
            gamePanel.player.attackCanceled = true;
            entity.speak();
        }
    }

    public void skeletonLord() {

        if (!gamePanel.bossBattleOn && !Progress.skeletonLordDefeated) {
            gamePanel.gameState = GamePanel.CUTSCENE_STATE;
            gamePanel.cManager.sceneNumber = gamePanel.cManager.skeletonLord;
        }
    }
    
}
