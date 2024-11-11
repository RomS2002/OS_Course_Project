package course_project.panels;

import course_project.controller.Controller;
import course_project.model.Figure;
import course_project.model.FigureColor;
import course_project.model.Process;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProcessForm extends JFrame {
    private JPanel mainPanel;
    private JButton createProcessButton;
    private JComboBox<String> figureComboBox;
    private JComboBox<String> colorComboBox;
    private JSpinner durationSpinner;
    private JLabel secondsLabel;
    private JLabel durationLabel;
    private JLabel createProcessLabel;
    private JLabel figureLabel;
    private JLabel colorLabel;
    private JPanel formPanel;
    private JLabel locationLabel;
    private JPanel locationPanel;
    private JLabel xLabel;
    private JSlider xSlider;
    private JLabel yLabel;
    private JSlider ySlider;
    private JLabel xValueLabel;
    private JLabel yValueLabel;

    private Controller controller;

    public ProcessForm(Controller controller) {

        this.controller = controller;

        for(Figure figure : Figure.values()) {
            figureComboBox.addItem(figure.getName());
        }

        for(FigureColor figureColor : FigureColor.values()) {
            colorComboBox.addItem(figureColor.getName());
        }

        durationSpinner.setValue(1);

        xValueLabel.setText(String.valueOf(xSlider.getValue()));
        yValueLabel.setText(String.valueOf(ySlider.getValue()));

        xSlider.addChangeListener((e) -> xValueLabel.setText(String.valueOf(xSlider.getValue())));
        ySlider.addChangeListener((e) -> yValueLabel.setText(String.valueOf(ySlider.getValue())));

        createProcessButton.addActionListener((e) -> {

            Process process = new Process(Figure.getByName(figureComboBox.getSelectedItem().toString()),
                    FigureColor.getByName(colorComboBox.getSelectedItem().toString()),
                    xSlider.getValue(), ySlider.getValue(), (int) durationSpinner.getValue());

            controller.getProcessManager().addProcess(process);
            controller.startTimer();

            setVisible(false);
            dispose();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                controller.startTimer();
            }
        });

        setContentPane(mainPanel);
        pack();
        setResizable(false);
    }
}
