package utilities;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Logo extends Application {
    public void start(Stage stage) {

        Pane root = new Pane();

        Scene scene = new Scene(root, 700, 700);
        stage.setScene(scene);

        Star star = new Star(250, 350, 300);
        star.createStaticStar();
        root.getChildren().add(star);

        star.setOnMouseEntered(new StarClickHandler(star));

        // star.setOnMouseClicked(new StarClickHandler(star));

        stage.setTitle("Logo e UP");
        stage.show();
    }
}

class Diamond extends Polygon {
    private final double size;
    private final double width;

    Diamond(double size, double x, double y) {
        this.size = size;
        this.width = size / 7;
        createDiamond();
        setTranslateX(x);
        setTranslateY(y);
    }

    private void createDiamond() {
        getPoints().addAll(
                -size, 0.0,
                0.0, -width,
                size, 0.0,
                0.0, width
        );
    }
}

class Star extends Pane {
    private final double size;
    private double x;
    private double y;
    private final Rotate rotateTransform;

    Star(double size, double x, double y) {
        this.size = size;
        this.x = x;
        this.y = y;

        rotateTransform = new Rotate(0, x, y);
        this.getTransforms().add(rotateTransform);
    }

    private Color[] CurrentColors = {
            Color.rgb(222, 221, 221),
            Color.rgb(194, 194, 193),
            Color.rgb(169, 169, 168),
            Color.rgb(150, 149, 148),
            Color.rgb(132, 131, 130),
            Color.rgb(112, 112, 111),
            Color.rgb(96, 93, 92),
            Color.rgb(31, 26, 23),
            Color.rgb(205, 46, 49)
    };

    public void createStaticStar() {
        for (int i = 0; i < 9; i++) {
            Diamond diamond = new Diamond(size, x, y);
            diamond.setFill(CurrentColors[i]);
            // diamond.setStroke(Color.BLACK);
            // diamond.setStroke(Color.WHITE);
            // diamond.setStrokeWidth(8);
            diamond.setRotate(20 * i);
            getChildren().add(diamond);
        }
    }

    public void rotate() {

        Timeline timeline = new Timeline();
        double totalDuration = 0.8;
        int steps = 9;
        double angleIncrement = 20; // Degrees per step

        double stepDuration = totalDuration / steps;

        // Add keyframes to the timeline
        for (int i = 1; i <= steps; i++) {
            double targetAngle = i * angleIncrement; // Target angle for this step
            KeyFrame keyFrame = new KeyFrame(
                    Duration.seconds(i * stepDuration), // Time for this keyframe
                    //Duration.INDEFINITE ,
                    event -> rotateTransform.setAngle(targetAngle) // Set the rotation angle
            );
            timeline.getKeyFrames().add(keyFrame);
        }

        // Play the animation
        timeline.play();
    }
}

class StarClickHandler implements EventHandler<MouseEvent> {
    private final Star star;

    public StarClickHandler(Star star) {
        this.star = star;
    }

    public void handle(MouseEvent event) {
        // Start the rotation animation on click
        star.rotate();
    }
}