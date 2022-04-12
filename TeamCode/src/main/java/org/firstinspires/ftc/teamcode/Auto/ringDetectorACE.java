package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Vision.ShippingElementDetector;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;


/*
public class ringDetectorACE {
    @Autonomous(name = "ringDetectorACE")
    public class ringDetectorACE extends LinearOpMode{
        public ProjectOdometryTest robot = new ProjectOdometryTest();
        OpenCvWebcam webcam;
        ShippingElementDetector detector = new ShippingElementDetector(telemetry);

        @Override
        public void runOpMode() throws InterruptedException {
            robot.init(hardwareMap);

            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"), cameraMonitorViewId);

            webcam.setPipeline(detector);

            webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
            {
                @Override
                public void onOpened()
                {
                    // Usually this is where you'll want to start streaming from the camera (see section 4)
                    webcam.startStreaming(1920, 1080, OpenCvCameraRotation.UPRIGHT);
                }
                @Override










*/