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
            robot.frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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
                        //turn 90 degrees to the right
                        encoderDrive(1,150,-150);
                        sleep(500);

                        //go straight for half a square
                        encoderDrive(1,100,100);
                        sleep(500);

                        //turn 90 degrees to the left
                        encoderDrive(1,-150,150);

                        //go straight for 2 and a half squares
                        encoderDrive(1, 500, 500);

                        //robot.dropoffwobblegoal();

                        //turn 90 degrees to the left
                        encoderDrive(1,-150,150);

                        //go straight one square
                        encoderDrive(1,200,200);

                        //turn 90 degrees to the right
                        encoderDrive(1, 150,-150);

                        //go straight for half a square
                        encoderDrive(1,100,100);
                        break;

                    case ONE:
                        //turn 90 degrees to the right
                        encoderDrive(1,150,-150);

                        //go straight for half a square
                        encoderDrive(1,100,100);

                        //turn 90 degrees to the left
                        encoderDrive(1,-150,150);

                        //go straight for 4 squares
                        encoderDrive(1, 800, 800);

                        //turn 90 degrees to the left
                        encoderDrive(1,-150,150);

                        //go straight for half a square
                        encoderDrive(1,100,100);

                        //robot.dropoffwobblegoal();

                        //go backwards half a square
                        encoderDrive(1,-100,-100);

                        //turn 90 degrees to the left
                        encoderDrive(1, -150,150);

                        //go straight 1 square
                        encoderDrive(1, 200, 200);
                        break;
                    case FOUR:
                        //turn 90 degrees to the right
                        encoderDrive(1,150,-150);

                        //go straight for half a square
                        encoderDrive(1,100,100);

                        //turn 90 degrees to the left
                        encoderDrive(1,-150,150);

                        //go straight for 4 and a half squares
                        encoderDrive(1, 900, 900);

                        //robot.dropoffwobblegoal();

                        //go backwards 1 and a half square
                        encoderDrive(1,-300,-300);
                        break;
                }
            }

        }
        public void encoderDrive(double speed, double leftCounts, double rightCounts) {

            int newLeftTarget;
            int newRightTarget;

            if (opModeIsActive()) {
                //determining new target position
                newLeftTarget = robot.frontleft.getCurrentPosition() + (int) (leftCounts);
                newRightTarget = robot.frontright.getCurrentPosition() + (int) (rightCounts);
                robot.frontleft.setTargetPosition(newLeftTarget);
                robot.frontright.setTargetPosition(newRightTarget);

                robot.frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                robot.frontleft.setPower(Math.abs(speed));
                robot.frontright.setPower(Math.abs(speed));
            }
            //stop robot
            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);

            robot.frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
}