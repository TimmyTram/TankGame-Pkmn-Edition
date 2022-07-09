package tankgame;

import tankgame.game.GameWorld;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class AudioPlayer {

    private AudioInputStream audioIn;
    private String audioFile;
    private Clip clip;

    public AudioPlayer(String audioFile) {
        this.audioFile = audioFile;
        try {
            audioIn = AudioSystem.getAudioInputStream(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("sounds/" + audioFile),
                    String.format("Could not find %s", audioFile)));
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }

    public void stop() {
        clip.stop();
    }

}
