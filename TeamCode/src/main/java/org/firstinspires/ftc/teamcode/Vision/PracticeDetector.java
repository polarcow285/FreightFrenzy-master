package org.firstinspires.ftc.teamcode.Vision;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PracticeDetector extends OpenCvPipeline {
    Telemetry telemetry;

    Mat mat = new Mat();

   /* static final Rect rightROI = new Rect(
            new Point( 180, 0),
            new Point(280, 180)
    );
    static final Rect middleROI = new Rect(
            new Point( 100, 0),
            new Point(180, 180)
    );
    static final Rect leftROI = new Rect(
            new Point( 0, 0),
            new Point(100, 180)
    );

    */




    public PracticeDetector(Telemetry t) { telemetry = t; }
    //https://stackoverflow.com/questions/66757199/color-percentage-in-image-for-python-using-opencv
    @Override
    public Mat processFrame(Mat input) {
        Rect rightROI = new Rect(
                new Point( (input.cols()/3) * 2, 0),
                new Point((input.cols()/3) * 3, input.rows())
        );
        Rect middleROI = new Rect(
                new Point( (input.cols()/3), input.rows()/2),
                new Point((input.cols()/3) * 2, input.rows())
        );
        Rect leftROI = new Rect(
                new Point( 0, 0),
                new Point((input.cols()/3), input.rows())
        );
        //mat.convertTo(destMat, -1, 50, 100);
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);


        //define HSV range to identify the color orange
        //Scalar lowHSV = new Scalar (10, 100, 140);
        //Scalar highHSV = new Scalar(20, 255, 255);
        Scalar lowHSV = new Scalar (15, 100, 100);
        Scalar highHSV = new Scalar(30, 255, 255);

        //applies a threshold (everything that is yellow will be white, everything else will be black)
        //returns a new mat with this threshold

        telemetry.addData("columns", input.cols());
        telemetry.addData("rows", input.rows());
        telemetry.update();
        Core.inRange(mat,lowHSV, highHSV, mat);


        Scalar color = new Scalar(64, 64, 64);
        Imgproc.rectangle(input, new Point( (input.cols()/3), input.rows()/2),
                new Point((input.cols()/3) * 2, input.rows()), color, 5);
        //Imgproc.rectangle(mat, new Point( (input.cols()/3), 0), new Point((input.cols()/3) * 2, input.rows()), color, 5);

        //extract regions of interest from camera frame
        //submat = sub-mat, a portion of the original
        Mat left = mat.submat(leftROI);
        Mat middle = mat.submat(middleROI);
        int whitePixels = Core.countNonZero(middle);
        Mat right = mat.submat(rightROI);


        /*
         * Compute the average pixel value of each submat region. We're
         * taking the average of a single channel buffer, so the value
         * we need is at index 0. We could have also taken the average
         * pixel value of the 3-channel image, and referenced the value
         * at index 2 here.
         */
        int avg1 = (int) Core.mean(middle).val[0];
        //avg2 = (int) Core.mean(region2_Cb).val[0];
        //avg3 = (int) Core.mean(region3_Cb).val[0];



        double leftPercentage = Core.sumElems(left).val[0] / leftROI.area() / 255;
        //double middlePercentage = Core.sumElems(middle).val[0] / middleROI.area() / 255;
        double middlePercentage = whitePixels / middleROI.area();
        double rightPercentage = Core.sumElems(right).val[0] / rightROI.area() / 255;

        //deallocates the Matrix data from the memory
//        left.release();
//        middle.release();
//        right.release();

       // telemetry.addData("left percentage", Math.round(leftPercentage * 100) + "%");
        //telemetry.addData("white pixels", whitePixels);
        telemetry.addData("average pixel value", avg1);
        //telemetry.addData("middle percentage", Math.round(middlePercentage * 100) + "%");
        //telemetry.addData("right percentage", Math.round(rightPercentage * 100) + "%");

        telemetry.update();

        return input;
    }
}
