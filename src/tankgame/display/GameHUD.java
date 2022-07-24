package tankgame.display;

import tankgame.constants.ResourceConstants;
import tankgame.ResourceHandler;
import tankgame.game.moveableObjects.tanks.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameHUD {

    private static final int offset = 50;
    private static final int GRAY_OFFSET = 30;
    private final BufferedImage HUD_BACKGROUND;
    private final Tank tank;
    private final int x;
    private final int y;
    private final int width;

    public GameHUD(Tank tank, int x, int y, int width, BufferedImage HUD_BACKGROUND) {
        this.tank = tank;
        this.x = x;
        this.y = y;
        this.width = width;
        this.HUD_BACKGROUND = HUD_BACKGROUND;
    }

    public void drawHUD(Graphics2D g2) {
        g2.drawImage(this.HUD_BACKGROUND, this.x, this.y, null);
        this.drawHealthBar(g2);
        this.drawLives(g2);
    }

    private void drawLives(Graphics2D g2) {
        int lives = this.tank.getLives();
        int livesXPosition = x + offset;
        int livesYPosition = y + (2 * offset);
        int livesXOffset = ResourceHandler.getImage(ResourceConstants.IMAGES_LIVES).getWidth();
        for(int i = 0; i < lives; i++) {
            livesXPosition += livesXOffset * i;
            g2.drawImage(ResourceHandler.getImage(ResourceConstants.IMAGES_LIVES), livesXPosition, livesYPosition, null);
            livesXPosition = x + offset;
        }
    }

    private void drawHealthBar(Graphics2D g2) {
        int healthPoints = this.tank.getCurrentHealthPoints();
        int maxHealthPoints = this.tank.getMaxHealthPoints();
        double maxHealthBarWidth = (this.width - offset * 4);
        double healthBarWidth = maxHealthBarWidth * (healthPoints / 100.0);

        g2.setColor(Color.lightGray);
        g2.fillRect(x + offset, this.y + offset - GRAY_OFFSET, this.width - (offset * 2), offset + GRAY_OFFSET);

        g2.setColor(Color.black);
        g2.fillRect(x + (int)(1.8 * offset), this.y + (int)(1.15 * offset), (int)(maxHealthBarWidth * 1.085), (int)(offset / 1.4));

        if(healthPoints >= 66) {
            g2.setColor(Color.green);
        } else if(healthPoints >= 33) {
            g2.setColor(Color.yellow);
        } else {
            g2.setColor(Color.red);
        }
        g2.fillRect(x + (2 * offset), this.y + (int)(1.25 * offset), (int)healthBarWidth, offset / 2);

        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        g2.setColor(Color.black);
        g2.drawString(tank.getName(), x + (int)(1.5 * offset), this.y + (int)(0.9 * offset));
        g2.drawString("HP: " + healthPoints + "/" + maxHealthPoints, x + (int)(4.2 * offset), this.y + (int)(0.9 * offset));
    }

}
