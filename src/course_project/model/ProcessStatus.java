package course_project.model;

public enum ProcessStatus {
    NEW("НОВЫЙ"),
    READY("ГОТОВ"),
    RUNNING("ЗАПУЩЕН"),
    FINISHED("ЗАВЕРШЁН");

    final String name;

    ProcessStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
