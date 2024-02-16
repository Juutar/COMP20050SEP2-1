package comp20050sep2.group1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Hexagon {
    
    double side;
    double x,y;

    Hexagon(double side, double x, double y){
        this.x = x;
        this.y = y;
        this.side = side;
    }

    public void drawHexagon(){
        
        double a1 = x;
        double a2 = y;

        double b1 = a1 - (side * Math.cos(Math.toRadians(30)));
        double b2 = a2 + (side * Math.sin(Math.toRadians(30)));

        double c1 = a1 + (side * Math.cos(Math.toRadians(30)));       //pi/6 = 0.523599
        double c2 = a2 + (side * Math.sin(Math.toRadians(30)));

        double d1 = b1;
        double d2 = b2 + side;

        double e1 = c1;
        double e2 = c2 + side;

        double f1 = a1;
        double f2 = a2 + 2 * side;


        Graphics2D g = GamePanel.get().graphics;
        g.setColor(Color.white);
        g.draw(new Line2D.Double(a1, a2, b1, b2));    //top left
        g.draw(new Line2D.Double(a1, a2, c1, c2));    //top right
        g.draw(new Line2D.Double(b1, b2, d1, d2));
        g.draw(new Line2D.Double(c1, c2, e1, e2));
        g.draw(new Line2D.Double(f1, f2, d1, d2));
        g.draw(new Line2D.Double(f1, f2, e1, e2));
    }
}
