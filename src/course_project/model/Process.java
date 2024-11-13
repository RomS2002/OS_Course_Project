package course_project.model;

import course_project.*;

public class Process {

    private static int next_pid = 1;

    private final int pid;
    private final Figure figure;
    private final FigureColor color;

    private final int x;
    private final int y;
    private final double duration;
    private final int priority;

    private double percentage = 0.0;
    private double percentageOnClear = 0.0;

    private ProcessStatus status;

    public Process(Figure figure, FigureColor color, int x, int y, int duration, int priority) {
        this.pid = next_pid;
        next_pid++;

        this.figure = figure;
        this.color = color;
        this.x = x;
        this.y = y;
        this.duration = duration;
        this.priority = priority;

        status = ProcessStatus.NEW;
    }

    public int getPid() {
        return pid;
    }

    public Figure getFigure() {
        return figure;
    }

    public FigureColor getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getDuration() {
        return duration;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getPercentageOnClear() {
        return percentageOnClear;
    }

    public void setPercentageOnClear(double percentageOnClear) {
        this.percentageOnClear = percentageOnClear;
    }

    public int getPriority() {
        return priority;
    }

    public ProcessStatus getStatus() {
        return status;
    }

    public void startProcess() {
        status = ProcessStatus.RUNNING;
    }

    public void stopProcess() {
        status = ProcessStatus.READY;
    }

    public void doAnimationStep() {

        double stepsPerSecond = Main.STEPS_PER_SECOND;

        if(status != ProcessStatus.RUNNING) {
            return;
        }

        percentage += 1 / (duration * stepsPerSecond);

        if(percentage >= 1) {
            status = ProcessStatus.FINISHED;
        }
    }

    @Override
    public String toString() {
        return String.format("Нарисовать %s %s за %d сек. в координатах (%d, %d)", color.getName(),
                figure.getName(), (int) duration, x, y);
    }
}
