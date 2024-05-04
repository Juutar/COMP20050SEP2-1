package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public abstract class AbstractButton extends JButton {

    Vector2D pos;
    final static int width = 150;
    final static int height = 40;
    String text;

    Color backgroundColor = new Color(0.0F, 0.F, 0.F, 0.5F);
    Color foregroundColor = Color.white;
    LineBorder border = new LineBorder(foregroundColor, 1);

    /**
     * @param pos Position of the button
     * @param text Text of the button
     */
    public AbstractButton(Vector2D pos, String text) {
        this.pos = pos;
        setLocation((int) pos.x - width / 2, (int) pos.y - height / 2);
        setSize(width, height);
        setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        setBorder(border);
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        this.text = text;
        setText(text);
        setFocusable(false);
        setVisible(true);
    }

    /**
     * Set the button text
     * @param text String to set text
     */
    public void setButtonText(String text) {
        this.text = text;
        setText(text);
    }

    public String getText() { return text; }

    public Vector2D getPos() { return this.pos; }

    public static int getButtonWidth() { return width; }

    public static int getButtonHeight() { return height; }

    abstract public void performAction();

}
