public class Main {
    /* Nödvändiga parametrar */
    private Viewport vp;
    // Contains everything except camera
    private Scene scene;
    private Camera camera;
    //// Pixel size
    private int pixelWidth;
    private int pixelHeight;


    /**
     * Constructor. Set default values.
     */
    public Main() {
        // Rendersize
        pixelWidth = 429;
        pixelHeight = 240;

        // Viewport and scene
        vp = new Viewport(true, pixelWidth, pixelHeight);
        scene = new Scene();

        // Initialize basic scene
        Initialize(scene);

        int i = 0;
        boolean direction = true;
        boolean ldirection = true;
        double stepSize = 0.05;
        Light l = scene.getLights().get(0);
        while (true) {
            i++;
            if (camera.getY() > 4 || camera.getY() < -10) {
                direction = !direction;
            }

            if (direction) {
                camera.setX(camera.getX() + stepSize);
                camera.setY(camera.getY() + stepSize);
                camera.setZ(camera.getZ() + stepSize);
            } else {
                camera.setX(camera.getX() - stepSize);
                camera.setY(camera.getY() - stepSize);
                camera.setZ(camera.getZ() - stepSize);
            }

      /*      if (l.getY() > 4 || l.getY() < -4) {
                ldirection = !ldirection;
            }
            if(ldirection)
                l.setY(l.getY() - 0.4);
            else
                l.setY(l.getY() + 0.4);*/



            camera.setDir(new Vector3(0, 0,0).subtract(camera.getPosition()));

            // Raymarch
            System.out.println("Raytracing commends..." + i);
            vp.render(camera, scene);
        }
    }

    public void Initialize(Scene scene) {
        // Camera
        Vector3 cameraPos = new Vector3(4, -4, 0);
        Vector3 cameraDirection = new Vector3(0, 0, 0).subtract(cameraPos);
        camera = new Camera(cameraPos,
                cameraDirection,
                1,
                pixelWidth,
                pixelHeight);

        // add spheres
        Sphere sph = new Sphere(new Vector3(0, 0,0), 1, new Color(255, 88, 152, 34));
        Sphere sph2 = new Sphere(new Vector3(-3, 1,3), 1, new Color(255, 23, 255, 50));
        Sphere sph3 = new Sphere(new Vector3(-8, -2,1.8), 1, new Color(255, 23, 137, 130));
        Sphere sph4 = new Sphere(new Vector3(-8, -2,1.8), 1, new Color(255, 23, 137, 130));
        scene.addGeometricObject(sph);
        scene.addGeometricObject(sph2);
        scene.addGeometricObject(sph3);
        scene.addGeometricObject(sph4);

        // add light
        Light light = new Light(new Vector3(5, -3, 2), 1);
        scene.addLight(light);
        //Light light2 = new Light(new Vector3(5, 3, 0), 1);
        //scene.addLight(light2);

        System.out.println("Camera direction of view: " + camera.getDir().toString());
        System.out.println("Camerapos + 4*dir = " + camera.pos.add(camera.getDir().scale(4)).toString());
        System.out.println("Sphere pos: " + sph.pos.toString() + " Sphere color: " + sph.getColor());

        // add plane
        // ...
    }

    /**
     * Main-metod: nu kör vi!
     * @param args
     */
    public static void main(String [] args) {
        Main m = new Main();
    }
}

