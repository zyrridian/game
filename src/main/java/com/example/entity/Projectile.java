package com.example.entity;

import com.example.common.GamePanel;

public class Projectile extends Entity {

    Entity user;

    public Projectile(GamePanel gamePanel) {
        super(gamePanel);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }

    public void update() {

        // check collision of projectile from player
        if (user == gamePanel.player) {
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
            if (monsterIndex != 999) {
                gamePanel.player.damageMonster(monsterIndex, this, attack * (gamePanel.player.level / 2), knockBackPower);
                generateParticle(user.projectile, gamePanel.monster[gamePanel.currentMap][monsterIndex]);
                alive = false;
            }
        }

        // check collision of projectile from monster
        if (user != gamePanel.player) {
            boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);
            if (!gamePanel.player.invincible && contactPlayer) {
                damagePlayer(attack);
                generateParticle(user.projectile, user.projectile);
                alive = false;
            }
        }

        switch (direction) {
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }

        life--;
        if (life <= 0) {
            alive = false;
            
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }

    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        return haveResource;
    }

    public void subtractResource(Entity user) {

    }
    
}
