/**
 * The camera object in the 3D world.
 */
public class Camera extends GeometricObject {
    /*
        Focal getLength is set by increasing or decreasing
        the "screen" size, which exists 10 units from the
        camera.

        CHANGE: use getLength from camera to "screen" instead.
     */
    private double viewWidth;
    private double viewHeight;
    private int pixelWidth;
    private int pixelHeight;
    private double zoom;

    public Camera(Vector3 pos, Vector3 dir, double zoom, int pixelWidth, int pixelHeight) {
        this.pos = pos;
        this.dir = dir.normalize(); //normalized
        this.zoom = zoom;
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;

        // Testing with 16:9 as default :P
        viewWidth = 1.6;
        viewHeight = 0.9;
    }

    public Vector3 getDirectionVector(int pixelX, int pixelY) {
        // A(x-x0)+B(y-y0)+C(z-z0)=0

        // Find vector parallel to the XY-plane and orthogonal to the normal (dir)
        // (POINTS TO THE RIGHT ON SCREEN)
        Vector3 horizontal = dir.crossMultiply(new Vector3(0, 0, 1)).normalize();
        // Vertical vector (POINTS UP ON SCREEN)
        Vector3 vertical = horizontal.crossMultiply(dir).normalize();
        // Find first point (center of plane) in plane (screen)
        Vector3 centerPoint = pos.add(dir.scale(zoom));
        // Find top left corner of screen
        Vector3 topLeftCorner = centerPoint.add(horizontal.scale(-viewWidth/2)).add(vertical.scale(viewHeight/2));
        // Find pixelpoint
        double horizontalStep = viewWidth * pixelX / pixelWidth;
        double verticalStep = -viewHeight * pixelY / pixelHeight;
        Vector3 pixelPos = topLeftCorner.add(horizontal.scale(horizontalStep)).add(vertical.scale(verticalStep));

        return pixelPos.subtract(pos).normalize();
    }

    public Vector3 getDir() {
        return dir;
    }

    public void setDir(Vector3 v) {
        this.dir = v.normalize();
    }
}
