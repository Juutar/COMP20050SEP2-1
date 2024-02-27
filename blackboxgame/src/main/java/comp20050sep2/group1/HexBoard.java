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

    private final ArrayList<Hexagon> hexes = new ArrayList<>();
    private final double side;
    private final int size;

    public boolean atomSelectorOn;
    public boolean trueAtomsVisible;
    public boolean evaluate;

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
        this.atomSelectorOn = true;
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
        assignPointableSides();
        assignLabels();

    }

    private void assignPointableSides() {
        for (int i = getNumHexes() - numOuterHexes(); i < getNumHexes(); i ++) {
            if((i - getNumHexes() + numOuterHexes()) % size == 0){
                hexes.get(i).pointableSides = 3;
            }
            else{
                hexes.get(i).pointableSides = 2;
            }

            hexes.get(i).boardLabels = new BoardLabel[hexes.get(i).pointableSides];

        }
    }

    private void assignLabels() {
        double start_x, start_y;
        double end_x, end_y;

        int labelIndex = 1;

        for (int i = getNumHexes() - numOuterHexes(); i < getNumHexes(); i ++) {

                for (int j = 0; j < hexes.get(i).pointableSides; j++) {

                    //default angle for hexagon size
                    int angle = Math.floorMod(-60 * Math.floorDiv(labelIndex, numLabels()/6), 360);

                    //angle offset if label is even/odd
                    if ((angle + 30) / 60 % 2 == 1 && labelIndex % 2 == 1
                            || (angle + 30) / 60 % 2 == 0 && labelIndex % 2 == 0) {
                        angle = Math.floorMod(angle+60, 360);
                    }

                    //angle offset if label is first of subset (if label is first of 3-label hexagon)
                    if (labelIndex % (numLabels()/6) == 1) {
                        angle = Math.floorMod(angle+120, 360);
                    }

                    start_x = hexes.get(i).center().x;
                    start_y = hexes.get(i).center().y;

                    end_x = start_x + (1.2*side) * Math.cos(Math.toRadians(angle));
                    end_y = start_y + (1.2*side) * Math.sin(Math.toRadians(angle));

                    hexes.get(i).boardLabels[j] = new BoardLabel("" + labelIndex++, new Vector2D(end_x, end_y));
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

    public void drawBoard() {
        drawBackgroundPoly();

        Graphics2D g = GamePanel.get().graphics;
        g.setColor(Color.WHITE);

        for (Hexagon hex : hexes) {
            hex.drawHexagon();
            if(hex.boardLabels != null){
                for(BoardLabel bl : hex.boardLabels){
                    if(!atomSelectorOn && bl == closestLabelToMouseCoors()){
                        AbstractRayPointer.drawRayPointer(bl.pos, hex.center());
                    }
                    else {
                        bl.writeText();
                    }
                 }
            }
        }

        // highlight nearest hex
        Hexagon nearest = closestHexToCoords(GamePanel.get().mouseCoords);

        if(atomSelectorOn){
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

    public BoardLabel closestLabelToCoords(Vector2D coords){
        BoardLabel leaderLabel = hexes.get(getNumHexes()-numOuterHexes()).boardLabels[0];
        double leader = Double.MAX_VALUE;

        double dist;

        for (int i = getNumHexes() - numOuterHexes(); i < getNumHexes(); i ++){
            for (BoardLabel bl : hexes.get(i).boardLabels){
                dist = coords.distanceSquared(bl.pos);
                if(dist < leader){
                    leaderLabel = bl;
                    leader = dist;
                }
            }
        }

        return leaderLabel;
    }

    public BoardLabel closestLabelToMouseCoors(){
        
        return closestLabelToCoords(GamePanel.get().mouseCoords);

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
    public void toggleTrueAtomsVisible() { trueAtomsVisible = !trueAtomsVisible; }
    public void toggleAtomSelectorOn() { atomSelectorOn = !atomSelectorOn; }

    public int getNumHexes() { return hexes.size();}
    public int getSize() { return size; }
    public ArrayList<Hexagon> getHexes() { return hexes; }

//    public static int numHexesPerBoardSize(int size) {
//        return (int) (1 + (6L *size*(size+1) / 2));
//    }

    public int numOuterHexes() {
        return 6 * size;
    }

    public int numLabels() {
        return (size+1) * 2 * 6 - 6;
    }
}
