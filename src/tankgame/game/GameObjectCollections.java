package tankgame.game;

import java.awt.*;
import java.util.ArrayList;

public class GameObjectCollections<T extends GameObject> {

    private final ArrayList<T> gameObjects = new ArrayList<>();

    public GameObjectCollections() {}

    public void update() {
        for(T gameObject : gameObjects) {
            gameObject.update();
        }
    }

    public void draw(Graphics g) {
        for(T gameObject : gameObjects) {
            gameObject.drawImage(g);
        }
    }

    public boolean add(T gameObject) {
        if(gameObject == null) {
            return false;
        }
        return gameObjects.add(gameObject);
    }

    public boolean remove(T gameObject) {
        if(gameObject == null) {
            return false;
        }
        return gameObjects.remove(gameObject);
    }


    public int size() {
        return this.gameObjects.size();
    }

    public T get(int index) {
        if(index > this.size()) {
            return null;
        }
        return this.gameObjects.get(index);
    }

    public int indexOf(T gameObject) {
        if(gameObject == null) {
            return -1;
        }
        return this.gameObjects.indexOf(gameObject);
    }

    public void clear() {
        this.gameObjects.clear();
    }

}
