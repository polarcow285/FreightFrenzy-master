package org.firstinspires.ftc.teamcode.Vision;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import org.firstinspires.ftc.robotcore.external.Telemetry;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoDetector extends OpenCvPipeline{
    //https://stemrobotics4all.org/ftc/java-lessons/opencv/
    Telemetry telemetry;
    Mat matGrayscale = new Mat();
    Mat matHSV = new Mat();
    Mat R = new Mat();
    Mat G = new Mat();
    Mat B = new Mat();
    Mat result = new Mat();
    Mat labels = new Mat();

    static final int STREAM_WIDTH = 1920; // modify for your camera
    static final int STREAM_HEIGHT = 1080; // modify for your camera


    public DemoDetector(Telemetry t) { telemetry = t; }

    /*
     * This function takes the RGB frame, converts to YCrCb,
     * and extracts the Y channel to the 'Y' variable
     */
    void inputToR(Mat input) {
        //Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
        ArrayList<Mat> rgbChannels = new ArrayList<Mat>(3);
        Core.split(input, rgbChannels);
        R = rgbChannels.get(0);
    }

    void inputToG(Mat input) {
        //Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
        ArrayList<Mat> rgbChannels = new ArrayList<Mat>(3);
        Core.split(input, rgbChannels);
        G = rgbChannels.get(1);
    }

    void inputToB(Mat input) {
        //Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
        ArrayList<Mat> rgbChannels = new ArrayList<Mat>(3);
        Core.split(input, rgbChannels);
        B = rgbChannels.get(2);
    }



    @Override
    public Mat processFrame(Mat src) {
        //CONTOURS
        Scalar red = new Scalar(255, 0, 0);

        Imgproc.cvtColor(src, matHSV, Imgproc.COLOR_RGB2HSV);

        //define HSV range to identify the color orange
        Scalar lowHSV = new Scalar (10, 100, 140);
        Scalar highHSV = new Scalar(20, 255, 255);

        //applies a threshold (everything that is yellow will be white, everything else will be black)
        //returns a new mat with this threshold
        Core.inRange(matHSV,lowHSV, highHSV, matHSV);

        //Imgproc.cvtColor(matHSV, matGrayscale, Imgproc.COLOR_RGB2GRAY);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(matHSV, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        for (MatOfPoint contour: contours) {
            Rect rectangle = Imgproc.boundingRect(contour);;
            Imgproc.rectangle(src, rectangle.tl(), rectangle.br(), new Scalar(255,0,0),1);
        }

        /*

        //https://stackoverflow.com/questions/18581633/fill-in-and-detect-contour-rectangles-in-java-opencv

        for (MatOfPoint contour: contours) {
            RotatedRect rotatedRect = Imgproc.minAreaRect(new MatOfPoint2f(contour.toArray()));
            drawRotatedRect(src, rotatedRect, red, 4);
        }
        */

        // Draw contours in dest Mat
        //Imgproc.drawContours(src, contours, -1, red);

        //int[] array = new int[20];

        //IMAGE SUBTRACTION
        /*
        inputToR(src);
        inputToG(src);
        inputToB(src);

        Core.subtract(R, B, result);
        //Core.subtract(result, G, result);

        //https://stackoverflow.com/questions/17035005/using-get-and-put-to-access-pixel-values-in-opencv-for-java

        int rows = result.rows(); //Calculates number of rows
        int cols = result.cols(); //Calculates number of columns
        int ch = result.channels(); //Calculates number of channels (Grayscale: 1, RGB: 3, etc.)

        for (int i=0; i<rows; i++)
        {
            for (int j=0; j<cols; j++)
            {
                double[] data = result.get(i, j); //Stores element in an array
                for (int k = 0; k < ch; k++) //Runs for the available number of channels
                {
                    if(data[k] < 50){
                        data[k] = 0;
                    }
                    else{
                        data[k] = 255;
                    }
                    ; //Pixel modification done here
                }
                result.put(i, j, data); //Puts element back into matrix
            }
        }
        double max = Double.NEGATIVE_INFINITY;

        Imgproc.connectedComponents(result, labels, 4);

        //double[] labelData = new ArrayList <Double>();
        double labelData[] = new double[rows*cols];

        for (int i=0; i<rows; i++)
        {
            for (int j=0; j<cols; j++)
            {
                labelData = result.get(i, j); //Stores element in an array
                //result.put(i, j, labelData); //Puts element back into matrix
            }
        }

        for(double cur: labelData)
            max = Math.max(max, cur);
        telemetry.addData("labels", Arrays.toString(labelData));
        telemetry.update();

         */
        //Core.sumElems(result).val[0] * result.cols()
        //sum the columns
        //Core.add(result, G, result);
       /// Core.subtract(src, B, result);
        //R - B yellow shines really well
        //avg = (int) Core.mean(R).val[0];
       // ArrayList<Mat> dst = new ArrayList<>(3);
        //Core.split(src, dst);

       // telemetry.addData("frame", src);
       // int x = src.width()/2 -10;
       // telemetry.addData("r", dst.get(0));
       // telemetry.addData("g", src.get(src.width()/2 - 10,src.height()/2)[1]);
        //telemetry.addData("b", src.get(src.width()/2 - 10,src.height()/2)[2]);
        //Color color = new Color(pixel, true);
        //telemetry.addData("avg", avg);
        //telemetry.update();

       // mat = dst.get(0);
        //Scalar color = new Scalar(0, 64, 0);
        //Point center = new Point(src.width()/2 -10, 10);
        //Imgproc.circle(src, center, 5, color, 1);
        //Imgproc.rectangle(mat, new Point(src.height()/2, src.height()/2),new Point(src.height()/2, 99) , color, 10);


        return src;
    }
    //https://stackoverflow.com/questions/18581633/fill-in-and-detect-contour-rectangles-in-java-opencv
    public static void drawRotatedRect(Mat image, RotatedRect rotatedRect, Scalar color, int thickness) {
        Point[] vertices = new Point[4];
        rotatedRect.points(vertices);
        MatOfPoint points = new MatOfPoint(vertices);
        Imgproc.drawContours(image, Arrays.asList(points), -1, color, thickness);
    }


}
