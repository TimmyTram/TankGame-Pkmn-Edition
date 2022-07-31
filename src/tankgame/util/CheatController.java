package tankgame.util;

import tankgame.game.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CheatController implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyPressed = e.getKeyCode();
        switch(keyPressed) {
            case KeyEvent.VK_F1 -> {
                GameState.hitboxState = GameState.hitboxState.nextState();
                System.out.println(
                        GameState.hitboxState.getState() ?
                                "[CHEATS]: Activating Hitbox Rendering. . ." : "[CHEATS]: Deactivating Hitbox Rendering. . ."
                );
            }
            case KeyEvent.VK_F2 -> {
                GameState.oneShotOneKillState = GameState.oneShotOneKillState.nextState();
                System.out.println(
                        GameState.oneShotOneKillState.getState() ?
                                "[CHEATS]: Activating ONE SHOT ONE KILL MODE. . ." : "[CHEATS]: Deactivating ONE SHOT ONE KILL MODE . . ."
                        );
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
