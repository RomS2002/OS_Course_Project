package course_project.panels;

import course_project.controller.Controller;
import course_project.model.Process;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProcessPanel extends JPanel {

    CustomButton plusButton = new CustomButton("images/plus_button.png");
    CustomButton clearButton = new CustomButton("images/clear_button.png");

    Controller controller;

    public ProcessPanel(Controller controller) {

        this.controller = controller;

        setPreferredSize(new Dimension(300, 400));
        plusButton.addActionListener(e -> {
            controller.stopTimer();
            ProcessForm processForm = new ProcessForm(controller);
            processForm.setLocationRelativeTo(getParent());
            processForm.setVisible(true);
        });
        add(plusButton);

        clearButton.addActionListener(e -> controller.getProcessManager().clearProgress());
        add(clearButton);

        setOpaque(false);
    }

    private void drawProcess(Graphics2D g2, Process process, int x, int y) {

        Image processBG;

        int textX = x + 58;
        int textY = y + 20;
        int dy = 15;

        try {
            switch(process.getStatus()) {
                case NEW -> processBG = ImageIO.read(new File("images/process_wait.png"));
                case READY -> processBG = ImageIO.read(new File("images/process_pause.png"));
                default -> processBG = ImageIO.read(new File("images/process_exec.png"));
            }

            g2.drawImage(processBG, x, y, null);

            String text = process.toString();
            int delimiterInd1 = text.indexOf(" ", 12) + 1;
            int delimiterInd2 = text.indexOf("в ");

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("TimesNewRoman", Font.BOLD, 12));

            g2.drawString(text.substring(0, delimiterInd1), textX, textY);
            g2.drawString(text.substring(delimiterInd1, delimiterInd2), textX, textY + dy);
            g2.drawString(text.substring(delimiterInd2), textX, textY + 2 * dy);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage buff = new BufferedImage(this.getWidth(), this.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) buff.getGraphics();

        int drawProcX = 30;
        int drawProcY = 70;
        int dy = 86;

        for(Process process : controller.getProcessManager().getProcessList()) {
            drawProcess(g2, process, drawProcX, drawProcY);
            drawProcY += dy;
        }

        g.drawImage(buff, 0, 0, null);
    }
}

class CustomButton extends JButton {

    Image buttonImage;

    public CustomButton(String filename) {
        try {
            buttonImage = ImageIO.read(new File(filename));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        int width = buttonImage.getWidth(this);
        int height = buttonImage.getHeight(this);

        setPreferredSize(new Dimension(width, height));
        setBorder(new EmptyBorder(0, 0, 0, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = buttonImage.getWidth(this);
        int height = buttonImage.getHeight(this);

        Image buf = createImage(width, height);

        Graphics2D g2 = (Graphics2D) buf.getGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(buttonImage, 0, 0, width, height, this);

        g.drawImage(buf, 0, 0, null);
    }
}
