package tankgame.game;

import tankgame.GameConstants;
import tankgame.ResourceConstants;
import tankgame.ResourceHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundLoader {

    private static BackgroundLoader instance;
    private static BufferedImage backgroundImage;
    private static int maxRowTile;
    private static int maxColumnTile;
    private static int bgWidth;
    private static int bgHeight;

    private BackgroundLoader() {}

    public static BackgroundLoader getInstance() {
        if(instance == null)
            instance = new BackgroundLoader();
        return instance;
    }
    public void initializeBackground() {
        backgroundImage = ResourceHandler.getImage(ResourceConstants.RESOURCE_FLOOR_TILE);
        bgWidth = ResourceHandler.getImage(ResourceConstants.RESOURCE_FLOOR_TILE).getWidth();
        bgHeight = ResourceHandler.getImage(ResourceConstants.RESOURCE_FLOOR_TILE).getHeight();
        maxRowTile = GameConstants.WORLD_WIDTH / bgWidth;
        maxColumnTile = GameConstants.WORLD_HEIGHT / bgHeight;
    }

    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for(int row = 0; row < maxRowTile; row++) {
            for(int col = 0; col < maxColumnTile; col++) {
                g2d.drawImage(backgroundImage, row * bgWidth, col * bgHeight, null);
            }
        }
    }
}
