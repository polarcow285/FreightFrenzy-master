package org.firstinspires.ftc.teamcode.Vision;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class DemoDetector extends OpenCvPipeline{
    Telemetry telemetry;
    Mat mat = new Mat();
    Mat grayMat = new Mat();
    Mat binaryMat = new Mat();
    static final Rect barcodeROI = new Rect(
            new Point( 140, 100),
            new Point(180, 140)
    );

    public DemoDetector(Telemetry t) { telemetry = t; }

    @Override
    public Mat processFrame(Mat input) {
        Mat dst = new Mat();
        //input = input.submat(barcodeROI);
        Imgproc.cvtColor(input, grayMat, Imgproc.COLOR_BGR2GRAY);

        //Preparing the kernel matrix object
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size((2*2) + 1, (2*2)+1));
        //Applying dilate on the Image
        //Imgproc.dilate(grayMat, dst, kernel);

        //Mat binaryMat = grayMat.submat(barcodeROI);

        Imgproc.threshold(grayMat, binaryMat, 100, 255, Imgproc.THRESH_BINARY_INV);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchey = new Mat();
        Imgproc.findContours(binaryMat, contours, hierarchey, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        //Drawing the Contours
        Scalar color = new Scalar(0, 0, 255);

        Imgproc.drawContours(input, contours, -1, color, 2, Imgproc.LINE_8,
                hierarchey, 2, new Point() ) ;


        return input;

        /*
        Imgproc.cvtColor(input, grayMat, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(grayMat, binaryMat, 100, 255, Imgproc.THRESH_BINARY_INV);
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchey = new Mat();
        Imgproc.findContours(binaryMat, contours, hierarchey, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        //Drawing the Contours
        Scalar color = new Scalar(0, 0, 255);
        Imgproc.drawContours(input, contours, -1, color, 2, Imgproc.LINE_8,
                hierarchey, 2, new Point() ) ;
        */

        /*
        ULTIMATE GOAL
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        //define HSV range to identify the color yellow
        Scalar lowHSV = new Scalar (5, 100, 50);
        Scalar highHSV = new Scalar(15, 255, 255);

        //applies a threshold (everything that is yellow will be white, everything else will be black)
        //returns a new mat with this threshold
        Core.inRange(mat,lowHSV, highHSV, mat);

        Mat region = mat.submat(barcodeROI);

        double regionPercentage = Core.sumElems(region).val[0] / barcodeROI.area() / 255;

        telemetry.addData("percent", regionPercentage * 100);
        telemetry.update();

         */

        //deallocates Matrix data from memory
        //region.release()
        //return region;

    }


}
