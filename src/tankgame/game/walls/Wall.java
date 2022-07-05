package tankgame.game.walls;

import tankgame.game.Collidable;
import tankgame.game.GameObject;
import tankgame.game.Projectile;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Wall extends GameObject implements Collidable {
    protected int width;
    protected int height;

    protected boolean destroyed = false;

    public Wall(float x, float y, BufferedImage img) {
        super(x, y, img);
        getImageDimension();
    }


    @Override
    public void drawImage(Graphics g) {
        g.drawImage(super.img, (int) x, (int) y, null);
        g.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
    }

    @Override
    public void checkCollision(Collidable obj) {

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

    public abstract void setDestroyed(boolean destroyed);

    public boolean getDestroyed() {
        return this.destroyed;
    }

}
