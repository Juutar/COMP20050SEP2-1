package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;
import comp20050sep2.group1.utils.Vector3D;

import java.awt.*;
import java.util.ArrayList;

public class HexBoard {

    public Vector2D pos;

    private ArrayList<Hexagon> hexes = new ArrayList<>();
    private double side;
    private int size;

    public HexBoard(double side, Vector2D pos, int size /* from 0, how many rings */) {
        hexes.add(new Hexagon(side, pos));
        this.side = side;
        this.pos = pos;
        this.size = size;

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
        }

        // test purposes
        g.drawRect((int)this.pos.x - 3, (int)this.pos.y - 3, 6, 6);

        // highlight nearest hex
        Hexagon nearest = closestHexToCoords(GamePanel.get().mouseCoords);
        g.drawRect((int)nearest.center().x - 10, (int)nearest.center().y - 10, 20, 20);
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
}
