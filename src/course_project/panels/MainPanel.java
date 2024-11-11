package course_project.panels;

import course_project.canvas.MyCanvas;
import course_project.controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainPanel extends JPanel {

    private final Image backgroundImage;
    private Controller controller;

    private MyCanvas canvas;
    private ProcessPanel processPanel;

    public MainPanel(String backgroundFilename) {
        try {
            backgroundImage = ImageIO.read(new File(backgroundFilename));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        controller = new Controller();
        canvas = new MyCanvas(controller);
        processPanel = new ProcessPanel(controller);

        controller.setCanvas(canvas);
        controller.setProcessPanel(processPanel);
        controller.startTimer();

        setLayout(new GridBagLayout());
        add(canvas);
        add(processPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
