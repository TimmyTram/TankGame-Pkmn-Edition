package tankgame.game;

public class GameState {

    public static int PLAYER_WINNER;
    public static HitboxState hitboxState = HitboxState.OFF;
    public static OneShotOneKillState oneShotOneKillState = OneShotOneKillState.OFF;

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

    public enum HitboxState {
        ON {
            @Override
            public HitboxState nextState() {
                return OFF;
            }

            @Override
            public boolean getState() {
                return true;
            }
        },

        OFF {
            @Override
            public HitboxState nextState() {
                return ON;
            }

            @Override
            public boolean getState() {
                return false;
            }
        };

        public abstract HitboxState nextState();
        public abstract boolean getState();
    }

    public enum OneShotOneKillState {
        ON {
            @Override
            public OneShotOneKillState nextState() {
                return OFF;
            }

            @Override
            public boolean getState() {
                return true;
            }
        },

        OFF {
            @Override
            public OneShotOneKillState nextState() {
                return ON;
            }

            @Override
            public boolean getState() {
                return false;
            }
        };

        public abstract OneShotOneKillState nextState();

        public abstract boolean getState();

    }

}
