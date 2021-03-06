package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Vision.ShippingElementDetector;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;

@Autonomous(name = "IntermediateAuto")
public class IntermediateAuto extends LinearOpMode {
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
            //strafing right
            robot.frontleft.setPower(-1);
            robot.frontright.setPower(1);
            robot.backleft.setPower(1);
            robot.backright.setPower(-1);
            sleep(300); //test

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
            sleep(700);

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
            sleep(1150);

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);
            sleep(200);

            //driving forward to reach the storage unit
            robot.frontleft.setPower(1);
            robot.frontright.setPower(1);
            robot.backleft.setPower(1);
            robot.backright.setPower(1);
            sleep(250);

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);
            sleep(200);

            switch (elementLocation) {
                case LEFT: case UNKNOWN:
                    //bottom level - level 1
                    //extend lift to the bottom level
                    while(robot.storageunit.getCurrentPosition() > -3000){
                        robot.frontleft.setPower(0);
                        robot.frontright.setPower(0);
                        robot.backleft.setPower(0);
                        robot.backright.setPower(0);
                        robot.storageunit.setPower(-1);
                    }
                    robot.storageunit.setPower(0);

                    //opening trapdoor
                    robot.trapdoor.setPosition(0);
                    sleep(3000);
                    //closing trapdoor
                    robot.trapdoor.setPosition(1);

                    //retract lift
                    while(robot.storageunit.getCurrentPosition() < 0){
                        robot.frontleft.setPower(0);
                        robot.frontright.setPower(0);
                        robot.backleft.setPower(0);
                        robot.backright.setPower(0);
                        robot.storageunit.setPower(1);
                    }
                    robot.storageunit.setPower(0);
                    break;
                case MIDDLE:
                    //middle level - level 2
                    while(robot.storageunit.getCurrentPosition() > -3315){
                        robot.frontleft.setPower(0);
                        robot.frontright.setPower(0);
                        robot.backleft.setPower(0);
                        robot.backright.setPower(0);
                        robot.storageunit.setPower(-1);
                    }
                    robot.storageunit.setPower(0);

                    //opening trapdoor
                    robot.trapdoor.setPosition(0);
                    sleep(3000);
                    //close trapdoor
                    robot.trapdoor.setPosition(1);

                    //retract lift
                    while(robot.storageunit.getCurrentPosition() < 0){
                        robot.frontleft.setPower(0);
                        robot.frontright.setPower(0);
                        robot.backleft.setPower(0);
                        robot.backright.setPower(0);
                        robot.storageunit.setPower(1);
                    }
                    robot.storageunit.setPower(0);
                    break;
                case RIGHT:
                    //top level - level 3
                    //extend lift all the way
                    while(robot.storageunit.getCurrentPosition() > -4768){ //limit is 5215
                        robot.frontleft.setPower(0);
                        robot.frontright.setPower(0);
                        robot.backleft.setPower(0);
                        robot.backright.setPower(0);
                        robot.storageunit.setPower(-1);
                    }
                    robot.storageunit.setPower(0);

                    //opening trapdoor
                    robot.trapdoor.setPosition(0);
                    sleep(3000);
                    //close trapdoor
                    robot.trapdoor.setPosition(1);

                    //retract lift
                    while(robot.storageunit.getCurrentPosition() < 0){
                        robot.frontleft.setPower(0);
                        robot.frontright.setPower(0);
                        robot.backleft.setPower(0);
                        robot.backright.setPower(0);
                        robot.storageunit.setPower(1);
                    }
                    robot.storageunit.setPower(0);
                    break;
            }

            //turning to face warehouse (trapdoor is facing warehouse)
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


        }
        else if (p==Path.Blue){
            //robot strafes away from the wall, left
            robot.frontleft.setPower(1);
            robot.frontright.setPower(-1);
            robot.backleft.setPower(-1);
            robot.backright.setPower(1);
            sleep(300);

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);
            sleep(200);

            //robot driving forward
            robot.frontleft.setPower(1);
            robot.frontright.setPower(1);
            robot.backleft.setPower(1);
            robot.backright.setPower(1);
            sleep(700);

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);
            sleep(200);

            //turning so that trapdoor faces the shipping hub
            robot.frontleft.setPower(-1);
            robot.frontright.setPower(1);
            robot.backleft.setPower(-1);
            robot.backright.setPower(1);
            sleep(1100);

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);
            sleep(200);

            //driving forward to reach the storage unit
            robot.frontleft.setPower(1);
            robot.frontright.setPower(1);
            robot.backleft.setPower(1);
            robot.backright.setPower(1);
            sleep(250);

            robot.frontleft.setPower(0);
            robot.frontright.setPower(0);
            robot.backleft.setPower(0);
            robot.backright.setPower(0);
            sleep(200);

            switch (elementLocation) {
                case LEFT: case UNKNOWN:
                    //bottom level - level 1
                    //extend the lift at the lowest level
                    while(robot.storageunit.getCurrentPosition() > -3000){//TEST
                        robot.frontleft.setPower(0);
                        robot.frontright.setPower(0);
                        robot.backleft.setPower(0);
                        robot.backright.setPower(0);
                        robot.storageunit.setPower(1);
                    }
                    robot.storageunit.setPower(0);

                    //opening trapdoor
                    robot.trapdoor.setPosition(0);
                    sleep(3000);
                    //close trapdoor
                    robot.trapdoor.setPosition(1);

                    //retract lift
                    while(robot.storageunit.getCurrentPosition() < 0){
                        robot.frontleft.setPower(0);
                        robot.frontright.setPower(0);
                        robot.backleft.setPower(0);
                        robot.backright.setPower(0);
                        robot.storageunit.setPower(1);
                    }
                    robot.storageunit.setPower(0);

                    break;
                case MIDDLE:
                    telemetry.addData("middle", "yay");
                    //middle level - level 2
                    //code to drop off at middle level
                    while(robot.storageunit.getCurrentPosition() > -3315){ //TEST
                        robot.frontleft.setPower(0);
                        robot.frontright.setPower(0);
                        robot.backleft.setPower(0);
                        robot.backright.setPower(0);
                        robot.storageunit.setPower(1);
                    }
                    robot.storageunit.setPower(0);

                    //opening trapdoor
                    robot.trapdoor.setPosition(0);
                    sleep(3000);
                    //close trapdoor
                    robot.trapdoor.setPosition(1);

                    //retract lift
                    while(robot.storageunit.getCurrentPosition() < 0){
                        robot.frontleft.setPower(0);
                        robot.frontright.setPower(0);
                        robot.backleft.setPower(0);
                        robot.backright.setPower(0);
                        robot.storageunit.setPower(1);
                    }
                    robot.storageunit.setPower(0);

                    break;
                case RIGHT:
                    //top level - level 3
                    //code to drop off at highest level
                    while(robot.storageunit.getCurrentPosition() > -4768){//originally 5215
                        robot.frontleft.setPower(0);
                        robot.frontright.setPower(0);
                        robot.backleft.setPower(0);
                        robot.backright.setPower(0);
                        robot.storageunit.setPower(1);
                    }
                    robot.storageunit.setPower(0);

                    //opening trapdoor
                    robot.trapdoor.setPosition(0);
                    sleep(3000);
                    //close trapdoor
                    robot.trapdoor.setPosition(1);

                    //retract lift
                    while(robot.storageunit.getCurrentPosition() < 0){
                        robot.frontleft.setPower(0);
                        robot.frontright.setPower(0);
                        robot.backleft.setPower(0);
                        robot.backright.setPower(0);
                        robot.storageunit.setPower(1);
                    }
                    robot.storageunit.setPower(0);

                    break;
            }

            //turning so that trapdoor is facing warehouse (blue side) (test)
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
