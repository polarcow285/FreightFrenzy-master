package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Vision.ACE;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;


    @Autonomous(name = "encodersACE")
    public class encodersACE extends LinearOpMode{
        public ProjectOdometryTest robot = new ProjectOdometryTest();
        OpenCvWebcam webcam;
        ACE detector = new ACE(telemetry);

        @Override
        public void runOpMode() throws InterruptedException {
            robot.init(hardwareMap);

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

            waitForStart();

            //get the number of rings
            ACE.NumberOfRings ringNumber = detector.getRingNumber();
            telemetry.addData("ring number:", ringNumber);
            telemetry.update();
            //red path
            if(p == Path.Red) {
                switch(ringNumber) {
                    case ZERO: case UNKNOWN:
                        moveRight(500, 1);
                        moveForwards(2000, 1);
                        //robot.dropWobbleGoal

                        //park on the white line
                        moveLeft(500, 1);
                        moveForwards(500, 1);
                        stopRobot(1000);
                        break;
                    case ONE:
                        moveRight(500, 1);
                        moveForwards(2000, 1);
                        moveLeft(500, 1);
                        moveForwards(500, 1);
                        //robot.dropWobbleGoal

                        //park on the white line
                        moveBackwards(500,1);
                        stopRobot(1000);
                        break;
                    case FOUR:
                        moveRight(500,1);
                        moveForwards(2500,1);
                        //robot.dropWobbleGoal

                        //park on the white line
                        moveBackwards(750, 1);
                        stopRobot(1000);
                        break;
                }
            }

        }
        public void moveForwards(int time, double speed) {
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
}