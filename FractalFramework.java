
import javax.swing.*;

public class FractalFramework extends JFrame {
    FractalPanel activeFractal;
    JLayeredPane layers;

    private FractalFramework() {
        setTitle("Fractal Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1500, 900);

        layers = new JLayeredPane();

        FractalPanel pythagoras = new Pythagoras();
        FractalPanel mandelbrot = new Mandelbrot();
        FractalPanel phoenix = new Phoenix();
        setActiveFractal(pythagoras);

        // fractal.setIgnoreRepaint(true);

        // JSplitPane sideSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new
        // JPanel().add(fractal.settings), fractal);
        // SplitPaneBorder border = new SplitPaneBorder(new Color(30, 30, 30), new
        // Color(150, 150, 150));
        // sideSplit.setBorder(border);
        // sideSplit.setDividerSize(15);
        // sideSplit.setOneTouchExpandable(true);
        // sideSplit.setDividerLocation(300);

        // add(sideSplit);

        // layers.setPreferredSize(new Dimension(1500, 900));
        // layers.add(new Mandelbrot(), 1);
        // layers.add(activeFractal, 1);

        // layers.add(bar, 0);
        // layers.add(activeFractal.settings, 0);
        add(layers);

        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem i1 = new JMenuItem("Save config");
        JMenuItem i2 = new JMenuItem("Load config");
        JMenuItem i3 = new JMenuItem("Exit program");
        i3.addActionListener(e -> dispose());
        i3.setAccelerator(KeyStroke.getKeyStroke("control W"));

        file.add(i1);
        file.add(i2);
        file.add(i3);
        menuBar.add(file);

        JMenu fractalMenu = new JMenu("Fractals");
        JMenuItem pythMenu = new JMenuItem("Pythagoras tree");
        pythMenu.addActionListener(e -> setActiveFractal(pythagoras));
        pythMenu.setAccelerator(KeyStroke.getKeyStroke("control P"));

        JMenuItem mandelMenu = new JMenuItem("Mandelbrot Set");
        mandelMenu.addActionListener(e -> setActiveFractal(mandelbrot));
        mandelMenu.setAccelerator(KeyStroke.getKeyStroke("control M"));

        JMenuItem phoenixMenu = new JMenuItem("Phoenix Set");
        phoenixMenu.addActionListener(e -> setActiveFractal(phoenix));
        phoenixMenu.setAccelerator(KeyStroke.getKeyStroke("control J"));

        fractalMenu.add(pythMenu);
        fractalMenu.add(mandelMenu);
        fractalMenu.add(phoenixMenu);
        menuBar.add(fractalMenu);

        setJMenuBar(menuBar);

        setVisible(true);

    }

    void setActiveFractal(FractalPanel fractal) {
        if (activeFractal != fractal) {
            if (activeFractal != null) {
                layers.remove(activeFractal);
                layers.remove(activeFractal.settings);
            }

            layers.add(fractal, 1);
            layers.add(fractal.settings, 0);
            activeFractal = fractal;
        }
    }

    public static void main(String[] args) {
        new FractalFramework();
    }
}
