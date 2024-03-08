package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;
import comp20050sep2.group1.utils.Vector3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Ray {

    public final BoardLabel firstLabel;
    public final BoardLabel lastLabel;
    public final ArrayList<Hexagon> points;
    public enum Result { ABSORBED, REFLECTED, DEFLECTED }
    public final Result result;
    public final RayMarker rayMarkers;

    public Ray(BoardLabel firstLabel) {
        this.firstLabel = firstLabel;
        this.points = new ArrayList<>();

        this.lastLabel = computePath();
        this.result = lastLabel == null ? Result.ABSORBED : (firstLabel == lastLabel ? Result.REFLECTED : Result.DEFLECTED);
        GamePanel.get().outputBox.setText(announcement());

        this.rayMarkers = new RayMarker(this);
    }

    private BoardLabel computePath() {

        HexBoard board = GamePanel.get().board;

        Vector3D prevDirection;
        Vector3D direction = firstLabel.rayDirection.copy();
        Vector3D hexCoords = board.getHexes().getKey(firstLabel.hexagon).copy();
        if (board.getHexes().getValue(hexCoords).hasTrueAtom()) {
            points.addLast(board.getHexes().getValue(hexCoords));
            return null;
        }
        Vector3D zeroVector = new Vector3D();

        while (board.getHexes().getKeySet().contains(hexCoords)) {
            points.set(points.size() - 1, board.getHexes().getValue(hexCoords));
            prevDirection = direction.copy();
            direction.sum(points.get(points.size() - 1).influenceVector);
            if (!Vector3D.isNormalised(direction)) { return firstLabel; }
            if (direction.equals(zeroVector)) {
                hexCoords.sum(prevDirection);
                if (board.getHexes().getValue(hexCoords).hasTrueAtom()) {
                    points.set(points.size() - 1, board.getHexes().getValue(hexCoords));
                    return null;
                } else {
                    direction = Vector3D.opposite(prevDirection);
                    hexCoords.sum(direction);
                }
            }
            hexCoords.sum(direction);
        }

        return points.get(points.size() - 1).getBoardLabelAtCoords(direction);
    }

    public void drawRay() {
        Graphics2D g = GamePanel.get().graphics;
        g.setColor(this.rayMarkers.color);
        Stroke prevStroke = g.getStroke();
        g.setStroke(new BasicStroke(3));

        for (int i = 0; i+1 < points.size(); i++) {
            g.drawLine(
                    (int)points.get(i).center().x,
                    (int)points.get(i).center().y,
                    (int)points.get(i+1).center().x,
                    (int)points.get(i+1).center().y
            );
        }
         g.setStroke(prevStroke);
    }

    public String announcement() {
        return (switch (result) {
            case ABSORBED -> "Absorbed.";
            case REFLECTED -> "Reflected.";
            case DEFLECTED -> "Deflected.";
        });
    }

    //debug only
    public void printRayPath() {
        HexBoard board = GamePanel.get().board;
        for(Hexagon hex : points) {
            System.out.print(board.getHexes().getKey(hex).toString() + " ");
        }
    }

    public int getScore() {
        return (result == Result.DEFLECTED) ? 2 : 1;
    }
}
