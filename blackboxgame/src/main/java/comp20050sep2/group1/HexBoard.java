package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;
import comp20050sep2.group1.utils.Vector3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class HexBoard {

    public Vector2D pos;

    private ArrayList<Hexagon> hexes = new ArrayList<>();
    private double side;
    private int size;

    public boolean toggleAtomSelector;

    public int numAtoms;
    public int atomIndex;
    public Hexagon[] guessAtomHexagons;
    public Hexagon[] trueAtomHexagons;

    public HexBoard(double side, Vector2D pos, int size /* from 0, how many rings */, int numAtoms) {
        hexes.add(new Hexagon(side, pos));
        this.side = side;
        this.pos = pos;
        this.size = size;
        this.numAtoms = numAtoms;
        this.atomIndex = 0;
        this.toggleAtomSelector = true;
        this.guessAtomHexagons = new Hexagon[numAtoms];
        this.trueAtomHexagons = new Hexagon[numAtoms];


        for (int i = 1; i <= size; ++i) {

            double angle = 30;
            double distance = Math.sqrt(3) * side * i;

            do {
                double angleForMore = angle + 120;
                for (int j = 0; j < i; ++j) {
                    double addX = Math.sin(Math.toRadians(angleForMore)) * Math.sqrt(3) * side * j;
                    double addY = Math.cos(Math.toRadians(angleForMore)) * Math.sqrt(3) * side * j;
                    hexes.add(new Hexagon(side, new Vector2D(pos.x + addX + Math.sin(Math.toRadians(angle)) * distance, pos.y + addY + Math.cos(Math.toRadians(angle)) * distance)));
                }

                angle += 60;
                if (angle >= 360)
                    angle -= 360;
            } while (angle != 30);
        }

        placeTrueAtoms();
    }

    private void drawBackgroundPoly() {
        Polygon backgroundPoly = new Polygon();
        final Vector2D vqm = vecFromAng(30).mulip(this.size + 0.2).mulip(side * 4 * Math.cos(Math.toRadians(30)) / Math.sqrt(3));
        final Vector2D vrm = vecFromAng(150).mulip(this.size + 0.2).mulip(side * 4 * Math.cos(Math.toRadians(30)) / Math.sqrt(3));
        final Vector2D vsm = vecFromAng(270).mulip(this.size + 0.2).mulip(side * 4 * Math.cos(Math.toRadians(30)) / Math.sqrt(3));
        backgroundPoly.addPoint((int)vqm.x + (int)this.pos.x, (int)vqm.y + (int)this.pos.y);
        backgroundPoly.addPoint((int)-vsm.x + (int)this.pos.x, (int)-vsm.y + (int)this.pos.y);
        backgroundPoly.addPoint((int)vrm.x + (int)this.pos.x, (int)vrm.y + (int)this.pos.y);
        backgroundPoly.addPoint((int)-vqm.x + (int)this.pos.x, (int)-vqm.y + (int)this.pos.y);
        backgroundPoly.addPoint((int)vsm.x + (int)this.pos.x, (int)vsm.y + (int)this.pos.y);
        backgroundPoly.addPoint((int)-vrm.x + (int)this.pos.x, (int)-vrm.y + (int)this.pos.y);

        Graphics2D g = GamePanel.get().graphics;

        g.setColor(new Color(0.0F, 0.F, 0.F, 0.5F));
        g.fillPolygon(backgroundPoly);
    }

    public void draw() {
        Graphics2D g = GamePanel.get().graphics;
        drawBackgroundPoly();

        BoardLabel[] labels = new BoardLabel[18];

        for(int i = 19; i < 37; i ++){
            labels[i - 19] = new BoardLabel("" + (i - 18), hexes.get(i));
            if(i <= 25){
                labels[i - 19].writeText(90, 5);
            }
            else if(i <= 27){
                labels[i - 19].writeText(-10, -70);
            }
            else if(i <= 34){
                labels[i - 19].writeText(-110, 5);
            }
            else if(i <= 36){
                labels[i - 19].writeText(-10, 80);
            }
        }

        g.setColor(Color.WHITE);
        for (Hexagon hex : hexes) {
            hex.drawHexagon();
        }

        // highlight nearest hex
        Hexagon nearest = closestHexToCoords(GamePanel.get().mouseCoords);

        if(toggleAtomSelector){
            g.setColor(Color.red);
            g.fillOval((int)nearest.center().x - 10, (int)nearest.center().y - 10, 20, 20);
            g.setColor(Color.white);
        }
        else{
            Hexagon nearestEdge = closestPerimeterHexToCoors(GamePanel.get().mouseCoords);
            Arrowhead aHead = new Arrowhead(null);
            aHead.drawArrow();
            g.setColor(Color.lightGray);
            g.fillOval((int)nearestEdge.center().x - 10, (int)nearestEdge.center().y - 10, 20, 20);
            g.setColor(Color.white);
        }
    }

    private final Vector2D vecFromAng(double ang) {
        return new Vector2D(Math.sin(Math.toRadians(ang)) * Math.sqrt(3) * 0.5 * (1.0 / Math.cos(Math.toRadians(30))), Math.cos(Math.toRadians(ang)) * Math.sqrt(3) * 0.5 * (1.0 / Math.cos(Math.toRadians(30))));
    }

    private final Vector2D VecQ = vecFromAng(60);
    private final Vector2D VecR = vecFromAng(180);
    private final Vector2D VecS = vecFromAng(-60);
    public Vector2D hexCoordsToCenter(Vector3D coords) {
        Vector2D res = new Vector2D(0, 0);
        res.addip(VecQ.mul(coords.x * side)).addip(VecR.mul(coords.y * side)).addip(VecS.mul(coords.z * side));
        res = res.mul(1, -1);
        res.addip(this.pos);
        return res;
    }

    public Hexagon closestHexToCoords(Vector2D coords) {
        double leader = Double.MAX_VALUE;
        Hexagon leaderHex = this.hexes.get(0);
        for (Hexagon h : this.hexes) {
            double dist = h.center().distanceSquared(coords);
            if (dist < leader) {
                leaderHex = h;
                leader = dist;
            }
        }

        return leaderHex;
    }

    public Hexagon closestPerimeterHexToCoors(Vector2D coords){
        Hexagon leaderHex = hexes.get(19);
        double leader = Double.MAX_VALUE;
        
        for(int i = 19; i < 37; i++){
            
            Hexagon h = hexes.get(i);
            double dist = h.center().distanceSquared(coords);

            if(dist < leader){
                leaderHex = h;
                leader = dist;
            }
        }

        return leaderHex;
    }

    public Vector2D closestSide(){

        double x = GamePanel.get().mouseCoords.x;
        double y = GamePanel.get().mouseCoords.y;

        Hexagon h = closestPerimeterHexToCoors(new Vector2D(x, y));

        if(h.pointableSides == 3){
            
        }
        else if(h.pointableSides == 2){

        }

        return null;

    }

    public void reposition(Vector2D coords) {
        final Vector2D delta = coords.sub(this.pos);

        this.pos = coords;

        for (Hexagon h : hexes) {
            h.reposition(h.pos.add(delta));
        }
    }

    private void placeTrueAtoms() {
        Random random = new Random();
        int randHexIndex;
        for(int i = 0; i < trueAtomHexagons.length; i++) {
            do {
                randHexIndex = random.nextInt(0, hexes.size());
            } while (Arrays.asList(trueAtomHexagons).contains(hexes.get(randHexIndex)));
            trueAtomHexagons[i] = hexes.get(randHexIndex);
            trueAtomHexagons[i].placeTrueAtom();
        }

    }
}
