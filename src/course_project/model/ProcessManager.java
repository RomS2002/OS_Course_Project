package course_project.model;

import java.util.LinkedList;
import java.util.List;

public class ProcessManager {

    private final List<Process> activeProcesses;
    private final List<Process> finishedProcesses;

    private final int maxActiveCount;

    public ProcessManager(int maxActiveCount) {
        activeProcesses = new LinkedList<>();
        finishedProcesses = new LinkedList<>();

        this.maxActiveCount = maxActiveCount;
    }

    public void addProcess(Process newProcess) {

        if(activeProcesses.isEmpty()) {
            activeProcesses.add(newProcess);
            newProcess.startProcess();
            return;
        }

        if(activeProcesses.size() == maxActiveCount) {
            throw new RuntimeException("ProcessManager queue overflow");
        }

        for(int i = 0; i < activeProcesses.size(); i++) {
            Process oldProcess = activeProcesses.get(i);

            if(newProcess.getPriority() > oldProcess.getPriority() ||
                    (newProcess.getPriority() == oldProcess.getPriority() &&
                            newProcess.getDuration() < oldProcess.getDuration())) {

                activeProcesses.add(i, newProcess);
                if(i == 0) {
                    oldProcess.stopProcess();
                    newProcess.startProcess();
                }
                return;
            }
        }
        activeProcesses.add(newProcess);
    }

    public void doAnimationStep() {
        if(activeProcesses.isEmpty()) {
            return;
        }

        Process runningProcess = activeProcesses.getFirst();
        runningProcess.doAnimationStep();

        if(runningProcess.getStatus() == ProcessStatus.FINISHED) {
            activeProcesses.remove(runningProcess);
            finishedProcesses.add(runningProcess);

            if(!activeProcesses.isEmpty()) {
                activeProcesses.getFirst().startProcess();
            }
        }
    }

    public void clearProgress() {
        for(Process process : activeProcesses) {
            process.setPercentageOnClear(process.getPercentage());
        }

        finishedProcesses.clear();
    }

    public List<Process> getProcessList() {
        return activeProcesses;
    }

    public List<Process> getFinishedProcessList() {
        return finishedProcesses;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("=============================\n");

        for(Process process : activeProcesses) {
            result.append(process.getStatus().getName()).append(": \"").append(process).append("\"\n");
        }
        result.append("=============================\n");
        return result.toString();
    }
}
