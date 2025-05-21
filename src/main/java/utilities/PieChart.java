package utilities;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;


public class PieChart extends Application {
   public void start(Stage stage){
       Pane pane = new Pane();

       Scene scene = new Scene(pane,1000 ,800);
       stage.setScene(scene);
       stage.setTitle("Pie Chart");

       double[] grades1 = {1, 2, 3 ,3 ,2 ,1 ,4};
       StatsPieChart piechart1 = new StatsPieChart(100, grades1, 100, 100);

       double[] grades2 = {6,7,8,7,9,7,10,10};
       StatsPieChart piechart2 = new StatsPieChart(150 , grades2  , 200 , 200);
       piechart2.setColors();

       double[] grades3 = {9,6,8};
       Color[] colors = {Color.RED , Color.GREEN , Color.BLUE , Color.YELLOW , Color.BLUEVIOLET};

       StatsPieChart pie = new StatsPieChart(300 ,200 , grades3  , 400 , 300);
       pie.setColors();
       pie.make3D(pie , 50);

       StatsPieChart pie2 = new StatsPieChart(300 ,100 , grades2  , 200 , 600);
       pie2.setColors(colors);
       pie2.make3D(pie2 , 50);

       StatsPieChart eliptical = new StatsPieChart(200 ,100 ,grades1  , 600 , 600);

       double[] grades = {2,3,2,4,5,5,5,1,2,3,4,2,2};
       StatsPieChart pie3 = new StatsPieChart(400 , 300 , grades , 800 , 200);
       pie3.setColors(colors);
       pie3.make3D(pie3 , 80);


       pane.getChildren().addAll(piechart1 , piechart2 , pie , pie2 , eliptical , pie3);

       stage.show();
   }
}

class StatsPieChart extends Pane {

    private double length;
    private double height;
    private final double[] Array;

    private double x;
    private double y;

    public StatsPieChart(double length , double[] Array , double x, double y) {
        this(length , length, Array, x, y);
    }
    public StatsPieChart(double length , double height , double[] Array , double x, double y) {
        if(length >= height){
            this.length = length;
            this.height = height;
        }else{
            this.length = height;
            this.height = length;
        }
        this.Array = Array;
        this.x = x;
        this.y = y;
        calculateArcLengths();
        setRandomColors();
        createPiechart();
    }

    private double[] Lengths;
    private double sum = 0;
    private void calculateArcLengths() {
        Map<Double, Double> sumMap = new HashMap<>();
        for (double num : Array) {
            sumMap.put(num, sumMap.getOrDefault(num, 0.0) + num);
        }
        double[] truncatedArray = new double[sumMap.size()];
        int index = 0;
        for (double key : sumMap.keySet()) {
            truncatedArray[index++] = sumMap.get(key);
        }
        Lengths = new double[truncatedArray.length];
        for (double value : truncatedArray) {
            sum += value;
        }
        for (int i = 0; i < truncatedArray.length; i++) {
            Lengths[i] = (truncatedArray[i] / sum) * 360;
        }
    }

    private Color[] CurrentColors;
    private Color[] randColors;
    private void setRandomColors() {
        randColors = new Color[Lengths.length];
        for (int i = 0; i < Lengths.length; i++) {
            Color randColor = new Color(Math.random(), Math.random(), Math.random(), Math.random());
            randColors[i] = randColor;
        }
        CurrentColors =  randColors;
    }
    public Color[] SEMSColor;
    private void setSEMSColor (){
        SEMSColor = new Color[5];
        SEMSColor[0] = Color.rgb(52,211,236);//6
        SEMSColor[1] = Color.rgb(114,102,186);//7
        SEMSColor[2] = Color.rgb(255,189,7);//8
        SEMSColor[3] = Color.rgb(240,80,80);//9
        SEMSColor[4] = Color.rgb(129,200,104); //10
        CurrentColors = SEMSColor;
    }

    public void createPiechart(){
        double currentAngle = 90;

        for(int i = 0; i < Lengths.length; i++) {

            Arc arc = new Arc();
            arc.setStartAngle(currentAngle);
            arc.setLength(Lengths[i]);
            arc.setRadiusX(length/2);
            arc.setRadiusY(height/2);
            arc.setCenterX(x);
            arc.setCenterY(y);
            arc.setType(ArcType.ROUND);
            arc.setFill(CurrentColors[i]);
            arc.setStroke(Color.BLACK);


            this.getChildren().add(arc);

            currentAngle += Lengths[i];
        }
    }
    public void make3D(StatsPieChart pieChart , double depth){

        for (int j = 1; j <= depth ; j++){
            double currentAngle = 90;
            for(int i = 0; i < Lengths.length; i++){
                Arc arc = new Arc();
                arc.setStartAngle(currentAngle);
                arc.setLength(Lengths[i]);

                if (currentAngle>190 && currentAngle<350){
                    double pointX = pieChart.x + pieChart.length/2*Math.cos(Math.toRadians(currentAngle));
                    double pointY = pieChart.y - pieChart.height/2*Math.sin(Math.toRadians(currentAngle));
                    Line line = new Line(pointX , pointY , pointX , pointY+depth-1);

                    this.getChildren().add(line);
                }

                arc.setRadiusX(length/2);
                arc.setRadiusY(height/2);
                arc.setCenterX(x);
                arc.setCenterY(y+depth-j);
                arc.setType(ArcType.ROUND);
                arc.setFill(CurrentColors[i]);
                if( j == 1 ){
                    arc.setStroke(Color.BLACK);

                    arc.setStrokeWidth(1.5);
                }
                this.getChildren().add(arc);

                currentAngle += Lengths[i];
            }
        }

        pieChart.createPiechart();
        Line line1 = new Line(pieChart.x-pieChart.length/2 , pieChart.y ,pieChart.x-pieChart.length/2,pieChart.y+depth);
        Line line2 = new Line(pieChart.x+pieChart.length/2 , pieChart.y  ,pieChart.x+pieChart.length/2,pieChart.y+depth);

        line1.setStrokeWidth(1.5);
        line2.setStrokeWidth(1.5);
        this.getChildren().addAll(line1 , line2);
    }

    public void setColors(Color[] colors){
        if (colors.length != Lengths.length) {
            System.out.println("Error: Lengths and Lengths do not match");
        }else{
            CurrentColors = colors;
            createPiechart();
        }
    }
    public void setColors(){
        setSEMSColor();
        createPiechart();
    }
}

