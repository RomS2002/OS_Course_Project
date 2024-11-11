package course_project.model;

public enum Figure {
    SQUARE("квадрат"),
    TRIANGLE("треугольник"),
    CIRCLE("круг");

    final String name;

    Figure(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Figure getByName(String name) {
        for(Figure figure : Figure.values()) {
            if(figure.getName().compareTo(name) == 0) {
                return figure;
            }
        }
        return null;
    }
}
