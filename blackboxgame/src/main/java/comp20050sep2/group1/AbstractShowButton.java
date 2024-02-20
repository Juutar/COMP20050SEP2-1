package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public abstract class AbstractShowButton extends JButton {

    int width = 150;
    int height = 40;
    boolean stateShow;
    String showText;
    String hideText;

    Color backgroundColor = new Color(0.0F, 0.F, 0.F, 0.5F);
    Color altBackgroundColor = new Color(0.5F, 0.5F, 0.5F, 0.5F);
    Color foregroundColor = Color.white;
    Color altForegroundColor = Color.black;
    LineBorder border = new LineBorder(foregroundColor, 1);
    LineBorder altBorder = new LineBorder(altForegroundColor, 1);


    public AbstractShowButton(Vector2D pos){
        setLocation((int) pos.x - width/2, (int) pos.y - height/2);
        setSize(width, height);
        setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        setBorder(border);
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        setCustomTexts();
        setText(showText);
        stateShow = true;
        setFocusable(false);
        setVisible(true);
    }

    public abstract void setCustomTexts();

    public void toggleState() {
        if(stateShow) {
            setText(hideText);
            setBackground(altBackgroundColor);
            setForeground(altForegroundColor);
            setBorder(altBorder);
        } else {
            setText(showText);
            setBackground(backgroundColor);
            setForeground(foregroundColor);
            setBorder(border);
        }
        stateShow = !stateShow;
    }

}
