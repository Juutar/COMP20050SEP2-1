package comp20050sep2.group1;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JFrame{
    
    private CardLayout cardLayout;
    private JPanel cardPanel;
    
    GameWindow(GamePanel gamePanel){

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
