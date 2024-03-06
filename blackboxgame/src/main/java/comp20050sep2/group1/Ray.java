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
        this.result = lastLabel == null ? Result.ABSORBED : (points.getFirst().equals(points.getLast()) ? Result.REFLECTED : Result.DEFLECTED);
        System.out.println(announcement());

        this.rayMarkers = new RayMarker(this);
    }

    private BoardLabel computePath() {
        //lacking reflected on entry edge case
        HexBoard board = GamePanel.get().board;

        Vector3D direction = firstLabel.rayDirection.copy();
        Vector3D hexCoords = board.getHexes().getKey(firstLabel.hexagon).copy();
        Vector3D zeroVector = new Vector3D();

        while (board.getHexes().getKeySet().contains(hexCoords) && (points.isEmpty() || !direction.equals(zeroVector))) {
            points.add(board.getHexes().getValue(hexCoords));
            direction.sum(points.getLast().influenceVector);
            hexCoords.sum(direction);
        }

        return !GamePanel.get().board.isOuterHex(points.getLast()) ? null : points.getLast().getBoardLabelAtCoords(direction);
    }

    public void drawRay(boolean hover) {
        Graphics2D g = GamePanel.get().graphics;

        // Hexagon prevHex = iterator.next();
        // Hexagon currHex;

        // g.setColor(Color.gray);
        // Stroke prevStroke = g.getStroke();
        // g.setStroke(new BasicStroke(3));

        // while(iterator.hasNext()){

        //     currHex = iterator.next();

        //     g.drawLine(
        //         (int)prevHex.center().x,
        //         (int)prevHex.center().y,
        //         (int)currHex.center().x,
        //         (int)currHex.center().y
        //     );

        //     prevHex = currHex;

        // }

        // g.setStroke(prevStroke);

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
}
