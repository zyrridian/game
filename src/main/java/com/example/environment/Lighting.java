package com.example.environment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;

import com.example.common.GamePanel;

public class Lighting {
    
    GamePanel gamePanel;
    BufferedImage darknessFilter;
    public int dayCounter;
    public float filterAlpha = 0F;

    // Day state
    public final int day = 0;
    public final int dusk = 1;
    public final int night = 2;
    public final int dawn = 3;
    public int dayState = day;

    public Lighting(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
        setLightSource();

    }

    public void setLightSource() {

        // Create buffered image
        darknessFilter = new BufferedImage(gamePanel.screenWidth, gamePanel.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        if (gamePanel.player.currentLight == null) {
            g2.setColor(new Color(0, 0, 0.1F, 0.97F));
        } else {

            // Get the center x and y of the light circle
            int centerX = gamePanel.player.screenX + (gamePanel.tileSize) / 2;
            int centerY = gamePanel.player.screenY + (gamePanel.tileSize) / 2;

            // Create a gradation effect within the light circle
            Color color[] = new Color[12];
            float fraction[] = new float[12];

            // Determine the gradient colors
            color[0] = new Color(0, 0, 0.1F, 0.1F);
            color[1] = new Color(0, 0, 0.1F, 0.42F);
            color[2] = new Color(0, 0, 0.1F, 0.52F);
            color[3] = new Color(0, 0, 0.1F, 0.61F);
            color[4] = new Color(0, 0, 0.1F, 0.69F);
            color[5] = new Color(0, 0, 0.1F, 0.76F);
            color[6] = new Color(0, 0, 0.1F, 0.82F);
            color[7] = new Color(0, 0, 0.1F, 0.87F);
            color[8] = new Color(0, 0, 0.1F, 0.91F);
            color[9] = new Color(0, 0, 0.1F, 0.92F);
            color[10] = new Color(0, 0, 0.1F, 0.93F);
            color[11] = new Color(0, 0, 0.1F, 0.94F);

            // Determine the colors distance
            fraction[0] = 0F;
            fraction[1] = 0.4F;
            fraction[2] = 0.5F;
            fraction[3] = 0.6F;
            fraction[4] = 0.65F;
            fraction[5] = 0.7F;
            fraction[6] = 0.75F;
            fraction[7] = 0.8F;
            fraction[8] = 0.85F;
            fraction[9] = 0.9F;
            fraction[10] = 0.95F;
            fraction[11] = 1F;

            // Create a gradation paint settings for the light circle
            RadialGradientPaint gradientPaint = new RadialGradientPaint(centerX, centerY, gamePanel.player.currentLight.lightRadius, fraction, color);

            // Set the gradient data on g2
            g2.setPaint(gradientPaint);

        }

        // Draw the screen rectangle without the light circle area
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        g2.dispose();

    }

    public void resetDay() {
        dayState = day;
        filterAlpha = 0F;
    }

    public void update() {
        if (gamePanel.player.lightUpdated) {
            setLightSource();
            gamePanel.player.lightUpdated = false;
        }

        // Check the sate of the day
        if (dayState == day) {
            dayCounter++;
            if (dayCounter > 600) { // Day state duration: 10 second
                dayState = dusk;
                dayCounter = 0;
            }
        }
        if (dayState == dusk) {
            filterAlpha += 0.001F; // Transition duration
            if (filterAlpha > 1F) {
                filterAlpha = 1F;
                dayState = night;
            }
        }
        if (dayState == night) {
            dayCounter++;
            if (dayCounter > 600) { // Night state duration
                dayState = dawn;
                dayCounter = 0;
            }
        }
        if (dayState == dawn) {
            filterAlpha -= 0.001F; // Transition duration
            if (filterAlpha < 0) {
                filterAlpha = 0;
                dayState = day;
            }
        }
    }

    public void draw(Graphics2D g2) {

        // if it's outside, draw day night alpha
        if (gamePanel.currentArea == GamePanel.OUTSIDE_AREA) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        }

        // draw darkness, always dark in dungeon
        if (gamePanel.currentArea == GamePanel.OUTSIDE_AREA || gamePanel.currentArea == GamePanel.DUNGEON_AREA) {
            g2.drawImage(darknessFilter, 0, 0, null);    
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F)); // always bright in indoor

        // Debug
        String situation = "";
        switch (dayState) {
            case day: situation = "Day"; break;
            case dusk: situation = "Dusk"; break;
            case night: situation = "Night"; break;
            case dawn: situation = "Dawn"; break;
        }
        
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        g2.drawString(situation, 800, 500);

    }

}
