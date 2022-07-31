package tankgame.game.moveableObjects.tanks;

import tankgame.game.GameState;
import tankgame.util.Animation;
import tankgame.util.Sound;
import tankgame.constants.GameConstants;
import tankgame.constants.ResourceConstants;
import tankgame.game.loaders.ResourceLoader;
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
    private int damage = 10;
    private int lives = 4;
    private boolean isLoser = false;
    private int playerID;
    private final String name;
    private final GameWorld gw;
    private ArrayList<int[]> validSpawnLocations;
    private Animation animation;
    private String animationType;
    private BufferedImage bulletImage;
    private Sound bulletSpawnSound;
    private Sound bulletCollideSound;

    public Tank(float x, float y, float vx, float vy, float angle, BufferedImage img, int playerID, String name, GameWorld gw) {
        super(x, y, 2, vx, vy, angle, img);
        this.playerID = playerID;
        this.name = name;
        this.gw = gw;
        this.animation = this.initAnimation();
        this.animation.start();
        this.initBullet();
    }

    private Animation initAnimation() {
        if(this.playerID == 1) {
            this.animationType = "TRAINER";
            return new Animation(this.x, this.y, ResourceLoader.getAnimation(this.animationType + "_RIGHT"));
        }
        this.animationType = "POKEMON";
        return new Animation(this.x, this.y, ResourceLoader.getAnimation(this.animationType + "_RIGHT"));
    }

    private void initBullet() {
        switch(this.playerID) {
            case 1 -> {
                this.bulletImage = ResourceLoader.getImage(ResourceConstants.IMAGES_BULLET_TRAINER);
                this.bulletSpawnSound = new Sound(ResourceLoader.getSound(ResourceConstants.SOUND_BULLET_TRAINER));
                this.bulletCollideSound = new Sound(ResourceLoader.getSound(ResourceConstants.SOUND_BULLET_COLLIDE_TRAINER));
            }
            case 2 -> {
                this.bulletImage = ResourceLoader.getImage(ResourceConstants.IMAGES_BULLET_POKEMON);
                this.bulletSpawnSound = new Sound(ResourceLoader.getSound(ResourceConstants.SOUND_BULLET_POKEMON));
                this.bulletCollideSound = new Sound(ResourceLoader.getSound(ResourceConstants.SOUND_BULLET_COLLIDE_POKEMON));
            }
        }
    }

    public Sound getBulletCollideSound() {
        return this.bulletCollideSound;
    }

    public void setX(float x){ this.x = x; }

    public void setY(float y) { this. y = y;}

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.hitBox.setLocation((int) x, (int) y);
    }

    public void setValidSpawnLocations(ArrayList<int[]> validSpawnLocations) {
        this.validSpawnLocations = validSpawnLocations;
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
        this.animation.setX(this.x);
        this.animation.setY(this.y);
        this.animationHandler();
        tick++;
        checkOneShotOneKillMode();
        checkAlive();
        checkBorder();
    }

    private void animationHandler() {
        float unitCircleAngle = Math.abs(this.angle) % 360;
        if(this.angle > 0) {
            // if we rotated clockwise the angle would not match a unit circle, so we use this formula to help convert it.
            unitCircleAngle = Math.abs((this.angle % 360) - 360);
        }

        // Refer to the UNIT CIRCLE diagram to find the areas of a circle where a player should face.
        if((unitCircleAngle >= 315 && unitCircleAngle <= 360) || (unitCircleAngle <= 45 && unitCircleAngle >= 0)) {
            this.animation.setFrames(ResourceLoader.getAnimation(this.animationType + "_RIGHT"));
        } else if(unitCircleAngle >= 135 && unitCircleAngle <= 225) {
            this.animation.setFrames(ResourceLoader.getAnimation(this.animationType + "_LEFT"));
        } else if((unitCircleAngle >= 225 && unitCircleAngle <= 315)) {
            this.animation.setFrames(ResourceLoader.getAnimation(this.animationType + "_DOWN"));
        } else if((unitCircleAngle >= 45 && unitCircleAngle <= 135)) {
            this.animation.setFrames(ResourceLoader.getAnimation(this.animationType + "_UP"));
        }
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
                this.bulletImage,
                this
        );
        gw.addToMovableGameObjectCollections(bullet);
        this.bulletSpawnSound.playSound();
    }

    private void checkAlive() {
        if(this.currentHealthPoints <= 0 && this.lives > 0) {
            this.lives -= 1;
            this.currentHealthPoints = this.maxHealthPoints;
            System.out.println("[TANK]: " + this.name + " LOST A LIFE. LIVES REMAINING: " + this.lives);
            this.randomizeSpawnLocation();
        } else if(this.currentHealthPoints <= 0) {
            this.isLoser = true;
        }
    }

    private void randomizeSpawnLocation() {
        int maxChoices = this.validSpawnLocations.size();
        int randomSelection = (new Random()).nextInt(maxChoices);
        int[] location = this.validSpawnLocations.get(randomSelection);
        System.out.println("[TANK]: " + this.name + " respawned @ row: " + (location[0] + 1) + " col: " + (location[1] + 1));
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
        this.animation.setDelay((int) (this.animation.getInitialDelay() / (this.R / 2)));
    }

    public void changeDelayBetweenShots(int delay) {
        this.ticksTillNextShot = delay;
    }

    private void checkBorder() {
        int limitX = ResourceLoader.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL).getWidth();
        int limitY = ResourceLoader.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL).getHeight();
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

    public int getPlayerID() {
        return this.playerID;
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
        this.animation.drawImage(g2d);
        super.drawHitbox(g);
    }

    private void checkOneShotOneKillMode() {
        if(GameState.oneShotOneKillState == GameState.OneShotOneKillState.ON) {
            this.damage = 100;
            this.R = 8;
        } else if(GameState.oneShotOneKillState == GameState.OneShotOneKillState.OFF) {
            this.damage = 10;
            this.R = 4;
        }
    }

}
