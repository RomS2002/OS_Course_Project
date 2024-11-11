package course_project.canvas;

import course_project.controller.Controller;
import course_project.model.Process;

import javax.swing.*;
import java.awt.*;

public class MyCanvas extends JPanel {

    private final Controller controller;

    private static final Stroke STROKE = new BasicStroke(3);
    private static final int FIGURE_SIZE = 40;

    public MyCanvas(Controller controller) {
        setPreferredSize(new Dimension(300, 400));
        this.controller = controller;
    }

    private void drawLineWithPercentage(Graphics2D g2, int x1, int y1, int x2, int y2,
                                        double percentageFromStart, double percentageToEnd) {
        x1 = x1 + (int) ((x2 - x1) * percentageFromStart);
        y1 = y1 + (int) ((y2 - y1) * percentageFromStart);
        x2 = x1 + (int) ((x2 - x1) * percentageToEnd);
        y2 = y1 + (int) ((y2 - y1) * percentageToEnd);

        if((x2 == x1) && (y2 == y1)) {
            return;
        }

        g2.drawLine(x1, y1, x2, y2);
    }

    private void drawByVertex(Graphics2D g2, Process process, int[] vertexX, int[] vertexY) {
        Color color = process.getColor().getColor();
        double percentage = process.getPercentage();
        double percentageOnClear = process.getPercentageOnClear();

        double maxPercentageOnLine = 1.0 / vertexX.length;

        g2.setColor(color);
        g2.setStroke(STROKE);

        for(int i = 0; i < vertexX.length; i++) {
            int x1 = vertexX[i];
            int x2 = vertexX[(i + 1) % vertexX.length];
            int y1 = vertexY[i];
            int y2 = vertexY[(i + 1) % vertexY.length];

            double endPercentage = Math.min(maxPercentageOnLine, Math.max(0, percentage - maxPercentageOnLine * i));
            endPercentage *= vertexX.length;

            double startPercentage = Math.min(maxPercentageOnLine, Math.max(0, percentageOnClear -
                    maxPercentageOnLine * i));
            startPercentage *= vertexX.length;

            drawLineWithPercentage(g2, x1, y1, x2, y2, startPercentage, endPercentage);
        }
    }

    private void drawTriangle(Process process, Graphics2D g2) {
        int x = process.getX();
        int y = process.getY();

        int[] vertexX = new int[]{
                (int) (x - (double) FIGURE_SIZE / 2),
                (int) (x + (double) FIGURE_SIZE / 2),
                x
        };
        int[] vertexY = new int[]{
                (int) (y + (double) FIGURE_SIZE / 2),
                (int) (y + (double) FIGURE_SIZE / 2),
                (int) (y - (double) FIGURE_SIZE / 2),
        };

        drawByVertex(g2, process, vertexX, vertexY);
    }

    private void drawCircle(Process process, Graphics2D g2) {
        Color color = process.getColor().getColor();

        int x = process.getX();
        int y = process.getY();

        double percentage = process.getPercentage();
        double percentageOnClear = process.getPercentageOnClear();

        g2.setColor(color);
        g2.setStroke(STROKE);

        int startAngle = (int) (360 * percentageOnClear);
        int endAngle = (int) (360 * percentage);

        g2.drawArc((int) (x - (double) FIGURE_SIZE / 2),
                (int) (y - (double) FIGURE_SIZE / 2),
                FIGURE_SIZE, FIGURE_SIZE, startAngle, endAngle - startAngle);
    }

    private void drawSquare(Process process, Graphics2D g2) {
        int x = process.getX();
        int y = process.getY();

        int[] vertexX = new int[]{
                (int) (x - (double) FIGURE_SIZE / 2),
                (int) (x + (double) FIGURE_SIZE / 2),
                (int) (x + (double) FIGURE_SIZE / 2),
                (int) (x - (double) FIGURE_SIZE / 2),
        };
        int[] vertexY = new int[]{
                (int) (y + (double) FIGURE_SIZE / 2),
                (int) (y + (double) FIGURE_SIZE / 2),
                (int) (y - (double) FIGURE_SIZE / 2),
                (int) (y - (double) FIGURE_SIZE / 2),
        };

        drawByVertex(g2, process, vertexX, vertexY);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Image buf = createImage(this.getWidth(), this.getHeight());

        Graphics2D g2 = (Graphics2D) buf.getGraphics();

        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.WHITE);
        g2.fillRect(2, 2, getWidth() - 3, getHeight() - 3);
        g2.setColor(Color.BLACK);
        g2.drawRect(2, 2, getWidth() - 3, getHeight() - 3);

        for(Process process : controller.getProcessManager().getProcessList()) {
            switch(process.getFigure()) {
                case TRIANGLE -> drawTriangle(process, g2);
                case CIRCLE -> drawCircle(process, g2);
                case SQUARE -> drawSquare(process, g2);
            }
        }

        for(Process process : controller.getProcessManager().getFinishedProcessList()) {
            switch(process.getFigure()) {
                case TRIANGLE -> drawTriangle(process, g2);
                case CIRCLE -> drawCircle(process, g2);
                case SQUARE -> drawSquare(process, g2);
            }
        }

        g.drawImage(buf, 0, 0, null);
    }
}
