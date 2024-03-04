package comp20050sep2.group1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import comp20050sep2.group1.utils.BiMap;
import comp20050sep2.group1.utils.Vector2D;
import comp20050sep2.group1.utils.Vector3D;

public class HexBoard {

    public Vector2D pos;
    private final BiMap<Vector3D, Hexagon> hexes;
    private final double side;
    private final int size;

    public boolean atomSelectorOn;
    public boolean trueAtomsVisible;
    public boolean evaluate;

    public int numAtoms;
    public int atomIndex;
    public Vector3D[] guessAtomHexagons;
    public Vector3D[] trueAtomHexagons;
    public Vector3D[] outerHexes;

    public HexBoard(double side, Vector2D pos, int size /* from 0, how many rings */, int numAtoms) {
        hexes = new BiMap<>();
        hexes.add(new Vector3D(0, 0, 0), new Hexagon(side, pos));
        this.side = side;
        this.pos = pos;
        this.size = size;
        this.numAtoms = numAtoms;
        this.atomIndex = 0;
        this.atomSelectorOn = true;
        this.guessAtomHexagons = new Vector3D[numAtoms];
        this.trueAtomHexagons = new Vector3D[numAtoms];
        this.outerHexes = new Vector3D[numOuterHexes()];

        Vector3D direction = new Vector3D();
        direction.setCoorsFromAngle(150);
        Vector3D nextHexagonVec = new Vector3D();
        nextHexagonVec.sum(direction);

        int outerHexesIndex = 0;
        for (int i = 1; i <= size; ++i) {
            double angle = 30;
            double distance = Math.sqrt(3) * side * i;
            int coorsAngle = 30;
            direction.setCoorsFromAngle(coorsAngle);

            do {
                double angleForMore = angle + 120;
                for (int j = 0; j < i; ++j) {
                    double addX = Math.sin(Math.toRadians(angleForMore)) * Math.sqrt(3) * side * j;
                    double addY = Math.cos(Math.toRadians(angleForMore)) * Math.sqrt(3) * side * j;

                    System.out.println(nextHexagonVec.toString());
                    hexes.add(nextHexagonVec.copy(), new Hexagon(side, new Vector2D(pos.x + addX + Math.sin(Math.toRadians(angle)) * distance, pos.y + addY + Math.cos(Math.toRadians(angle)) * distance)));
                    if (isOuterHex(nextHexagonVec)) { outerHexes[outerHexesIndex++] = nextHexagonVec.copy(); }
                    nextHexagonVec.sum(direction);
                }

                coorsAngle = Math.floorMod(coorsAngle - 60, 360);
                if(coorsAngle == 30) {
                    direction.setCoorsFromAngle(150);
                    nextHexagonVec.sum(direction);
                } else {
                    direction.setCoorsFromAngle(coorsAngle);
                }

                angle += 60;
                if (angle >= 360)
                    angle -= 360;
            } while (angle != 30);
        }

        placeTrueAtoms();
        assignPointableSides();
        assignLabels();
        assignNeighbours();

    }

    private void assignNeighbours(){
        
        Iterator<Hexagon> hexes_iter = hexes.getValueSet().iterator();

        //h stores the current hexagon who's neighbours we have to find

        while(hexes_iter.hasNext()){
            Hexagon h = hexes_iter.next();
            if(h.hasTrueAtom()){

                Vector3D coordsVec = hexes.getKey(h);       //get the coordinates of the hexagon

                for(int angle = 0; angle <= 300; angle += 60){

                    Vector3D neighbour = Vector3D.binaryAdd(coordsVec, coordsVec.getNeighbouringCoords(angle));
                    if(hexes.getKeySet().contains(neighbour)){
                        hexes.getValue(neighbour).underInfluence = true;

                        if(h.neighbors == null){
                            h.neighbors = new ArrayList<>();
                        }

                        h.neighbors.add(hexes.getValue(neighbour));

                        if(hexes.getValue(neighbour).influenceVector == null){
                            hexes.getValue(neighbour).influenceVector = coordsVec.getNeighbouringCoords(angle);
                        }
                        else{
                            hexes.getValue(neighbour).influenceVector.sum(coordsVec.getNeighbouringCoords(angle));
                        }
                    }
                }
            }
        }
    }

