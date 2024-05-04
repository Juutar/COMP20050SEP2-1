package comp20050sep2.group1;

import comp20050sep2.group1.utils.BiMap;
import comp20050sep2.group1.utils.Vector2D;
import comp20050sep2.group1.utils.Vector3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class HexBoard {

    private final BiMap<Vector3D, Hexagon> hexes;
    public final ArrayList<Ray> rayList;
    private final double side;
    private final int size;
    private final Vector2D VecQ = vecFromAng(60);
    private final Vector2D VecR = vecFromAng(180);
    private final Vector2D VecS = vecFromAng(-60);
    public Vector2D pos;
    public boolean atomSelectorOn;
    public boolean trueAtomsVisible;
    public boolean raysVisible;
    public boolean evaluate;
    public boolean scoreVisible;
    public int numAtoms;
    public int atomIndex;
    public int score;
    public Vector3D[] guessAtomHexagons;
    public Vector3D[] trueAtomHexagons;
    public Vector3D[] outerHexes;

    /**
     * Constructs hexboard to play
     * @param side Length of the board sides
     * @param pos Position of the board
     * @param size Number of rings surrounding the center hexagon
     * @param numAtoms Number of atoms you can place
     */
    public HexBoard(double side, Vector2D pos, int size /* from 0, how many rings */, int numAtoms) {
        hexes = new BiMap<>();
        this.rayList = new ArrayList<>();
        hexes.add(new Vector3D(0, 0, 0), new Hexagon(side, pos));
        this.side = side;
        this.pos = pos;
        this.size = size;
        this.numAtoms = numAtoms;
        this.atomIndex = 0;
        this.score = 0;
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

                    hexes.add(nextHexagonVec.copy(), new Hexagon(side, new Vector2D(pos.x + addX + Math.sin(Math.toRadians(angle)) * distance, pos.y + addY + Math.cos(Math.toRadians(angle)) * distance)));
                    if (isOuterHex(nextHexagonVec)) {
                        outerHexes[outerHexesIndex++] = nextHexagonVec.copy();
                    }
                    nextHexagonVec.sum(direction);
                }

                coorsAngle = Math.floorMod(coorsAngle - 60, 360);
                if (coorsAngle == 30) {
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

    /**
     * Assigns neighbours
     */
    private void assignNeighbours() {

        Iterator<Hexagon> hexes_iter = hexes.getValueSet().iterator();

        //h stores the current hexagon who's neighbours we have to find

        while (hexes_iter.hasNext()) {
            Hexagon h = hexes_iter.next();
            if (h.hasTrueAtom()) {

                Vector3D coordsVec = hexes.getKey(h);       //get the coordinates of the hexagon

                for (int angle = 0; angle <= 300; angle += 60) {

                    Vector3D neighbour = Vector3D.binaryAdd(coordsVec, coordsVec.getNeighbouringCoords(angle));
                    if (hexes.getKeySet().contains(neighbour)) {

                        if (h.neighbors == null) {
                            h.neighbors = new ArrayList<>();
                        }

                        h.neighbors.add(hexes.getValue(neighbour));
                        hexes.getValue(neighbour).influenceVector.sum(coordsVec.getNeighbouringCoords(angle));
                    }
                }
            }
        }
    }

    /**
     * Assigns the pointable sides of border hexagons
     */
    private void assignPointableSides() {
        for (Vector3D vec : outerHexes) {
            if (vec.q == 0 || vec.r == 0 || vec.s == 0) {
                hexes.getValue(vec).pointableSides = 3;
            } else {
                hexes.getValue(vec).pointableSides = 2;
            }
            hexes.getValue(vec).boardLabels = new BoardLabel[hexes.getValue(vec).pointableSides];
        }
    }

    /**
     * Assigns labels to bordering hexagons
     */
    private void assignLabels() {
        int labelIndex = 1;
        for (Vector3D vec : outerHexes) {
            for (int j = 0; j < hexes.getValue(vec).pointableSides; j++, labelIndex++) {
                hexes.getValue(vec).boardLabels[j] = new BoardLabel("" + labelIndex, hexes.getValue(vec), computeLabelAngle(labelIndex));
            }
        }
    }

    /**
     * Drawing the background polygon
     */
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

    /**
     * Drawing the grid
     */
    public void drawBoard() {
        drawBackgroundPoly();

        Graphics2D g = GamePanel.get().graphics;
        g.setColor(Color.WHITE);

        for (Hexagon hex : hexes.getValueSet()) {

            hex.drawHexagon();
            for (Ray r : rayList) {
                r.rayMarkers.drawRayMarker((closestLabelToMouseCoords().hexagon == r.points.get(0)) || (closestLabelToMouseCoords().hexagon == r.points.get(r.points.size() - 1)));
            }

            if (hex.boardLabels != null) {
                for (BoardLabel bl : hex.boardLabels) {
                    if (!atomSelectorOn && bl == closestLabelToMouseCoords()) {
                        AbstractRayPointer.drawRayPointer(new Vector2D(bl.pos.x + 10, bl.pos.y - 5), hex.center());
                        Vector3D vec = new Vector3D();
                        vec.setCoorsFromAngle(closestLabelToMouseCoords().angle);
                        GamePanel.get().lastMousePoint = vec;
                    } else {
                        bl.writeText();
                    }
                }
            }

            if (raysVisible) {
                for (Ray r : rayList) {
                    r.drawRay();
                }
            }

        }

        // highlight nearest hex
        Hexagon nearest = closestHexToCoords(GamePanel.get().mouseCoords);

        if (atomSelectorOn) {
            g.setColor(Color.red);
            g.fillOval((int) nearest.center().x - 10, (int) nearest.center().y - 10, 20, 20);
            g.setColor(Color.white);
        }

    }

    /**
     * Returns a normalised vector based on the angle passed
     * @param ang Angle from which the vector is derived
     * @return Vector with 2D coordinates
     */
    private final Vector2D vecFromAng(double ang) {
        return new Vector2D(Math.sin(Math.toRadians(ang)) * Math.sqrt(3) * 0.5 * (1.0 / Math.cos(Math.toRadians(30))),
                Math.cos(Math.toRadians(ang)) * Math.sqrt(3) * 0.5 * (1.0 / Math.cos(Math.toRadians(30))));
    }

    /**
     * Gets the 2D coordinates based on 3D coordinates passed
     * @param coords 3D coordinates
     * @return 2D coordinates
     */
    public Vector2D hexCoordsToCenter(Vector3D coords) {
        Vector2D res = new Vector2D(0, 0);
        res.addip(VecQ.mul(coords.q * side)).addip(VecR.mul(coords.r * side)).addip(VecS.mul(coords.s * side));
        res = res.mul(1, -1);
        res.addip(this.pos);
        return res;
    }

    /**
     * Gets the closest hexagon to passed coordinates
     * @param coords Coordinates to compare against
     * @return The hexagon reference
     */
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

    /**
     * Returns the closest board label to coordinates
     * @param coords Coordinates to compare against
     * @return The board label
     */
    public BoardLabel closestLabelToCoords(Vector2D coords) {
        BoardLabel leaderLabel = hexes.getValue(outerHexes[0]).boardLabels[0];
        double leader = Double.MAX_VALUE;
        double dist;

        for (Vector3D vec : outerHexes) {
            for (BoardLabel bl : hexes.getValue(vec).boardLabels) {
                dist = coords.distanceSquared(bl.pos);
                if (dist < leader) {
                    leaderLabel = bl;
                    leader = dist;
                }
            }
        }

        return leaderLabel;
    }

    /**
     * Returns the closest board label to mouse coordinates
     * @return The board label
     */
    public BoardLabel closestLabelToMouseCoords() {
        return closestLabelToCoords(GamePanel.get().mouseCoords);
    }

    /**
     * Returns the angle of the label from the center of the grid
     * @param labelIndex Index of the label
     * @return The angle with respect to the center of the grid
     */
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

    /**
     * Repositions the board
     * @param coords New coordinates
     */
    public void reposition(Vector2D coords) {
        final Vector2D delta = coords.sub(this.pos);

        this.pos = coords;

        for (Hexagon h : hexes.getValueSet()) {
            h.reposition(h.pos.add(delta));
        }
    }

    /**
     * Place all true atoms on the board for the player to guess
     */
    private void placeTrueAtoms() {
        Random random = new Random();
        int q, r, s;
        Vector3D randomVector;
        for (int i = 0; i < trueAtomHexagons.length; i++) {
            do {
                do {
                    q = random.nextInt(-size, size);
                    r = random.nextInt(-size, size);
                    s = -q - r;

                } while (!(q + r + s == 0) || (s > size) || (s < -size));
                randomVector = new Vector3D(q, r, s);
            } while (Arrays.asList(trueAtomHexagons).contains(randomVector));
            trueAtomHexagons[i] = randomVector;
            hexes.getValue(trueAtomHexagons[i]).placeTrueAtom();
        }
    }

    
    public void toggleEvaluate() { evaluate = !evaluate; }
    public void toggleTrueAtomsVisible() { trueAtomsVisible = !trueAtomsVisible; }
    public void toggleRaysVisible() { raysVisible = !raysVisible; }

    public void toggleAtomSelectorOn() { atomSelectorOn = !atomSelectorOn; }


    public int numHexes() { return 6 * size * (size + 1) / 2 + 1; }

    public int getSize() { return size; }

    public BiMap<Vector3D, Hexagon> getHexes() { return hexes; }

    public int numOuterHexes() { return 6 * size; }

    public int numLabels() { return (size + 1) * 2 * 6 - 6; }


    public boolean isOuterHex(Vector3D vec) { return vec.q == size || vec.r == size || vec.s == size || vec.q == -size || vec.r == -size || vec.s == -size; }

    public boolean isOuterHex(Hexagon hex) { return isOuterHex(hexes.getKey(hex)); }

    public boolean rayAlreadyExists(BoardLabel bl) {
        for(Ray r : rayList) {
            if (r.firstLabel.equals(bl) || r.lastLabel == bl) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the score of the current game
     * @return the socre
     */
    public int getScore() {
        if (score == 0) {
            for (Vector3D vec : guessAtomHexagons) {
                if (vec != null && !Arrays.asList(trueAtomHexagons).contains(vec)) {
                    score += 5;
                }
            }
            for (Ray r : rayList) {
                score += r.getScore();
            }
        }
        return score;
    }

    /**
     * Toggle score visibility
     */

    public void toggleScore() {
        if (scoreVisible) {
            GamePanel.get().outputBox.setText("");
        } else {
            GamePanel.get().outputBox.setText(String.valueOf(getScore()));
        }
        scoreVisible = !scoreVisible;
    }

    /**
     * Checks whether user has placed all atoms
     * @return whether user has placed all atoms
     */
    public boolean allGuessAtomsPlaced() {
        for (Vector3D vec : guessAtomHexagons) {
            if (vec == null) {
                return false;
            }
        }
        return true;
    }
}
