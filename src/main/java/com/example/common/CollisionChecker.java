package com.example.common;

import com.example.entity.Entity;

public class CollisionChecker {

    GamePanel gamePanel;
    
    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        int tileNumber1, tileNumber2;

        // Use a temporal direction when it's being knockbacked
        String direction = entity.direction;
        if (entity.knockback) direction = entity.knockbackDirection;

        switch (direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
                tileNumber1 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityLeftCol][entityTopRow];
                tileNumber2 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityRightCol][entityTopRow];
                if (gamePanel.tileManager.tile[tileNumber1].collision || gamePanel.tileManager.tile[tileNumber2].collision) entity.collisionOn = true;
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
                tileNumber1 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityLeftCol][entityBottomRow];
                tileNumber2 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNumber1].collision || gamePanel.tileManager.tile[tileNumber2].collision) entity.collisionOn = true;
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                tileNumber1 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityLeftCol][entityTopRow];
                tileNumber2 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityLeftCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNumber1].collision || gamePanel.tileManager.tile[tileNumber2].collision) entity.collisionOn = true;
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
                tileNumber1 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityRightCol][entityTopRow];
                tileNumber2 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNumber1].collision || gamePanel.tileManager.tile[tileNumber2].collision) entity.collisionOn = true;
                break;
        }

    }

    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        // Use a temporal direction when it's being knockbacked
        String direction = entity.direction;
        if (entity.knockback) direction = entity.knockbackDirection;

        for (int i = 0; i < gamePanel.obj[1].length; i++) {

            if (gamePanel.obj[gamePanel.currentMap][i] != null) {

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid area position
                gamePanel.obj[gamePanel.currentMap][i].solidArea.x = gamePanel.obj[gamePanel.currentMap][i].worldX + gamePanel.obj[gamePanel.currentMap][i].solidArea.x;
                gamePanel.obj[gamePanel.currentMap][i].solidArea.y = gamePanel.obj[gamePanel.currentMap][i].worldY + gamePanel.obj[gamePanel.currentMap][i].solidArea.y;
                
                switch (direction) {
                    case "up": entity.solidArea.y -= entity.speed; break;
                    case "down": entity.solidArea.y += entity.speed; break;
                    case "left": entity.solidArea.x -= entity.speed; break;
                    case "right": entity.solidArea.x += entity.speed; break;
                }

                if (entity.solidArea.intersects(gamePanel.obj[gamePanel.currentMap][i].solidArea)) {
                    if (gamePanel.obj[gamePanel.currentMap][i].collision) entity.collisionOn = true;
                    if (player) index = i;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.obj[gamePanel.currentMap][i].solidArea.x = gamePanel.obj[gamePanel.currentMap][i].solidAreaDefaultX;
                gamePanel.obj[gamePanel.currentMap][i].solidArea.y = gamePanel.obj[gamePanel.currentMap][i].solidAreaDefaultY;

            }
        }

        return index;

    }

    // Check NPC or Monster Collision
    public int checkEntity(Entity entity, Entity[][] target) {

        int index = 999;

        // Use a temporal direction when it's being knockbacked
        String direction = entity.direction;
        if (entity.knockback) direction = entity.knockbackDirection;

        for (int i = 0; i < target[1].length; i++) {

            if (target[gamePanel.currentMap][i] != null) {

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid area position
                target[gamePanel.currentMap][i].solidArea.x = target[gamePanel.currentMap][i].worldX + target[gamePanel.currentMap][i].solidArea.x;
                target[gamePanel.currentMap][i].solidArea.y = target[gamePanel.currentMap][i].worldY + target[gamePanel.currentMap][i].solidArea.y;
                
                switch (direction) {
                    case "up": entity.solidArea.y -= entity.speed; break;
                    case "down": entity.solidArea.y += entity.speed; break;
                    case "left": entity.solidArea.x -= entity.speed; break;
                    case "right": entity.solidArea.x += entity.speed; break;
                }

                if (entity.solidArea.intersects(target[gamePanel.currentMap][i].solidArea)) {
                    if (target[gamePanel.currentMap][i] != entity) {
                        entity.collisionOn = true;
                        index = i;   
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gamePanel.currentMap][i].solidArea.x = target[gamePanel.currentMap][i].solidAreaDefaultX;
                target[gamePanel.currentMap][i].solidArea.y = target[gamePanel.currentMap][i].solidAreaDefaultY;

            }
        }

        return index;

    }

    // Check NPC or Monster hit Player
    public boolean checkPlayer(Entity entity) {

        boolean contactPlayer = false;

        // Get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // Get the player's solid area position
        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;
        
        switch (entity.direction) {
            case "up": entity.solidArea.y -= entity.speed; break;
            case "down": entity.solidArea.y += entity.speed; break;
            case "left": entity.solidArea.x -= entity.speed; break;
            case "right": entity.solidArea.x += entity.speed; break;
        }

        if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;

        return contactPlayer;

    }

}
