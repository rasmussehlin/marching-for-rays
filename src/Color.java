public class Color {
    private int alpha;
    private int red;
    private int green;
    private int blue;

    public Color(int a, int r, int g, int b) {
        alpha = a;
        red = r;
        green = g;
        blue = b;
    }

    public static int getColorInt(int a, int r, int g, int b) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public int getColorInt() {
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    public int getAlpha() {
        return alpha;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

}
