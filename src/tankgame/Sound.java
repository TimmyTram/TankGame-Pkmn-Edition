package tankgame;

import javax.sound.sampled.Clip;

public class Sound implements Runnable {
    private final Clip clip;

    public Sound(Clip clip) {
        this.clip = clip;
    }

    public void playSound() {
        if(clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.start();
    }

    public void stopSound() {
        if(clip.isRunning()) {
            clip.stop();
        }
    }

    @Override
    public void run() {
        //clip.loop(Clip.LOOP_CONTINUOUSLY);
        this.playSound();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
