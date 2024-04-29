package comp20050sep2.group1;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class RayMarker {
    public static ArrayList<Color> prevColors = new ArrayList<>();
    public static final Color ABSORPTION_COLOR = Color.gray;
    public static final Color REFLECTION_COLOR = Color.black;

    Color color;
    ArrayList<BoardLabel> labels;

    public RayMarker(Ray ray) {
        labels = new ArrayList<>();
        labels.add(ray.firstLabel);

        switch(ray.result) {
            case ABSORBED:
                this.color = ABSORPTION_COLOR;
                break;
            case REFLECTED:
                this.color = REFLECTION_COLOR;
                break;
            case DEFLECTED:
                this.color = getDeflectionColor();
                labels.add(ray.lastLabel);
                break;
        }
    }

    public void drawRayMarker(boolean hover) {
        Graphics2D g = GamePanel.get().graphics;

        g.setColor(hover ? Color.white : color);
        Stroke initialStroke = g.getStroke();
        g.setStroke(new BasicStroke(3));

        for(BoardLabel bl : labels) {
            Hexagon hex = bl.hexagon;
            g.drawLine((int) bl.pos.x +10, (int) bl.pos.y -5, (int) hex.pos.x, (int) hex.pos.y);

        }
        g.setStroke(initialStroke);
    }

    public Color getDeflectionColor() {
        Random random = new Random();
        Color color;
        do {
            color = new Color(random.nextInt());
        } while (prevColors.contains(color) || color.equals(ABSORPTION_COLOR) || color.equals(REFLECTION_COLOR));
        prevColors.add(color);
        return color;
    }
}
