
import javax.swing.*;
import java.awt.*;

public class Phoenix extends AbstractFractal {
    double c = 0.5667;
    double p = -0.5;

    public Phoenix() {
        super();

        JPanel pPanel = new JPanel();
        pPanel.setBorder(BorderFactory.createTitledBorder("Set complex constant (real value)"));
        JSpinner pSpinner = new JSpinner(new SpinnerNumberModel(p, null, null, 0.01));
        pSpinner.setPreferredSize(new Dimension(50, 25));
        pSpinner.addChangeListener(e -> {
            p = (double) pSpinner.getValue();
            repaint();
        });
        JLabel pLabel = new JLabel("P");
        pPanel.add(pLabel);
        pPanel.add(pSpinner);
        settings.add(pPanel);

        JPanel cPanel = new JPanel();
        cPanel.setBorder(BorderFactory.createTitledBorder("Set real constant"));
        JSpinner cSpinner = new JSpinner(new SpinnerNumberModel(c, null, null, 0.01));
        cSpinner.setPreferredSize(new Dimension(50, 25));
        cSpinner.addChangeListener(e -> {
            c = (double) cSpinner.getValue();
            repaint();
        });
        JLabel cLabel = new JLabel("C");
        cPanel.add(cLabel);
        cPanel.add(cSpinner);
        settings.add(cPanel);
    }

    @Override
    void drawFractal(int px, int py) {

        // Z(n)
        double zy = (px - xOffset) / zoom;
        double zx = -(py - yOffset) / zoom;
        // Z(n-1)
        double sx = 0;
        double sy = 0;
        int i = 0;

        double fx = zx * zx;
        double fy = zy * zy;
        while (fx + fy <= 4.0 && i < iMax) {

            fx = fx - fy + p * sx + c;
            fy = 2.0 * zx * zy + p * sy;
            sx = zx;
            sy = zy;
            zx = fx;
            zy = fy;
            fx = zx * zx;
            fy = zy * zy;
            i++;
        }
        if (i < iMax) {
            img.setRGB(px, py, lerpColor(backColor, borderColor, Math.sqrt((double) i / iMax)));
        } else {
            img.setRGB(px, py, bodyColor.getRGB());
        }
    }
}
