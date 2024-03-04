package comp20050sep2.group1.utils;

import java.util.Objects;

public class Vector3D {
    public int q, r, s;

    public Vector3D(int q, int r, int s) {
        if(q + r + s == 0) {
            this.q = q;
            this.r = r;
            this.s = s;
        }
    }

    public Vector3D() {
        this.q = 0;
        this.r = 0;
        this.s = 0;
    }

    public void setCoors(int q, int r, int s) {
        if (q + r + s == 0) {
            this.q = q;
            this.r = r;
            this.s = s;
        }
    }

    public void setCoorsFromAngle(int angle) {
        switch (angle%360) {
            case 30 -> setCoors(1, -1, 0);
            case 90 -> setCoors(1, 0, -1);
            case 150 -> setCoors(0, 1, -1);
            case 210 -> setCoors(-1, 1, 0);
            case 270 -> setCoors(-1, 0, 1);
            case 330 -> setCoors(0, -1, 1);
        };
    }


    //this function works relative to the x-axis
    public Vector3D getNeighbouringCoords(int angle){
        switch(angle % 360){
            case 0 -> { return new Vector3D(angle, angle, angle); }
            case 60 -> { return new Vector3D(angle, angle, angle); }
            case 120 -> { return new Vector3D(angle, angle, angle); }
            case 180 -> { return new Vector3D(angle, angle, angle); }
            case 240 -> { return new Vector3D(angle, angle, angle); }
            case 300 -> { return new Vector3D(angle, angle, angle); }
            default -> { return new Vector3D(0, 0, 0); }        //itself
        }
    }

    public void sum(Vector3D vec) {
        this.q = q + vec.q;
        this.r = r + vec.r;
        this.s = s + vec.s;
    }

    public Vector3D addInv(){
        return new Vector3D(-1 * this.q, -1 * this.r, -1 * this.s);
    }

    @Override
    public String toString() {
        return "[ " + q + ", " + r + ", " + s + " ]";
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Vector3D)
                && this.q == ((Vector3D) o).q
                && this.r == ((Vector3D) o).r
                && this.s == ((Vector3D) o).s;
    }

    public Vector3D copy() {
        return new Vector3D(q, r, s);
    }

    @Override
    public int hashCode() {
        return Objects.hash(q, r, s);
    }
}
