/**
 * Three dimensional vector
 */
public class Vector3 {
    private double x;
    private double y;
    private double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3(Vector3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public Vector3 add(Vector3 v) {
        return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    public Vector3 add(double s) {
        return new Vector3(this.x + s, this.y + s, this.z + s);
    }

    public Vector3 subtract(Vector3 v) {
        return new Vector3(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    public Vector3 subtract(double s) {
        return new Vector3(this.x - s, this.y - s, this.z - s);
    }

    public double getLength() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public Vector3 normalize() {
        double length = getLength();
        return new Vector3(this.x / length, this.y / length, this.z / length);
    }

    public Vector3 scale(double scalar) {
        return new Vector3(x * scalar, y * scalar, z * scalar);
    }

    public Vector3 crossMultiply(Vector3 v) {
        Vector3 crossProduct = new Vector3(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x
        );

        return crossProduct;
    }

    public double dotMultiply(Vector3 v) {
        double dotProduct = x * v.x + y * v.y + z * v.z;
        return dotProduct;
    }

    /**
     * Project this vector onto v.
     * @param v vector to project onto
     * @return projected vector
     */
    public Vector3 projectOn(Vector3 v) {
        double factor = this.dotMultiply(v) / Math.pow(v.getLength(), 2);
        Vector3 projection = v.scale(factor);
        return  projection;
    }

    @Override
    public String toString() {
        String result = "(" + x + ", " + y + ", " + z + ")";
        return result;
    }

    /**
     * @return x position
     */
    public double getX() {
        return x;
    }

    /**
     * Set x position
     * @param x x position to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return y position
     */
    public double getY() {
        return y;
    }

    /**
     * Set y position
     * @param y y position to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return z position
     */
    public double getZ() {
        return z;
    }

    /**
     * Set z position
     * @param z z position to set
     */
    public void setZ(double z) {
        this.z = z;
    }
}
