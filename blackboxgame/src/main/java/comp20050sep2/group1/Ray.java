package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;
import comp20050sep2.group1.utils.Vector3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Ray {

    public final Hexagon start;
    private final Vector2D forward;
    public ArrayList<Hexagon> points;
    public Hexagon end = null;
    public boolean exited = false;
    private Vector2D exitCoord;

    public Ray(Hexagon startHex_, Vector2D forward_) {
        this.points = new ArrayList<>();
        this.points.add(startHex_);
        this.forward = forward_;
        this.start = startHex_;

        Vector3D oldVel;

        Vector3D vel = Vector3D.addInv(GamePanel.get().lastMousePoint);
        oldVel = vel.copy();

        if (startHex_.hasTrueAtom()) {
            System.out.println("atom on edge hit");
            return;
        }

        if (startHex_.underInfluence) {
            vel.sum(startHex_.influenceVector);
        }

        if (vel.q == 0 && vel.r == 0 && vel.s == 0) {     //when this happens an atom has def been hit
            System.out.println("Atom hit!");

        }

        if ((vel.q + vel.r + vel.s) != 0) {
            vel = startHex_.influenceVector.copy();
        }

        setNext(vel);

        HexBoard board = GamePanel.get().board;

        for (int i = 0; i < board.getHexes().size(); i++) {      //i j picked the limit as a big number, there should be a break statement in this when the end is reached

            oldVel = vel.copy();

            if (points.get(points.size() - 1).underInfluence) {       //if the hexagon where the ray is at is under any influence
                vel.sum(points.get(points.size() - 1).influenceVector);     //then add the influence to the velocity
                if (vel.q == 0 && vel.r == 0 && vel.s == 0) {                                                                         //if the sum is a zero vector
                    if (board.getHexes().getValue(Vector3D.binaryAdd(board.getHexes().getKey(points.get(points.size() - 1)), oldVel)).hasTrueAtom()) {    //if the original velocity + position of the ray = hexagon with atom => atom hit
                        System.out.println("atom hit!");
                        exited = false;
                        break;
                    } else {       //otherwise just a reflection (180 degree deflection)
                        vel = Vector3D.addInv(oldVel);
                        System.out.println("rotated 180 degrees no atom hit!");
                    }
                }
            }

            setNext(vel);
            if (points.get(points.size() - 1) == null) {
                points.remove(points.size() - 1);
                System.out.println("the ray reached");
                exited = true;
                break;
            }

        }

        end = points.get(points.size() - 1);
    }

    public static void drawRayHexes(Hexagon a, Hexagon b) {

        GamePanel.get().graphics.setColor(Color.gray);
        Stroke prevStroke = GamePanel.get().graphics.getStroke();
        GamePanel.get().graphics.setStroke(new BasicStroke(3));

        GamePanel.get().graphics.drawLine(
                (int) a.center().x,
                (int) a.center().y,
                (int) b.center().x,
                (int) b.center().y
        );

        GamePanel.get().graphics.setStroke(prevStroke);

    }

    public void addHextoRoute(Hexagon h) {
        this.points.add(h);
    }

    public void drawRay(boolean hover) {

        if (points.isEmpty()) {
            return;
        }

        Vector2D rayHead = points.get(0).pos.sub(forward.mul(0.5));

        Graphics2D g = GamePanel.get().graphics;

        g.setColor(hover ? Color.WHITE : (exited ? Color.GREEN : Color.RED));
        g.setStroke(new BasicStroke(3));

        g.drawLine((int) rayHead.x, (int) rayHead.y, (int) rayHead.x - (int) forward.x, (int) rayHead.y - (int) forward.y);

        if (points.size() < 2 || !exited) {
            return;
        }

        Vector2D rayTail = exitCoord;
        Vector2D rayTail2 = points.get(points.size() - 1).pos;
        Vector2D backwards = rayTail2.sub(rayTail);
        rayTail2 = rayTail.add(backwards.mul(0.75));

        g.drawLine((int) rayTail.x, (int) rayTail.y, (int) rayTail2.x, (int) rayTail2.y);

        // only for debugging

        // Iterator<Hexagon> iterator = points.iterator();

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

    public void drawToLabel(BoardLabel label, boolean entry) {       //entry false = exiting the board, entry true = entering the board

        Graphics2D graphics = GamePanel.get().graphics;
        graphics.setColor(Color.gray);
        Stroke prevStroke = graphics.getStroke();
        graphics.setStroke(new BasicStroke(3));

        if (entry) {
            GamePanel.get().graphics.drawLine((int) label.pos.x, (int) label.pos.y, (int) points.get(0).center().x, (int) points.get(0).center().y);
        } else if (!entry) {
            GamePanel.get().graphics.drawLine((int) label.pos.x, (int) label.pos.y, (int) points.get(points.size() - 1).center().x, (int) points.get(points.size() - 1).center().y);
        }

        graphics.setStroke(prevStroke);

    }

    public void setNext(Vector3D vel) {

        Vector3D next = Vector3D.binaryAdd(vel, GamePanel.get().board.getHexes().getKey(points.get(points.size() - 1)));

        if (GamePanel.get().board.getHexes().getKeySet().contains(next)) {
            points.add(GamePanel.get().board.getHexes().getValue(next));
        } else {
            points.add(null);
            exitCoord = GamePanel.get().board.hexCoordsToCenter(next);
        }

    }

    public void setNextHex(Hexagon h) {
        points.add(h);
    }

}
