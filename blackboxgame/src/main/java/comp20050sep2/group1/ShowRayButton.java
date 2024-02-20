package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class ShowRayButton extends JButton{

    int width = 150;
    int height = 40;

    public ShowRayButton(Vector2D pos){
        setLocation((int) pos.x - width/2, (int) pos.y - height/2);
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
