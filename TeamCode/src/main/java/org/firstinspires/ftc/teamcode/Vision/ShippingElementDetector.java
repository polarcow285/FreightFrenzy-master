package org.firstinspires.ftc.teamcode.Vision;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import org.firstinspires.ftc.robotcore.external.Telemetry;


public class ShippingElementDetector extends OpenCvPipeline {
        Telemetry telemetry;
        //video frame of camera, is our input for processFrame()
        Mat mat = new Mat();

        public enum ShippingElementLocation {
            LEFT,
            MIDDLE,
            RIGHT,
            UNKNOWN
        }
        private ShippingElementLocation elementLocation;

        //defining regions of interest (ROI)
        //Divide the camera frame into three rectangles
        //rectangles are made from defining two opposite vertices of a triangle,
        //which are connected by the diagonals
        static final Rect leftROI = new Rect(
                new Point( 200, 0),
                new Point(700, 900)
        );
        static final Rect middleROI = new Rect(
            new Point( 700, 0),
            new Point(1200, 900)
        );
        static final Rect rightROI = new Rect(
            new Point( 1200, 0),
            new Point(1600, 900)
        );



        public ShippingElementDetector(Telemetry t) { telemetry = t; }

        @Override
        public Mat processFrame(Mat input) {
            

            //HSV = hue(color), saturation(intensity), value (brightness)
            Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

            //define HSV range to identify the color yellow
            Scalar lowHSV = new Scalar (15, 100, 10);
            Scalar highHSV = new Scalar(30, 255, 255);

            //applies a threshold (everything that is yellow will be white,
            // everything else will be black)
            //returns a new mat with this threshold
            Core.inRange(mat,lowHSV, highHSV, mat);



            //extract regions of interest from camera frame
            //submat = sub-matrix, a portion of the original
            Mat left = mat.submat(leftROI);
            Mat middle = mat.submat(middleROI);
            Mat right = mat.submat(rightROI);


            //calculate what percentage of the ROI became white
            //(add all the pixels together, divide by its area, divide by 255)
            double leftPercentage = Core.sumElems(left).val[0] / leftROI.area() / 255;
            double middlePercentage = Core.sumElems(middle).val[0] / middleROI.area() / 255;
            double rightPercentage = Core.sumElems(right).val[0] / rightROI.area() / 255;

            //deallocates the Matrix data from the memory


            left.release();
            middle.release();
            right.release();

            if(leftPercentage > middlePercentage && leftPercentage > rightPercentage){
                elementLocation = ShippingElementLocation.LEFT;
            }
            else if(middlePercentage > leftPercentage && middlePercentage > rightPercentage){
                elementLocation = ShippingElementLocation.MIDDLE;
            }
            else if(rightPercentage > leftPercentage && rightPercentage > middlePercentage){
                elementLocation = ShippingElementLocation.RIGHT;
            }
            else {
                elementLocation = ShippingElementLocation.UNKNOWN;
            }
            telemetry.addData("left percentage", Math.round(leftPercentage * 100) + "%");
            telemetry.addData("middle percentage", Math.round(middlePercentage * 100) + "%");
            telemetry.addData("right percentage", Math.round(rightPercentage * 100) + "%");

            telemetry.update();
            return mat;



        }
        public ShippingElementLocation getShippingElementLocation(){
            return elementLocation;
        }

}
