
import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;

public class Pythagoras extends FractalPanel {
    Color backColor = Color.cyan;
    Color lineColor = Color.gray;
    Color fillColor = Color.green;

    boolean drawLine = true;
    boolean drawFill = false;

    double CUTOFFSIZE = 0.7;
    double FILLCUTOFF = 1.5;
    int maxDepth = 0;
    double size = 100;
    double angle = 45;
    double aSin;
    double aCos;

    double startX1;
    double startX2;
    double startY;
    double newX1;
    double newX2;
    double newY;
    Rectangle2D viewRect;

    public Pythagoras() {
        super();

        initSettings();

        startX1 = (getSize().width / 2) - size / 2;
        startX2 = (getSize().width / 2) + size / 2;
        startY = 700;
        newX1 = startX1 * zoom + xOffset;
        newX2 = startX2 * zoom + xOffset;
        newY = startY * zoom + yOffset;

        viewRect = new Rectangle2D.Double(0, 0, WIDTH, HEIGHT);
    }

    void initSettings() {

        colorSettings.put("BackR", backColor.getRed());
        colorSettings.put("BackG", backColor.getGreen());
        colorSettings.put("BackB", backColor.getBlue());

        colorSettings.put("LineR", lineColor.getRed());
        colorSettings.put("LineG", lineColor.getGreen());
        colorSettings.put("LineB", lineColor.getBlue());

        colorSettings.put("FillR", fillColor.getRed());
        colorSettings.put("FillG", fillColor.getGreen());
        colorSettings.put("FillB", fillColor.getBlue());

        settings.add(getColorPicker("Set background color", "Back"));

        JRadioButton lineToggle = new JRadioButton("Toggle line", drawLine);
        lineToggle.addActionListener(e -> {
            drawLine = !drawLine;
            repaint();
        });
        ColorSelect linePicker = getColorPicker("Set line color", "Line");
        linePicker.add(lineToggle);
        settings.add(linePicker);

        JRadioButton fillToggle = new JRadioButton("Toggle fill", drawFill);
        fillToggle.addActionListener(e -> {
            drawFill = !drawFill;
            repaint();
        });
        ColorSelect fillPicker = getColorPicker("Set fill color", "Fill");
        fillPicker.add(fillToggle);
        settings.add(fillPicker);

        JPanel spinnerPanel = new JPanel();
        spinnerPanel.setBorder(BorderFactory.createTitledBorder("Set fractal depth"));

        JPanel sliderPanel = new JPanel();
        sliderPanel.setBorder(BorderFactory.createTitledBorder("Set tree angle"));

        JSlider angleSlider = new JSlider();
        angleSlider.setMinimum(5);
        angleSlider.setMaximum(85);
        angleSlider.setValue(45);

        JTextField textField = new JTextField();
        textField.setEditable(false);
        textField.setText("" + angleSlider.getValue());
        textField.setPreferredSize(new Dimension(20, 20));

        angleSlider.addChangeListener(e -> {
            textField.setText("" + angleSlider.getValue());
            angle = angleSlider.getValue();
            repaint();
        });

        sliderPanel.add(angleSlider);
        sliderPanel.add(textField);
        settings.add(sliderPanel);

        JSpinner iter = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
        iter.setPreferredSize(new Dimension(50, 25));
        iter.addChangeListener(e -> {
            maxDepth = (int) iter.getValue();
            repaint();
        });
        JLabel label = new JLabel("Iterations");
        spinnerPanel.add(label);
        spinnerPanel.add(iter);
        settings.add(spinnerPanel);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        backColor = new Color(colorSettings.get("BackR"), colorSettings.get("BackG"), colorSettings.get("BackB"));
        lineColor = new Color(colorSettings.get("LineR"), colorSettings.get("LineG"), colorSettings.get("LineB"));
        fillColor = new Color(colorSettings.get("FillR"), colorSettings.get("FillG"), colorSettings.get("FillB"));

        setBackground(backColor);

        Graphics2D g2D = (Graphics2D) g;

        newX1 = startX1 * zoom + xOffset;
        newX2 = startX2 * zoom + xOffset;
        newY = 700 * zoom + yOffset;

        // g2D.transform(aTrans);

        // g.fillRect(centerX, centerY, 250, 250);
        // System.out.println("repainting tree");

        double rad = Math.toRadians(angle);
        aSin = Math.sin(rad);
        aCos = Math.cos(rad);
        double xTrans = aCos * aCos;
        double yTrans = aCos * aSin;

        long startTime = System.currentTimeMillis();
        drawTree(g2D, 0, xTrans, yTrans, newX1, newY, newX2, newY, size * zoom);
        long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + " milliseconds " + Math.round(zoom) + "X zoom");
        // drawTree(g2D, 0, xTrans, yTrans, (x1 + aTrans.getTranslateX()) *
        // aTrans.getScaleX(),
        // (700 + aTrans.getTranslateX()) * aTrans.getScaleY(), (x2 +
        // aTrans.getTranslateX()) * aTrans.getScaleX(),
        // (700 + aTrans.getTranslateX()) * aTrans.getScaleY(), size);

    }

    // coords 1 and 2 are the bottom verteces of a square
    // (maybe switch to float for performance?)
    private void drawTree(Graphics2D g, int depth, double xForm, double yForm, double x1, double y1, double x2,
            double y2, double side) {
        if ((maxDepth != 0 && depth >= maxDepth) || side < CUTOFFSIZE) {
            return;
        }

        // Vector from coord 1 to 2
        double dx = x2 - x1;
        double dy = y2 - y1;

        // top vertices of the square
        double x3 = x2 + dy;
        double y3 = y2 - dx;
        double x4 = x1 + dy;
        double y4 = y1 - dx;
        // triangle right angle point
        double x5 = x4 + xForm * side;
        double y5 = y4 - yForm * side;

        Path2D square = new Path2D.Double();
        square.moveTo(x1, y1);
        square.lineTo(x2, y2);
        square.lineTo(x3, y3);
        square.lineTo(x4, y4);
        square.closePath();
        boolean inBounds = square.intersects(viewRect);

        Path2D triangle = null;
        if (drawFill) {
            triangle = new Path2D.Double();
            triangle.moveTo(x3, y3);
            triangle.lineTo(x5, y5);
            triangle.lineTo(x4, y4);
            triangle.closePath();
            if (inBounds == false && triangle.intersects(viewRect)) {
                inBounds = true;
            }
        }

        // drawing stuff, only do when square in frame
        // check without intersect function?
        if (inBounds) {

            if (drawFill && side > FILLCUTOFF) {
                g.setColor(fillColor);
                g.fill(square);
                if (depth < maxDepth - 1 || maxDepth == 0) {

                    g.setColor(fillColor);
                    g.fill(triangle);
                }
            }
            if (drawLine) {
                g.setColor(lineColor);
                g.draw(square);
            }

        }

        boolean goLeft = true;
        boolean goRight = true;
        if (!inBounds) {
            Path2D rectLeft = getBranchRect(x4, y4, x5, y5);
            goLeft = rectLeft.intersects(viewRect);

            Path2D rectRight = getBranchRect(x5, y5, x3, y3);
            goRight = rectRight.intersects(viewRect);
        }

        // Left branch
        if (goLeft)
            drawTree(g, depth + 1, xForm * aCos - yForm * aSin, xForm * aSin + yForm * aCos, x4, y4, x5, y5,
                    side * aCos);

        // Right branch
        if (goRight)
            drawTree(g, depth + 1, xForm * aSin + yForm * aCos, xForm * -aCos + yForm * aSin, x5, y5, x3, y3,
                    side * aSin);
    }

    Path2D getBranchRect(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        Path2D branchRect = new Path2D.Double();

        double centerX = (x1 + x2 + dy) / 2;
        double centerY = (y1 + y2 - dx) / 2;
        double xLeft = (2 + aSin) / aSin;
        double xRight = (2 + aCos) / aCos;
        double yScale = 10 / (9 * aSin * aCos);
        branchRect.moveTo(centerX - dx * xLeft - dy * (yScale - 1.5), centerY - dy * xLeft + dx * (yScale - 1.5));
        branchRect.lineTo(centerX + dx * xRight - dy * (yScale - 1.5), centerY + dy * xRight + dx * (yScale - 1.5));
        branchRect.lineTo(centerX + dx * xRight + dy * (yScale + 1.5), centerY + dy * xRight - dx * (yScale + 1.5));
        branchRect.lineTo(centerX - dx * xLeft + dy * (yScale + 1.5), centerY - dy * xLeft - dx * (yScale + 1.5));

        // branchRect.moveTo(x1 - dx * 2.5, y1 - dy * 2.5);
        // branchRect.lineTo(x2 + dx * 2.5, y2 + dy * 2.5);
        // branchRect.lineTo(x2 + dx * 2.5 + dy * 4, y2 + dy * 2.5 + -dx * 4);
        // branchRect.lineTo(x1 - dx * 2.5 + dy * 4, y1 - dy * 2.5 + -dx * 4);
        branchRect.closePath();
        return branchRect;
    }
}