    private void assignPointableSides() {
       for(Vector3D vec : outerHexes) {
           if(vec.q == 0 || vec.r == 0 || vec.s == 0) {
               hexes.getValue(vec).pointableSides = 3;
           } else {
               hexes.getValue(vec).pointableSides = 2;
           }
           hexes.getValue(vec).boardLabels = new BoardLabel[hexes.getValue(vec).pointableSides];
       }
    }

    private void assignLabels() {
        int labelIndex = 1;
        for (Vector3D vec : outerHexes) {
            for (int j = 0; j < hexes.getValue(vec).pointableSides; j++, labelIndex++) {
                hexes.getValue(vec).boardLabels[j] = new BoardLabel("" + labelIndex, hexes.getValue(vec), computeLabelAngle(labelIndex));
            }
        }
    }

    private void drawBackgroundPoly() {
        Polygon backgroundPoly = new Polygon();
        final Vector2D vqm = vecFromAng(30).mulip(this.size + 0.2)
                .mulip(side * 4 * Math.cos(Math.toRadians(30)) / Math.sqrt(3));
        final Vector2D vrm = vecFromAng(150).mulip(this.size + 0.2)
                .mulip(side * 4 * Math.cos(Math.toRadians(30)) / Math.sqrt(3));
        final Vector2D vsm = vecFromAng(270).mulip(this.size + 0.2)
                .mulip(side * 4 * Math.cos(Math.toRadians(30)) / Math.sqrt(3));
        backgroundPoly.addPoint((int) vqm.x + (int) this.pos.x, (int) vqm.y + (int) this.pos.y);
        backgroundPoly.addPoint((int) -vsm.x + (int) this.pos.x, (int) -vsm.y + (int) this.pos.y);
        backgroundPoly.addPoint((int) vrm.x + (int) this.pos.x, (int) vrm.y + (int) this.pos.y);
        backgroundPoly.addPoint((int) -vqm.x + (int) this.pos.x, (int) -vqm.y + (int) this.pos.y);
        backgroundPoly.addPoint((int) vsm.x + (int) this.pos.x, (int) vsm.y + (int) this.pos.y);
        backgroundPoly.addPoint((int) -vrm.x + (int) this.pos.x, (int) -vrm.y + (int) this.pos.y);

        Graphics2D g = GamePanel.get().graphics;

        g.setColor(new Color(0.0F, 0.F, 0.F, 0.5F));
        g.fillPolygon(backgroundPoly);
    }

    public void drawBoard() {
        drawBackgroundPoly();

        Graphics2D g = GamePanel.get().graphics;
        g.setColor(Color.WHITE);

        for (Hexagon hex : hexes.getValueSet()) {
            
            hex.drawHexagon();
            
            if (hex.boardLabels != null) {
                for (BoardLabel bl : hex.boardLabels) {
                    if (!atomSelectorOn && bl == closestLabelToMouseCoords()) {
                        AbstractRayPointer.drawRayPointer(bl.pos, hex.center());
                    } else {
                        bl.writeText();
                    }
                }
            }

            //temp code

            if(hex.underInfluence){
                g.setColor(Color.MAGENTA);
                g.fillRect((int)hex.center().x - 7, (int)hex.center().y - 7, 15, 15);
                g.drawString(hex.influenceVector.toString(), (int)hex.center().x, (int)hex.center().y);
            }
        }

        // highlight nearest hex
        Hexagon nearest = closestHexToCoords(GamePanel.get().mouseCoords);

        if (atomSelectorOn) {
            g.setColor(Color.red);
            g.fillOval((int) nearest.center().x - 10, (int) nearest.center().y - 10, 20, 20);
            g.setColor(Color.white);
        }

        Ray r = new Ray(hexes.getValue(outerHexes[0]));

        for(int i = 0; i <= outerHexes.length; i ++){
            r.setNextHex(hexes.getValue(outerHexes[i % outerHexes.length]));
        }

        r.drawRay();
        

    }

    private final Vector2D vecFromAng(double ang) {
        return new Vector2D(Math.sin(Math.toRadians(ang)) * Math.sqrt(3) * 0.5 * (1.0 / Math.cos(Math.toRadians(30))),
                Math.cos(Math.toRadians(ang)) * Math.sqrt(3) * 0.5 * (1.0 / Math.cos(Math.toRadians(30))));
    }

