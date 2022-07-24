package tankgame.game;

public class GameState {

    public static int PLAYER_WINNER;

    public enum RunningState {

        RUNNING {
            @Override
            public RunningState nextState() {
                return STOPPED;
            }

            @Override
            public boolean getState() {
                return true;
            }
        },

        STOPPED {
            @Override
            public RunningState nextState() {
                return RUNNING;
            }

            @Override
            public boolean getState() {
                return false;
            }
        };

        public abstract RunningState nextState();
        public abstract boolean getState();
    }

}
