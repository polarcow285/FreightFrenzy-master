package org.firstinspires.ftc.teamcode.Vision;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
@Disabled
public class RVATDetector extends OpenCvPipeline {
    //video frame of camera, is our input for processFrame()
    Mat mat = new Mat();


    public enum ObjectLocation {
        LOW,
        MIDDLE,
        HIGH,
        UNKNOWN
    }

    //define regions of interest (ROI):
    static final Rect lowROI = new Rect(
            new Point(0,0 ),
            new Point(1920, 360)
    );
    static final Rect middleROI = new Rect(
            new Point(0,360 ),
            new Point(1920, 720)
    );
    static final Rect highROI = new Rect(
            new Point(0, 720),
            new Point(1920,1080 )
    );
    private ObjectLocation objectLocation;

    @Override
    public Mat processFrame(Mat input) {
        //turn into HSV (hue(color), saturation(intensity), value (brightness))
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        //HSV = hue(color), saturation(intensity), value (brightness)


        //define HSV range to identify the color blue
        Scalar lowHSV = new Scalar (105, 100, 140);
        Scalar highHSV = new Scalar(135, 255, 255);

        //applies a threshold (everything that is yellow will be white,
        // everything else will be black)
        //returns a new mat with this threshold
        Core.inRange(mat, lowHSV, highHSV, mat);

        Core.inRange(mat, lowHSV, highHSV, mat);

        //extract regions of interest from camera frame:
        //submat = sub-matrix, a portion of the original
        Mat low = mat.submat(lowROI);
        Mat middle = mat.submat(middleROI);
        Mat high = mat.submat(highROI);

        //calculate what percentage of the ROI became white:
        //(add all the pixels together, divide by its area, divide by 255)
        double lowPercentage = Core.sumElems(low).val[0] / lowROI.area() / 255;
        double middlePercentage = Core.sumElems(middle).val[0] / middleROI.area() / 255;
        double highPercentage = Core.sumElems(high).val[0] / highROI.area() / 255;

        //deallocates the Matrix data from the memory
        low.release();
        middle.release();
        high.release();



        //control flow/if statements to determine which ROI has the shipping element:
        if(lowPercentage > middlePercentage && lowPercentage > highPercentage){
            objectLocation = RVATDetector.ObjectLocation.LOW;
        }
        else if(middlePercentage > lowPercentage && middlePercentage > highPercentage){
            objectLocation = RVATDetector.ObjectLocation.MIDDLE;
        }
        else if(highPercentage > lowPercentage && highPercentage > middlePercentage){
            objectLocation = RVATDetector.ObjectLocation.HIGH;
        }
        else{
            objectLocation = RVATDetector.ObjectLocation.UNKNOWN;
        }
        return mat;


    }



    public ObjectLocation getShippingElementLocation(){
        return objectLocation;
    }
}
