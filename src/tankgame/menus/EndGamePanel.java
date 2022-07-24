package tankgame.menus;

import tankgame.Launcher;
import tankgame.constants.GameConstants;
import tankgame.game.GameState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class EndGamePanel extends JPanel {
    private BufferedImage menuBackground;
    private JButton restart;
    private JButton mainMenu;
    private JButton exit;
    private JLabel winner;
    private Launcher lf;

    public EndGamePanel(Launcher lf) {
        this.lf = lf;
        try {
            menuBackground = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResource("menu/map_selection.png"),
                    String.format("Could not find %s", "menu/title.png"))
            );
        } catch (IOException e) {
            System.out.println("Error cant read menu background");
            e.printStackTrace();
            System.exit(-3);
        }
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        winner = new JLabel();
        winner.setFont(new Font("Courier New", Font.BOLD, 24));
        winner.setBounds(90, 275, GameConstants.END_MENU_SCREEN_WIDTH, 50);
        winner.setForeground(Color.WHITE);

        restart = new JButton("Restart");
        restart.setFont(new Font("Courier New", Font.BOLD, 24));
        restart.setBounds(175, 350, 175, 50);
        restart.setBackground(Color.black);
        restart.setForeground(Color.red);
        restart.setFocusPainted(false);
        restart.addActionListener((actionEvent -> {
            this.lf.setFrame("game");
        }));

        mainMenu = new JButton("Main Menu");
        mainMenu.setFont(new Font("Courier New", Font.BOLD, 24));
        mainMenu.setBounds(175, 400, 175, 50);
        mainMenu.setBackground(Color.black);
        mainMenu.setForeground(Color.red);
        mainMenu.setFocusPainted(false);
        mainMenu.addActionListener((actionEvent -> {
            this.lf.setFrame("start");
        }));


        exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(175, 450, 175, 50);
        exit.setBackground(Color.black);
        exit.setForeground(Color.red);
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

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
    }
}
