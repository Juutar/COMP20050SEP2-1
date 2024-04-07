package comp20050sep2.group1;

import javax.swing.JFrame;

public class OptionFrame extends JFrame{
    
    public OptionFrame(){
        addWindowListener(OptionsPanel.get());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Demo game");
        add(OptionsPanel.get());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

}
