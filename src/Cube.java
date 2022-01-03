/**
 * The object representation of a Cube.
 */
public class Cube extends GeometricObject {
    // Size of the cube.
    private double width;
    private double height;
    private double depth;

    /**
     * Constructor
     * @param pos position of center of cube
     * @param width width, double size
     * @param height height, double size
     * @param depth depth, double size
     */
    public Cube(Vector3 pos, double width, double height, double depth) {
        super();
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.depth = depth;
    }
}
