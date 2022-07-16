package tankgame.display;

import tankgame.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Minimap {

    private static final double SCALE = 0.2;

    private BufferedImage minimap;
    private double scaledWidth;
    private double scaledHeight;

    private double truePositionX;
    private double truePositionY;

    public Minimap() {}

    public void initializeMiniMapDimensions() {
        scaledWidth = GameConstants.WORLD_WIDTH * SCALE;
        scaledHeight = GameConstants.WORLD_HEIGHT * SCALE;
        truePositionX = GameConstants.WORLD_WIDTH + scaledWidth - 10;
        truePositionY = 2 * GameConstants.WORLD_HEIGHT + scaledHeight;
    }

    public void drawMinimap(BufferedImage world, Graphics2D g2) {
        BufferedImage minimap = world.getSubimage(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        g2.scale(SCALE, SCALE);
        g2.drawImage(minimap, (int)(truePositionX), (int)(truePositionY), null);
    }

    public int getScaledWidth() {
        return (int) this.scaledWidth;
    }

    public int getScaledHeight() {
        return (int) this.scaledHeight;
    }

}
