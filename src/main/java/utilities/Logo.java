package utilities;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Logo extends Pane {

    public Logo() {
        Star star = new Star(80);
        star.createStaticStar();


        star.setLayoutX(100);
        star.setLayoutY(100);

        star.setOnMouseClicked(new StarClickHandler(star));
        getChildren().add(star);

        setPrefSize(200, 200);
    }

    private static class Diamond extends Polygon {
        private final double size;
        private final double width;

        Diamond(double size) {
            this.size = size;
            this.width = size / 7;
            createDiamond();
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

    private static class Star extends Pane {
        private final double size;
        private final Rotate rotateTransform;
        private final Color[] currentColors = {
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

        Star(double size) {
            this.size = size;
            rotateTransform = new Rotate(0, 0, 0);
            getTransforms().add(rotateTransform);
            setPrefSize(size * 3, size * 3);
        }

        public void createStaticStar() {
            double centerX = 0;
            double centerY = 0;

            for (int i = 0; i < 9; i++) {
                Diamond diamond = new Diamond(size);
                diamond.setFill(currentColors[i]);
                diamond.setRotate(20 * i);
                getChildren().add(diamond);
            }


            rotateTransform.setPivotX(centerX);
            rotateTransform.setPivotY(centerY);
        }

        public void rotate() {
            Timeline timeline = new Timeline();
            double totalDuration = 0.8;
            int steps = 9;
            double angleIncrement = 20;
            double stepDuration = totalDuration / steps;

            for (int i = 1; i <= steps; i++) {
                double targetAngle = i * angleIncrement;
                KeyFrame keyFrame = new KeyFrame(
                        Duration.seconds(i * stepDuration),
                        event -> rotateTransform.setAngle(targetAngle)
                );
                timeline.getKeyFrames().add(keyFrame);
            }

            timeline.play();
        }
    }

    private static class StarClickHandler implements EventHandler<MouseEvent> {
        private final Star star;

        StarClickHandler(Star star) {
            this.star = star;
        }

        public void handle(MouseEvent event) {
            star.rotate();
        }
    }
}