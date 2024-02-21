package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

//based on calculations, in order to get to the midpoint of an equilateral triangle
//increase y by hypotenuse / 2
//increase x by sqrt(3) * h / 2

/*
 * (1)
 * |\
 * |  \
 * |    >   (0)     vertices of the triangle
 * |  /
 * | /
 *  (2)
 */

public class Arrowhead {
    
    double x;
    double y;
    double dir;
    double size;        //distance between the center and a vertex

    Vector2D[] vertices;

    Arrowhead(Vector2D pos, double size){
        this.x = pos.x;     //center of the triangle: x
        this.y = pos.y;     //center of the triangle: y
        this.dir = 0;
        this.size = size;

        vertices = new Vector2D[3];

        vertices[0] = new Vector2D();
        vertices[1] = new Vector2D();
        vertices[2] = new Vector2D();

        vertices[0].x = x + this.size;
        vertices[0].y = y;

        vertices[1].x = x - size * Math.cos(Math.toRadians(60));
        vertices[1].y = y - size * Math.sin(Math.toRadians(60));

        vertices[2].x = x - size * Math.cos(Math.toRadians(60));
        vertices[2].y = y + size * Math.sin(Math.toRadians(60));

    }

    public void setDirection(double dir){
        
        this.x += size * Math.cos(Math.toRadians(dir));
        this.y += size * Math.sin(Math.toRadians(dir));

        this.dir = dir;

        vertices[0].x = x + this.size * Math.cos(Math.toRadians(dir));
        vertices[0].y = y * Math.cos(Math.toRadians(dir));

        vertices[1].x = x - size * Math.cos(Math.toRadians((60 + dir) % 360));
        vertices[1].y = y - size * Math.sin(Math.toRadians((60 + dir) % 360));

        vertices[2].x = x - size * Math.cos(Math.toRadians((60 + dir) % 360));
        vertices[2].y = y + size * Math.sin(Math.toRadians((60 + dir) % 360));

    }

    public void drawArrow(){

        GamePanel.get().graphics.fillPolygon(new int[]{(int)vertices[0].x, (int)vertices[1].x, (int)vertices[2].x}, new int[]{(int)vertices[0].y, (int)vertices[1].y, (int)vertices[2].y}, 3);

    }

}
