package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import javax.swing.border.LineBorder;
import java.awt.*;

public abstract class AbstractShowButton extends AbstractButton {

    boolean stateShow;
    String altText;

    Color altBackgroundColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);
    Color altForegroundColor = Color.black;
    LineBorder altBorder = new LineBorder(altForegroundColor, 1);

    /**
     * Constructs a abstract button which performs certain state change functionality on being pressed
     * @param pos Position of the button
     * @param text Text of the button
     * @param altText Alternate text of the button
     */
    public AbstractShowButton(Vector2D pos, String text, String altText) {
        super(pos, text);
        stateShow = true;
    }

    /**
     *  Functionality of button when pressed
     */
    public void toggleState() {
        if (stateShow) {
            setText(altText);
            setBackground(altBackgroundColor);
            setForeground(altForegroundColor);
            setBorder(altBorder);
        } else {
            setText(text);
            setBackground(backgroundColor);
            setForeground(foregroundColor);
            setBorder(border);
        }
        stateShow = !stateShow;
    }

    /**
     * @return state of the button
     */
    public boolean isStateShow() {
        return this.stateShow;
    }

}
