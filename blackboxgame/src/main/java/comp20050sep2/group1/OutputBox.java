package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;
import javax.swing.border.LineBorder;
import java.awt.*;

public class OutputBox {

    Vector2D pos;
    Dimension size = new Dimension(200, 60);
    String text = "";
    Color backgroundColor = new Color(0.0F, 0.F, 0.F, 0.5F);
    Color foregroundColor = Color.white;
    LineBorder border = new LineBorder(foregroundColor, 1);

    /**
     * Constructs output box which lets user know the result of shooting the atom
     * @param pos Position of the button
     */
    public OutputBox(Vector2D pos) {
        this.pos = pos;
    }

    /**
     * Set text of the button
     * @param text Text to be set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Graphics object to draw button with
     * @param g Graphics2D context
     */
    public void drawOutputBox(Graphics2D g) {
        g.setColor(backgroundColor);
        g.fillRect((int) (pos.x - size.width/2.0), (int) (pos.y - size.height/2.0), size.width, size.height);
        g.setColor(foregroundColor);
        g.drawRect((int) (pos.x - size.width/2.0), (int) (pos.y - size.height/2.0), size.width, size.height);
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        g.drawString(text, (int) pos.x - g.getFontMetrics().stringWidth(text)/2, (int) pos.y + 5);
    }
}
