package comp20050sep2.group1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Iterator;

import comp20050sep2.group1.utils.Vector3D;

public class Ray {
    
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
        graphics.setColor(Color.gray);
        Stroke prevStroke = graphics.getStroke();
        graphics.setStroke(new BasicStroke(3));

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

    public void drawToLabel(BoardLabel label, boolean entry){       //entry false = exiting the board, entry true = entering the board
        
        Graphics2D graphics = GamePanel.get().graphics;
        graphics.setColor(Color.gray);
        Stroke prevStroke = graphics.getStroke();
        graphics.setStroke(new BasicStroke(3));
        
        if(entry == true){
            GamePanel.get().graphics.drawLine((int)label.pos.x, (int)label.pos.y, (int)points.get(0).center().x, (int)points.get(0).center().y);
        }
        else if(entry == false){
            GamePanel.get().graphics.drawLine((int)label.pos.x, (int)label.pos.y, (int)points.get(points.size() - 1).center().x, (int)points.get(points.size() - 1).center().y);
        }

        graphics.setStroke(prevStroke);

    }

    public void setNext(Vector3D vel){
        
        Vector3D next = Vector3D.binaryAdd(vel, GamePanel.get().board.getHexes().getKey(points.get(points.size() - 1)));

        if(GamePanel.get().board.getHexes().getKeySet().contains(next)){
            points.add(GamePanel.get().board.getHexes().getValue(next));
        }
        
    }

    public void setNextHex(Hexagon h){
        points.add(h);
    }

    public static void drawRayHexes(Hexagon a, Hexagon b){
        
        GamePanel.get().graphics.setColor(Color.gray);
        Stroke prevStroke = GamePanel.get().graphics.getStroke();
        GamePanel.get().graphics.setStroke(new BasicStroke(3));

        GamePanel.get().graphics.drawLine(
            (int)a.center().x,
            (int)a.center().y,
            (int)b.center().x,
            (int)b.center().y
        );

        GamePanel.get().graphics.setStroke(prevStroke);

    }

}
