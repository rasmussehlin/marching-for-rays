/**
 * Abstract light class.
 */
public class Light extends GeometricObject {
    private double intensity;

    public Light(Vector3 pos, double intensity) {
        this.pos = pos;
        this.intensity = intensity;
        renderMe = false;
    }

    public double getIntensity() {
        return this.intensity;
    }
}
