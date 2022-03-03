package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;
import org.firstinspires.ftc.teamcode.Vision.ShippingElementDetector;

import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "Autonomous")
public class ComplexAuto extends LinearOpMode {
    //public ProjectOdometryTest robot = new ProjectOdometryTest();
    OpenCvWebcam webcam;
    ShippingElementDetector detector = new ShippingElementDetector(telemetry);

    @Override
    public void runOpMode() throws InterruptedException {
        //drivetrain.init(hardwareMap);
        SampleMecanumDrive drivetrain = new SampleMecanumDrive(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"), cameraMonitorViewId);

        webcam.setPipeline(detector);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                // Usually this is where you'll want to start streaming from the camera (see section 4)
                webcam.startStreaming(1920, 1080, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

        Path p = Path.Red;

        while (!isStarted()) {
            if (gamepad1.b) {
                p = Path.Red;
            }
            if (gamepad1.x) {
                p = Path.Blue;
            }
            telemetry.addData("Path: ", p);
            telemetry.update();
        }
        //Vector2d represents a coordinate (x,y)

        Trajectory strafeLeft = drivetrain.trajectoryBuilder(new Pose2d(11.6, 58.7, Math.toRadians(180)))
                .strafeLeft(15)
                .build();

        Trajectory poop = drivetrain.trajectoryBuilder(strafeLeft.end())
                .splineTo(new Vector2d(-12, 40), Math.toRadians(90))
                .build();
        /*




        TrajectorySequence ComplexAuto4 = drivetrain.trajectorySequenceBuilder(new Pose2d(7.5, -63, Math.toRadians(0)))
                .strafeLeft(15)
                .lineToLinearHeading(new Pose2d(-13, -47, Math.toRadians(270)))
                //.lineTo(new Vector2d(-13, -47))
                .build();

        */
        TrajectorySequence ComplexAutoRedShipping = drivetrain.trajectorySequenceBuilder(new Pose2d(7.5, -63, Math.toRadians(0)))
                .strafeLeft(10)
                .forward(20)
                .turn(Math.toRadians(-90))
                .forward(12)
                //.lineTo(new Vector2d(-13, -47))
                .build();
        TrajectorySequence ComplexAutoRedWarehouse = drivetrain.trajectorySequenceBuilder(ComplexAutoRedShipping.end())
                .back(12)
                .turn(Math.toRadians(0))
                .strafeRight(10)
                .forward(62)
                .strafeLeft(15)
                .build();
        /*Trajectory hmmm = drivetrain.trajectoryBuilder(ComplexAuto4.end())
                .lineTo(new Vector2d(-13, -47))
                .build();
        */
        waitForStart();

        //starting pose for blue side near warehouse
        //Pose2d startPose = new Pose2d(11.6, 58.7, Math.toRadians(180));

        //starting pose for red side near warehouse
        Pose2d startPose = new Pose2d(7.5, -63, Math.toRadians(0));
        drivetrain.setPoseEstimate(startPose);




        drivetrain.followTrajectorySequence(ComplexAutoRedShipping);
        //extend lift to the bottom level RED
        drivetrain.storageunit.setTargetPosition(-750);
        drivetrain.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrain.storageunit.setPower(1);
        while(drivetrain.storageunit.isBusy()) {
            // Let the drive team see that we're waiting on the motor
            telemetry.addData("Status", drivetrain.storageunit.getCurrentPosition());
            telemetry.update();
        }
        drivetrain.storageunit.setPower(0);

        //opening trapdoor
        drivetrain.trapdoor.setPosition(0);
        sleep(3000);
        //closing trapdoor
        drivetrain.trapdoor.setPosition(1);

        //retract lift
        drivetrain.storageunit.setTargetPosition(0);
        drivetrain.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drivetrain.storageunit.setPower(1);
        while(drivetrain.storageunit.isBusy()) {
            // Let the drive team see that we're waiting on the motor
            telemetry.addData("Status", drivetrain.storageunit.getCurrentPosition());
            telemetry.update();
        }
        drivetrain.storageunit.setPower(0);

        drivetrain.followTrajectorySequence(ComplexAutoRedWarehouse);


        //drivetrain.followTrajectory(hmmm);

        //drivetrain.followTrajectory(strafeLeft);
        //drivetrain.followTrajectory(poop);



        //get the position of the shipping element
        /*ShippingElementDetector.ShippingElementLocation elementLocation = detector.getShippingElementLocation();
        telemetry.addData("element location:", elementLocation);
        telemetry.update();
        */

    }
}
