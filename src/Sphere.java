/**
 * Object representation of a sphere.
 */
public class Sphere extends GeometricObject {
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    private double radius;

    /**
     * Constructir for sphere.
     * @param pos position of sphere (center)
     * @param radius radius of sphere
     */
    public Sphere(Vector3 pos, double radius, Color color) {
        super();
        this.pos = pos;
        this.radius = radius;
        this.color = color;
    }

    /**
     * Returns the distance from vector p to the EDGE of the sphere.
     * @param p from where to measure
     * @return the getLength between the two points.
     */
    @Override
    public double distance(Vector3 p) {
        double length = pos.subtract(p).getLength() - radius;
        return length;
    }
}
