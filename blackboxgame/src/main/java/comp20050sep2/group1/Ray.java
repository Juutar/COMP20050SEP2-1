package comp20050sep2.group1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Iterator;

public class Ray {
    
    public Hexagon startHex;
    public ArrayList<Hexagon> points;
    
    public Ray(Hexagon startHex){
        this.points = new ArrayList<>();
        this.points.add(startHex);
    }

    public void addHextoRoute(Hexagon h){
        this.points.add(h);
    }

    public void drawRay(){
        
        if(points.size() == 1){ return; }

        Iterator<Hexagon> iterator = points.iterator();

        Hexagon prevHex = iterator.next();
        Hexagon currHex;

        Graphics2D graphics = GamePanel.get().graphics;
        graphics.setColor(Color.BLUE);
        Stroke prevStroke = graphics.getStroke();
        graphics.setStroke(new BasicStroke(5));

        while(iterator.hasNext()){
            currHex = iterator.next();
            graphics.drawLine(
                (int)prevHex.center().x,
                (int)prevHex.center().y,
                (int)currHex.center().x,
                (int)currHex.center().y
            );

            prevHex = currHex;

        }

        graphics.setStroke(prevStroke);

    }

    public static void drawRayHexes(Hexagon a, Hexagon b){
        
        GamePanel.get().graphics.setColor(Color.BLUE);
        Stroke prevStroke = GamePanel.get().graphics.getStroke();
        GamePanel.get().graphics.setStroke(new BasicStroke(5));

        GamePanel.get().graphics.drawLine(
            (int)a.center().x,
            (int)a.center().y,
            (int)b.center().x,
            (int)b.center().y
        );

        GamePanel.get().graphics.setStroke(prevStroke);

    }

}
