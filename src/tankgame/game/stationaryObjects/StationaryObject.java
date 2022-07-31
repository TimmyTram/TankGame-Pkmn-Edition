package tankgame.game.stationaryObjects;

import tankgame.constants.GameConstants;
import tankgame.game.Collidable;
import tankgame.game.GameObject;
import tankgame.game.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class StationaryObject extends GameObject implements Collidable {

    protected boolean isDestroyed = false;

    public StationaryObject(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void handleCollision(Collidable obj) {}

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public void drawImage(Graphics g) {}

    @Override
    public void drawHitbox(Graphics g) {
        if(GameState.hitboxState == GameState.HitboxState.ON) {
            g.setColor(Color.blue);
            g.drawRect((int)(x) ,(int)(y),this.img.getWidth(), this.img.getHeight());
        }
    }

    @Override
    public void update() {}

    public boolean getIsDestroyed() {
        return this.isDestroyed;
    }

    public void setDestroyed(boolean status) {
        this.isDestroyed = status;
    }

}
