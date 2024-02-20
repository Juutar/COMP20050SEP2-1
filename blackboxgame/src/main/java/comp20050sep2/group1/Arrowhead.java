package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

public class Arrowhead {
    
    Vector2D pos;
    double dir;

    Arrowhead(Vector2D pos){
        this.pos = pos;
        double dir;
    }

    public void drawArrow(){

        GamePanel.get().graphics.fillPolygon(new int[]{10, 30, 10}, new int[]{10, 20, 30}, 3);

    }

}
