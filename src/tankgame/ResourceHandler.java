package tankgame;

import tankgame.game.GameWorld;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ResourceHandler {
        private static Map<String, BufferedImage> images = new HashMap<>();
        private static Map<String, AudioPlayer> sounds = new HashMap<>();
        private static Map<String, List<BufferedImage>> animations = new HashMap<>();

        public static BufferedImage getImage(String key) {
            return ResourceHandler.images.get(key);
        }

        public static AudioPlayer getSound(String key) {
            return ResourceHandler.sounds.get(key);
        }



        public static List<BufferedImage> getAnimation(String key) {
            return ResourceHandler.animations.get(key);
        }

        public static void initImages() {
            try {
                ResourceHandler.images.put(GameConstants.RESOURCE_TANK_1, readImg("tanks/" + GameConstants.RESOURCE_TANK_1 + ".png"));
                ResourceHandler.images.put(GameConstants.RESOURCE_TANK_2, readImg("tanks/" + GameConstants.RESOURCE_TANK_2 + ".png"));
                ResourceHandler.images.put(GameConstants.RESOURCE_BULLET_1, readImg("bullet/" + GameConstants.RESOURCE_BULLET_1 + ".png"));
                ResourceHandler.images.put(GameConstants.RESOURCE_FLOOR_TILE, readImg("floor/" + GameConstants.RESOURCE_FLOOR_TILE + ".png"));
                ResourceHandler.images.put(GameConstants.RESOURCE_UNBREAKABLE_WALL, readImg("walls/" + GameConstants.RESOURCE_UNBREAKABLE_WALL + ".png"));
                ResourceHandler.images.put(GameConstants.RESOURCE_BREAKABLE_WALL, readImg("walls/" + GameConstants.RESOURCE_BREAKABLE_WALL + ".png"));
                ResourceHandler.images.put(GameConstants.RESOURCE_BARRAGE, readImg("powerups/" + GameConstants.RESOURCE_BARRAGE + ".png"));
                ResourceHandler.images.put(GameConstants.RESOURCE_HEAL, readImg("powerups/" + GameConstants.RESOURCE_HEAL + ".png"));
                ResourceHandler.images.put(GameConstants.RESOURCE_SPEED, readImg("powerups/" + GameConstants.RESOURCE_SPEED + ".png"));
                ResourceHandler.images.put(GameConstants.RESOURCE_MENU_TITLE, readImg("menu/" + GameConstants.RESOURCE_MENU_TITLE + ".png"));
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }

        public static void initSounds() {
            ResourceHandler.sounds.put(GameConstants.RESOURCE_BULLET_SOUND_1, new AudioPlayer(GameConstants.RESOURCE_BULLET_SOUND_1));
            ResourceHandler.sounds.put(GameConstants.RESOURCE_BULLET_SOUND_1_COLLIDE, new AudioPlayer(GameConstants.RESOURCE_BULLET_SOUND_1_COLLIDE));
            ResourceHandler.sounds.put(GameConstants.RESOURCE_BARRAGE_SOUND, new AudioPlayer(GameConstants.RESOURCE_BARRAGE_SOUND));
            ResourceHandler.sounds.put(GameConstants.RESOURCE_HEAL_SOUND, new AudioPlayer(GameConstants.RESOURCE_HEAL_SOUND));
            ResourceHandler.sounds.put(GameConstants.RESOURCE_SPEED_SOUND, new AudioPlayer(GameConstants.RESOURCE_SPEED_SOUND));
            ResourceHandler.sounds.put(GameConstants.RESOURCE_ROCK_SMASH_SOUND, new AudioPlayer(GameConstants.RESOURCE_ROCK_SMASH_SOUND));
        }

        public static void initAnimations() {

        }

        private static BufferedImage readImg(String resource) throws IOException {
            return ImageIO.read(
                    Objects.requireNonNull(GameWorld.class.getClassLoader().getResource(resource),
                            String.format("Could not find %s", resource))
            );
        }
}