    private final Vector2D VecQ = vecFromAng(60);
    private final Vector2D VecR = vecFromAng(180);
    private final Vector2D VecS = vecFromAng(-60);

    public Vector2D hexCoordsToCenter(Vector3D coords) {
        Vector2D res = new Vector2D(0, 0);
        res.addip(VecQ.mul(coords.q * side)).addip(VecR.mul(coords.r * side)).addip(VecS.mul(coords.s * side));
        res = res.mul(1, -1);
        res.addip(this.pos);
        return res;
    }

    public Hexagon closestHexToCoords(Vector2D coords) {
        double leader = Double.MAX_VALUE;
        Hexagon leaderHex = this.hexes.getValue(new Vector3D(0, 0, 0));
        for (Hexagon h : hexes.getValueSet()) {
            double dist = h.center().distanceSquared(coords);
            if (dist < leader) {
                leaderHex = h;
                leader = dist;
            }
        }

        return leaderHex;
    }

    public BoardLabel closestLabelToCoords(Vector2D coords){
        BoardLabel leaderLabel = hexes.getValue(outerHexes[0]).boardLabels[0];
        double leader = Double.MAX_VALUE;
        double dist;

        for (Vector3D vec : outerHexes) {
            for (BoardLabel bl : hexes.getValue(vec).boardLabels){
                dist = coords.distanceSquared(bl.pos);
                if (dist < leader) {
                    leaderLabel = bl;
                    leader = dist;
                }
            }
        }

        return leaderLabel;
    }

    public BoardLabel closestLabelToMouseCoords(){
        return closestLabelToCoords(GamePanel.get().mouseCoords);
    }

    public int computeLabelAngle(int labelIndex) {
        // default angle for hexagon position
        int angle = Math.floorMod(150 - (60 * Math.floorDiv(labelIndex, numLabels() / 6)), 360);

        // angle offset if label is even/odd
        if ((angle + 30) / 60 % 2 == 1 && labelIndex % 2 == 1
                || (angle + 30) / 60 % 2 == 0 && labelIndex % 2 == 0) {
            angle = Math.floorMod(angle - 60, 360);
        }

        // angle offset if label is first of subset (if label is first of 3-label
        // hexagon)
        if (labelIndex % (numLabels() / 6) == 1) {
            angle = Math.floorMod(angle + 120, 360);
        }
        return angle;
    }

    public void reposition(Vector2D coords) {
        final Vector2D delta = coords.sub(this.pos);

        this.pos = coords;

        for (Hexagon h : hexes.getValueSet()) {
            h.reposition(h.pos.add(delta));
        }
    }

    private void placeTrueAtoms() {
        Random random = new Random();
        int q, r, s;
        Vector3D randomVector;
        for(int i = 0; i < trueAtomHexagons.length; i++) {
            do{
                do {
                    q = random.nextInt(-size, size);
                    r = random.nextInt(-size, size);
                    s = -q-r;

                } while (!(q + r + s == 0) || (s > size) || (s < -size));
                randomVector = new Vector3D(q, r, s);
            } while(Arrays.asList(trueAtomHexagons).contains(randomVector));
            trueAtomHexagons[i] = randomVector;
            hexes.getValue(trueAtomHexagons[i]).placeTrueAtom();
        }
    }

    public void toggleEvaluate() {
        evaluate = !evaluate;
    }

    public void toggleTrueAtomsVisible() {
        trueAtomsVisible = !trueAtomsVisible;
    }

    public void toggleAtomSelectorOn() {
        atomSelectorOn = !atomSelectorOn;
    }


    public int numHexes() { return 6*size*(size+1)/2+1;}
    public int getSize() { return size; }
    public BiMap<Vector3D, Hexagon> getHexes() { return hexes; }

    public int numOuterHexes() {
        return 6 * size;
    }

    public int numLabels() {
        return (size + 1) * 2 * 6 - 6;
    }


    public boolean isOuterHex(Vector3D vec) {
        return vec.q == size || vec.r == size || vec.s == size
                || vec.q == -size || vec.r == -size || vec.s == -size;
    }

    public boolean isOuterHex(Hexagon hex) {
        return isOuterHex(hexes.getKey(hex));
    }

}
