package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;
import org.firstinspires.ftc.teamcode.Vision.ShippingElementDetector;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

@Autonomous(name = "autonomous")
public class Regionals extends LinearOpMode {
    OpenCvWebcam webcam;
    ShippingElementDetector detector = new ShippingElementDetector(telemetry);

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive robot = new SampleMecanumDrive(hardwareMap);
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"), cameraMonitorViewId);

        webcam.setPipeline(detector);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                // Usually this is where you'll want to start streaming from the camera (see section 4)
                webcam.startStreaming(1920, 1080, OpenCvCameraRotation.UPRIGHT);
                telemetry.addData("camera initialized!", "");
                telemetry.update();
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });
        float numberOfSeconds = 0;
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

        TrajectorySequence ComplexAutoRed = robot.trajectorySequenceBuilder(new Pose2d(7.5, -63, Math.toRadians(0)))
                .waitSeconds(0.5)
                .forward(23)
                .turn(Math.toRadians(-90))
                .build();
        TrajectorySequence ComplexAutoBlue = robot.trajectorySequenceBuilder(new Pose2d(-7.5, -63, Math.toRadians(0)))
                .waitSeconds(0.5)
                .back(23)
                .turn(Math.toRadians(-90))
                .build();

        Trajectory retractLift = robot.trajectoryBuilder(p == Path.Red ? ComplexAutoRed.end() : ComplexAutoBlue.end())
                .back(0.2)
                .addDisplacementMarker(()->{
                    robot.storageunit.setTargetPosition(0);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                })
                //.waitSeconds(0.5)
                //.turn(Math.toRadians(-90))
                .build();
        
        TrajectorySequence bottomLevel = robot.trajectorySequenceBuilder(p == Path.Red ? ComplexAutoRed.end() : ComplexAutoBlue.end())
                .waitSeconds(0.5)
                .forward(12)
                .addDisplacementMarker(() -> {
                    // Run your action in here!
                    robot.storageunit.setTargetPosition(-750);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                })
                .build();

        TrajectorySequence middleLevel = robot.trajectorySequenceBuilder(p == Path.Red ? ComplexAutoRed.end() : ComplexAutoBlue.end())
                .waitSeconds(0.5)
                .forward(9) //test
                .addDisplacementMarker(() -> {
                    // Run your action in here!
                    robot.storageunit.setTargetPosition(-2350);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                })
                .build();

        TrajectorySequence topLevel = robot.trajectorySequenceBuilder(p == Path.Red ? ComplexAutoRed.end() : ComplexAutoBlue.end())
                .setVelConstraint(robot.getVelocityConstraint(15, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH ))
                .waitSeconds(0.5)
                .forward(3) //test
                .waitSeconds(2)
                .addDisplacementMarker(() -> {
                    // Run your action in here!
                    robot.storageunit.setTargetPosition(-5000);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                })
                .build();
        TrajectorySequence ComplexAutoRedTop = robot.trajectorySequenceBuilder(new Pose2d(7.5, -63, Math.toRadians(0)))
                .waitSeconds(0.5)
                .forward(20)
                .turn(Math.toRadians(-90))
                .build();
        TrajectorySequence ComplexAutoBlueTop = robot.trajectorySequenceBuilder(new Pose2d(-7.5, -63, Math.toRadians(0)))
                .waitSeconds(0.5)
                .back(20)
                .turn(Math.toRadians(-90))
                .build();

        waitForStart();

        //get the position of the shipping element
        ShippingElementDetector.ShippingElementLocation elementLocation = detector.getShippingElementLocation();
        telemetry.addData("element location:", elementLocation);
        telemetry.update();

        Pose2d startPose;
        if (p == Path.Red) {
            //RED SIDE
            startPose = new Pose2d(7.5, -63, Math.toRadians(0));
            robot.setPoseEstimate(startPose);
            //strafing right
            robot.setMotorPowers(-1, 1, -1, 1);
            sleep(350);
            //test

            robot.setMotorPowers(0,0,0,0);
            telemetry.addData("pose", robot.getPoseEstimate());
            telemetry.update();
            sleep(200);

            if(!isStopRequested()){
                if(elementLocation == ShippingElementDetector.ShippingElementLocation.RIGHT){
                    robot.followTrajectorySequence(ComplexAutoRedTop);
                }
                else{
                    robot.followTrajectorySequence(ComplexAutoRed);
                }
            }

            switch (elementLocation) {
                case LEFT: case UNKNOWN:
                    //bottom level - level 1 - RED

                    //driving forward to reach the shipping hub + extend RED
                    if (!isStopRequested()){
                        robot.followTrajectorySequence(bottomLevel);
                    }





                    //opening trapdoor
                    robot.trapdoor.setPosition(0);
                    sleep(3000);
                    //closing trapdoor
                    robot.trapdoor.setPosition(1);
                    if(!isStopRequested()){
                        robot.followTrajectory(retractLift);
                    }




                    robot.setMotorPowers(-1, -1, -1, -1);
                    sleep(300);

                    robot.setMotorPowers(0, 0, 0, 0);
                    sleep(200);
                    break;
                case MIDDLE:
                    //middle level - level 2 RED

                    //go to middle shipping hub + extend lift to the middle level RED
                    if (!isStopRequested()){
                        robot.followTrajectorySequence(middleLevel);
                    }




                    //opening trapdoor
                    robot.trapdoor.setPosition(0);
                    sleep(3000);
                    //close trapdoor
                    robot.trapdoor.setPosition(1);

                    //retract lift
                    if (!isStopRequested()){
                        robot.followTrajectory(retractLift);
                    }
                    break;
                case RIGHT:
                    //top level - level 3 RED

                    //driving forward to reach the shipping hub + extend lift RED
                    if (!isStopRequested()){
                        robot.followTrajectorySequence(topLevel);
                    }

                    robot.storageunit.setPower(0);

                    //opening trapdoor
                    robot.trapdoor.setPosition(0);
                    sleep(3000);
                    //close trapdoor
                    robot.trapdoor.setPosition(1);

                    //retract lift
                    if (!isStopRequested()){
                        robot.followTrajectory(retractLift);
                    }
                    break;


            }

            robot.setMotorPowers(1, 1, -1, -1);
            sleep(1000);

            robot.setMotorPowers(0,0,0,0);
            sleep(500);

            //strafing against wall
            //front left, back left, back right, front right
            robot.setMotorPowers(-1, 1, -1, 1);
            sleep(1500);

            robot.setMotorPowers(0,0,0,0);
            sleep(500);

            //going into warehouse
            robot.setMotorPowers(1,1,1,1);
            sleep(1660);

            robot.setMotorPowers(0,0,0,0);
            sleep(200);

            //strafing left in the warehouse
            robot.setMotorPowers(1,-1,1,-1);
            sleep(900);

            //moving forward
            robot.setMotorPowers(0.7,0.7,0.7,0.7);
            sleep(700);

            robot.setMotorPowers(0,0,0,0);
            sleep(200);

            //turning
            robot.setMotorPowers(-1, -1, 1, 1);
            sleep(1200);

            robot.setMotorPowers(0,0,0,0);
            sleep(200);

            //moving backward
            robot.setMotorPowers(-1, -1, -1, -1);
            sleep(200);

            robot.setMotorPowers(0,0,0,0);


        } else if (p == Path.Blue) {
            startPose = new Pose2d(-7.5, -63, Math.toRadians(0));
            robot.setPoseEstimate(startPose);

            //BLUE SIDE
            //robot strafes away from the wall, left
            robot.setMotorPowers(-1, 1, -1, 1);
            sleep(350);

            robot.setMotorPowers(0, 0,0, 0);
            sleep(200);

            if(!isStopRequested()){
                if(elementLocation == ShippingElementDetector.ShippingElementLocation.RIGHT){
                    robot.followTrajectorySequence(ComplexAutoBlueTop);
                }
                else{
                    robot.followTrajectorySequence(ComplexAutoBlue);
                }
            }

            switch (elementLocation) {
                case LEFT:
                case UNKNOWN:
                    //bottom level - level 1 BLUE
                    //driving forward to reach the shipping hub BLUE
                    if (!isStopRequested()){
                        robot.followTrajectorySequence(bottomLevel);
                    }

                    //extend lift to the bottom level BLUE

                    //opening trapdoor
                    robot.trapdoor.setPosition(0);
                    sleep(3000);
                    //closing trapdoor
                    robot.trapdoor.setPosition(1);

                    //move back and retract lift
                    if (!isStopRequested()){
                        robot.followTrajectory(retractLift);
                    }

                    //move back a little so that we don't bump the shipping hub
                    //while turning
                    robot.setMotorPowers(-1, -1, -1, -1);
                    sleep(300);

                    robot.setMotorPowers(0,0, 0, 0);
                    sleep(200);

                    break;
                case MIDDLE:
                    //middle level - level 2 BLUE
                    //code to drop off at middle level BLUE
                    if (!isStopRequested()){
                        robot.followTrajectorySequence(middleLevel);
                    }


                    //opening trapdoor
                    robot.trapdoor.setPosition(0);
                    sleep(3000);
                    //close trapdoor
                    robot.trapdoor.setPosition(1);

                    //move back and retract lift
                    if (!isStopRequested()){
                        robot.followTrajectory(retractLift);
                    }


                    break;

                case RIGHT:
                    //top level - level 3 - BLUE
                    //driving forward to reach the shipping hub BLUE
                    if (!isStopRequested()){
                        robot.followTrajectorySequence(topLevel);
                    }


                    //opening trapdoor
                    robot.trapdoor.setPosition(0);
                    sleep(3000);
                    //close trapdoor
                    robot.trapdoor.setPosition(1);

                    //move back and retract lift
                    if (!isStopRequested()){
                        robot.followTrajectory(retractLift);
                    }

                    break;
            }

            //turning to face the warehouse (trapdoor is facing warehouse) BLUE
            robot.setMotorPowers(-1, -1, 1, 1);
            sleep(1000);

            robot.setMotorPowers(0, 0,0, 0);
            sleep(200);

            //strafing into the wall (test)
            robot.setMotorPowers(1, -1, 1, -1);
            sleep(1500);

            robot.setMotorPowers(0, 0,0, 0);
            sleep(200);

            //driving into warehouse (test)
            robot.setMotorPowers(1, 1, 1, 1);
            sleep(1660);

            robot.setMotorPowers(0, 0,0, 0);
            sleep(200);

            //strafing right in the warehouse
            robot.setMotorPowers(-1, 1, -1, 1);
            sleep(900);

            robot.setMotorPowers(0, 0,0, 0);
            sleep(200);
            //test

            //moving forward
            robot.setMotorPowers(0.7,0.7,0.7,0.7);
            sleep(700);

            robot.setMotorPowers(0,0,0,0);
            sleep(200);

            //turning
            robot.setMotorPowers(1, 1, -1, -1);
            sleep(1200);

            robot.setMotorPowers(0,0,0,0);
            sleep(200);

//            //moving backward
//            robot.setMotorPowers(-1, -1, -1, -1);
//            sleep(200);
//
//            robot.setMotorPowers(0,0,0,0);



        }
        robot.setMotorPowers(0,0,0,0);

        telemetry.update();


        webcam.stopStreaming();


    }
    //methods that JV helped us with but we're prob not gonna use :/
    /*public void moveForwards(int time, double speed) {
        robot.frontleft.setPower(speed);
        robot.frontright.setPower(speed);
        robot.backleft.setPower(speed);
        robot.backright.setPower(speed);
        sleep(time);
    }

    public void moveRight(int time, double speed) {
        robot.frontleft.setPower(-speed);
        robot.frontright.setPower(speed);
        robot.backleft.setPower(speed);
        robot.backright.setPower(-speed);
        sleep(time);
    }

    public void moveLeft(int time, double speed) {
        robot.frontleft.setPower(speed);
        robot.frontright.setPower(-speed);
        robot.backleft.setPower(-speed);
        robot.backright.setPower(speed);
        sleep(time);
    }

    public void moveBackwards(int time, double speed) {
        robot.frontleft.setPower(-speed);
        robot.frontright.setPower(-speed);
        robot.backleft.setPower(-speed);
        robot.backright.setPower(-speed);
        sleep(time);
    }

    public void turnRight(int time, double speed) {
        robot.frontleft.setPower(speed);
        robot.frontright.setPower(-speed);
        robot.backleft.setPower(speed);
        robot.backright.setPower(-speed);
        sleep(time);
    }

    public void turnLeft(int time, double speed) {
        robot.frontleft.setPower(-speed);
        robot.frontright.setPower(speed);
        robot.backleft.setPower(-speed);
        robot.backright.setPower(speed);
        sleep(time);
    }

    public void stopRobot(int time) {
        robot.frontleft.setPower(0);
        robot.frontright.setPower(0);
        robot.backleft.setPower(0);
        robot.backright.setPower(0);
        sleep(time);
    }
    */

}



