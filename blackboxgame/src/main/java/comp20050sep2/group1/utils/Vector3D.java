package comp20050sep2.group1.utils;

public class Vector3D {
    public int q, r, s;

    public Vector3D(int q, int r, int s) {
        this.q = q;
        this.r = r;
        this.s = s;
    }

    public Vector3D scalMult(int scalar) {
        return new Vector3D(q*scalar, r*scalar, s*scalar);
    }

    public static Vector3D angleToCoors(int angle) {
        return switch (angle%360) {
            case 30 -> new Vector3D(1, -1, 0);
            case 90 -> new Vector3D(1, 0, -1);
            case 150 -> new Vector3D(0, 1, -1);
            case 210 -> new Vector3D(-1, 1, 0);
            case 270 -> new Vector3D(-1, 0, 1);
            case 330 -> new Vector3D(0, -1, 1);
            default -> null;
        };
    }

    @Override
    public String toString() {
        return "[ " + q + ", " + r + ", " + s +" ]";
    }
}
