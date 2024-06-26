package com.example.common;

import java.awt.event.*;

import javax.swing.Timer;

public class KeyHandler implements KeyListener {

    GamePanel gamePanel;
    Timer timer;
    public boolean upPressed, leftPressed, downPressed, rightPressed, enterPressed, shotKeyPressed, spacePressed;

    // Debug
    public boolean showDebugText = false;
    public boolean godModeOn = false;

    // Constructor
    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (gamePanel.gameState) {
            case GamePanel.TITLE_STATE: handleTitleState(code); break;
            case GamePanel.PLAY_STATE: handlePlayState(code); break;
            case GamePanel.PAUSE_STATE: handlePauseState(code); break;
            case GamePanel.DIALOGUE_STATE: handleDialogueState(code); break;
            case GamePanel.CUTSCENE_STATE: handleDialogueState(code); break;
            case GamePanel.CHARACTER_STATE: handleCharacterState(code); break;
            case GamePanel.OPTIONS_STATE: handleOptionsState(code); break;
            case GamePanel.GAME_OVER_STATE: handleGameOverState(code); break;
            case GamePanel.TRADE_STATE: handleTradeState(code); break;
            case GamePanel.MAP_STATE: handleMapState(code); break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = false;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = false;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = false;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = false;
        if (code == KeyEvent.VK_J || code == KeyEvent.VK_ENTER) enterPressed = false;
        if (code == KeyEvent.VK_K || code == KeyEvent.VK_SPACE) spacePressed = false;
        if (code == KeyEvent.VK_L || code == KeyEvent.VK_CONTROL) shotKeyPressed = false;
    }

    // ======================================== HANDLE STATE ======================================== //

