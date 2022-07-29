package tankgame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Animation extends Thread {

    private float x, y;
    private final int initialDelay = 120;
    private int currentDelay;
    private List<BufferedImage> frames;
    private boolean isRunning;
    private int currentFrame = 0;

    public Animation(float x, float y, List<BufferedImage> frames) {
        this.x = x;
        this.y = y;
        this.frames = frames;
        this.currentDelay = this.initialDelay;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void setFrames(List<BufferedImage> frames) {
        this.frames = frames;
    }

    public void setDelay(int delay) {
        this.currentDelay = delay;
    }

    public int getInitialDelay() {
        return this.initialDelay;
    }

    public void setX (float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void drawImage(Graphics2D g2) {
        if(this.isRunning) {
            g2.drawImage(this.frames.get(this.currentFrame), (int) this.x, (int) this.y, null);
        }
    }

    @Override
    public void run() {
        this.isRunning = true;
        try {
            while(this.isRunning) {
                this.currentFrame = (this.currentFrame + 1) % this.frames.size();
//                if(this.currentFrame == this.frames.size() - 1) {
//                    this.isRunning = false;
//                }
                Thread.sleep(currentDelay);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
