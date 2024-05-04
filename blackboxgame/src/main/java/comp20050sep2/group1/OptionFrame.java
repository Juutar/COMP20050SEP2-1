package comp20050sep2.group1;

import javax.swing.JFrame;

public class OptionFrame extends JFrame{
    
    /**
     * Constructs option jframe
     */
    public OptionFrame(){
        addWindowListener(OptionsPanel.get());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Options");
        add(OptionsPanel.get());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

}
