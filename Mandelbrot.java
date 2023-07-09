
public class Mandelbrot extends AbstractFractal {

    public Mandelbrot() {
        super();
    }

    @Override
    void drawFractal(int px, int py) {
        double cR = (px - xOffset) / zoom;
        double cI = (py - yOffset) / zoom;

        double zr = 0;
        double zi = 0;
        int i = 0;

        double pr = zr * zr;
        double pi = zi * zi;
        while (pr + pi <= 4.0 && i < iMax) {
            zi = 2 * zr * zi + cI;
            zr = pr - pi + cR;
            pr = zr * zr;
            pi = zi * zi;
            i++;
        }
        if (i < iMax) {
            img.setRGB(px, py, lerpColor(backColor, borderColor, Math.sqrt((double) i / iMax)));
        } else {
            img.setRGB(px, py, bodyColor.getRGB());
        }
    }
}
