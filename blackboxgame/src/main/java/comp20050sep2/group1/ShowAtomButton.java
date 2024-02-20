package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ShowAtomButton extends JButton {

    boolean showState;
    int width = 150;
    int height = 40;

    public ShowAtomButton(Vector2D pos) {
        setLocation((int) pos.x-width/2, (int) pos.y-height/2);
        setSize(width, height);
        setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        setFocusable(false);
        setVisible(true);

        this.showState = false;
        toggleState();
    }

    public void toggleState() {
        if(showState) {
            setText("Hide True Atoms");
            setBackground(new Color(0.5F, 0.5F, 0.5F, 0.5F));
            setForeground(Color.black);
            setBorder(new LineBorder(Color.black));
        } else {
            setText("Show True Atoms");
            setBackground(new Color(0.0F, 0.F, 0.F, 0.5F));
            setForeground(Color.white);
            setBorder(new LineBorder(Color.white, 1));
        }
        showState = !showState;
    }
}
