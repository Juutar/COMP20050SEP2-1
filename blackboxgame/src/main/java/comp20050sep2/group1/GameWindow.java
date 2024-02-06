package comp20050sep2.group1;

import javax.swing.JFrame;

public class GameWindow extends JFrame{
    
    GameWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //add gamepanel
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
        setVisible(true);
    }
}
