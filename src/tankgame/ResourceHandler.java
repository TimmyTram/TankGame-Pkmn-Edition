package tankgame;

import tankgame.game.GameWorld;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ResourceHandler {

    private static ResourceHandler resourceHandler = null;
    public static BufferedImage t1img;
    public static BufferedImage t2img;
    public static BufferedImage bulletImg;

    public static BufferedImage breakableWallImg;

    public static BufferedImage unbreakableWallImg;

    private ResourceHandler() {}

    public static ResourceHandler getInstance() {
        if(resourceHandler == null)
            resourceHandler = new ResourceHandler();
        return resourceHandler;
    }

    public void initializeResources(String t1, String t2, String bullet, String breakableWall, String unbreakableWall) {
        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory. When running a jar, class loaders will read from withing the jar.
             */
            t1img = ImageIO.read(
                    Objects.requireNonNull(GameWorld.class.getClassLoader().getResource(t1),
                            String.format("Could not find %s", t1))
            );

            t2img = ImageIO.read(
                    Objects.requireNonNull(GameWorld.class.getClassLoader().getResource(t2),
                            String.format("Could not find %s", t2))
            );

            bulletImg = ImageIO.read(
                    Objects.requireNonNull(GameWorld.class.getClassLoader().getResource(bullet),
                            String.format("Could not find %s", bullet))
            );

            breakableWallImg = ImageIO.read(
                    Objects.requireNonNull(GameWorld.class.getClassLoader().getResource(breakableWall),
                            String.format("Could not find %s", breakableWall))
            );

            unbreakableWallImg = ImageIO.read(
                    Objects.requireNonNull(GameWorld.class.getClassLoader().getResource(unbreakableWall),
                            String.format("Could not find %s", unbreakableWall))
            );

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

}
