package comp20050sep2.group1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import comp20050sep2.group1.utils.Vector2D;
import comp20050sep2.group1.utils.Vector3D;

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

    boolean evaluate;

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

        for(int i = 19; i < 37; i ++){
            
            if((i - 19) % size == 0){
                hexes.get(i).pointableSides = 3;
            }
            else{
                hexes.get(i).pointableSides = 2;
            }

            hexes.get(i).boardLabels = new BoardLabel[hexes.get(i).pointableSides];

        }

        double start_x, start_y;
        double end_x, end_y;

        int pointableSides;
        int angleOffset = 0;

        int k = 0;
        int indexOffset = 0;

        for(int i = 6 * size + 1; i < 37; i ++){

            k = hexes.get(i).pointableSides;

            int j = 0;

            for(j = 0; j < hexes.get(i).pointableSides; j ++){
                hexes.get(i).boardLabels[j] = new BoardLabel("" + (indexOffset + k --));
            }

            indexOffset += j;

        }

        for(int i = 19; i < 37; i ++){

            pointableSides = hexes.get(i).pointableSides;
            
            start_x = hexes.get(i).center().x;
            start_y = hexes.get(i).center().y;

            if(((i - 19) % 3 == 0) && ((i - 19) != 0)){
                angleOffset -= 60;
            }

            for(int j = 0; j < pointableSides; j ++){

                end_x = start_x + (side + 10) * Math.cos(Math.toRadians((j * 60) + angleOffset));
                end_y = start_y + (side + 10) * Math.sin(Math.toRadians((j * 60) + angleOffset));

                hexes.get(i).boardLabels[j].x = end_x;
                hexes.get(i).boardLabels[j].y = end_y;

            }

        }

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

        g.setColor(Color.WHITE);

        for (Hexagon hex : hexes) {
            hex.drawHexagon();
            if(hex.pointableSides != 0){
                for(BoardLabel bl : hex.boardLabels){ 
                    if(!toggleAtomSelector){
                        if(bl != closestLabelToMouseCoors()){
                            bl.writeText();
                        }
                        else{
                            GamePanel.get().graphics.setColor(Color.white);
                            g.drawLine((int)bl.x, (int)bl.y, (int)hex.center().x, (int)hex.center().y);
                            g.setColor(Color.lightGray);
                            g.fillOval((int)hex.center().x - 10, (int)hex.center().y - 10, 20, 20);
                            g.setColor(Color.white);
                        }
                    }
                    else{
                        bl.writeText();
                    }
                 }
            }
        }

        // highlight nearest hex
        Hexagon nearest = closestHexToCoords(GamePanel.get().mouseCoords);

        if(toggleAtomSelector){
            g.setColor(Color.red);
            g.fillOval((int)nearest.center().x - 10, (int)nearest.center().y - 10, 20, 20);
            g.setColor(Color.white);
        }
        else{
            //else case
        }

        // Arrowhead ah = new Arrowhead(pos, 15);
        // ah.drawArrow();

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

    public BoardLabel closestLabelToMouseCoors(){
        BoardLabel leaderLabel = hexes.get(19).boardLabels[0];
        double leader = Double.MAX_VALUE;

        double mouse_x, mouse_y;
        double dist;
        Vector2D mouse_vec, label_vec;

        for(int i = 19; i < 37; i ++){
            for(int j = 0; j < hexes.get(i).pointableSides; j ++){
                mouse_x = GamePanel.get().mouseCoords.x;
                mouse_y = GamePanel.get().mouseCoords.y;
                mouse_vec = new Vector2D(mouse_x, mouse_y);
                label_vec = new Vector2D(hexes.get(i).boardLabels[j].x, hexes.get(i).boardLabels[j].y);
                dist = mouse_vec.distanceSquared(label_vec);
                if(dist < leader){
                    leaderLabel = hexes.get(i).boardLabels[j];
                    leader = dist;
                }
            }
        }

        return leaderLabel;

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

    public void toggleEvaluate() {
        evaluate = !evaluate;
    }
}
