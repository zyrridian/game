package com.example.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import com.example.common.GamePanel;
import com.example.common.UtilityTool;

public class Entity {

    // References
    GamePanel gamePanel;

    // Images
    public BufferedImage up1, up2, left1, left2, down1, down2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage guardUp, guardDown, guardLeft, guardRight;
    public BufferedImage image, image2, image3;

    // Collision Areas
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;

    // Dialogues
    public String dialogues[][] = new String[20][20];

    // Entity Linking
    public Entity attacker;
    public Entity linkedEntity;

    // Temporary
    public boolean temp = false;

    // Entity State
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNumber = 1;
    public int dialogueSet = 0;
    public int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockback = false;
    public String knockbackDirection;
    public boolean guarding = false;
    public boolean transparent = false;
    public boolean offBalance = false;
    public boolean opened = false;
    public boolean inRage = false;
    public boolean sleep = false;
    public boolean drawing = true; // to stop drawing player when camera moving in cutscene
    public Entity loot;

    // Counters
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    public int hpBarCounter = 0;
    int knockBackCounter = 0;
    public int guardCounter = 0;
    int offBalanceCounter = 0;
    public int slimeKilled = 0;

    // Character Attributes
    public String name;
    public int defaultSpeed;
    public int speed;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public int motion1_duration;
    public int motion2_duration;
    public Entity currentWeapon;
    public Entity currentShield;
    public Entity currentLight;
    public Projectile projectile;
    public boolean boss;

    // Item Attributes
    public final int maxInventorySize = 20;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int price;
    public int knockBackPower = 0;
    public boolean stackable = false;
    public int amount = 1;
    public int lightRadius;

    // Entity Types
    public int type;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_obstacle = 8;
    public final int type_light = 9;
    public final int type_pickaxe = 10;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // ======================================== Getters for Screen Position ======================================== //

    public int getScreenX() {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        return screenX;
    }
    
    public int getScreenY() {
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
        return screenY;
    }

    // ======================================== Getters for Entity Edges ======================================== //

    public int getLeftX() {
        return worldX + solidArea.x;
    }

    public int getRightX() {
        return worldX + solidArea.x + solidArea.width;
    }

    public int getTopY() {
        return worldY + solidArea.y;
    }

    public int getBottomY() {
        return worldY + solidArea.y + solidArea.height;
    }

    // ======================================== Getters for Grid Position ======================================== //

    public int getCol() {
        return (worldX + solidArea.x) / gamePanel.tileSize;
    }

    public int getRow() {
        return (worldY + solidArea.y) / gamePanel.tileSize;
    }

    // ======================================== Getters for Entity Center ======================================== //

    public int getCenterX() {
        int centerX = worldX + left1.getWidth() / 2;
        return centerX;
    }

    public int getCenterY() {
        int centerY = worldY + up1.getHeight() / 2;
        return centerY;
    }

    // ======================================== Distance Calculations ======================================== //

    public int getXdistance(Entity target) {
        int xDistance = Math.abs(getCenterX() - target.getCenterX());
        return xDistance;
    }

    public int getYdistance(Entity target) {
        int yDistance = Math.abs(getCenterY() - target.getCenterY());
        return yDistance;
    }

    public int getTileDistance(Entity target) {
        int tileDistance = (getXdistance(target) + getYdistance(target)) / gamePanel.tileSize;
        return tileDistance;
    }

    // ======================================== Getters for Target Position ======================================== //

    public int getGoalCol(Entity target) {
        int goalCol = (target.worldX + target.solidArea.x) / gamePanel.tileSize;
        return goalCol;
    }

    public int getGoalRow(Entity target) {
        int goalRow = (target.worldY + target.solidArea.y) / gamePanel.tileSize;
        return goalRow;
    }

    // ======================================== State Reset ======================================== //

    public void resetCounter() {
        spriteCounter = 0;
        actionLockCounter = 0;
        invincibleCounter = 0;
        shotAvailableCounter = 0;
        dyingCounter = 0;
        hpBarCounter = 0;
        knockBackCounter = 0;
        guardCounter = 0;
        offBalanceCounter = 0;
        slimeKilled = 0;
    }

