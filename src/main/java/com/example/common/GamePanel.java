package com.example.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.example.Main;
import com.example.ai.PathFinder;
import com.example.data.SaveLoad;
import com.example.entity.Entity;
import com.example.entity.Player;
import com.example.environment.EnvironmentManager;
import com.example.tile.Map;
import com.example.tile.TileManager;
import com.example.tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {
    
    // Screen Settings
    final int originalTileSize = 16; // 16 x 16 pixels
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48 x 48 pixels
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // World Settings
    public int maxWorldCol;
    public int maxWorldRow;
    public final int maxMap = 10;
    public int currentMap = 0;

    // Full Screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

    // Options
    public boolean fullscreenOn = false;

    // FPS
    int FPS = 60;

    // System
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound music = new Sound();
    Sound sfx = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    public Config config = new Config(this);
    public PathFinder pathFinder = new PathFinder(this);
    EnvironmentManager eManager = new EnvironmentManager(this);
    Map map = new Map(this);
    SaveLoad saveLoad = new SaveLoad(this);
    public EntityGenerator eGenerator = new EntityGenerator(this);
    public CutsceneManager cManager = new CutsceneManager(this);
    Thread gameThread;

    // Entity and Object
    public Player player = new Player(this, keyHandler);
    public Entity obj[][] = new Entity[maxMap][100];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][50];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    public Entity projectile[][] = new Entity[maxMap][20];
    // public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    // Game State
    public int gameState;
    public final static int TITLE_STATE = 0;
    public final static int PLAY_STATE = 1;
    public final static int PAUSE_STATE = 2;
    public final static int DIALOGUE_STATE = 3;
    public final static int CHARACTER_STATE = 4;
    public final static int OPTIONS_STATE = 5;
    public final static int GAME_OVER_STATE = 6;
    public final static int TRANSITION_STATE = 7;
    public final static int TRADE_STATE = 8;
    public final static int SLEEP_STATE = 9;
    public final static int MAP_STATE = 10;
    public final static int CUTSCENE_STATE = 11;

    // Others
    public boolean bossBattleOn = false;

    // Area
    public int currentArea;
    public int nextArea;
    public final static int OUTSIDE_AREA = 50;
    public final static int INDOOR_AREA = 51;
    public final static int DUNGEON_AREA = 52;

    // Music
    public int currentMusicIndex = 0;

    public GamePanel () {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // improve game's rendering
        this.addKeyListener(keyHandler);
        this.setFocusable(true); // game panel can be focused to received input
    }

    public void setupGame() {
        
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setMonster();
        assetSetter.setInteractiveTile();
        // playMusic(6);
        eManager.setup();

        gameState = TITLE_STATE;
        currentArea = OUTSIDE_AREA;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if (fullscreenOn) {
            setFullScreen();
        }
        
    }

    public void resetGame(boolean restart) {
        // stopMusic();
        currentArea = OUTSIDE_AREA;
        removeTempEntity();
        bossBattleOn = false;
        player.restoreStatus();
        player.resetCounter();
        assetSetter.setNPC();
        assetSetter.setMonster();

        if (restart) {
            player.setDefaultValues();
            player.setDefaultPosition();
            assetSetter.setObject();
            assetSetter.setInteractiveTile();
            eManager.lighting.resetDay();
        }
        
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setFullScreen() {

        // This one is lag and fps drop
        // // Get local screen device
        // GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        // GraphicsDevice gd = ge.getDefaultScreenDevice();
        // gd.setFullScreenWindow(Main.window);
        // // Get fullscreen width and height
        // screenWidth2 = Main.window.getWidth();
        // screenHeight2 = Main.window.getHeight();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        Main.window.setExtendedState(JFrame.MAXIMIZED_BOTH);

        screenWidth2 = (int) width;
        screenHeight2 = (int) height;
        // Offset factor to be used by mouse listener or motion listener if you are using cursor in your game. Multiply your e.getX() and 3.getY() by this.
        // fullScreenOffsetFactor = (float) screenWidth / (float) screenWidth2;

    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        
        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta > 1) {
                update();
                drawToTempScreen(); // Draw everything to the buffered image
                drawToScreen(); // Draw the buffered image to the screen
                delta--;
            }

        }

    }

    public void update() {
        if (gameState == PLAY_STATE) {

            // Player
            player.update();

            // NPC
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }

            // Monster
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].alive && !monster[currentMap][i].dying) {
                        monster[currentMap][i].update();
                    }
                    if (!monster[currentMap][i].alive) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }

            // Projectile
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    if (projectile[currentMap][i].alive) {
                        projectile[currentMap][i].update();
                    }
                    if (!projectile[currentMap][i].alive) {
                        projectile[currentMap][i] = null;
                    }
                }
            }

            // Particle
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).alive) {
                        particleList.get(i).update();
                    }
                    if (!particleList.get(i).alive) {
                        particleList.remove(i);
                    }
                }
            }

            // Interactive Tile
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                    
                }
            }

            // Upload lightning
            eManager.update();

        }
        if (gameState == PAUSE_STATE) {
            // nothing            
        }
    }

    public void drawToTempScreen() {

        // Debug
        long drawStart = 0;
        if (keyHandler.showDebugText) drawStart = System.nanoTime();
        
        if (gameState == TITLE_STATE) { // Title screen
            ui.draw(g2);
        } else if (gameState == GamePanel.MAP_STATE) { // Map screen
            map.drawFullMapScreen(g2);
        } else { // Main Screen

            // Tile
            tileManager.draw(g2);

            // Add interactive tile
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }

            // Add entities to the list
            entityList.add(player);
            
            // Add npc
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }

            // Add object
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }

            // Add monster
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }

            // Add projectile
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    entityList.add(projectile[currentMap][i]);
                }
            }

            // Add particle
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }

            // Sort
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            // Draw Entities
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            // Empty entity list
            entityList.clear();

            // Environment
            eManager.draw(g2);

            // Mini map
            map.drawMiniMap(g2);

            // Cutscene
            cManager.draw(g2);

            // UI
            ui.draw(g2);

        }

        // Debug
        if (keyHandler.showDebugText) {

            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);

            int x = 10;
            int y = 400;
            int lineHeight = 30;

            g2.drawString("WorldX: " + player.worldX, x, y); y += lineHeight;
            g2.drawString("WorldY: " + player.worldY, x, y); y += lineHeight;
            g2.drawString("Col: " + (player.worldX + player.solidArea.x) / tileSize, x, y); y += lineHeight;
            g2.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize, x, y); y += lineHeight;
            g2.drawString("Draw time: " + passed, x, y); y += lineHeight;
            g2.drawString("God mode: " + keyHandler.godModeOn, x, y); y += lineHeight;

        }

    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect(int i) {
        sfx.setFile(i);
        sfx.play();
    }

    public void changeArea() {
        if (nextArea != currentArea) {
            stopMusic();
            if (nextArea == OUTSIDE_AREA) playMusic(0);
            if (nextArea == INDOOR_AREA) playMusic(6);
            if (nextArea == DUNGEON_AREA) playMusic(7);
            assetSetter.setNPC();
        }
        currentArea = nextArea;
        assetSetter.setMonster();
    }

    public void removeTempEntity() {
        for (int mapNumber = 0; mapNumber < maxMap; mapNumber++) {
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[mapNumber][i] != null && obj[mapNumber][i].temp) {
                    obj[mapNumber][i] = null;
                }
            }
        }
    }

}
