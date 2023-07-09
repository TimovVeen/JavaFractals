
import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;

public class FractalPanel extends JPanel implements MouseWheelListener, MouseMotionListener, MouseListener {
    int WIDTH = 1150;
    int HEIGHT = 800;

    HashMap<String, Integer> colorSettings;
    JPanel settings;

    double zoom = 1;
    double prevZoom = 1;
    double xOffset = 0;
    double yOffset = 0;
    boolean updateZoom;
    boolean updateDrag;
    boolean mouseUp;
    Point mouseStart;
    int dragX;
    int dragY;

    double MINZOOM = 0.25;

    public FractalPanel() {
        addMouseListener(this);
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        setBounds(325, 10, WIDTH, HEIGHT);

        settings = new JPanel();
        settings.setBounds(0, 0, 300, 900);
        settings.setBackground(new Color(230, 230, 230));
        // settings.setLayout(new GridLayout(0, 1, 0, 0));
        colorSettings = new HashMap<String, Integer>();
    }

    ColorSelect getColorPicker(String title, String color) {
        ColorSelect picker = new ColorSelect(title);

        setSlider(picker.getSlider("R"), color + "R");
        setSlider(picker.getSlider("G"), color + "G");
        setSlider(picker.getSlider("B"), color + "B");

        return picker;
    }

    void setSlider(JSlider s, String type) {
        s.setValue(colorSettings.get(type));
        s.addChangeListener(e -> changeSetting(type, s.getValue()));
    }

    public void changeSetting(String setting, int value) {
        colorSettings.replace(setting, value);
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (updateZoom) {
            // Mouse position on the panel
            double panelX = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
            double panelY = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

            double zoomDiv = zoom / prevZoom;

            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * panelX;
            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * panelY;

            prevZoom = zoom;

            updateZoom = false;
        }

        if (updateDrag) {
            if (mouseUp) {
                xOffset += dragX;
                yOffset += dragY;
                updateDrag = false;
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // Zoom in
        if (e.getWheelRotation() < 0) {
            zoom *= 1.1;
            updateZoom = true;
            repaint();
        }
        // Zoom out
        if (e.getWheelRotation() > 0 && zoom > MINZOOM) {
            zoom /= 1.1;
            updateZoom = true;
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseUp = false;
        mouseStart = MouseInfo.getPointerInfo().getLocation();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseUp = true;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point curPoint = e.getLocationOnScreen();
        dragX = curPoint.x - mouseStart.x;
        dragY = curPoint.y - mouseStart.y;

        updateDrag = true;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