    // ======================================== Action Setters ======================================== //

    public void setLoot(Entity entity) {}

    public void setAction() {}

    public void move(String direction) {}

    public void damageReaction() {}

    public void speak() {}

    // ======================================== Interaction Methods ======================================== //

    public void facePlayer() {
        // Turn NPC direction to player when speak
        switch (gamePanel.player.direction) {
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "left": direction = "right"; break;
            case "right": direction = "left"; break;
        }
    }

    public void startDialogue(Entity entity, int setNumber) {
        gamePanel.gameState = GamePanel.DIALOGUE_STATE;
        gamePanel.ui.npc = entity;
        dialogueSet = setNumber;
    }

    public void interact() {}

    public boolean use(Entity entity) {
        return false;
    }

    // ======================================== Drop Methods ======================================== //

    public void checkDrop() {}

    public void dropItem(Entity droppedItem) {
        for (int i = 0; i < gamePanel.obj[1].length; i++) {
            if (gamePanel.obj[gamePanel.currentMap][i] == null) {
                gamePanel.obj[gamePanel.currentMap][i] = droppedItem;
                gamePanel.obj[gamePanel.currentMap][i].worldX = worldX; // the dead monster's worldX
                gamePanel.obj[gamePanel.currentMap][i].worldY = worldY;
                break;
            }
        }
    }

    // ======================================== Particle Methods ======================================== //

    public Color getParticleColor() {
        Color color = null;
        return color;
    }

