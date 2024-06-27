package com.example.common;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    
    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl floatControl;
    int volumeScale = 3;
    float volume;

    public Sound() {
        soundURL[0] = getClass().getResource("/sounds/WhiteSpace.wav");
        soundURL[1] = getClass().getResource("/sounds/coin.wav");
        soundURL[2] = getClass().getResource("/sounds/powerup.wav");
        soundURL[3] = getClass().getResource("/sounds/unlock.wav");
        soundURL[4] = getClass().getResource("/sounds/fanfare.wav");
        soundURL[5] = getClass().getResource("/sounds/cursor.wav");
        // soundURL[6] = getClass().getResource("/sounds/mainmenu.wav");
        soundURL[6] = getClass().getResource("/sounds/Merchant.wav");
        soundURL[7] = getClass().getResource("/sounds/RisingTensions.wav");
        // soundURL[6] = getClass().getResource("/sounds/LostAtASleepover.wav");
        // soundURL[7] = getClass().getResource("/sounds/Friends.wav");
        soundURL[8] = getClass().getResource("/sounds/hitmonster.wav");
        soundURL[9] = getClass().getResource("/sounds/receivedamage.wav");
        soundURL[10] = getClass().getResource("/sounds/swordslash.wav");
        soundURL[11] = getClass().getResource("/sounds/burning.wav");
        soundURL[12] = getClass().getResource("/sounds/cuttree.wav");
        soundURL[13] = getClass().getResource("/sounds/gameover.wav");
        soundURL[14] = getClass().getResource("/sounds/stairs.wav");
        soundURL[15] = getClass().getResource("/sounds/sleep.wav");
        soundURL[16] = getClass().getResource("/sounds/blocked.wav");
        soundURL[17] = getClass().getResource("/sounds/parry.wav");
        soundURL[18] = getClass().getResource("/sounds/speak.wav");
        soundURL[19] = getClass().getResource("/sounds/chipwall.wav");
        soundURL[20] = getClass().getResource("/sounds/dooropen.wav");
        soundURL[21] = getClass().getResource("/sounds/BossBattle.wav");
        soundURL[22] = getClass().getResource("/sounds/Ending.wav");
        soundURL[23] = getClass().getResource("/sounds/Credit.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audio);
            floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        } catch (Exception e) {
            
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void checkVolume() {
        switch (volumeScale) {
            case 0: volume = -80F; break;
            case 1: volume = -20F; break;
            case 2: volume = -12F; break;
            case 3: volume = -5F; break;
            case 4: volume = 1F; break;
            case 5: volume = 6F; break;
        }
        floatControl.setValue(volume);
    }

}

        
