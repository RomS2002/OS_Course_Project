package course_project.controller;

import course_project.Main;
import course_project.canvas.MyCanvas;
import course_project.model.ProcessManager;
import course_project.panels.ProcessPanel;

import javax.swing.*;

public class Controller {

    private final ProcessManager processManager;
    private final Timer timer;
    private MyCanvas canvas;
    private ProcessPanel processPanel;

    public Controller() {
        processManager = new ProcessManager(5);

        timer = new Timer(1000 / Main.STEPS_PER_SECOND, (e) -> {
            processManager.doAnimationStep();
            if(canvas != null && processPanel != null) {
                canvas.repaint();
                processPanel.repaint();
            }
        });
    }

    public void setCanvas(MyCanvas canvas) {
        this.canvas = canvas;
    }

    public void setProcessPanel(ProcessPanel processPanel) {
        this.processPanel = processPanel;
    }

    public ProcessManager getProcessManager() {
        return processManager;
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void repaintCanvas() {
        canvas.repaint();
    }
}
