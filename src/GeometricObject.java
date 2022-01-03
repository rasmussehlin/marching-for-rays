/**
 * Objects that exist in my computer world of 3D.
 */
public abstract class GeometricObject {
    protected Vector3 pos;
    protected Vector3 dir;
    protected Color color;
    public boolean renderMe;

    public GeometricObject() {
        renderMe = true;
    }

    public double distance(Vector3 v) {
        return pos.subtract(v).getLength();
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public Vector3 getPosition(){
        return new Vector3(pos.getX(), pos.getY(), pos.getZ());
    }

    public void setPosition(Vector3 v) {
        pos.setX(v.getX());
        pos.setY(v.getY());
        pos.setZ(v.getZ());
    }

    /**
     * Get x position.
     * @return returns x position.
     */
    public double getX() {
        return pos.getX();
    }

    /**
     * Set x position.
     * @param x x position to set.
     */
    public void setX(double x) {
        this.pos.setX(x);
    }

    /**
     * Get y position.
     * @return returns y position.
     */
    public double getY() {
        return pos.getY();
    }

    /**
     * Set y position.
     * @param y y position to set.
     */
    public void setY(double y) {
        this.pos.setY(y);
    }

    /**
     * Get z position.
     * @return returns z position.
     */
    public double getZ() {
        return pos.getZ();
    }

    /**
     * Set z position.
     * @param z z position to set.
     */
    public void setZ(double z) {
        this.pos.setZ(z);
    }
}
