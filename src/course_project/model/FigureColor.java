package course_project.model;

import java.awt.*;

public enum FigureColor {
    RED("красный", Color.RED),
    BLUE("синий", Color.BLUE),
    GREEN("зелёный", Color.GREEN),
    YELLOW("жёлтый", Color.YELLOW),
    BLACK("чёрный", Color.BLACK),
    ORANGE("оранжевый", Color.ORANGE),
    PURPLE("фиолетовый", new Color(128, 0, 128));

    final String name;
    final Color color;

    FigureColor(String colorName, Color color) {
        this.name = colorName;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public static FigureColor getByName(String name) {
        for(FigureColor figureColor : FigureColor.values()) {
            if(figureColor.getName().compareTo(name) == 0) {
                return figureColor;
            }
        }
        return null;
    }
}
