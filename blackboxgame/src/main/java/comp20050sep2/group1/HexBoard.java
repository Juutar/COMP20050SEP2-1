package comp20050sep2.group1;

import java.util.ArrayList;

public class HexBoard {

    public double x,y;

    private ArrayList<Hexagon> hexes = new ArrayList<>();

    public HexBoard(double side, double x, double y, int size /* from 0, how many rings */) {
        hexes.add(new Hexagon(side, x, y));

        for (int i = 1; i <= size; ++i) {

            double angle = 30;
            double distance = Math.sqrt(3) * side * i;

            do {
                double angleForMore = angle + 120;
                for (int j = 0; j < i; ++j) {
                    double addX = Math.sin(Math.toRadians(angleForMore)) * Math.sqrt(3) * side * j;
                    double addY = Math.cos(Math.toRadians(angleForMore)) * Math.sqrt(3) * side * j;
                    hexes.add(new Hexagon(side, x + addX + Math.sin(Math.toRadians(angle)) * distance, y + addY + Math.cos(Math.toRadians(angle)) * distance));
                }

                angle += 60;
                if (angle >= 360)
                    angle -= 360;
            } while (angle != 30);
        }
    }

    public void draw() {
        for (Hexagon hex : hexes) {
            hex.drawHexagon();
        }
    }
}
