package tankgame.game.loaders;

import tankgame.constants.ResourceConstants;
import tankgame.game.GameWorld;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class ResourceLoader {
        private static final Map<String, BufferedImage> images = new HashMap<>();
        private static final Map<String, Clip> sounds = new HashMap<>();
        private static final Map<String, List<BufferedImage>> animations = new HashMap<>();

        private static final ArrayList<String> gameMaps = new ArrayList<>();

        public static BufferedImage getImage(String key) {
            return ResourceLoader.images.get(key);
        }

        public static Clip getSound(String key) {
            return ResourceLoader.sounds.get(key);
        }

        public static List<BufferedImage> getAnimation(String key) {
            return ResourceLoader.animations.get(key);
        }

        public static String getGameMap(String key) {
            if(gameMaps.contains(key)) {
                return gameMaps.get(gameMaps.indexOf(key));
            }
            return null;
        }

        public static String getGameMap(int index) {
            return gameMaps.get(index);
        }

        public static int getNumberOfMaps() {
            return gameMaps.size();
        }

        public static void initImages() {
            try {
                ResourceLoader.images.put(ResourceConstants.IMAGES_BULLET_TRAINER, readImg("bullet/" + ResourceConstants.IMAGES_BULLET_TRAINER));
                ResourceLoader.images.put(ResourceConstants.IMAGES_BULLET_POKEMON, readImg("bullet/" + ResourceConstants.IMAGES_BULLET_POKEMON));
                ResourceLoader.images.put(ResourceConstants.IMAGES_FLOOR_TILE, readImg("floor/" + ResourceConstants.IMAGES_FLOOR_TILE));
                ResourceLoader.images.put(ResourceConstants.IMAGES_UNBREAKABLE_WALL, readImg("walls/" + ResourceConstants.IMAGES_UNBREAKABLE_WALL));
                ResourceLoader.images.put(ResourceConstants.IMAGES_BREAKABLE_WALL, readImg("walls/" + ResourceConstants.IMAGES_BREAKABLE_WALL));
                ResourceLoader.images.put(ResourceConstants.IMAGES_BARRAGE, readImg("powerups/" + ResourceConstants.IMAGES_BARRAGE));
                ResourceLoader.images.put(ResourceConstants.IMAGES_HEAL, readImg("powerups/" + ResourceConstants.IMAGES_HEAL));
                ResourceLoader.images.put(ResourceConstants.IMAGES_SPEED, readImg("powerups/" + ResourceConstants.IMAGES_SPEED));
                ResourceLoader.images.put(ResourceConstants.IMAGES_MENU_TITLE, readImg("menu/" + ResourceConstants.IMAGES_MENU_TITLE));
                ResourceLoader.images.put(ResourceConstants.IMAGES_LIVES, readImg("lives/" + ResourceConstants.IMAGES_LIVES));
                ResourceLoader.images.put(ResourceConstants.IMAGES_HUD_1, readImg("hud/" + ResourceConstants.IMAGES_HUD_1));
                ResourceLoader.images.put(ResourceConstants.IMAGES_HUD_2, readImg("hud/" + ResourceConstants.IMAGES_HUD_2));
                ResourceLoader.images.put(ResourceConstants.IMAGES_TANK_ARROW, readImg("tanks/" + ResourceConstants.IMAGES_TANK_ARROW));
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }

        public static void initSounds() {
            try {
                AudioInputStream as;
                Clip clip;

                as = readAudio("sounds/" + ResourceConstants.SOUND_BULLET_TRAINER);
                clip = AudioSystem.getClip();
                clip.open(as);
                ResourceLoader.sounds.put(ResourceConstants.SOUND_BULLET_TRAINER, clip);

                as = readAudio("sounds/" + ResourceConstants.SOUND_BULLET_COLLIDE_TRAINER);
                clip = AudioSystem.getClip();
                clip.open(as);
                ResourceLoader.sounds.put(ResourceConstants.SOUND_BULLET_COLLIDE_TRAINER, clip);

                as = readAudio("sounds/" + ResourceConstants.SOUND_BULLET_POKEMON);
                clip = AudioSystem.getClip();
                clip.open(as);
                ResourceLoader.sounds.put(ResourceConstants.SOUND_BULLET_POKEMON, clip);

                as = readAudio("sounds/" + ResourceConstants.SOUND_BULLET_COLLIDE_POKEMON);
                clip = AudioSystem.getClip();
                clip.open(as);
                ResourceLoader.sounds.put(ResourceConstants.SOUND_BULLET_COLLIDE_POKEMON, clip);

                as = readAudio("sounds/" + ResourceConstants.SOUND_BARRAGE);
                clip = AudioSystem.getClip();
                clip.open(as);
                ResourceLoader.sounds.put(ResourceConstants.SOUND_BARRAGE, clip);

                as = readAudio("sounds/" + ResourceConstants.SOUND_HEAL);
                clip = AudioSystem.getClip();
                clip.open(as);
                ResourceLoader.sounds.put(ResourceConstants.SOUND_HEAL, clip);

                as = readAudio("sounds/" + ResourceConstants.SOUND_SPEED);
                clip = AudioSystem.getClip();
                clip.open(as);
                ResourceLoader.sounds.put(ResourceConstants.SOUND_SPEED, clip);

                as = readAudio("sounds/" + ResourceConstants.SOUND_ROCK_SMASH);
                clip = AudioSystem.getClip();
                clip.open(as);
                ResourceLoader.sounds.put(ResourceConstants.SOUND_ROCK_SMASH, clip);

                as = readAudio("music/" + ResourceConstants.SOUND_MUSIC_DRIFTVEIL_CITY);
                clip = AudioSystem.getClip();
                clip.open(as);
                ResourceLoader.sounds.put(ResourceConstants.SOUND_MUSIC_DRIFTVEIL_CITY, clip);

            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
                e.printStackTrace();
                System.exit(-2);
            }
        }

        public static void initAnimations() {
            try {
                String basename = "dawn_%d";
                ResourceLoader.animations.put(
                        "TRAINER_RIGHT",
                        readAnimations(4, basename, "animations/trainer/right/", ".png")
                );
                ResourceLoader.animations.put(
                        "TRAINER_LEFT",
                        readAnimations(4, basename, "animations/trainer/left/", ".png")
                );
                ResourceLoader.animations.put(
                        "TRAINER_UP",
                        readAnimations(4, basename, "animations/trainer/up/", ".png")
                );
                ResourceLoader.animations.put(
                        "TRAINER_DOWN",
                        readAnimations(4, basename, "animations/trainer/down/", ".png")
                );
                basename = "bidoof_%d";
                ResourceLoader.animations.put(
                        "POKEMON_RIGHT",
                        readAnimations(2, basename, "animations/pokemon/right/", ".png")
                );
                ResourceLoader.animations.put(
                        "POKEMON_LEFT",
                        readAnimations(2, basename, "animations/pokemon/left/", ".png")
                );
                ResourceLoader.animations.put(
                        "POKEMON_UP",
                        readAnimations(2, basename, "animations/pokemon/up/", ".png")
                );
                ResourceLoader.animations.put(
                        "POKEMON_DOWN",
                        readAnimations(2, basename, "animations/pokemon/down/", ".png")
                );
            } catch (IOException e) {
                System.out.println(e);
                System.exit(-2);
            }
        }

        public static void initMaps() {
            ResourceLoader.gameMaps.add(ResourceConstants.MAP_2FORT);
            ResourceLoader.gameMaps.add(ResourceConstants.MAP_PILLAR);
            ResourceLoader.gameMaps.add(ResourceConstants.MAP_TUNNELS);
        }

        private static BufferedImage readImg(String resource) throws IOException {
            return ImageIO.read(
                    Objects.requireNonNull(GameWorld.class.getClassLoader().getResource(resource),
                            String.format("Could not find %s", resource))
            );
        }

        private static AudioInputStream readAudio(String resource) throws UnsupportedAudioFileException, IOException {
            return AudioSystem.getAudioInputStream(Objects.requireNonNull(ResourceLoader.class.getClassLoader().getResource(resource),
                    String.format("Could not find %s", resource)));
        }

        private static ArrayList<BufferedImage> readAnimations(int length, String basename, String path, String extension) throws IOException {
            ArrayList<BufferedImage> result = new ArrayList<>();
            for(int i = 1; i <= length; i++) {
                String fname = String.format(basename, i);
                String fullPath = path + fname + extension;
                result.add(readImg(fullPath));
            }
            return result;
        }

}