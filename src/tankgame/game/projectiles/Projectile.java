package tankgame.game.projectiles;

import tankgame.GameConstants;
import tankgame.ResourceConstants;
import tankgame.ResourceHandler;
import tankgame.game.Collidable;
import tankgame.game.MovableObject;
import tankgame.game.tanks.Tank;
import tankgame.game.walls.BreakableWall;
import tankgame.game.walls.Wall;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Projectile extends MovableObject {

    private final Tank ownership;
    private boolean isDestroyed = false;

    public Projectile(float x, float y, float vx, float vy, float angle, float R, BufferedImage img, Tank ownership) {
        super(x, y, vx, vy, angle, R, img);
        this.ownership = ownership;
    }

    @Override
    public void update() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation((int) x, (int) y);
    }

    private void checkBorder() {
        int limitX = ResourceHandler.getImage(ResourceConstants.RESOURCE_UNBREAKABLE_WALL).getWidth();
        int limitY = ResourceHandler.getImage(ResourceConstants.RESOURCE_UNBREAKABLE_WALL).getHeight();
        if(x >= GameConstants.WORLD_WIDTH - limitX || x < limitX || y < limitY || y >= GameConstants.WORLD_HEIGHT - limitY) {
            this.isDestroyed = true;
        }
    }

    public Tank getOwnership() {
        return this.ownership;
    }

    public void setDestroyed(boolean status) {
        this.isDestroyed = status;
    }

    public abstract void playSound();

    public boolean getIsDestroyed() {
        return this.isDestroyed;
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox.getBounds();
    }

    @Override
    public void handleCollision(Collidable obj) {
        if(obj instanceof Wall) {
            this.isDestroyed = true;
            if(obj instanceof BreakableWall) {
                ((BreakableWall) obj).setDestroyed(true);
                ((BreakableWall) obj).playSound();
            }
            this.playSound();
        }
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

}
