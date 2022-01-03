import java.util.ArrayList;

public class Scene {
    private ArrayList<Light> lights;
    public ArrayList<GeometricObject> geometricObjects;

    public Scene() {
        lights = new ArrayList<>();
        geometricObjects = new ArrayList<>();
    }

    public void addGeometricObject(GeometricObject g) {
        geometricObjects.add(g);
    }

    public void addLight(Light l) {
        lights.add(l);
    }

    public ArrayList<Light> getLights() {
        return (ArrayList) lights.clone();
    }

    public ArrayList<GeometricObject> getGeometricObjects() {
        return (ArrayList) geometricObjects.clone();
    }

    public ArrayList<GeometricObject> getAllObjects() {
        ArrayList<GeometricObject> toReturn = (ArrayList<GeometricObject>) geometricObjects.clone();
        for (Light light : lights) {
            toReturn.add(light);
        }

        return toReturn;
    }

}
