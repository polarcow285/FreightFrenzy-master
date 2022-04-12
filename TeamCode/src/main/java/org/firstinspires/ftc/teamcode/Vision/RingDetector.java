package org.firstinspires.ftc.teamcode.Vision;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class RingDetector extends OpenCvPipeline {
    Telemetry telemetry;
    //video frame of camera, is our input for processFrame()
    Mat mat = new Mat();

    public enum numberOfRings{
        ZERO,
        ONE,
        FOUR
    }

    private numberOfRings numOfRings;

    static final Rect ROI = new Rect(
            new Point( 120, 100),
            new Point(200, 200)
    );

    public RingDetector(Telemetry t) { telemetry = t; }

    @Override
    public Mat processFrame(Mat input) {
        //HSV = hue(color), saturation(intensity), value (brightness)
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        //define HSV range to identify the color yellow
        Scalar lowHSV = new Scalar (3, 80, 80);
        Scalar highHSV = new Scalar(30, 255, 255);

        Core.inRange(mat, lowHSV, highHSV, mat);

        //extract regions of interest from camera frame
        //submat = sub-matrix, a portion of the original
        Mat full = mat.submat(ROI);


        //calculate what percentage of the ROI became white
        //(add all the pixels together, divide by its area, divide by 255)
        double percentage = Core.sumElems(full).val[0] / ROI.area() / 255;

        telemetry.addData("ring %", (Core.sumElems(full).val[0] / ROI.area() / 255) * 100);

        if(percentage == 0){
            numOfRings = numberOfRings.ZERO;
        }
        /*if(leftPercentage > middlePercentage && leftPercentage > rightPercentage){
            elementLocation = ShippingElementDetector.ShippingElementLocation.LEFT;
        }
        else if(middlePercentage > leftPercentage && middlePercentage > rightPercentage){
            elementLocation = ShippingElementDetector.ShippingElementLocation.MIDDLE;
        }
        else if(rightPercentage > leftPercentage && rightPercentage > middlePercentage){
            elementLocation = ShippingElementDetector.ShippingElementLocation.RIGHT;
        }
        else{
            elementLocation = ShippingElementDetector.ShippingElementLocation.UNKNOWN;
        }
        telemetry.addData("left percentage", Math.round(leftPercentage * 100) + "%");
        telemetry.addData("middle percentage", Math.round(middlePercentage * 100) + "%");
        telemetry.addData("right percentage", Math.round(rightPercentage * 100) + "%");*/

        telemetry.update();
        return full;


    }
}
