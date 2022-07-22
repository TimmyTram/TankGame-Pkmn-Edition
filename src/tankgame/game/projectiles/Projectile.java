package tankgame.game.projectiles;

import tankgame.GameConstants;
import tankgame.game.Collidable;
import tankgame.game.MovableObject;
import tankgame.game.tanks.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Projectile extends MovableObject {

    private final Tank ownership;
    private boolean toBeDestroyed = false;

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
    }

    private void checkBorder() {
        if(x >= GameConstants.WORLD_WIDTH || x < 0 || y < 0 || y >= GameConstants.WORLD_HEIGHT) {
            this.toBeDestroyed = true;
        }
    }

    public Tank getOwnership() {
        return this.ownership;
    }

    public void setToBeDestroyed(boolean status) {
        this.toBeDestroyed = status;
    }

    public boolean getToBeDestroyed() {
        return this.toBeDestroyed;
    }

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

}
