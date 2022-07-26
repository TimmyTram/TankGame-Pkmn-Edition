package tankgame.display;

import tankgame.constants.GameConstants;
import tankgame.game.moveableObjects.tanks.Tank;

import java.awt.image.BufferedImage;

public class Camera {

    private static final int SPLIT_SCREEN_WIDTH = (GameConstants.GAME_SCREEN_WIDTH / 2);
    private BufferedImage splitScreen;
    private final Tank tank;
    private final int HUD_LIMIT;

    public Camera(Tank tank, int HUD_LIMIT) {
        this.tank = tank;
        this.HUD_LIMIT = (HUD_LIMIT / 2) + 12;
    }

    public void drawSplitScreen(BufferedImage world) {
        int x = (int) tank.getX();
        int y = (int) tank.getY();
        splitScreen = world.getSubimage(this.checkBorderX(x), this.checkBorderY(y), SPLIT_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT - HUD_LIMIT);
    }

    public BufferedImage getSplitScreen() {
        return this.splitScreen;
    }

    /**
     *
     * @param x this is the current x coordinate of the tank this camera is tracking
     * @return x value that will tell the camera where to position the sub-image without it going out the raster
     */
    private int checkBorderX(int x) {
        if(x < GameConstants.GAME_SCREEN_WIDTH / 4) {
            x = GameConstants.GAME_SCREEN_WIDTH / 4;
        } else if (x > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 4) {
            x = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 4;
        }
        x -= GameConstants.GAME_SCREEN_WIDTH / 4;
        return x;
    }

    /**
     *
     * @param y this the current y coordinate of the tank this camera is tracking
     * @return y value that will tell the camera where to position the sub-image without it going out the raster
     */
    private int checkBorderY(int y) {
        if(y < GameConstants.GAME_SCREEN_HEIGHT / 2) {
            y = GameConstants.GAME_SCREEN_HEIGHT / 2;
        } else if(y > GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT / 2 + HUD_LIMIT) {
            y = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT / 2 + HUD_LIMIT;
        }
        y -= GameConstants.GAME_SCREEN_HEIGHT / 2;
        return y;
    }
}
