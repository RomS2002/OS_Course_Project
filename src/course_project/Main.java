package course_project;

import course_project.panels.MainPanel;

import javax.swing.*;

public class Main {

    public static final int STEPS_PER_SECOND = 30;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(640, 480);
        frame.setResizable(false);
        frame.setTitle("Модель планирования процессов SJF с приоритетами");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.add(new MainPanel("images/background.jpg"));
        frame.setVisible(true);
    }
}
