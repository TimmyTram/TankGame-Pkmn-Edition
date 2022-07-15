package tankgame.game;

import tankgame.GameConstants;
import tankgame.ResourceHandler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Tank extends MovableObject {

    private float R = 2;
    private float ROTATIONSPEED = 2.25f;

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;

    private boolean shootPressed;
    private int tick = 100;
    private int ticksTillNextShot = 100;

    private final GameCollections<Projectile> projectileGameCollections;

    private final int maxHealthPoints = 100;

    private int currentHealthPoints = 100;
    private final int damage = 10;

    private int lives = 3;


    Tank(float x, float y, float vx, float vy, float angle, BufferedImage img, GameCollections<Projectile> projectileGameCollections) {
        super(x, y, 2, vx, vy, angle, img);
        this.projectileGameCollections = projectileGameCollections;
    }

    void setX(float x){ this.x = x; }

    void setY(float y) { this. y = y;}

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void toggleShootPressed() {
        this.shootPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void unToggleShootPressed() {
        this.shootPressed = false;
    }

    @Override
    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }

        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }

        if (this.RightPressed) {
            this.rotateRight();
        }

        if(this.shootPressed && tick >= ticksTillNextShot) {
            this.shoot();
            tick = 0;
        }

        if(currentHealthPoints <= 0) {
            this.lives -= 1;
        }

        if(lives <= 0) {
            System.out.println("game over");
        }

        tick++;
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx =  Math.round(R * Math.cos(Math.toRadians(angle)));
        vy =  Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
    }

    private void moveForwards() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    private void shoot() {
        Bullet bullet = new Bullet(
                (float)(this.x + (Math.cos(Math.toRadians(angle))) + (this.img.getWidth() / 2.0)),
                (float)(this.y + (Math.sin(Math.toRadians(angle))) + (this.img.getHeight() / 2.0)),
                0,
                0,
                this.angle,
                GameConstants.BULLET_SPEED,
                ResourceHandler.getImage(GameConstants.RESOURCE_BULLET_1),
                this
        );
        this.projectileGameCollections.add(bullet);
        ResourceHandler.getSound(GameConstants.RESOURCE_BULLET_SOUND_1).play();
    }

    public void takeDamage() {
        this.currentHealthPoints -= this.damage;
    }

    public void heal(int amount) {
        this.currentHealthPoints += amount;
        this.currentHealthPoints = Math.min(currentHealthPoints, maxHealthPoints);
    }

    public void changeSpeed(float speed) {
        this.R = speed;
    }

    public void changeDelayBetweenShots(int delay) {
        this.ticksTillNextShot = delay;
    }

    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        //g2d.drawRect((int)(x + vx) ,(int)(y + vy),this.img.getWidth(), this.img.getHeight());
    }
}
