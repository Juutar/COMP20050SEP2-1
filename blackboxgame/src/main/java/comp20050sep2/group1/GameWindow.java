package comp20050sep2.group1;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    GameWindow(GamePanel gamePanel) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        cardPanel.add(gamePanel, "Game Panel");

        add(cardPanel);

        setLocationRelativeTo(null);
        setResizable(false);
        pack();
        setVisible(true);
    }

}
