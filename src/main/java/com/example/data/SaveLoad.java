package com.example.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.example.common.GamePanel;

public class SaveLoad {

    GamePanel gamePanel;

    public SaveLoad(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void save() {
        try {

            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));

            // Player stats
            DataStorage dStorage = new DataStorage();
            dStorage.level = gamePanel.player.level;
            dStorage.maxLife = gamePanel.player.maxLife;
            dStorage.life = gamePanel.player.life;
            dStorage.maxMana = gamePanel.player.maxMana;
            dStorage.mana = gamePanel.player.mana;
            dStorage.strength = gamePanel.player.strength;
            dStorage.dexterity = gamePanel.player.dexterity;
            dStorage.exp = gamePanel.player.exp;
            dStorage.nextLevelExp = gamePanel.player.nextLevelExp;
            dStorage.coin = gamePanel.player.coin;

            // Player inventory
            for (int i = 0; i < gamePanel.player.inventory.size(); i++) {
                dStorage.itemNames.add(gamePanel.player.inventory.get(i).name);
                dStorage.itemAmounts.add(gamePanel.player.inventory.get(i).amount);
            }

            // Player equipment
            dStorage.currentWeaponSlot = gamePanel.player.getCurrentWeaponSlot();
            dStorage.currentShieldSlot = gamePanel.player.getCurrentShieldSlot();

            // Objects on map
            dStorage.mapObjectWorldX = new int[gamePanel.maxMap][gamePanel.obj[1].length];
            dStorage.mapObjectWorldY = new int[gamePanel.maxMap][gamePanel.obj[1].length];
            dStorage.mapObjectNames = new String[gamePanel.maxMap][gamePanel.obj[1].length];
            dStorage.mapObjectLootNames = new String[gamePanel.maxMap][gamePanel.obj[1].length];
            dStorage.mapObjectOpened = new boolean[gamePanel.maxMap][gamePanel.obj[1].length];

            for (int mapNumber = 0; mapNumber < gamePanel.maxMap; mapNumber++) {
                for (int i = 0; i < gamePanel.obj[1].length; i++) {
                    if (gamePanel.obj[mapNumber][i] == null) {
                        dStorage.mapObjectNames[mapNumber][i] = "NA";
                    } else {
                        dStorage.mapObjectNames[mapNumber][i] = gamePanel.obj[mapNumber][i].name;
                        dStorage.mapObjectWorldX[mapNumber][i] = gamePanel.obj[mapNumber][i].worldX;
                        dStorage.mapObjectWorldY[mapNumber][i] = gamePanel.obj[mapNumber][i].worldY;
                        if (gamePanel.obj[mapNumber][i].loot != null) {
                            dStorage.mapObjectLootNames[mapNumber][i] = gamePanel.obj[mapNumber][i].loot.name;
                        }
                        dStorage.mapObjectOpened[mapNumber][i] = gamePanel.obj[mapNumber][i].opened;
                    }
                }
            }

            // Write the DataStorage object
            outputStream.writeObject(dStorage);

        } catch (Exception e) {
            System.out.println("Save Exception!");
        }
    }

    public void load() {
        try {

            ObjectInputStream oInputStream = new ObjectInputStream(new FileInputStream(new File("save.dat")));
            
            // Read the DataStorage object
            DataStorage dStorage = (DataStorage) oInputStream.readObject();
            gamePanel.player.level = dStorage.level;
            gamePanel.player.maxLife = dStorage.maxLife;
            gamePanel.player.life = dStorage.life;
            gamePanel.player.maxMana = dStorage.maxMana;
            gamePanel.player.mana = dStorage.mana;
            gamePanel.player.strength = dStorage.strength;
            gamePanel.player.dexterity = dStorage.dexterity;
            gamePanel.player.exp = dStorage.exp;
            gamePanel.player.nextLevelExp = dStorage.nextLevelExp;
            gamePanel.player.coin = dStorage.coin;

            // Player inventory
            gamePanel.player.inventory.clear();
            for (int i = 0; i < dStorage.itemNames.size(); i++) {
                gamePanel.player.inventory.add(gamePanel.eGenerator.getObject(dStorage.itemNames.get(i)));
                gamePanel.player.inventory.get(i).amount = dStorage.itemAmounts.get(i);
            }

            // Player equipment
            gamePanel.player.currentWeapon = gamePanel.player.inventory.get(dStorage.currentWeaponSlot);
            gamePanel.player.currentShield = gamePanel.player.inventory.get(dStorage.currentShieldSlot);
            gamePanel.player.getAttack();
            gamePanel.player.getDefense();
            gamePanel.player.getAttackImage();

            // Objects on map
            for (int mapNumber = 0; mapNumber < gamePanel.maxMap; mapNumber++) {
                for (int i = 0; i < gamePanel.obj[1].length; i++) {
                    if (dStorage.mapObjectNames[mapNumber][i].equals("NA")) {
                        gamePanel.obj[mapNumber][i] = null;
                    } else {
                        gamePanel.obj[mapNumber][i] = gamePanel.eGenerator.getObject(dStorage.mapObjectNames[mapNumber][i]);
                        gamePanel.obj[mapNumber][i].worldX = dStorage.mapObjectWorldX[mapNumber][i];
                        gamePanel.obj[mapNumber][i].worldY = dStorage.mapObjectWorldY[mapNumber][i];
                        if (dStorage.mapObjectLootNames[mapNumber][i] != null) {
                            gamePanel.obj[mapNumber][i].setLoot(gamePanel.eGenerator.getObject(dStorage.mapObjectLootNames[mapNumber][i]));
                        }
                        gamePanel.obj[mapNumber][i].opened = dStorage.mapObjectOpened[mapNumber][i];
                        if (gamePanel.obj[mapNumber][i].opened) {
                            gamePanel.obj[mapNumber][i].down1 = gamePanel.obj[mapNumber][i].image2;
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Load Exception!");
        }
    }
    
}
