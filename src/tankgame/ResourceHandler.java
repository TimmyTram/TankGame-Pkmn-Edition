package tankgame;

import tankgame.constants.ResourceConstants;
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
                ResourceHandler.images.put(ResourceConstants.RESOURCE_TANK_1, readImg("tanks/" + ResourceConstants.RESOURCE_TANK_1));
                ResourceHandler.images.put(ResourceConstants.RESOURCE_TANK_2, readImg("tanks/" + ResourceConstants.RESOURCE_TANK_2));
                ResourceHandler.images.put(ResourceConstants.RESOURCE_BULLET_1, readImg("bullet/" + ResourceConstants.RESOURCE_BULLET_1));
                ResourceHandler.images.put(ResourceConstants.RESOURCE_FLOOR_TILE, readImg("floor/" + ResourceConstants.RESOURCE_FLOOR_TILE));
                ResourceHandler.images.put(ResourceConstants.RESOURCE_UNBREAKABLE_WALL, readImg("walls/" + ResourceConstants.RESOURCE_UNBREAKABLE_WALL));
                ResourceHandler.images.put(ResourceConstants.RESOURCE_BREAKABLE_WALL, readImg("walls/" + ResourceConstants.RESOURCE_BREAKABLE_WALL));
                ResourceHandler.images.put(ResourceConstants.RESOURCE_BARRAGE, readImg("powerups/" + ResourceConstants.RESOURCE_BARRAGE));
                ResourceHandler.images.put(ResourceConstants.RESOURCE_HEAL, readImg("powerups/" + ResourceConstants.RESOURCE_HEAL));
                ResourceHandler.images.put(ResourceConstants.RESOURCE_SPEED, readImg("powerups/" + ResourceConstants.RESOURCE_SPEED));
                ResourceHandler.images.put(ResourceConstants.RESOURCE_MENU_TITLE, readImg("menu/" + ResourceConstants.RESOURCE_MENU_TITLE));
                ResourceHandler.images.put(ResourceConstants.RESOURCE_LIVES, readImg("lives/" + ResourceConstants.RESOURCE_LIVES));
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }

        public static void initSounds() {
            ResourceHandler.sounds.put(ResourceConstants.RESOURCE_BULLET_SOUND_1, new AudioPlayer(ResourceConstants.RESOURCE_BULLET_SOUND_1));
            ResourceHandler.sounds.put(ResourceConstants.RESOURCE_BULLET_SOUND_1_COLLIDE, new AudioPlayer(ResourceConstants.RESOURCE_BULLET_SOUND_1_COLLIDE));
            ResourceHandler.sounds.put(ResourceConstants.RESOURCE_BARRAGE_SOUND, new AudioPlayer(ResourceConstants.RESOURCE_BARRAGE_SOUND));
            ResourceHandler.sounds.put(ResourceConstants.RESOURCE_HEAL_SOUND, new AudioPlayer(ResourceConstants.RESOURCE_HEAL_SOUND));
            ResourceHandler.sounds.put(ResourceConstants.RESOURCE_SPEED_SOUND, new AudioPlayer(ResourceConstants.RESOURCE_SPEED_SOUND));
            ResourceHandler.sounds.put(ResourceConstants.RESOURCE_ROCK_SMASH_SOUND, new AudioPlayer(ResourceConstants.RESOURCE_ROCK_SMASH_SOUND));
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