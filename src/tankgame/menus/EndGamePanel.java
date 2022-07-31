package tankgame.menus;

import tankgame.Launcher;
import tankgame.constants.GameConstants;
import tankgame.game.GameState;

import javax.swing.*;
import java.awt.*;


public class EndGamePanel extends MenuPanel {
    private JButton restart;
    private JButton mainMenu;
    private JButton exit;
    private JLabel winner;

    public EndGamePanel(Launcher lf) {
        super(lf, "menu.png");
        winner = new JLabel();
        winner.setFont(new Font("Courier New", Font.BOLD, 24));
        winner.setBounds(90, 275, GameConstants.END_MENU_SCREEN_WIDTH, 50);
        winner.setForeground(Color.WHITE);

        restart = super.createButton("Restart", 24, 175, 350, 175, 50, "game");
        mainMenu = super.createButton("Main Menu", 24, 175, 400, 175, 50, "start");

        exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(175, 450, 175, 50);
        exit.setBackground(Color.decode(GameConstants.BUTTON_BACKGROUND_COLOR));
        exit.setForeground(Color.decode(GameConstants.BUTTON_FOREGROUND_COLOR));
        exit.setFocusPainted(false);
        exit.addActionListener((actionEvent -> {
            this.lf.closeGame();
        }));

        this.add(winner);
        this.add(restart);
        this.add(mainMenu);
        this.add(exit);
    }

    public void updateWinnerStatus() {
        this.winner.setText("THE WINNER IS PLAYER " + GameState.PLAYER_WINNER + "!");
    }
}
