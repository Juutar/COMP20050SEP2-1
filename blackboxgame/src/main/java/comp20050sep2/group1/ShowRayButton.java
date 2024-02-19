package comp20050sep2.group1;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class ShowRayButton extends JButton{
    
    public ShowRayButton(){
        
        setSize(150, 40);
        setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        setFocusable(false);
        setVisible(true);

        setText("Set Rays");
        setBackground(new Color(0.0F, 0.F, 0.F, 0.5F));
        setForeground(Color.white);
        setBorder(new LineBorder(Color.white, 1));

    }

}
