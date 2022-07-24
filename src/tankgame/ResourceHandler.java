package tankgame;

import tankgame.constants.ResourceConstants;
import tankgame.game.GameWorld;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class ResourceHandler {
        private static final Map<String, BufferedImage> images = new HashMap<>();
        private static final Map<String, Clip> sounds = new HashMap<>();
        private static final Map<String, List<BufferedImage>> animations = new HashMap<>();

        private static final ArrayList<String> gameMaps = new ArrayList<>();

        public static BufferedImage getImage(String key) {
            return ResourceHandler.images.get(key);
        }

        public static Clip getSound(String key) {
            return ResourceHandler.sounds.get(key);
        }

        public static List<BufferedImage> getAnimation(String key) {
            return ResourceHandler.animations.get(key);
        }

        public static String getGameMaps(String key) {
            if(gameMaps.contains(key)) {
                return gameMaps.get(gameMaps.indexOf(key));
            }
            return null;
        }

        public static String getRandomMap() {
            int maxChoices = gameMaps.size();
            int randomSelection = (new Random()).nextInt(maxChoices);
            return gameMaps.get(randomSelection);
        }

        public static void initImages() {
            try {
                ResourceHandler.images.put(ResourceConstants.IMAGES_TANK_1, readImg("tanks/" + ResourceConstants.IMAGES_TANK_1));
                ResourceHandler.images.put(ResourceConstants.IMAGES_TANK_2, readImg("tanks/" + ResourceConstants.IMAGES_TANK_2));
                ResourceHandler.images.put(ResourceConstants.IMAGES_BULLET_1, readImg("bullet/" + ResourceConstants.IMAGES_BULLET_1));
                ResourceHandler.images.put(ResourceConstants.IMAGES_FLOOR_TILE, readImg("floor/" + ResourceConstants.IMAGES_FLOOR_TILE));
                ResourceHandler.images.put(ResourceConstants.IMAGES_UNBREAKABLE_WALL, readImg("walls/" + ResourceConstants.IMAGES_UNBREAKABLE_WALL));
                ResourceHandler.images.put(ResourceConstants.IMAGES_BREAKABLE_WALL, readImg("walls/" + ResourceConstants.IMAGES_BREAKABLE_WALL));
                ResourceHandler.images.put(ResourceConstants.IMAGES_BARRAGE, readImg("powerups/" + ResourceConstants.IMAGES_BARRAGE));
                ResourceHandler.images.put(ResourceConstants.IMAGES_HEAL, readImg("powerups/" + ResourceConstants.IMAGES_HEAL));
                ResourceHandler.images.put(ResourceConstants.IMAGES_SPEED, readImg("powerups/" + ResourceConstants.IMAGES_SPEED));
                ResourceHandler.images.put(ResourceConstants.IMAGES_MENU_TITLE, readImg("menu/" + ResourceConstants.IMAGES_MENU_TITLE));
                ResourceHandler.images.put(ResourceConstants.IMAGES_LIVES, readImg("lives/" + ResourceConstants.IMAGES_LIVES));
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }

        public static void initSounds() {
            try {
                AudioInputStream as;
                Clip clip;

                as = readAudio("sounds/" + ResourceConstants.SOUND_BULLET_1);
                clip = AudioSystem.getClip();
                clip.open(as);
                ResourceHandler.sounds.put(ResourceConstants.SOUND_BULLET_1, clip);

                as = readAudio("sounds/" + ResourceConstants.SOUND_BULLET_COLLIDE_1);
                clip = AudioSystem.getClip();
                clip.open(as);
                ResourceHandler.sounds.put(ResourceConstants.SOUND_BULLET_COLLIDE_1, clip);

                as = readAudio("sounds/" + ResourceConstants.SOUND_BARRAGE);
                clip = AudioSystem.getClip();
                clip.open(as);
                ResourceHandler.sounds.put(ResourceConstants.SOUND_BARRAGE, clip);

                as = readAudio("sounds/" + ResourceConstants.SOUND_HEAL);
                clip = AudioSystem.getClip();
                clip.open(as);
                ResourceHandler.sounds.put(ResourceConstants.SOUND_HEAL, clip);

                as = readAudio("sounds/" + ResourceConstants.SOUND_SPEED);
                clip = AudioSystem.getClip();
                clip.open(as);
                ResourceHandler.sounds.put(ResourceConstants.SOUND_SPEED, clip);

                as = readAudio("sounds/" + ResourceConstants.SOUND_ROCK_SMASH);
                clip = AudioSystem.getClip();
                clip.open(as);
                ResourceHandler.sounds.put(ResourceConstants.SOUND_ROCK_SMASH, clip);

                as = readAudio("music/" + ResourceConstants.SOUND_MUSIC_DRIFTVEIL_CITY);
                clip = AudioSystem.getClip();
                clip.open(as);
                ResourceHandler.sounds.put(ResourceConstants.SOUND_MUSIC_DRIFTVEIL_CITY, clip);

            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
                e.printStackTrace();
                System.exit(-2);
            }
        }

        public static void initAnimations() {

        }

        public static void initMaps() {
            ResourceHandler.gameMaps.add(ResourceConstants.MAP_DEBUG);
            ResourceHandler.gameMaps.add(ResourceConstants.MAP_2FORT);
        }

        private static BufferedImage readImg(String resource) throws IOException {
            return ImageIO.read(
                    Objects.requireNonNull(GameWorld.class.getClassLoader().getResource(resource),
                            String.format("Could not find %s", resource))
            );
        }

        private static AudioInputStream readAudio(String resource) throws UnsupportedAudioFileException, IOException {
            return AudioSystem.getAudioInputStream(Objects.requireNonNull(ResourceHandler.class.getClassLoader().getResource(resource),
                    String.format("Could not find %s", resource)));
        }
}