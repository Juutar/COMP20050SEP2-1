package comp20050sep2.group1;

import javax.swing.JFrame;

public class GameWindow extends JFrame{
    
    GameWindow(GamePanel gamePanel){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(gamePanel);
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
        setVisible(true);
    }

}
