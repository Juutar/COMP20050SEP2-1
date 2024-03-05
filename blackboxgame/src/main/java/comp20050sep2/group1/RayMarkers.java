package comp20050sep2.group1;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class RayMarkers {
    Color absorptionColor = Color.white;
    Color reflectionColor = Color.black;
    Color deflectionColor;
    static ArrayList<Color> prevColors = new ArrayList<>();

    public RayMarkers(Ray ray) {
        //implementation depends on ray class
    }

    public Color getAbsorptionColor() { return absorptionColor; }
    public Color getReflectionColor() { return reflectionColor; }
    public Color getDeflectionColor() {
        Random random = new Random();
        do {
            deflectionColor = new Color(random.nextInt());
        } while (prevColors.contains(deflectionColor) || deflectionColor.equals(absorptionColor) || deflectionColor.equals(reflectionColor));
        prevColors.add(deflectionColor);
        return deflectionColor;
    }
}
