package org.firstinspires.ftc.teamcode.Vision;

import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class RVATDetector extends OpenCvPipeline {
    //video frame of camera, is our input for processFrame()
    Mat mat = new Mat();


    public enum ObjectLocation {
        LOW,
        MIDDLE,
        HIGH,
    }

    //define regions of interest (ROI):
    static final Rect lowROI = new Rect(
            new Point(0, 0),
            new Point(1920, 360)
    );
    static final Rect middleROI = new Rect(
            new Point(0, 360),
            new Point(1920, 720)
    );
    static final Rect highROI = new Rect(
            new Point(0, 720),
            new Point(1920, 1080)
    );
    private ObjectLocation objectLocation;

    @Override
    public Mat processFrame(Mat input) {
        //turn into HSV (hue(color), saturation(intensity), value (brightness))


        //define HSV range to identify the color yellow



        //applies a threshold (everything that is yellow will be white,
        //everything else will be black)
        //returns a new mat with this threshold




        //extract regions of interest from camera frame:
        //submat = sub-matrix, a portion of the original


        //deallocates the Matrix data from the memory
        _______.release();
        -------.release();
        -------.release();

        //calculate what percentage of the ROI became white:
        //(add all the pixels together, divide by its area, divide by 255)
        double lowPercentage = Core.sumElems(-------).val[0] / lowROI.area() / 255;
        double middlePercentage = Core.sumElems(-------).val[0] / middleROI.area() / 255;
        double highPercentage = Core.sumElems(-------).val[0] / highROI.area() / 255;

        //control flow to determine which ROI has the shipping element:


        return mat;


    }



    public ObjectLocation getShippingElementLocation(){
        return objectLocation;
    }
}
