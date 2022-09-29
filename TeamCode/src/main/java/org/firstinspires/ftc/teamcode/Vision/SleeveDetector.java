package org.firstinspires.ftc.teamcode.Vision;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SleeveDetector extends OpenCvPipeline {
    Telemetry telemetry;

    Mat greenMat = new Mat();
    Mat yellowMat = new Mat();
    Mat purpleMat = new Mat();

    public enum SleeveColor {
        GREEN,
        YELLOW,
        PURPLE,
        UNKNOWN
    }

    private SleeveColor color;





    public SleeveDetector(Telemetry t) { telemetry = t; }

    //set up regions of interest, etc


    @Override
    public Mat processFrame(Mat input) {
        Rect ROI = new Rect(
                new Point(input.cols()/3,input.rows()/2),
                new Point(input.cols()/3*2,input.rows())
        );

        //image processing code

        //HSV = hue(color), saturation(intensity), value (brightness)
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        //define HSV range to identify the color green
        Scalar greenLowHSV = new Scalar(40, 30, 100);
        Scalar greenHighHSV = new Scalar(80, 255, 255);

        //define HSV range to identify the color yellow
        Scalar yellowLowHSV = new Scalar(15, 100, 100);
        Scalar yellowHighHSV = new Scalar(30, 255, 255);

        //define HSV range to identify the color purple
        Scalar purpleLowHSV = new Scalar(135, 100, 100);
        Scalar purpleHighHSV = new Scalar(172, 255, 255);

        //applies a threshold (everything that is green will be white,
        // everything else will be black)
        //returns a new mat with this threshold
        Core.inRange(greenMat, greenLowHSV, greenHighHSV, greenMat);
        Mat greenSubmat = greenMat.submat(ROI);


        int greenAvg = (int) Core.mean(greenSubmat).val[0];


        //calculate what percentage of the ROI became white
        //(add all the pixels together, divide by its area, divide by 255)
        //double greenPercentage = Core.sumElems(mat).val[0] / ROI.area() / 255;


        //telemetry.addData("green percentage", Math.round(greenPercentage * 100) + "%");


        //deallocates the Matrix data from memory
        //mat.release();



        //applies a threshold (everything that is yellow will be white,
        // everything else will be black)
        //returns a new mat with this threshold
        Core.inRange(yellowMat, yellowLowHSV, yellowHighHSV, yellowMat);
        Mat yellowSubmat = yellowMat.submat(ROI);

        int yellowAvg = (int) Core.mean(yellowSubmat).val[0];

        //calculate what percentage of the ROI became white
        //(add all the pixels together, divide by its area, divide by 255)
        //double yellowPercentage = Core.sumElems(mat).val[0] / ROI.area() / 255;


        //deallocates the Matrix data from memory
        //mat.release();



        //applies a threshold (everything that is purple will be white,
        // everything else will be black)
        //returns a new mat with this threshold
        //Core.inRange(mat, purpleLowHSV, purpleHighHSV, mat);
        //int purpleAvg = (int) Core.mean(submat).val[0];

        //calculate what percentage of the ROI became white
        //(add all the pixels together, divide by its area, divide by 255)
        //double purplePercentage = Core.sumElems(mat).val[0] / ROI.area() / 255;


        //deallocates the Matrix data from memory
        //mat.release();


        /*
        if(greenPercentage > yellowPercentage && greenPercentage > purplePercentage){
            color = SleeveColor.GREEN;
        }
        else if(yellowPercentage > greenPercentage && yellowPercentage > purplePercentage){
            color = SleeveColor.YELLOW;
        }
        else if(purplePercentage > greenPercentage && purplePercentage > yellowPercentage){
            color = SleeveColor.PURPLE;
        }
        else{
            color = SleeveColor.UNKNOWN;
        }
        telemetry.addData("green percentage", Math.round(greenPercentage * 100) + "%");
        telemetry.addData("yellow percentage", Math.round(yellowPercentage * 100) + "%");
        telemetry.addData("purple percentage", Math.round(purplePercentage * 100) + "%");

        telemetry.update();

         */
        telemetry.addData("average green pixel value", greenAvg);
        telemetry.addData("average yellow pixel value", yellowAvg);
        //telemetry.addData("average purple pixel value", purpleAvg);

        telemetry.update();

        return mat;
    }
    public SleeveColor getSleeveColor(){
        return color;
    }
}