/**
 * An object representation of a plane.
 */
public class Plane extends GeometricObject {
    // Two vectors defining the plane. P = position + a * v1 + b * v2
    private Vector3 v1;
    private Vector3 v2;

    public Plane(Vector3 pos, Vector3 v1, Vector3 v2, Color c) {
        super();
        this.color = c;
        this.pos = pos;
        this.v1 = v1;
        this.v2 = v2;
    }

    public Vector3 getNormal() {
        return v1.crossMultiply(v2);
    }

    @Override
    public double distance(Vector3 p) {
        double distance = p.projectOn(getNormal()).getLength();
        return distance;
    }
}
