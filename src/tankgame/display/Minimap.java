package tankgame.display;

import tankgame.constants.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Minimap {

    private static final double SCALE = 0.2;
    private final double scaledWidth;
    private final double scaledHeight;


    public Minimap() {
        this.scaledWidth = GameConstants.GAME_SCREEN_WIDTH / 2f - (GameConstants.WORLD_WIDTH * SCALE) / 2f;
        this.scaledHeight = GameConstants.GAME_SCREEN_HEIGHT - GameConstants.WORLD_HEIGHT * (SCALE * 1.14);
    }

    public void drawMinimap(BufferedImage world, Graphics2D g2) {
        BufferedImage minimap = world.getSubimage(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(this.scaledWidth, this.scaledHeight);
        affineTransform.scale(SCALE, SCALE);
        g2.drawImage(minimap, affineTransform, null);
    }

    public int getScaledWidth() {
        return (int) this.scaledWidth;
    }

    public int getScaledHeight() {
        return (int) this.scaledHeight;
    }

}
