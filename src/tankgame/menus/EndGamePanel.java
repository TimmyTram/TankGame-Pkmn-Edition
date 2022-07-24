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
    private JButton start;
    private JButton exit;
    private JLabel winner;
    private Launcher lf;

    public EndGamePanel(Launcher lf) {
        this.lf = lf;
        try {
            menuBackground = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResource("menu/title.png"),
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
        winner.setBounds(87, 300, GameConstants.END_MENU_SCREEN_WIDTH, 50);
        winner.setBackground(Color.black);
        winner.setForeground(Color.red);

        start = new JButton("Return to Start");
        start.setFont(new Font("Courier New", Font.BOLD, 12));
        start.setBounds(175, 375, 150, 50);
        start.setBackground(Color.black);
        start.setForeground(Color.red);
        start.setFocusPainted(false);
        start.addActionListener((actionEvent -> {
            this.lf.setFrame("start");
        }));


        exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(175, 450, 150, 50);
        exit.setBackground(Color.black);
        exit.setForeground(Color.red);
        exit.setFocusPainted(false);
        exit.addActionListener((actionEvent -> {
            this.lf.closeGame();
        }));

        this.add(winner);
        this.add(start);
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
