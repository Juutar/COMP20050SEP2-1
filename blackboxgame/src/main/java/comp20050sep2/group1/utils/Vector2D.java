package comp20050sep2.group1.utils;

public class Vector2D {

    public double x, y;

    /**
     * Creates a vector with default values
     */
    public Vector2D() {
        this(0, 0);
    }

    /**
     * Creates a vector based on passed arguments
     * @param x X coordinate of the vector object
     * @param y y coordinate of the vector object
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a vector based on a passed reference to another vector
     * @param ref Vector whose values are copied to construct this object
     */
    public Vector2D(Vector2D ref) {
        this.x = ref.x;
        this.y = ref.y;
    }

    /**
     * Use to check equality of the values of this vector with the passed vector
     * @param o Vector to be compared against
     * @return
     */
    public boolean equals(Vector2D o) {
        return o.x == x && o.y == y;
    }

    /**
     * Adds values to the coordinates of this vector based on the coordinates of the passed vector
     * @param o Reference to the vector whose values are to be added
     * @return this vector
     */
    public Vector2D addip(Vector2D o) {
        return addip(o.x, o.y);
    }

    /**
     * Adds values to the x and y coordinates of this vector 
     * @param x value to be added to x coordinate of this vector
     * @param y value to be added to y coordinate of this vector
     * @return this vector
     */
    public Vector2D addip(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /** Translating the coordinates of this vector
     * @param s Translation factor
     * @return this vector
     */
    public Vector2D mulip(double s) {
        this.x *= s;
        this.y *= s;
        return this;
    }

    /**
     * Translating coordinates by different factor
     * @param sX Translation factor for x
     * @param sy Translation factor for y
     * @return this vector
     */
    public Vector2D mulip(double sX, double sy) {
        this.x *= sX;
        this.y *= sy;
        return this;
    }

    /**
     * Subtract the coordinates of this vector with the passed values
     * @param x value to be subtracted by x coordinate
     * @param y value to be subtracted by y coordinate
     * @return new vector
     */
    public Vector2D sub(double x, double y) {
        return new Vector2D(this.x - x, this.y - y);
    }

    /**
     * Subtract the coordinates from the coordinates passed
     * @param o Vector reference
     * @return new vector
     */
    public Vector2D sub(Vector2D o) {
        return new Vector2D(x - o.x, y - o.y);
    }

    /**
     * Adds coordinates to this vector and returns new vector
     * @param x value to add to x
     * @param y value to add to y
     * @return new vector
     */
    public Vector2D add(double x, double y) {
        return new Vector2D(this.x + x, this.y + y);
    }

    /**
     * Adds coordinates to this vector based on vector passed as an argument and returns new vector
     * @param o Reference vector
     * @return new vector
     */
    public Vector2D add(Vector2D o) {
        return new Vector2D(x + o.x, y + o.y);
    }

    /** 
     * Scaling coordinates of this vector
     * @param s Scale factor
     * @return new vector
     */
    public Vector2D mul(double s) {
        return new Vector2D(x * s, y * s);
    }

    /**
     * Scales components by differnet factors
     * @param sX x scaling factor
     * @param sy y scaling factor
     * @return new vector
     */
    public Vector2D mul(double sX, double sy) {
        return new Vector2D(x * sX, y * sy);
    }

    /**
     * Calculates the squared distance between this and another vector
     * @param o Vector from which this vectors distance is calculated
     * @return the squared distance
     */
    public double distanceSquared(Vector2D o) {
        return distanceSquared(o.x, o.y);
    }

    /**
     * Calculates the squared distance between this and a set of coordinates
     * @param x x coordinate
     * @param y y coordinate
     * @return the distance squared
     */
    public double distanceSquared(double x, double y) {
        return Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2);
    }
}
