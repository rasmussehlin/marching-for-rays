import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * The viewport handles the actual window and the rendering to the window.
 * This is probably where the ray-tracing(marching?) happens.
 */
public class Viewport {
    private static double MAX_INTENSITY = 1;
    private boolean raymarching;
    private static double HIT_THRESHOLD = 0.001;
    private static double OUT_OF_SIGHT = 20;
    private static Color OUT_OF_SIGHT_COLOR = new Color(255, 50,50,50);
    private JFrame frame;
    private int pixelWidth;
    private int pixelHeight;
    private GeometricObject hitObject;

    public Viewport(boolean raymarching, int pixelWidth, int pixelHeight) {
        this.raymarching = raymarching;

        // Create JFrame
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(pixelWidth + 50, pixelHeight + 200);
        frame.setVisible(true);

        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;

        hitObject = null;
    }

    /**
     * Raymarch render
     * @param cam camera object
     * @param scene scene arraylist with objects
     */
    public void render(Camera cam, Scene scene) {
        // Render pixels!
        int[] data = new int[pixelWidth * pixelHeight];

        BufferedImage bi;

        if (raymarching) {
            for (int y = 0; y < pixelHeight; y++) {
                for (int x = 0; x < pixelWidth; x++) {
                    //System.out.print(y + ": ");
                    Color result = generatePixel(cam.pos, cam.getDirectionVector(x, y), scene);
                    if (result != null) {
                        // Save color-data to array
                        data[y * pixelWidth + x] = result.getColorInt();
                    } else {
                        data[y * pixelWidth + x] = Color.getColorInt(255, 100, 100, 125);
                    }
                }
            }
        } else {/* Raytrace... */}

        // Create BufferedImage to write to file, from rendered data.
        bi = new BufferedImage(pixelWidth, pixelHeight, BufferedImage.TYPE_INT_ARGB);
        bi.setRGB(0, 0, pixelWidth, pixelHeight, data, 0, pixelWidth);
        drawToJFrame(bi, pixelWidth, pixelHeight);

        // Save file
        //saveImage(bi);
    }

    /**
     * Raymarch from from in direction of dir, generate pixel
     * @param from position to start at
     * @param dir direction vector
     * @return getLength from 'from' to 'to'
     */
    private Color generatePixel(Vector3 from, Vector3 dir, Scene scene) {
        Vector3 currentPosition = from;

        currentPosition = rayMarch(currentPosition, scene, dir);

        if (hitObject == null) {
            return null;
        } else {
            return shade(currentPosition, hitObject, scene);
        }
    }

    /**
     * Actual raymarch. Sets "hitObject" to object hit, NULL if no object hit.
     * Marches from "currentPosition" in direction "dir" (normalized).
     * @param currentPosition
     * @param scene
     * @param dir
     */
    private Vector3 rayMarch(Vector3 currentPosition, Scene scene, Vector3 dir) {
        double length = HIT_THRESHOLD + 0.01;

        while (length > HIT_THRESHOLD && length < OUT_OF_SIGHT) {
            length = shortestDistanceInScene(currentPosition, scene.getGeometricObjects());
            currentPosition = currentPosition.add(dir.scale(length - length * 0.01));
        }

        if (length >= OUT_OF_SIGHT) {
            hitObject = null;
        }

        return currentPosition;
    }

    private double shortestDistanceInScene(Vector3 p, ArrayList<GeometricObject> objects) {
        double distance = OUT_OF_SIGHT;

        for (GeometricObject obj : objects) {
            double thisDistance = obj.distance(p);
            if (thisDistance < distance) {
                distance = thisDistance;
                hitObject = obj;
            }
        }

        return distance;
    }

    private Color shade(Vector3 currentPosition, GeometricObject obj, Scene scene) {
        Color color;

        // If we hit an object in the scene...
        if (obj != null) {
            // We start with the colors from the object
            int a = obj.getColor().getAlpha();
            int r = obj.getColor().getRed();
            int g = obj.getColor().getGreen();
            int b = obj.getColor().getBlue();

            Vector3 objPosition = obj.getPosition();

            double intensity = 0;

            // ... calculate color by raymarching to lights.
            for (Light light : scene.getLights()) {
                // Check if there is a geometric object between currentPosition and light
                // Object in the way? -> No light (duh)
                rayMarch(new Vector3(currentPosition.add(currentPosition.subtract(objPosition).normalize())), scene, light.getPosition().subtract(currentPosition).normalize());
                if (hitObject == null) {
                    // Calculate angle between the speheres normalvector at currentPosition and the vecot currentPosition -> light
                    // 0 - 90 degrees == 0.0 - 1.0 multiplier
                    Vector3 normSph = currentPosition.subtract(objPosition).normalize();
                    Vector3 currToLight = light.getPosition().subtract(currentPosition).normalize();
                    double cosAngle = (normSph.dotMultiply(currToLight)) / (normSph.getLength() * currToLight.getLength());

                    // System.out.println("Lightfactor: " + cosAngle);
                    if (cosAngle > 0) {
                        intensity += light.getIntensity() * cosAngle;
                    }
                }
            }

            double lightVal = intensity / MAX_INTENSITY;
            if (lightVal > MAX_INTENSITY) {
                lightVal = MAX_INTENSITY;
            }

            color = new Color(a, (int)(r*lightVal), (int)(g*lightVal), (int)(b*lightVal));
        } else {
            color = OUT_OF_SIGHT_COLOR;
        }

        return color;
    }

    /**
     * Creates random image data for specific size
     * @param size size of data array (pixelWidth * pixelHeight)
     * @return pixel data int[], ARGB
     */
    private int[] randomImageDataARGB(int size) {
        int[] data = new int[size];

        Random rand = new Random();

        for (int i = 0; i < data.length; i++) {
            int a = rand.nextInt(255);
            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);

            int p = (a << 24) | (r << 16) | (g << 8) | b;

            data[i] = p;
        }

        return data;
    }

    /**
     * Draw to screen
     * @param bi buffered image to draw
     * @param pixelWidth size of image width
     * @param pixelHeight size of image height
     */
    private void drawToJFrame(BufferedImage bi, int pixelWidth, int pixelHeight) {
        frame.getContentPane().removeAll();

        // Create JPanel
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bi, 0, 0, null);
            }
        };
        frame.add(panel);

        // Redraw
        frame.getContentPane().validate();
        frame.getContentPane().repaint();
    }

    /**
     * Save image to drive. .png format
     * @param bi buffered image to save
     */
    private void saveImage(BufferedImage bi) {
        // https://docs.oracle.com/javase/tutorial/2d/images/saveimage.html
        try {
            // retrieve image
            File outputFile = new File("saved.png");
            ImageIO.write(bi, "png", outputFile);
            System.out.println("Saved file!");
        } catch (IOException e) {
                System.out.println("ERROR: " + e);
        }
    }
}
