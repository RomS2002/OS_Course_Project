package course_project;

import course_project.model.*;
import course_project.model.Process;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public Test() {
        ProcessManager processManager = new ProcessManager(5);

        Thread thread = new Thread(() -> {
            List<course_project.model.Process> processList = new ArrayList<>();
            processList.add(new course_project.model.Process(Figure.CIRCLE, FigureColor.ORANGE, 10, 15, 10));
            processList.add(new course_project.model.Process(Figure.SQUARE, FigureColor.BLACK, 10, 15, 6));
            processList.add(new course_project.model.Process(Figure.TRIANGLE, FigureColor.BLUE, 10, 15, 4));
            processList.add(new course_project.model.Process(Figure.TRIANGLE, FigureColor.GREEN, 20, 15, 1));
            processList.add(new course_project.model.Process(Figure.CIRCLE, FigureColor.PURPLE, 10, 15, 20));
            processList.add(new course_project.model.Process(Figure.SQUARE, FigureColor.YELLOW, 10, 15, 3));

            synchronized(this) {
                for(Process process : processList) {
                    processManager.addProcess(process);
                    try {
                        Thread.sleep(2000);
                    } catch(InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    notifyAll();
                }
            }
        });
        thread.start();

        synchronized(this) {
            try {
                wait();
            } catch(InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        double stepTimeD = 1000.0 / 30;
        double time = 0.0;
        while(!processManager.getProcessList().isEmpty()) {
            time += stepTimeD;
            if(Math.round(time % 1000) == 0) {
                System.out.println(processManager);
                System.out.println(time);
            }
            processManager.doAnimationStep();
            try {
                Thread.sleep((long) stepTimeD);
            } catch(InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        new Test();
    }
}
