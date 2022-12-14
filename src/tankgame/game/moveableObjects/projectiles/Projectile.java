package tankgame.game.moveableObjects.projectiles;

import tankgame.constants.GameConstants;
import tankgame.constants.ResourceConstants;
import tankgame.game.loaders.ResourceLoader;
import tankgame.game.Collidable;
import tankgame.game.moveableObjects.MoveableObject;
import tankgame.game.moveableObjects.tanks.Tank;
import tankgame.game.stationaryObjects.walls.BreakableWall;
import tankgame.game.stationaryObjects.walls.Wall;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Projectile extends MoveableObject {

    protected final Tank ownership;
    protected boolean isDestroyed = false;

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
        int limitX = ResourceLoader.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL).getWidth();
        int limitY = ResourceLoader.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL).getHeight();
        if(x >= GameConstants.WORLD_WIDTH - limitX || x < limitX || y < limitY || y >= GameConstants.WORLD_HEIGHT - limitY) {
            this.isDestroyed = true;
            this.playSound();
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
    public void handleCollision(Collidable obj) {
        if(obj instanceof Wall) {
            this.isDestroyed = true;
            if(obj instanceof BreakableWall) {
                ((BreakableWall) obj).setDestroyed(true);
                ((BreakableWall) obj).playSound();
            }
            this.playSound();
        }
        if(obj instanceof Projectile && ((Projectile) obj).getOwnership() != this.ownership) {
            this.setDestroyed(true);
            this.playSound();
        }
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

}
