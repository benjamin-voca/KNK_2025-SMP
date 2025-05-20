package utilities;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class TestLogo extends Pane {
    private double size = 250;
    private double x = 350;
    private double y = 300;
    private final Rotate rotateTransform;

    public TestLogo() {
        rotateTransform = new Rotate(0, x, y);
        this.getTransforms().add(rotateTransform);
        createStaticStar();
        this.setOnMouseEntered(e -> rotate());
    }

    private void createStaticStar() {
        Color[] colors = {
                Color.rgb(222, 221, 221), Color.rgb(194, 194, 193), Color.rgb(169, 169, 168),
                Color.rgb(150, 149, 148), Color.rgb(132, 131, 130), Color.rgb(112, 112, 111),
                Color.rgb(96, 93, 92), Color.rgb(31, 26, 23), Color.rgb(205, 46, 49)
        };

        for (int i = 0; i < 9; i++) {
            Diamond diamond = new Diamond(size, x, y);
            diamond.setFill(colors[i]);
            diamond.setRotate(20 * i);
            getChildren().add(diamond);
        }
    }

    public void rotate() {
        Timeline timeline = new Timeline();
        double stepDuration = 0.8 / 9;
        for (int i = 1; i <= 9; i++) {
            double angle = i * 20;
            KeyFrame keyFrame = new KeyFrame(
                    Duration.seconds(i * stepDuration),
                    e -> rotateTransform.setAngle(angle)
            );
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }

    static class Diamond extends Polygon {
        Diamond(double size, double x, double y) {
            double width = size / 7;
            getPoints().addAll(
                    -size, 0.0,
                    0.0, -width,
                    size, 0.0,
                    0.0, width
            );
            setTranslateX(x);
            setTranslateY(y);
        }
    }
}
