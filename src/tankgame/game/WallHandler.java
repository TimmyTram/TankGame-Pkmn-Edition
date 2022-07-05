package tankgame.game;

import tankgame.game.walls.Wall;

import java.awt.*;
import java.util.ArrayList;

public class WallHandler {

    private final ArrayList<Wall> walls = new ArrayList<>();

    public WallHandler() {}

    public void addWall(Wall wall) {
        if(wall == null) {
            return;
        }
        this.walls.add(wall);
    }

    public boolean destroyWall(Wall wall) {
        if(wall == null) {
            return false;
        }
        return this.walls.remove(wall);
    }

    public void drawWalls(Graphics g) {
        for(Wall wall : walls) {
            wall.drawImage(g);
        }
    }

    public int size() {
        return this.walls.size();
    }

    public Wall get(int index) {
        if(index > this.size()) {
            return null;
        }
        return this.walls.get(index);
    }

}
