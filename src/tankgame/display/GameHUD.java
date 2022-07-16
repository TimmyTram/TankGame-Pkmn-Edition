package tankgame.display;

import tankgame.game.tanks.Tank;

import java.awt.*;

public class GameHUD {

    private static final int offset = 50;
    private static final int GRAY_OFFSET = 30;
    private final Tank tank;
    private int x;
    private int y;
    private int width;
    private int height;

    public GameHUD(Tank tank, int x, int y, int width, int height) {
        this.tank = tank;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawHUD(Graphics2D g2) {
        //g2.setColor(Color.red);
        //g2.fillRect(x, y, width, height);
        this.drawLives(g2);
        this.drawHealthBar(g2);
    }

    private void drawLives(Graphics2D g2) {
        int lives = this.tank.getLives();
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
