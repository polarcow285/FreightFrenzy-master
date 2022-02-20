package org.firstinspires.ftc.teamcode.Vision;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.openftc.easyopencv.OpenCvPipeline;

public class DemoDetector extends OpenCvPipeline{
    Mat mat = new Mat();

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        //define HSV range to identify the color yellow
        Scalar lowHSV = new Scalar (15, 100, 100);
        Scalar highHSV = new Scalar(30, 255, 255);

        //applies a threshold (everything that is yellow will be white, everything else will be black)
        //returns a new mat with this threshold
        Core.inRange(mat,lowHSV, highHSV, mat);

        return mat;
    }


}
