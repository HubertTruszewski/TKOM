package pl.truszewski;

import java.util.List;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import pl.truszewski.interpreter.objects.CustomObject;

public class Window extends Application {
    public static List<CustomObject> FIGURES_TO_DRAW;
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private final int WIDTH = 1600;
    private final int HEIGHT = 800;
    private final int OFFSET = 100;

    @Override
    public void start(final Stage stage) throws Exception {
        Group group = new Group();
        int counter = 0;
        for (CustomObject figure : FIGURES_TO_DRAW) {
            int xPosition = 2 * OFFSET + counter * (WIDTH - 2 * OFFSET) / FIGURES_TO_DRAW.size();
            Shape3D shape = mapFigureToFXObject(figure);
            if (shape != null) {
                shape.setMaterial(new PhongMaterial(Color.CORNFLOWERBLUE));
                shape.translateXProperty().setValue(xPosition);
                shape.translateYProperty().setValue(HEIGHT / 2);
                group.getChildren().add(shape);
                ++counter;
            }
        }

        Scene scene = new Scene(group, WIDTH, HEIGHT);
        scene.setFill(Color.LIGHTGRAY);
        initMouseControl(group, scene);
        stage.setScene(scene);
        stage.setTitle("TKOM - Figures");
        stage.show();
    }

    private void initMouseControl(Group group, Scene scene) {
        Rotate xRoatate;
        Rotate yRoatate;

        group.getTransforms().addAll(xRoatate = new Rotate(0, Rotate.X_AXIS), yRoatate = new Rotate(0, Rotate.Y_AXIS));
        xRoatate.angleProperty().bind(angleX);
        yRoatate.angleProperty().bind(angleY);

        scene.setOnMouseClicked(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });
    }

    private Shape3D mapFigureToFXObject(CustomObject customObject) {
        return switch (customObject.getValueType()) {
            case CONE -> null;
            case CYLINDER -> new Cylinder((double) customObject.findVariable("r").get().value().value(),
                    (double) customObject.findVariable("H").get().value().value());
            case SPHERE -> new Sphere((double) customObject.findVariable("R").get().value().value());
            case CUBOID -> new Box((double) customObject.findVariable("a").get().value().value(),
                    (double) customObject.findVariable("b").get().value().value(),
                    (double) customObject.findVariable("H").get().value().value());
            case PYRAMID -> buildPyramid(customObject);
            case LIST -> null;
            case ITERATOR -> null;
            case FIGURE -> null;
            default -> null;
        };
    }

    private Shape3D buildPyramid(CustomObject customObject) {
        float a = ((Double) customObject.findVariable("a").get().value().value()).floatValue();
        float H = ((Double) customObject.findVariable("H").get().value().value()).floatValue();
        return new Cylinder(a, H, 3);
    }
}
