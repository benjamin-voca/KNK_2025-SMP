package utilities;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.time.LocalTime;

public class Clock extends Canvas {
    private double width;
    private double height;
    private final GraphicsContext gc;

    private double posX = 0;
    private double posY = 0;

    public Clock() {
        this(0, 0, 150, 150);
    }

    public Clock(double posX, double posY, double width, double height) {
        super(width, height);
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;

        gc = getGraphicsContext2D();
        drawClock();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> drawClock()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        setLayoutX(posX);
        setLayoutY(posY);
    }

    private void drawClock() {
        LocalTime now = LocalTime.now();
        int second = now.getSecond();
        int minute = now.getMinute();
        int hour = now.getHour() % 12;

        double centerX = width / 2;
        double centerY = height / 2;
        double radius = width / 2 - 5;

        gc.clearRect(0, 0, width, height);

        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.fillOval(5, 5, width - 10, height - 10);
        gc.strokeOval(5, 5, width - 10, height - 10);

        drawHand(centerX, centerY, radius * 0.5, (hour * 30) + (minute * 0.5), Color.BLACK, 4);
        drawHand(centerX, centerY, radius * 0.7, (minute * 6), Color.BLACK, 3);
        drawHand(centerX, centerY, radius * 0.9, (second * 6), Color.RED, 2);
    }

    private void drawHand(double centerX, double centerY, double length, double angle, Color color, double width) {
        double radian = Math.toRadians(angle - 90);
        double x = centerX + length * Math.cos(radian);
        double y = centerY + length * Math.sin(radian);

        gc.setStroke(color);
        gc.setLineWidth(width);
        gc.strokeLine(centerX, centerY, x, y);
    }
}
