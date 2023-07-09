
import javax.swing.*;

import java.awt.*;
import java.awt.image.*;

public class AbstractFractal extends FractalPanel {
    BufferedImage img;
    int iterations = 0;
    double iStart;
    int iMax;
    // int gradientMax = 50;

    Color backColor = new Color(0, 0, 100);
    Color borderColor = Color.white;
    Color bodyColor = Color.black;

    public AbstractFractal() {
        super();
        xOffset = WIDTH / 2.0;
        yOffset = HEIGHT / 2.0;
        zoom = WIDTH / 4.0;
        prevZoom = zoom;
        MINZOOM = zoom;
        iStart = zoom;

        initSettings();
    }

    void initSettings() {
        colorSettings.put("BackR", backColor.getRed());
        colorSettings.put("BackG", backColor.getGreen());
        colorSettings.put("BackB", backColor.getBlue());

        colorSettings.put("BorderR", borderColor.getRed());
        colorSettings.put("BorderG", borderColor.getGreen());
        colorSettings.put("BorderB", borderColor.getBlue());

        colorSettings.put("BodyR", bodyColor.getRed());
        colorSettings.put("BodyG", bodyColor.getGreen());
        colorSettings.put("BodyB", bodyColor.getBlue());

        settings.add(getColorPicker("Set background color", "Back"));
        settings.add(getColorPicker("Set border color", "Border"));
        settings.add(getColorPicker("Set body color", "Body"));

        JPanel spinnerPanel = new JPanel();
        spinnerPanel.setBorder(BorderFactory.createTitledBorder("Set max iterations"));

        JSpinner iter = new JSpinner(new SpinnerNumberModel(iterations, 0, null, 1));
        iter.setPreferredSize(new Dimension(50, 25));
        iter.addChangeListener(e -> {
            iterations = (int) iter.getValue();
            repaint();
        });
        JLabel label = new JLabel("Iterations");
        spinnerPanel.add(label);
        spinnerPanel.add(iter);
        settings.add(spinnerPanel);

        // JPanel gradientPanel = new JPanel();
        // gradientPanel.setBorder(BorderFactory.createTitledBorder("Set max gradient
        // iterations"));

        // JSpinner grad = new JSpinner(new SpinnerNumberModel(gradientMax, 1, null,
        // 1));
        // grad.setPreferredSize(new Dimension(50, 25));
        // grad.addChangeListener(e -> {
        // gradientMax = (int) grad.getValue();
        // repaint();
        // });
        // JLabel gradLabel = new JLabel("Max");
        // gradientPanel.add(gradLabel);
        // gradientPanel.add(grad);
        // settings.add(gradientPanel);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        backColor = new Color(colorSettings.get("BackR"), colorSettings.get("BackG"), colorSettings.get("BackB"));
        borderColor = new Color(colorSettings.get("BorderR"), colorSettings.get("BorderG"),
                colorSettings.get("BorderB"));
        bodyColor = new Color(colorSettings.get("BodyR"), colorSettings.get("BodyG"), colorSettings.get("BodyB"));

        System.out.println((long) (4.0 * zoom / WIDTH));

        iMax = iterations == 0 ? (int) (100 + Math.sqrt(zoom / iStart)) : iterations;

        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        for (int px = 0; px < WIDTH; px++) {
            for (int py = 0; py < HEIGHT; py++) {
                drawFractal(px, py);
            }
        }

        g.drawImage(img, 0, 0, null);
    }

    void drawFractal(int px, int py) {
        img.setRGB(px, py, backColor.getRGB());
    }

    // Make this not linear like sqrt or smth idk
    int lerpColor(Color colA, Color colB, double k) {
        // if (i >= gradientMax) {
        // return colB.getRGB();
        // }
        // double k = (double) i / gradientMax;
        double nk = 1 - k;

        int r = (int) (colA.getRed() * nk + colB.getRed() * k);
        int g = (int) (colA.getGreen() * nk + colB.getGreen() * k);
        int b = (int) (colA.getBlue() * nk + colB.getBlue() * k);

        return new Color(r, g, b).getRGB();
    }
}
