package tankgame.display;

import tankgame.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Minimap {

    private static final double SCALE = 0.2;

    public Minimap() {}

    /**
     * Function performs math calculations to place the minimap at the center of the screen.
     * @param world an image of the game world
     * @param g2 used to make a sub-image which represents the minimap of the world
     */
    public void drawMinimap(BufferedImage world, Graphics2D g2) {
        BufferedImage minimap = world.getSubimage(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        g2.scale(SCALE, SCALE);
        double scaledWidth = GameConstants.WORLD_WIDTH * SCALE;
        double scaledHeight = GameConstants.WORLD_HEIGHT * SCALE;
        double truePositionX = GameConstants.WORLD_WIDTH + scaledWidth - 10;
        double truePositionY = 2 * GameConstants.WORLD_HEIGHT + scaledHeight;
        g2.drawImage(minimap, (int)(truePositionX), (int)(truePositionY), null);
    }
}
