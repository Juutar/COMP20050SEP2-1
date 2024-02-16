package comp20050sep2.group1.utils;

public class Vector2D {

    public double x, y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D ref) {
        this.x = ref.x;
        this.y = ref.y;
    }

    public Vector2D addip(Vector2D o) {
        return addip(o.x, o.y);
    }

    public Vector2D addip(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2D mul(double s) {
        return new Vector2D(x * s, y * s);
    }

    public Vector2D mul(double s, double sy) {
        return new Vector2D(x * s, y * sy);
    }

    public double distanceSquared(Vector2D o) {
        return distanceSquared(o.x, o.y);
    }

    public double distanceSquared(double x, double y) {
        return Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2);
    }
}
