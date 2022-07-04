package tankgame.game.walls;

import tankgame.game.Collidable;
import tankgame.game.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Wall extends GameObject implements Collidable {
    protected int width;
    protected int height;

    public Wall(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void drawImage(Graphics g) {
        g.drawImage(super.img, (int) x, (int) y, null);
    }

    @Override
    public void checkCollision(Collidable obj) {

    }

    protected void getImageDimension() {
        width = super.img.getWidth();
        height = super.img.getHeight();
    }

    public Rectangle getBounds() {
        return new Rectangle((int) super.x, (int) super.y, this.width, this.height);
    }

}
