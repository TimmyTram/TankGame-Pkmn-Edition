package tankgame.game.walls;

import tankgame.game.Collidable;
import tankgame.game.StationaryObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Wall extends StationaryObject implements Collidable {
    protected boolean destroyed = false;

    public Wall(float x, float y, BufferedImage img) {
        super(x, y, img);
    }


    @Override
    public void drawImage(Graphics g) {
        g.drawImage(super.img, (int) x, (int) y, null);

        g.setColor(Color.red);
        g.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
    }

    @Override
    public void update() {}

    @Override
    public Rectangle getHitBox() {
        return this.hitBox.getBounds();
    }

    @Override
    public void handleCollision(Collidable obj) {

    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    public abstract void setDestroyed(boolean destroyed);

    public boolean getDestroyed() {
        return this.destroyed;
    }

}
