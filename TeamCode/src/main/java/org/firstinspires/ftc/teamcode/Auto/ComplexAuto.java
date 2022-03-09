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

@Autonomous(name = "Complex Auto")
public class ComplexAuto extends LinearOpMode {
    //public ProjectOdometryTest robot = new ProjectOdometryTest();
    OpenCvWebcam webcam;
    ShippingElementDetector detector = new ShippingElementDetector(telemetry);

    @Override
    public void runOpMode() throws InterruptedException {
        //drivetrain.init(hardwareMap);
        SampleMecanumDrive drivetrain = new SampleMecanumDrive(hardwareMap);

        int numberOfSeconds = 0;
        Path p = Path.Red;

        while (!isStarted()) {
            if (gamepad1.b) {
                p = Path.Red;
            }
            if (gamepad1.x) {
                p = Path.Blue;
            }
            if (gamepad1.left_bumper) {
                numberOfSeconds = numberOfSeconds + 1;
            }
            else if (gamepad1.right_bumper){
                numberOfSeconds = numberOfSeconds - 1;
            }
            telemetry.addData("Path: ", p);
            telemetry.addData("wait number of seconds", numberOfSeconds);
            telemetry.update();
        }
        //Vector2d represents a coordinate (x,y)

        TrajectorySequence ComplexAutoRedBottomShipping = drivetrain.trajectorySequenceBuilder(new Pose2d(7.5, -63, Math.toRadians(0)))
                .strafeLeft(10)
                .waitSeconds(1)
                .forward(23)
                .turn(Math.toRadians(-90))
                .forward(12)
                //.lineTo(new Vector2d(-13, -47))
                .build();
        TrajectorySequence ComplexAutoRedShippingMiddle = drivetrain.trajectorySequenceBuilder(new Pose2d(7.5, -63, Math.toRadians(0)))
                .strafeLeft(10)
                .waitSeconds(1)
                .forward(23)
                .turn(Math.toRadians(-90))
                .forward(2)
                .build();
        TrajectorySequence ComplexAutoBlueShipping = drivetrain.trajectorySequenceBuilder(new Pose2d(-7.5, -63, Math.toRadians(0)))
                .strafeRight(10)
                .forward(20)
                .turn(Math.toRadians(90))
                .forward(12)
                //.lineTo(new Vector2d(-13, -47))
                .build();
        TrajectorySequence testRed = drivetrain.trajectorySequenceBuilder(new Pose2d(7.5, -63, Math.toRadians(0)))
                //.strafeRight(10)
                .forward(20)
                //.turn(Math.toRadians(90))
                //.forward(12)
                //.lineTo(new Vector2d(-13, -47))
                .build();

        /*TrajectorySequence ComplexAutoRedWarehouse = drivetrain.trajectorySequenceBuilder(ComplexAutoRedShipping.end())
                .back(20)
                .turn(Math.toRadians(-90))
                .strafeLeft(30)
                .forward(30)
                .strafeRight(15)
                .build();

         */

        waitForStart();
        //test waiting

        drivetrain.setMotorPowers(0, 0, 0, 0);
        sleep(numberOfSeconds*1000);
        Pose2d startPose = new Pose2d(7.5, -63, Math.toRadians(0));
        drivetrain.setPoseEstimate(startPose);

        drivetrain.followTrajectorySequence(testRed);
        /*
        drivetrain.storageunit.setTargetPosition(-3100);
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
        */
        /*if(p==Path.Red){
            Pose2d startPose = new Pose2d(7.5, -63, Math.toRadians(0));
            drivetrain.setPoseEstimate(startPose);

            drivetrain.followTrajectorySequence(ComplexAutoRedShippingMiddle);
            drivetrain.storageunit.setTargetPosition(-3100);
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
            //extend lift to the bottom level RED
            /*drivetrain.storageunit.setTargetPosition(-750);
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

            //drivetrain.followTrajectorySequence(ComplexAutoRedWarehouse);

            drivetrain.setMotorPowers(1, 1, -1, -1);
            sleep(1000);

            drivetrain.setMotorPowers(0,0,0,0);
            sleep(500);

            //strafing against wall
            //front left, back left, back right, front right
            drivetrain.setMotorPowers(-1, 1, -1, 1);
            sleep(1500);

            drivetrain.setMotorPowers(0,0,0,0);
            sleep(500);

            //going into warehouse
            drivetrain.setMotorPowers(1,1,1,1);
            sleep(1750);

            drivetrain.setMotorPowers(0,0,0,0);
            sleep(200);

            //strafing left in the warehouse
            drivetrain.setMotorPowers(1,-1,-1,1);
            sleep(300);

            drivetrain.setMotorPowers(0,0,0,0);

        }
        */
        //starting pose for blue side near warehouse
        //Pose2d startPose = new Pose2d(11.6, 58.7, Math.toRadians(180));

        //starting pose for red side near warehouse




        //drivetrain.followTrajectorySequence(testRed);



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
