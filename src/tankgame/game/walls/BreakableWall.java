package tankgame.game.walls;

import java.awt.image.BufferedImage;

public class BreakableWall extends Wall {

    public BreakableWall(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void setDestroyed(boolean destroyed) {
        super.destroyed = destroyed;
    }
}
