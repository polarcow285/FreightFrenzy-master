package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Vision.ShippingElementDetector;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;

@Autonomous(name = "AutoShippingElement")
public class Meet3 extends LinearOpMode{
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
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

        Path p = Path.Red;

        while(!isStarted()) {
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

        //get the position of the shipping element
        ShippingElementDetector.ShippingElementLocation elementLocation = detector.getShippingElementLocation();
        telemetry.addData("element location:", elementLocation);
        telemetry.update();

        if(p == Path.Red){
            //RED SIDE
            //strafing right
            robot.frontleft.setPower(-1);
            robot.frontright.setPower(1);
            robot.backleft.setPower(1);
            robot.backright.setPower(-1);
            sleep(300);
            //test

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);
            sleep(200);

            //driving forward
            robot.frontleft.setPower(1);
            robot.frontright.setPower(1);
            robot.backleft.setPower(1);
            robot.backright.setPower(1);
            sleep(680);

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);
            sleep(200);

            //turning right
            robot.frontleft.setPower(1);
            robot.frontright.setPower(-1);
            robot.backleft.setPower(1);
            robot.backright.setPower(-1);
            sleep(1100);

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);
            sleep(200);

            switch (elementLocation) {
                case LEFT: case UNKNOWN:
                    //bottom level - level 1 - RED

                    //driving forward to reach the shipping hub RED
                    robot.frontleft.setPower(1);
                    robot.frontright.setPower(1);
                    robot.backleft.setPower(1);
                    robot.backright.setPower(1);
                    sleep(530);

                    robot.frontleft.setPower(0);
                    robot.frontright.setPower(0);
                    robot.backleft.setPower(0);
                    robot.backright.setPower(0);
                    sleep(200);

                    //extend lift to the bottom level RED
                    robot.storageunit.setTargetPosition(-750);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                    while(robot.storageunit.isBusy()) {
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
                    while(robot.storageunit.isBusy()) {
                        // Let the drive team see that we're waiting on the motor
                        telemetry.addData("Status", robot.storageunit.getCurrentPosition());
                        telemetry.update();
                    }
                    robot.storageunit.setPower(0);
                    //move back a little so that we don't bump the shipping hub
                    //while turning
                    robot.frontleft.setPower(-1);
                    robot.frontright.setPower(-1);
                    robot.backleft.setPower(-1);
                    robot.backright.setPower(-1);
                    sleep(300);

                    robot.frontleft.setPower(0);
                    robot.frontright.setPower(0);
                    robot.backleft.setPower(0);
                    robot.backright.setPower(0);
                    sleep(200);
                    break;
                case MIDDLE:
                    //middle level - level 2 RED
                    robot.frontleft.setPower(1);
                    robot.frontright.setPower(1);
                    robot.backleft.setPower(1);
                    robot.backright.setPower(1);
                    sleep(300);
                    robot.frontleft.setPower(0);
                    robot.frontright.setPower(0);
                    robot.backleft.setPower(0);
                    robot.backright.setPower(0);


                    //extend lift to the middle level RED
                    robot.storageunit.setTargetPosition(-3100);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                    while(robot.storageunit.isBusy()) {
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
                    while(robot.storageunit.isBusy()) {
                        // Let the drive team see that we're waiting on the motor
                        telemetry.addData("Status", robot.storageunit.getCurrentPosition());
                        telemetry.update();
                    }
                    robot.storageunit.setPower(0);
                    break;
                case RIGHT:
                    //top level - level 3 RED

                    //driving forward to reach the shipping hub RED
                    robot.frontleft.setPower(1);
                    robot.frontright.setPower(1);
                    robot.backleft.setPower(1);
                    robot.backright.setPower(1);
                    sleep(170);

                    robot.frontleft.setPower(0);
                    robot.frontright.setPower(0);
                    robot.backleft.setPower(0);
                    robot.backright.setPower(0);
                    sleep(200);

                    //extend lift to the top level RED
                    robot.storageunit.setTargetPosition(-4758);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                    while(robot.storageunit.isBusy()) {
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
                    while(robot.storageunit.isBusy()) {
                        // Let the drive team see that we're waiting on the motor
                        telemetry.addData("Status", robot.storageunit.getCurrentPosition());
                        telemetry.update();
                    }
                    robot.storageunit.setPower(0);
                    break;


            }

            //turning to face warehouse (trapdoor is facing warehouse) RED
            robot.frontleft.setPower(1);
            robot.frontright.setPower(-1);
            robot.backleft.setPower(1);
            robot.backright.setPower(-1);
            sleep(1000);

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);
            sleep(500);

            //strafing against wall

            robot.frontleft.setPower(-1);
            robot.frontright.setPower(1);
            robot.backleft.setPower(1);
            robot.backright.setPower(-1);
            sleep(1500);

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);
            sleep(500);

            //going into warehouse
            robot.frontleft.setPower(1);
            robot.frontright.setPower(1);
            robot.backleft.setPower(1);
            robot.backright.setPower(1);
            sleep(1750);

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);
            sleep(200);

            //strafing left in the warehouse
            robot.frontleft.setPower(1);
            robot.frontright.setPower(-1);
            robot.backleft.setPower(-1);
            robot.backright.setPower(1);
            sleep(300);

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);

        }
        else if (p==Path.Blue){
            //BLUE SIDE
            //robot strafes away from the wall, left
            robot.frontleft.setPower(-1);
            robot.frontright.setPower(1);
            robot.backleft.setPower(1);
            robot.backright.setPower(-1);
            sleep(300);

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);
            sleep(200);

            //robot driving forward
            robot.frontleft.setPower(-1);
            robot.frontright.setPower(-1);
            robot.backleft.setPower(-1);
            robot.backright.setPower(-1);
            sleep(680);

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);
            sleep(200);

            //turning so that trapdoor faces the shipping hub
            robot.frontleft.setPower(1);
            robot.frontright.setPower(-1);
            robot.backleft.setPower(1);
            robot.backright.setPower(-1);
            sleep(1100);

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);
            sleep(200);

            switch (elementLocation) {
                case LEFT: case UNKNOWN:
                    telemetry.addData("beginning", "yay");
                    //bottom level - level 1 BLUE
                    //driving forward to reach the shipping hub BLUE
                    robot.frontleft.setPower(1);
                    robot.frontright.setPower(1);
                    robot.backleft.setPower(1);
                    robot.backright.setPower(1);
                    sleep(530);

                    robot.frontleft.setPower(0);
                    robot.frontright.setPower(0);
                    robot.backleft.setPower(0);
                    robot.backright.setPower(0);
                    sleep(200);

                    //extend lift to the bottom level BLUE
                    robot.storageunit.setTargetPosition(-750);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                    while(robot.storageunit.isBusy()) {
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
                    while(robot.storageunit.isBusy()) {
                        // Let the drive team see that we're waiting on the motor
                        telemetry.addData("Status", robot.storageunit.getCurrentPosition());
                        telemetry.update();
                    }
                    robot.storageunit.setPower(0);

                    //move back a little so that we don't bump the shipping hub
                    //while turning
                    robot.frontleft.setPower(-1);
                    robot.frontright.setPower(-1);
                    robot.backleft.setPower(-1);
                    robot.backright.setPower(-1);
                    sleep(300);

                    robot.frontleft.setPower(0);
                    robot.frontright.setPower(0);
                    robot.backleft.setPower(0);
                    robot.backright.setPower(0);
                    sleep(200);


                    break;
                case MIDDLE:
                    telemetry.addData("middle", "yay");
                    //middle level - level 2 BLUE
                    //code to drop off at middle level BLUE

                    robot.frontleft.setPower(1);
                    robot.frontright.setPower(1);
                    robot.backleft.setPower(1);
                    robot.backright.setPower(1);
                    sleep(300);
                    robot.frontleft.setPower(0);
                    robot.frontright.setPower(0);
                    robot.backleft.setPower(0);
                    robot.backright.setPower(0);

                    //extend lift to the middle level
                    robot.storageunit.setTargetPosition(-3100);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                    while(robot.storageunit.isBusy()) {
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
                    while(robot.storageunit.isBusy()) {
                        // Let the drive team see that we're waiting on the motor
                        telemetry.addData("Status", robot.storageunit.getCurrentPosition());
                        telemetry.update();
                    }
                    robot.storageunit.setPower(0);
                    break;

                case RIGHT:
                    //top level - level 3 - BLUE
                    //driving forward to reach the shipping hub BLUE
                    robot.frontleft.setPower(1);
                    robot.frontright.setPower(1);
                    robot.backleft.setPower(1);
                    robot.backright.setPower(1);
                    sleep(175);

                    robot.frontleft.setPower(0);
                    robot.frontright.setPower(0);
                    robot.backleft.setPower(0);
                    robot.backright.setPower(0);
                    sleep(200);
                    //extend lift to the top level BLUE
                    robot.storageunit.setTargetPosition(-4758);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setPower(1);
                    while(robot.storageunit.isBusy()) {
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
                    while(robot.storageunit.isBusy()) {
                        // Let the drive team see that we're waiting on the motor
                        telemetry.addData("Status", robot.storageunit.getCurrentPosition());
                        telemetry.update();
                    }
                    robot.storageunit.setPower(0);
                    break;
            }

            //turning to face the warehouse (trapdoor is facing warehouse) BLUE
            robot.frontleft.setPower(-1);
            robot.frontright.setPower(1);
            robot.backleft.setPower(-1);
            robot.backright.setPower(1);
            sleep(1000);

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);
            sleep(500);

            //strafing into the wall (test)
            robot.frontleft.setPower(1);
            robot.frontright.setPower(-1);
            robot.backleft.setPower(-1);
            robot.backright.setPower(1);
            sleep(1500);

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);
            sleep(500);

            //driving into warehouse (test)
            robot.frontleft.setPower(1);
            robot.frontright.setPower(1);
            robot.backleft.setPower(1);
            robot.backright.setPower(1);
            sleep(1750);

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);
            sleep(200);

            //strafing right in the warehouse
            robot.frontleft.setPower(-1);
            robot.frontright.setPower(1);
            robot.backleft.setPower(1);
            robot.backright.setPower(-1);
            sleep(300); //test

            //stop
            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);

        }




        telemetry.update();


        webcam.stopStreaming();




    }


}
