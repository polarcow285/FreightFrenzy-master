package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;
import org.firstinspires.ftc.teamcode.Vision.ShippingElementDetector;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
@Autonomous(name = "Autonomous")
public class Regionals extends LinearOpMode {
    public SampleMecanumDrive robot = new SampleMecanumDrive(hardwareMap);
    OpenCvWebcam webcam;
    ShippingElementDetector detector = new ShippingElementDetector(telemetry);

    TrajectorySequence ComplexAutoRed = robot.trajectorySequenceBuilder(new Pose2d(7.5, -63, Math.toRadians(0)))
            .waitSeconds(1)
            .forward(23)
            .turn(Math.toRadians(-90))
            .build();

    TrajectorySequence bottomLevel = robot.trajectorySequenceBuilder(new Pose2d(7.5, -63, Math.toRadians(0)))
            .waitSeconds(1)
            .forward(12)
            .build();

    TrajectorySequence middleLevel = robot.trajectorySequenceBuilder(new Pose2d(7.5, -63, Math.toRadians(0)))
            .waitSeconds(1)
            .forward(8) //test
            .build();

    TrajectorySequence topLevel = robot.trajectorySequenceBuilder(new Pose2d(7.5, -63, Math.toRadians(0)))
            .waitSeconds(1)
            .forward(2) //test
            .build();
    @Override
    public void runOpMode() throws InterruptedException {

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
        int numberOfSeconds = 0;
        Path p = Path.Red;

        while (!isStarted()) {
            if (gamepad1.b) {
                p = Path.Red;
            }
            if (gamepad1.x) {
                p = Path.Blue;
            }
            if (gamepad1.dpad_up) {
                numberOfSeconds = numberOfSeconds + 1;
            }
            else if (gamepad1.dpad_down){
                numberOfSeconds = numberOfSeconds - 1;
            }
            telemetry.addData("Path: ", p);
            telemetry.addData("wait number of seconds", numberOfSeconds);
            telemetry.update();
        }

        waitForStart();
        //test waiting
        robot.setMotorPowers(0, 0, 0, 0);
        sleep(numberOfSeconds*1000);

        //get the position of the shipping element
        ShippingElementDetector.ShippingElementLocation elementLocation = detector.getShippingElementLocation();
        telemetry.addData("element location:", elementLocation);
        telemetry.update();

        if (p == Path.Red) {
            //RED SIDE

            //strafing right
            robot.setMotorPowers(-1, 1, -1, 1);
            sleep(300);
            //test

            robot.setMotorPowers(0,0,0,0);
            sleep(200);

            robot.followTrajectorySequence(ComplexAutoRed);

            switch (elementLocation) {
                case LEFT:
                case UNKNOWN:
                    //bottom level - level 1 - RED

                    //driving forward to reach the shipping hub RED
                    robot.followTrajectorySequence(bottomLevel);

                    //extend lift to the bottom level RED
                    robot.storageunit.setTargetPosition(-750);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                    while (robot.storageunit.isBusy()) {
                        // Let the drive team see that we're waiting on the motor
                        telemetry.addData("Status", robot.storageunit.getCurrentPosition());
                        telemetry.update();
                    }
                    robot.storageunit.setPower(0);

                    //opening trapdoor
                    robot.trapdoor.setPosition(0);
                    sleep(3000);
                    //closing trapdoor
                    robot.trapdoor.setPosition(1);

                    //retract lift
                    robot.storageunit.setTargetPosition(0);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                    while (robot.storageunit.isBusy()) {
                        // Let the drive team see that we're waiting on the motor
                        telemetry.addData("Status", robot.storageunit.getCurrentPosition());
                        telemetry.update();
                    }
                    robot.storageunit.setPower(0);
                    //move back a little so that we don't bump the shipping hub
                    //while turning
                    robot.setMotorPowers(-1, -1, -1, -1);
                    sleep(300);

                    robot.setMotorPowers(0, 0, 0, 0);
                    sleep(200);
                    break;
                case MIDDLE:
                    //middle level - level 2 RED
                    robot.followTrajectorySequence(middleLevel);


                    //extend lift to the middle level RED
                    robot.storageunit.setTargetPosition(-3100);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                    while (robot.storageunit.isBusy()) {
                        // Let the drive team see that we're waiting on the motor
                        telemetry.addData("Status", robot.storageunit.getCurrentPosition());
                        telemetry.update();
                    }
                    robot.storageunit.setPower(0);

                    //opening trapdoor
                    robot.trapdoor.setPosition(0);
                    sleep(3000);
                    //close trapdoor
                    robot.trapdoor.setPosition(1);

                    //retract lift
                    robot.storageunit.setTargetPosition(0);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                    while (robot.storageunit.isBusy()) {
                        // Let the drive team see that we're waiting on the motor
                        telemetry.addData("Status", robot.storageunit.getCurrentPosition());
                        telemetry.update();
                    }
                    robot.storageunit.setPower(0);
                    break;
                case RIGHT:
                    //top level - level 3 RED

                    //driving forward to reach the shipping hub RED
                    robot.followTrajectorySequence(topLevel);

                    //extend lift to the top level RED
                    robot.storageunit.setTargetPosition(-4758);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                    while (robot.storageunit.isBusy()) {
                        // Let the drive team see that we're waiting on the motor
                        telemetry.addData("Status", robot.storageunit.getCurrentPosition());
                        telemetry.update();
                    }
                    robot.storageunit.setPower(0);

                    //opening trapdoor
                    robot.trapdoor.setPosition(0);
                    sleep(3000);
                    //close trapdoor
                    robot.trapdoor.setPosition(1);

                    //retract lift
                    robot.storageunit.setTargetPosition(0);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                    while (robot.storageunit.isBusy()) {
                        // Let the drive team see that we're waiting on the motor
                        telemetry.addData("Status", robot.storageunit.getCurrentPosition());
                        telemetry.update();
                    }
                    robot.storageunit.setPower(0);
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
            sleep(1750);

            robot.setMotorPowers(0,0,0,0);
            sleep(200);

            //strafing left in the warehouse
            robot.setMotorPowers(1,-1,-1,1);
            sleep(300);

            robot.setMotorPowers(0,0,0,0);

        } else if (p == Path.Blue) {
            //BLUE SIDE
            //robot strafes away from the wall, left
            moveRight(350, 1);
            stopRobot(200);
            //robot driving forward
            moveBackwards(680, 1);
            stopRobot(200);
            //turning so that trapdoor faces the shipping hub
            turnRight(1175, 1);
            stopRobot(200);

            switch (elementLocation) {
                case LEFT:
                case UNKNOWN:
                    telemetry.addData("beginning", "yay");
                    //bottom level - level 1 BLUE
                    //driving forward to reach the shipping hub BLUE
                    moveForwards(540, 1);
                    stopRobot(200);

                    //extend lift to the bottom level BLUE
                    robot.storageunit.setTargetPosition(-1000);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                    while (robot.storageunit.isBusy()) {
                        // Let the drive team see that we're waiting on the motor
                        telemetry.addData("Status", robot.storageunit.getCurrentPosition());
                        telemetry.update();
                    }
                    robot.storageunit.setPower(0);

                    //opening trapdoor
                    robot.trapdoor.setPosition(0);
                    sleep(3000);
                    //closing trapdoor
                    robot.trapdoor.setPosition(1);

                    //retract lift
                    robot.storageunit.setTargetPosition(0);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                    while (robot.storageunit.isBusy()) {
                        // Let the drive team see that we're waiting on the motor
                        telemetry.addData("Status", robot.storageunit.getCurrentPosition());
                        telemetry.update();
                    }
                    robot.storageunit.setPower(0);

                    //move back a little so that we don't bump the shipping hub
                    //while turning

                    moveBackwards(300, 1);
                    stopRobot(200);

                    break;
                case MIDDLE:
                    telemetry.addData("middle", "yay");
                    //middle level - level 2 BLUE
                    //code to drop off at middle level BLUE

                    moveForwards(400, 1);
                    robot.frontleft.setPower(0);
                    robot.frontright.setPower(0);
                    robot.backleft.setPower(0);
                    robot.backright.setPower(0);

                    //extend lift to the middle level
                    robot.storageunit.setTargetPosition(-3100);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                    while (robot.storageunit.isBusy()) {
                        // Let the drive team see that we're waiting on the motor
                        telemetry.addData("Status", robot.storageunit.getCurrentPosition());
                        telemetry.update();
                    }
                    robot.storageunit.setPower(0);

                    //opening trapdoor
                    robot.trapdoor.setPosition(0);
                    sleep(3000);
                    //close trapdoor
                    robot.trapdoor.setPosition(1);

                    //retract lift
                    robot.storageunit.setTargetPosition(0);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                    while (robot.storageunit.isBusy()) {
                        // Let the drive team see that we're waiting on the motor
                        telemetry.addData("Status", robot.storageunit.getCurrentPosition());
                        telemetry.update();
                    }
                    robot.storageunit.setPower(0);
                    break;

                case RIGHT:
                    //top level - level 3 - BLUE
                    //driving forward to reach the shipping hub BLUE
                    moveForwards(175, 1);
                    stopRobot(200);

                    //extend lift to the top level BLUE
                    robot.storageunit.setTargetPosition(-4758);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                    while (robot.storageunit.isBusy()) {
                        // Let the drive team see that we're waiting on the motor
                        telemetry.addData("Status", robot.storageunit.getCurrentPosition());
                        telemetry.update();
                    }
                    robot.storageunit.setPower(0);

                    //opening trapdoor
                    robot.trapdoor.setPosition(0);
                    sleep(3000);
                    //close trapdoor
                    robot.trapdoor.setPosition(1);

                    //retract lift
                    robot.storageunit.setTargetPosition(0);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                    while (robot.storageunit.isBusy()) {
                        // Let the drive team see that we're waiting on the motor
                        telemetry.addData("Status", robot.storageunit.getCurrentPosition());
                        telemetry.update();
                    }
                    robot.storageunit.setPower(0);
                    break;
            }

            //turning to face the warehouse (trapdoor is facing warehouse) BLUE
            turnLeft(1000, 1);
            stopRobot(500);

            //strafing into the wall (test)
            moveLeft(1500, 1);
            stopRobot(500);

            //driving into warehouse (test)
            moveForwards(1591, 1);
            stopRobot(200);

            //strafing right in the warehouse
            moveRight(500, 1);
            //test

            //stop
            robot.setMotorPowers(0,0,0,0);

        }


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

