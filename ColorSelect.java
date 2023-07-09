
import javax.swing.*;
import java.awt.*;

public class ColorSelect extends JPanel {

    public ColorSelect(String title) {
        setName(title);
        setBorder(BorderFactory.createTitledBorder(title));
        setLayout(new GridLayout(0, 1, 0, 0));
    }

    JSlider getSlider(String title) {
        JPanel sliderPanel = new JPanel();
        JLabel sliderLabel = new JLabel(title);
        JSlider slider = new JSlider(0, 255);
        JTextField textField = new JTextField();
        textField.setEditable(false);
        textField.setText("" + slider.getValue());
        textField.setPreferredSize(new Dimension(30, 25));
        slider.addChangeListener(e -> textField.setText(Integer.toString(slider.getValue())));
        sliderPanel.add(sliderLabel);
        sliderPanel.add(slider);
        sliderPanel.add(textField);
        add(sliderPanel);
        return slider;
    }

}
