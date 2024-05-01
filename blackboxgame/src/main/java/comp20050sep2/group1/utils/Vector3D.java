package comp20050sep2.group1.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Vector3D {
    public int q, r, s;

    /**
     * 
     * Constucts a 3d vector through arguments
     * 
     * @param q - First coordinate
     * @param r - Second coordinate
     * @param s - Third coordinate
     */
    public Vector3D(int q, int r, int s) {
        if (q + r + s == 0) {
            this.q = q;
            this.r = r;
            this.s = s;
        }
    }

    /**
     * 
     * Constucts a 3d vector by copying values of another vector
     * 
     * @param ref - Vector reference from which values are copied
     */
    public Vector3D(Vector3D ref) {
        this.q = ref.q;
        this.r = ref.r;
        this.s = ref.s;
    }

    /**
     * 
     * Constucts a the zero vector of 3D vector space
     * 
     * 
     */
    public Vector3D() {
        this.q = 0;
        this.r = 0;
        this.s = 0;
    }

    /**
     * @param a First vector to be added
     * @param b Second vector to be added
     * @return The new summed vector
     */
    public static Vector3D binaryAdd(Vector3D a, Vector3D b) {
        return new Vector3D(a.q + b.q, a.r + b.r, a.s + b.s);
    }

    /**
     * Calculates and returns the inverted vector of the one passed as an argument
     * @param vec Vector who's magnitudal inverse is to be calculated
     * @return new inversed vector
     */
    public static Vector3D addInv(Vector3D vec) {
        return new Vector3D(-1 * vec.q, -1 * vec.r, -1 * vec.s);
    }

    /**
     * Rests the coordinates of the vector if they are valid
     * @param q Value to replace coordinate q of this object
     * @param r Value to replace coordinate r of this object
     * @param s Value to replace coordinate s of this object
     */
    public void setCoors(int q, int r, int s) {
        if (q + r + s == 0) {
            this.q = q;
            this.r = r;
            this.s = s;
        }
    }

    
    /**
     * 
     * calculate and reset values of vector to a normalised vector based on angle passed
     * @param angle Angle to reset the vector
     */
    public void setCoorsFromAngle(int angle) {
        switch (angle % 360) {
            case 30 -> setCoors(1, -1, 0);
            case 90 -> setCoors(1, 0, -1);
            case 150 -> setCoors(0, 1, -1);
            case 210 -> setCoors(-1, 1, 0);
            case 270 -> setCoors(-1, 0, 1);
            case 330 -> setCoors(0, -1, 1);
        }
    }

    //this function works relative to the x-axis
    /**
     * 
     * Finds neighbouring closest vector based on passed angle and defaults to itself if the angle is invalid
     * @param angle
     * @return neightbouring vector situated at passed angle
     */
    public Vector3D getNeighbouringCoords(int angle) {
        switch (angle % 360) {
            case 0 -> {
                return new Vector3D(1, 0, -1);
            }
            case 60 -> {
                return new Vector3D(0, 1, -1);
            }
            case 120 -> {
                return new Vector3D(-1, 1, 0);
            }
            case 180 -> {
                return new Vector3D(-1, 0, 1);
            }
            case 240 -> {
                return new Vector3D(0, -1, 1);
            }
            case 300 -> {
                return new Vector3D(1, -1, 0);
            }
            default -> {
                return new Vector3D(0, 0, 0);
            }        //itself
        }
    }

    /**
     * Adds a vector to this object
     * @param vec Vector with which this vector is to be added
     */
    public void sum(Vector3D vec) {
        this.q += vec.q;
        this.r += vec.r;
        this.s += vec.s;
    }

    /**
     * Calculates and returns the opposite vector of the one passed as an argument
     * @param vec Vector who's magnitudal inverse is to be calculated
     * @return new opposite vector
     */
    public static Vector3D opposite(Vector3D vec) {
        return new Vector3D(vec.q*(-1), vec.r*(-1), vec.s*(-1));
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

    /**
     * @return a new copy of this vector
     */
    public Vector3D copy() {
        return new Vector3D(q, r, s);
    }

    /**
     * Checks if a vector is normalised
     * @param vec Vector on which a normalisation check is performed
     * @return a boolean based on whether the check was passed
     */
    public static boolean isNormalised(Vector3D vec){
        if((Math.abs(vec.q) > 1) || (Math.abs(vec.r) > 1) || (Math.abs(vec.s) > 1)){
            return false;
        }

        return true;

    }

    @Override
    public int hashCode() {
        return Objects.hash(q, r, s);
    }

}
