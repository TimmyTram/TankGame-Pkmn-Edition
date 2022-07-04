package tankgame;

import tankgame.game.GameWorld;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ResourceHandler {

    private static ResourceHandler resourceHandler = null;
    private BufferedImage t1img;
    private BufferedImage t2img;
    private BufferedImage bulletImg;

    private BufferedImage breakableWallImg;

    private BufferedImage unbreakableWallImg;

    private BufferedImage spreadImg;

    private BufferedImage barrageImg;

    private BufferedImage speedImg;

    private ResourceHandler() {}

    public static ResourceHandler getInstance() {
        if(resourceHandler == null)
            resourceHandler = new ResourceHandler();
        return resourceHandler;
    }

    public void initializeResources(
            String t1,
            String t2,
            String bullet,
            String breakableWall,
            String unbreakableWall,
            String spread,
            String barrage,
            String speed
    ) {
        t1img = readImg(t1);
        t2img = readImg(t2);
        bulletImg = readImg(bullet);
        breakableWallImg = readImg(breakableWall);
        unbreakableWallImg = readImg(unbreakableWall);
        spreadImg = readImg(spread);
        barrageImg = readImg(barrage);
        speedImg = readImg(speed);
    }

    private BufferedImage readImg(String resource) {
        try {
            return ImageIO.read(
                    Objects.requireNonNull(GameWorld.class.getClassLoader().getResource(resource),
                            String.format("Could not find %s", resource))
            );
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public BufferedImage getT1img() {
        return t1img;
    }

    public BufferedImage getT2img() {
        return t2img;
    }

    public BufferedImage getBulletImg() {
        return bulletImg;
    }

    public BufferedImage getBreakableWallImg() {
        return breakableWallImg;
    }

    public BufferedImage getUnbreakableWallImg() {
        return unbreakableWallImg;
    }

    public BufferedImage getSpreadImg() {
        return spreadImg;
    }

    public BufferedImage getBarrageImg() {
        return barrageImg;
    }

    public BufferedImage getSpeedImg() {
        return speedImg;
    }
}
