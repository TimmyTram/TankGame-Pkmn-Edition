package tankgame.game.powerups;

import tankgame.game.Collidable;
import tankgame.game.GameObject;
import tankgame.game.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PowerUp extends GameObject implements Collidable {

    protected int width;
    protected int height;

    public PowerUp(float x, float y, BufferedImage img) {
        super(x, y, img);
        getImageDimension();
    }

    public abstract void empower(Tank tank);

    @Override
    public void drawImage(Graphics g) {
        g.drawImage(super.img, (int) x, (int) y, null);
    }

    protected void getImageDimension() {
        this.width = super.img.getWidth();
        this.height = super.img.getHeight();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) super.x, (int) super.y, this.width, this.height);
    }

}
