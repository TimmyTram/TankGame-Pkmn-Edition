package tankgame.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class StationaryObject extends GameObject implements Collidable {

    public StationaryObject(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public Rectangle getHitBox() {
        return null;
    }

    @Override
    public void handleCollision(Collidable obj) {

    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public void drawImage(Graphics g) {

    }

    @Override
    public void update() {

    }
}
