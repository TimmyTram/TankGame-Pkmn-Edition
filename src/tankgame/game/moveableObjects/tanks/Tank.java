package tankgame.game.moveableObjects.tanks;

import tankgame.Sound;
import tankgame.constants.GameConstants;
import tankgame.constants.ResourceConstants;
import tankgame.ResourceHandler;
import tankgame.game.Collidable;
import tankgame.game.GameObject;
import tankgame.game.GameWorld;
import tankgame.game.stationaryObjects.powerups.PowerUp;
import tankgame.game.moveableObjects.projectiles.Bullet;
import tankgame.game.moveableObjects.MoveableObject;
import tankgame.game.moveableObjects.projectiles.Projectile;
import tankgame.game.stationaryObjects.walls.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Tank extends MoveableObject {

    private float R = 2;
    private final float ROTATIONSPEED = 2.25f;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;
    private int tick = 100;
    private int ticksTillNextShot = 100;
    private final int maxHealthPoints = 100;
    private int currentHealthPoints = 100;
    private final int damage = 10;
    private int lives = 4;
    private boolean isLoser = false;
    private final String name;
    private final GameWorld gw;
    private final ArrayList<int[]> validSpawnLocations;

    public Tank(float x, float y, float vx, float vy, float angle, BufferedImage img, String name, GameWorld gw) {
        super(x, y, 2, vx, vy, angle, img);
        this.name = name;
        this.gw = gw;
        this.validSpawnLocations = gw.getEmptySpaces();
    }

    public void setX(float x){ this.x = x; }

    public void setY(float y) { this. y = y;}

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.hitBox.setLocation((int) x, (int) y);
    }

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
        tick++;
        checkAlive();
        checkBorder();
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
        this.hitBox.setLocation((int) x, (int) y);
    }

    private void moveForwards() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation((int) x, (int) y);
    }

    private void shoot() {
        Bullet bullet = new Bullet(
                (float)(this.x + (Math.cos(Math.toRadians(angle))) + (this.img.getWidth() / 2.0)),
                (float)(this.y + (Math.sin(Math.toRadians(angle))) + (this.img.getHeight() / 2.0)),
                0,
                0,
                this.angle,
                4,
                ResourceHandler.getImage(ResourceConstants.IMAGES_BULLET_1),
                this
        );
        gw.addToMovableGameObjectCollections(bullet);
        (new Sound(ResourceHandler.getSound(ResourceConstants.SOUND_BULLET_1))).playSound();
    }

    private void checkAlive() {
        if(this.currentHealthPoints <= 0 && this.lives > 0) {
            this.lives -= 1;
            this.currentHealthPoints = this.maxHealthPoints;
            System.out.println(this.name + " LOST A LIFE. LIVES REMAINING: " + this.lives);
            this.randomizeSpawnLocation();
        } else if(this.currentHealthPoints <= 0) {
            this.isLoser = true;
        }
    }

    private void randomizeSpawnLocation() {
        int maxChoices = this.validSpawnLocations.size();
        int randomSelection = (new Random()).nextInt(maxChoices);
        int[] location = this.validSpawnLocations.get(randomSelection);
        this.setPosition(
                location[1] * ResourceConstants.FLOOR_TILE_DIMENSION,
                location[0] * ResourceConstants.FLOOR_TILE_DIMENSION
        );
    }

    public boolean getIsLoser() {
        return this.isLoser;
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
        int limitX = ResourceHandler.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL).getWidth();
        int limitY = ResourceHandler.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL).getHeight();
        if (x < limitX) {
            x = limitX;
        }
        if (x >= GameConstants.WORLD_WIDTH - limitX * 2.1) {
            x = (float) (GameConstants.WORLD_WIDTH - limitX * 2.1);
        }
        if (y < limitY) {
            y = limitY;
        }
        if (y >= GameConstants.WORLD_HEIGHT - limitY * 2) {
            y = GameConstants.WORLD_HEIGHT - limitY * 2;
        }
    }

    public int getCurrentHealthPoints() {
        return this.currentHealthPoints;
    }

    public int getMaxHealthPoints() {
        return this.maxHealthPoints;
    }

    public int getLives() {
        return this.lives;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox.getBounds();
    }

    @Override
    public void handleCollision(Collidable obj) {
        if(obj instanceof PowerUp) {
            ((PowerUp) obj).empower(this);
            ((PowerUp) obj).setDestroyed(true);
            ((PowerUp) obj).playSound();
        } else if(obj instanceof Projectile) {
            if(((Projectile) obj).getOwnership() != this) {
                ((Projectile) obj).setDestroyed(true);
                ((Projectile) obj).playSound();
                this.takeDamage();
            }
        } else if(obj instanceof Wall || obj instanceof Tank) {
            Rectangle intersection = this.hitBox.intersection(obj.getHitBox());
            if(intersection.height > intersection.width && this.x < intersection.x) { // left detection
                this.setPosition(this.x - (intersection.width / 2f), this.y);
            } else if(intersection.height > intersection.width && this.x > ((GameObject) obj).getX()) { // right detection
                this.setPosition(this.x + (intersection.width / 2f), this.y);
            } else if(intersection.height < intersection.width && this.y < intersection.y) { // top detection
                this.setPosition(this.x, this.y - (intersection.height / 2f));
            } else if(intersection.height < intersection.width && this.y > ((GameObject) obj).getY()) { // bottom detection
                this.setPosition(this.x, this.y + (intersection.height / 2f));
            }
        }
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

        g2d.setColor(Color.magenta);
        g2d.drawRect((int)(x) ,(int)(y),this.img.getWidth(), this.img.getHeight());

    }
}
