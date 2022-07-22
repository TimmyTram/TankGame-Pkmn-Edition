package tankgame.game;

import java.awt.*;

public interface Collidable {
    Rectangle getHitBox();
    void handleCollision(Collidable obj);
    boolean isCollidable();

}