    public int getParticleSize() {
        int size = 0;
        return size;
    }

    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 0;
        return maxLife;
    }

    public void generateParticle(Entity generator, Entity target) {

        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle particle1 = new Particle(gamePanel, target, color, size, speed, maxLife, -2, -1);
        Particle particle2 = new Particle(gamePanel, target, color, size, speed, maxLife, 2, -1);
        Particle particle3 = new Particle(gamePanel, target, color, size, speed, maxLife, -2, 1);
        Particle particle4 = new Particle(gamePanel, target, color, size, speed, maxLife, 2, 1);

        gamePanel.particleList.add(particle1);
        gamePanel.particleList.add(particle2);
        gamePanel.particleList.add(particle3);
        gamePanel.particleList.add(particle4);

    }

    // ======================================== Collision Handling ======================================== //

    public void checkCollision() {
        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.iTile);
        boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);
        if (this.type == type_monster && contactPlayer) {
            damagePlayer(attack);
        }
    }

    public void checkAttackOrNot(int rate, int straight, int horizontal) {
        boolean targetInRange = false;
        int xDistance = getXdistance(gamePanel.player);
        int yDistance = getYdistance(gamePanel.player);

        switch (direction) {
            case "up": if (gamePanel.player.getCenterY() < getCenterY() && yDistance < straight && xDistance < horizontal) targetInRange = true; break;
            case "down": if (gamePanel.player.getCenterY() > getCenterY() && yDistance < straight && xDistance < horizontal) targetInRange = true; break;
            case "left": if (gamePanel.player.getCenterX() < getCenterX() && xDistance < straight && yDistance < horizontal) targetInRange = true; break;
            case "right": if (gamePanel.player.getCenterX() > getCenterX() && xDistance < straight && yDistance < horizontal) targetInRange = true; break;
        }

        if (targetInRange) {
            // Check if it initiates an attack
            int i = new Random().nextInt(rate);
            if (i == 0) {
                attacking = true;
                spriteNumber = 1;
                spriteCounter  = 0;
                shotAvailableCounter = 0;
            }
        }
    }

    public void checkShootOrNot(int rate, int shotInterval) {
        int i = new Random().nextInt(rate);
        if (i == 0 && !projectile.alive && shotAvailableCounter == shotInterval) {
            projectile.set(worldX, worldY, direction, true, this);
            // Check vacancy
            for (int j = 0; j < gamePanel.projectile[1].length; j++) {
                if (gamePanel.projectile[gamePanel.currentMap][j] == null) {
                    gamePanel.projectile[gamePanel.currentMap][j] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
        }
    }

    public void checkStartChasingOrNot(Entity target, int distance, int rate) {
        if (getTileDistance(target) < distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) onPath = true;
        }
    }

    public void checkStopChasingOrNot(Entity target, int distance, int rate) {
        if (getTileDistance(target) > distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) onPath = false;
        }
    }

    public void getRandomDirection(int interval) {
        actionLockCounter++;
        if (actionLockCounter > interval) { // Giving delay x frame every movement
            Random random = new Random();
            int i = random.nextInt(100) + 1; // pick up a number from 1 to 100
            if (i <= 25) direction = "up";
            if (i > 25 && i <= 50) direction = "down";
            if (i > 50 && i <= 75)direction = "left";
            if (i > 75 && i <= 100) direction = "right";
            actionLockCounter = 0;
        }
    }

    public void moveTowardPlayer(int interval) { // For skeleton lord ai move - every x frame boss will move to player
        actionLockCounter++;
        if (actionLockCounter > interval) { // Giving delay x frame every movement
            if (getXdistance(gamePanel.player) > getYdistance(gamePanel.player)) {
                if (gamePanel.player.getCenterX() < getCenterX()) { // Player on the left side
                    direction = "left";
                } else {
                    direction = "right";
                }
            } else if (getXdistance(gamePanel.player) < getYdistance(gamePanel.player)) {
                if (gamePanel.player.getCenterY() < getCenterY()) { // Player on the left side
                    direction = "up";
                } else {
                    direction = "down";
                }
            }
            actionLockCounter = 0; // If change direction
        }
    }

    public String getOppositeDirection(String direction) { // For guard or parry system
        String oppositeDirection = "";
        switch (direction) {
            case "up": oppositeDirection = "down"; break;
            case "down": oppositeDirection = "up"; break;
            case "left": oppositeDirection = "right"; break;
            case "right": oppositeDirection = "left"; break;
        }
        return oppositeDirection;
    }

    public void attacking() {
        
        spriteCounter++;
        if (spriteCounter <= motion1_duration) spriteNumber = 1;

        if (spriteCounter > motion1_duration && spriteCounter <= motion2_duration) {
            spriteNumber = 2;

            // Save the current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player's worldX and worldY for the attackArea
            switch (direction) {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }

            // attackArea become solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            if (type == type_monster) {
                if (gamePanel.collisionChecker.checkPlayer(this)) {
                    damagePlayer(attack);
                }
            } else {
                // Check monster collision with the updated worldX, worldY, and solidArea
                int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
                gamePanel.player.damageMonster(monsterIndex, this, attack, currentWeapon.knockBackPower);

                // Damage interactive tile
                int iTileIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.iTile);
                gamePanel.player.damageInteractiveTile(iTileIndex);

                // Hit monster projectile
                int projectileIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.projectile);
                gamePanel.player.damageProjectile(projectileIndex);
            }

            // After checking collision, restore the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }

        if (spriteCounter > motion2_duration) {
            spriteNumber = 1;
            spriteCounter = 0;
            attacking = false;
        }

    }

    public void damagePlayer(int attack) {
        if (!gamePanel.player.invincible) {

            // Get damage
            int damage = attack - gamePanel.player.defense;

            // Get an opposite direction of this attacker
            String canGuardDirection = getOppositeDirection(direction);

            // Check if player use guard or not when receive damage from monsters
            if (gamePanel.player.guarding && gamePanel.player.direction.equals(canGuardDirection)) {
                // Parry - success if guard pressed 10 frame before monster attack
                if (gamePanel.player.guardCounter < 10) {
                    damage = 0;
                    gamePanel.playSoundEffect(17);
                    setKnockBack(this, gamePanel.player, knockBackPower);
                    offBalance = true;
                    spriteCounter =- 60; // temporary stun effect
                } else { // Normal attack
                    damage /= 3;
                    gamePanel.playSoundEffect(16);
                }
            } else {
                gamePanel.playSoundEffect(9);
                if (damage < 1) damage = 1;
            }

            // If monster gives player damage
            if (damage != 0) {
                gamePanel.player.transparent = true;
                setKnockBack(gamePanel.player, this, knockBackPower);
            }

            // Reduce player's life based on damage and make it invincible
            gamePanel.player.life -= damage;
            gamePanel.player.invincible = true;

        }
    }

    public void setKnockBack(Entity target, Entity attacker, int knockBackPower) {
        this.attacker = attacker;
        target.knockbackDirection = attacker.direction;
        target.speed += knockBackPower;
        target.knockback = true;
    }

    public boolean inCamera() {
        boolean inCamera = false;
        if (worldX + gamePanel.tileSize * 5 > gamePanel.player.worldX - gamePanel.player.screenX &&
            worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
            worldY + gamePanel.tileSize * 5 > gamePanel.player.worldY - gamePanel.player.screenY &&
            worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY
        ) {
            inCamera = true;
        }
        return inCamera;
    }

    // ======================================== Update and Draw ======================================== //
    
    public void update() {

        if (!sleep) { // Update entity only when sleep is false

            if (knockback) {
         
                checkCollision();
    
                if (collisionOn) {
                    knockBackCounter = 0;
                    knockback = false;
                    speed = defaultSpeed;
                } else if (!collisionOn) {
                    switch (knockbackDirection) {
                        case "up": worldY -= speed; break;
                        case "down": worldY += speed; break;
                        case "left": worldX -= speed; break;
                        case "right": worldX += speed; break;
                    }
                }
                
                knockBackCounter++;
                if (knockBackCounter == 10) {
                    knockBackCounter = 0;
                    knockback = false;
                    speed = defaultSpeed;
                }
                
            } else if (attacking) {
                attacking();
            } else {
    
                setAction();
                checkCollision();
    
                // If collision is false, player can move
                if (!collisionOn) {
                    switch (direction) {
                        case "up": worldY -= speed; break;
                        case "down": worldY += speed; break;
                        case "left": worldX -= speed; break;
                        case "right": worldX += speed; break;
                    }
                }
    
                // Change walking image every 10 frames
                spriteCounter++;
                if (spriteCounter > 24) {
                    if (spriteNumber == 1) {
                        spriteNumber = 2;
                    } else if (spriteNumber == 2) {
                        spriteNumber = 1;
                    }
                    spriteCounter = 0;
                }
    
            }
    
            if (invincible) {
                invincibleCounter++;
                if (invincibleCounter > 40) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
    
            if (shotAvailableCounter < 30) {
                shotAvailableCounter++;
            }
    
            // Offbalance active after parry
            if (offBalance == true) {
                offBalanceCounter++;
                if (offBalanceCounter > 60) {
                    offBalance = false;
                    offBalanceCounter = 0;
                }
            }
            
        }
    
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        // Draw only the map that visible in camera
        if (inCamera()) {

            int tempScreenX = getScreenX();
            int tempScreenY = getScreenY();

            switch (direction) {
                case "up":
                    if (!attacking) {
                        if (spriteNumber == 1) image = up1;
                        if (spriteNumber == 2) image = up2;
                    }
                    if (attacking) {
                        tempScreenY = getScreenY() - up1.getHeight();
                        if (spriteNumber == 1) image = attackUp1;
                        if (spriteNumber == 2) image = attackUp2;
                    }
                    break;
                case "down":
                    if (!attacking) {
                        if (spriteNumber == 1) image = down1;
                        if (spriteNumber == 2) image = down2;
                    }
                    if (attacking) {
                        if (spriteNumber == 1) image = attackDown1;
                        if (spriteNumber == 2) image = attackDown2;
                    }
                    break;
                case "left":
                    if (!attacking) {
                        if (spriteNumber == 1) image = left1;
                        if (spriteNumber == 2) image = left2;
                    }
                    if (attacking) {
                        tempScreenX = getScreenX() - left1.getWidth();
                        if (spriteNumber == 1) image = attackLeft1;
                        if (spriteNumber == 2) image = attackLeft2;
                    }
                    break;
                case "right":
                    if (!attacking) {
                        if (spriteNumber == 1) image = right1;
                        if (spriteNumber == 2) image = right2;
                    }
                    if (attacking) {
                        if (spriteNumber == 1) image = attackRight1;
                        if (spriteNumber == 2) image = attackRight2;
                    }
                    break;
            }

            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4F);
            }

            if (dying) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, tempScreenX, tempScreenY, null);
            changeAlpha(g2, 1F);

        }

    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 5;
        if (dyingCounter <= i) { changeAlpha(g2, 0F); }
        if (dyingCounter > i && dyingCounter <= i * 2) { changeAlpha(g2, 1F);  }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) { changeAlpha(g2, 0F); }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) { changeAlpha(g2, 1F); }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) { changeAlpha(g2, 0F); }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) { changeAlpha(g2, 1F); }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) { changeAlpha(g2, 0F); }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) { changeAlpha(g2, 1F); }
        if (dyingCounter > i * 8) {
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = utilityTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void searchPath(int goalCol, int goalRow) {

        int startCol = (worldX + solidArea.x) / gamePanel.tileSize;
        int startRow = (worldY + solidArea.y) / gamePanel.tileSize;

        gamePanel.pathFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gamePanel.pathFinder.search()) {
            
            // Next worldX & worldY
            int nextX = gamePanel.pathFinder.pathList.get(0).col * gamePanel.tileSize;
            int nextY = gamePanel.pathFinder.pathList.get(0).row * gamePanel.tileSize;

            // Entity's solidArea position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gamePanel.tileSize) {
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gamePanel.tileSize) {
                direction = "down";
            } else if (enTopY >= nextY && enBottomY < nextY + gamePanel.tileSize) {
                // left or right
                if (enLeftX > nextX) direction = "left";
                if (enLeftX < nextX) direction = "right";
            } else if (enTopY > nextY && enLeftX > nextX) {
                // up or left
                direction = "up";
                checkCollision();
                if (collisionOn) direction = "left";
            } else if (enTopY > nextY && enLeftX < nextX) {
                // up or right
                direction = "up";
                checkCollision();
                if (collisionOn) direction = "right";
            } else if (enTopY < nextY && enLeftX > nextX) {
                // down or left
                direction = "down";
                checkCollision();
                if (collisionOn) direction = "left";
            } else if (enTopY < nextY && enLeftX < nextX) {
                // down or right
                direction = "down";
                checkCollision();
                if (collisionOn) direction = "right";
            }

            // If reaches the goal, stop the search (disable it if you want npc follows user)
            int nextCol = gamePanel.pathFinder.pathList.get(0).col;
            int nextRow = gamePanel.pathFinder.pathList.get(0).row;
            if (nextCol == goalCol && nextRow == goalRow) onPath = false;

        }

    }
    
    public int getDetected(Entity user, Entity target[][], String targetName) {

        int index = 999;

        // Check the surrounding object, example: to open the door
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction) {
            case "up": nextWorldY = user.getTopY() - user.speed; break;
            case "down": nextWorldY = user.getBottomY() + user.speed; break;
            case "left": nextWorldX = user.getLeftX() - user.speed; break;
            case "right": nextWorldX = user.getRightX() + user.speed; break;
        }

        int col = nextWorldX / gamePanel.tileSize;
        int row = nextWorldY / gamePanel.tileSize;

        for (int i = 0; i < target[1].length; i++) {
            if (target[gamePanel.currentMap][i] != null) {
                if (target[gamePanel.currentMap][i].getCol() == col && target[gamePanel.currentMap][i].getRow() == row && target[gamePanel.currentMap][i].name.equals(targetName)) {
                    index = i;
                    break;
                }
            }
        }

        return index;

    }
}