    public void handleTitleState(int code) {
        if (gamePanel.ui.titleScreenState == 0) {
                
        } else if (gamePanel.ui.titleScreenState == 1) {
            if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                gamePanel.ui.commandNumber--;
                gamePanel.playSoundEffect(5);
                if (gamePanel.ui.commandNumber < 0) gamePanel.ui.commandNumber = 2;
            }
            if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                gamePanel.ui.commandNumber++;
                gamePanel.playSoundEffect(5);
                if (gamePanel.ui.commandNumber > 2) gamePanel.ui.commandNumber = 0;
            }
            if (code == KeyEvent.VK_ENTER) {
                gamePanel.playSoundEffect(3);
                if (gamePanel.ui.commandNumber == 0) {
                    gamePanel.gameState = GamePanel.PLAY_STATE;
                    gamePanel.playMusic(0);
                }
                if (gamePanel.ui.commandNumber == 1) {
                    gamePanel.saveLoad.load();
                    gamePanel.gameState = GamePanel.PLAY_STATE;
                    gamePanel.playMusic(0);
                }
                if (gamePanel.ui.commandNumber == 2) System.exit(code);
            }
        }
    }

    public void handlePlayState(int code) {

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = true;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = true;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = true;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = true;
        if (code == KeyEvent.VK_J || code == KeyEvent.VK_ENTER) enterPressed = true;
        if (code == KeyEvent.VK_K || code == KeyEvent.VK_SPACE) spacePressed = true;
        if (code == KeyEvent.VK_L || code == KeyEvent.VK_CONTROL) shotKeyPressed = true;
        if (code == KeyEvent.VK_M) gamePanel.gameState = GamePanel.MAP_STATE;
        if (code == KeyEvent.VK_P) gamePanel.gameState = GamePanel.PAUSE_STATE;
        if (code == KeyEvent.VK_C) gamePanel.gameState = GamePanel.CHARACTER_STATE;
        if (code == KeyEvent.VK_ESCAPE) gamePanel.gameState = GamePanel.OPTIONS_STATE;

        if (code == KeyEvent.VK_X) {
            if (!gamePanel.map.miniMapOn) {
                gamePanel.map.miniMapOn = true;
            } else {
                gamePanel.map.miniMapOn = false;
            }
        }

        // Debug
        if (code == KeyEvent.VK_T) {
            if (!showDebugText) {
                showDebugText = true;
            } else if (showDebugText) {
                showDebugText = false;
            }
        }
        if (code == KeyEvent.VK_R) {
            switch (gamePanel.currentMap) {
                case 0: gamePanel.tileManager.loadMap("/maps/worldV3.txt", 0); break;
                case 1: gamePanel.tileManager.loadMap("/maps/interior.txt", 1); break;
            }
            
        }
        if (code == KeyEvent.VK_G) {
            if (!godModeOn) {
                godModeOn = true;
            } else if (godModeOn) {
                godModeOn = false;
            }
        }

    }

    public void handlePauseState(int code) {
        if (code == KeyEvent.VK_P || code == KeyEvent.VK_ESCAPE) {
            gamePanel.gameState = GamePanel.PLAY_STATE;
        }
    }

    public void handleDialogueState(int code) {
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_E) {
            enterPressed = true;
        }
    }

    public void handleCharacterState(int code) {
        if (code == KeyEvent.VK_C) gamePanel.gameState = GamePanel.PLAY_STATE;
        if (code == KeyEvent.VK_ENTER) gamePanel.player.selectItem();
        playerInventory(code);
    }

    public void handleOptionsState(int code) {

        if (code == KeyEvent.VK_ESCAPE) gamePanel.gameState = GamePanel.PLAY_STATE;
        if (code == KeyEvent.VK_ENTER) enterPressed = true;

        int maxcCommandNum = 0;
        switch (gamePanel.ui.subState) {
            case 0: maxcCommandNum = 5; break;
            case 3: maxcCommandNum = 1; break;
        }

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gamePanel.ui.commandNumber--;
            gamePanel.playSoundEffect(5);
            if (gamePanel.ui.commandNumber < 0) gamePanel.ui.commandNumber = maxcCommandNum;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gamePanel.ui.commandNumber++;
            gamePanel.playSoundEffect(5);
            if (gamePanel.ui.commandNumber > maxcCommandNum) gamePanel.ui.commandNumber = 0;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if (gamePanel.ui.subState == 0) {
                if (gamePanel.ui.commandNumber == 1 && gamePanel.music.volumeScale > 0) {
                    gamePanel.music.volumeScale--;
                    gamePanel.music.checkVolume();
                    gamePanel.playSoundEffect(5);
                }
                if (gamePanel.ui.commandNumber == 2 && gamePanel.sfx.volumeScale > 0) {
                    gamePanel.sfx.volumeScale--;
                    gamePanel.playSoundEffect(5);
                }
            }
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if (gamePanel.ui.subState == 0) {
                if (gamePanel.ui.commandNumber == 1 && gamePanel.music.volumeScale < 5) {
                    gamePanel.music.volumeScale++;
                    gamePanel.music.checkVolume();
                    gamePanel.playSoundEffect(5);
                }
                if (gamePanel.ui.commandNumber == 2 && gamePanel.sfx.volumeScale < 5) {
                    gamePanel.sfx.volumeScale++;
                    gamePanel.playSoundEffect(5);
                }
            }
        }
    }

    public void handleGameOverState(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gamePanel.ui.commandNumber--;
            gamePanel.playSoundEffect(5);
            if (gamePanel.ui.commandNumber < 0) gamePanel.ui.commandNumber = 1;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gamePanel.ui.commandNumber++;
            gamePanel.playSoundEffect(5);
            if (gamePanel.ui.commandNumber > 1) gamePanel.ui.commandNumber = 0;
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gamePanel.ui.commandNumber == 0) {
                gamePanel.currentMusicIndex = 0;
                gamePanel.eventHandler.teleport(0, 11, 35, GamePanel.OUTSIDE_AREA);
                gamePanel.resetGame(false);
                gamePanel.playMusic(0);
            } else if (gamePanel.ui.commandNumber == 1) {
                gamePanel.gameState = GamePanel.TITLE_STATE;
                gamePanel.resetGame(true);
            }
        }
    }

    public void handleTradeState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (gamePanel.ui.subState == 0) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gamePanel.ui.commandNumber--;
                gamePanel.playSoundEffect(5);
                if (gamePanel.ui.commandNumber < 0) gamePanel.ui.commandNumber = 2;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gamePanel.ui.commandNumber++;
                gamePanel.playSoundEffect(5);
                if (gamePanel.ui.commandNumber > 2) gamePanel.ui.commandNumber = 0;
            }
        }
        if (gamePanel.ui.subState == 1) {
            npcInventory(code);
            if (code == KeyEvent.VK_ESCAPE) gamePanel.ui.subState = 0;
        }
        if (gamePanel.ui.subState == 2) {
            playerInventory(code);
            if (code == KeyEvent.VK_ESCAPE) gamePanel.ui.subState = 0;
        }
    }

    public void handleMapState(int code) {
        if (code == KeyEvent.VK_M) {
            gamePanel.gameState = GamePanel.PLAY_STATE;
        }
    }

    // ======================================== HANDLE TRADE ======================================== //

    public void playerInventory(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gamePanel.playSoundEffect(5);
            if (gamePanel.ui.playerSlotRow != 0) gamePanel.ui.playerSlotRow--;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            gamePanel.playSoundEffect(5);
            if (gamePanel.ui.playerSlotCol != 0) gamePanel.ui.playerSlotCol--;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gamePanel.playSoundEffect(5);   
            if (gamePanel.ui.playerSlotRow != 3) gamePanel.ui.playerSlotRow++;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            gamePanel.playSoundEffect(5);
            if (gamePanel.ui.playerSlotCol != 4) gamePanel.ui.playerSlotCol++;
        }
    }

    public void npcInventory(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gamePanel.playSoundEffect(5);
            if (gamePanel.ui.npcSlotRow != 0) gamePanel.ui.npcSlotRow--;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            gamePanel.playSoundEffect(5);
            if (gamePanel.ui.npcSlotCol != 0) gamePanel.ui.npcSlotCol--;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gamePanel.playSoundEffect(5);   
            if (gamePanel.ui.npcSlotRow != 3) gamePanel.ui.npcSlotRow++;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            gamePanel.playSoundEffect(5);
            if (gamePanel.ui.npcSlotCol != 4) gamePanel.ui.npcSlotCol++;
        }
    }

}
