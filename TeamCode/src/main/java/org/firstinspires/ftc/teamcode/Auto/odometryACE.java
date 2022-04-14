package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Vision.ACE;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;

@Autonomous(name = "odometryACE")
public class odometryACE extends LinearOpMode{
    //public SampleMecanumDrive robot = new SampleMecanumDrive();
    OpenCvWebcam webcam;
    ACE detector = new ACE(telemetry);

    @Override
    public void runOpMode() throws InterruptedException {
        //robot.init(hardwareMap);
        SampleMecanumDrive robot = new SampleMecanumDrive(hardwareMap);

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

        TrajectorySequence redZero = robot.trajectorySequenceBuilder(new Pose2d(-63, -47, Math.toRadians(0)))
         //going to box
                .lineToLinearHeading(new Pose2d(0,-59, Math.toRadians(0)))
                //robot.dropoffwobblegoal(); PUT A DISPLACEMENT MARKER OAEISJFO ISEJFLSDJ
        //park on launchline
                .lineToLinearHeading(new Pose2d(0,-34, Math.toRadians(0)))
                .lineToLinearHeading(new Pose2d(12,-34, Math.toRadians(0)))
                .build();

        TrajectorySequence redOne = robot.trajectorySequenceBuilder(new Pose2d(-63, -47, Math.toRadians(0)))
                //drive to box
                .lineToLinearHeading(new Pose2d(12,-59, Math.toRadians(0)))
                .lineToLinearHeading(new Pose2d(20,-34.5, Math.toRadians(0)))
                //robot.dropoffwobblegoal();
                .lineToLinearHeading(new Pose2d(12,-34, Math.toRadians(0)))
                .build();

        TrajectorySequence redFour = robot.trajectorySequenceBuilder(new Pose2d(-63, -47, Math.toRadians(0)))
                //drive to box
                .lineToLinearHeading(new Pose2d(43,-59, Math.toRadians(0)))
                //robot.dropoffwobblegoal();
                .lineToLinearHeading(new Pose2d(12,-34, Math.toRadians(0)))
                .build();

        TrajectorySequence blueZero = robot.trajectorySequenceBuilder(new Pose2d(-61, 48, Math.toRadians(0)))
                .lineToLinearHeading(new Pose2d(-4, 58.5, Math.toRadians(0)))
                //robot.dropoffwobblegoal();
                .lineToLinearHeading(new Pose2d(-4, 35, Math.toRadians(0)))
                .lineToLinearHeading(new Pose2d(11.5, 35, Math.toRadians(0)))
                .build();

        /*TrajectorySequence blueOne = robot.trajectorySequenceBuilder(new Pose2d(-61, 48, Math.toRadians(0)))
                .lineToLinearHeading(new Pose2d(-4, 58.5, Math.toRadians(0)))
                //robot.dropoffwobblegoal();
                .lineToLinearHeading(new Pose2d(-4, 35, Math.toRadians(0)))
                .lineToLinearHeading(new Pose2d(11.5, 35, Math.toRadians(0)))
                .build();

         */

        /*TrajectorySequence blueFour = robot.trajectorySequenceBuilder(new Pose2d(-61, 48, Math.toRadians(0)))
                .lineToLinearHeading(new Pose2d(-4, 58.5, Math.toRadians(0)))
                //robot.dropoffwobblegoal();
                .lineToLinearHeading(new Pose2d(-4, 35, Math.toRadians(0)))
                .lineToLinearHeading(new Pose2d(11.5, 35, Math.toRadians(0)))
                .build();

         */



        waitForStart();

        //get the number of rings
        ACE.NumberOfRings RingNumber = detector.getRingNumber();
        telemetry.addData("ring number:", RingNumber);
        telemetry.update();

        if(p == Path.Red) {
            switch(RingNumber) {
                case ZERO: case UNKNOWN:
                    //zero rings is closest box
                    if (!isStopRequested()){
                        robot.followTrajectorySequence(redZero);
                    }
                    break;
                case ONE:
                //one ring is middle box
                    if (!isStopRequested()){
                        robot.followTrajectorySequence(redOne);
                    }
                    break;
                case FOUR:
                    //last box
                    if (!isStopRequested()){
                        robot.followTrajectorySequence(redFour);
                    }
                    break;

            }
        }

        if(p == Path.Blue) {
            switch(RingNumber) {
                case ZERO: case UNKNOWN:
                    //zero rings is closest box
                    if (!isStopRequested()){
                        robot.followTrajectorySequence(blueZero);
                    }
                    break;
                case ONE:
                    //one ring is middle box
                    if (!isStopRequested()){
                        robot.followTrajectorySequence(blueOne);
                    }
                    break;
                case FOUR:
                    //last box
                    if (!isStopRequested()){
                        robot.followTrajectorySequence(blueFour);
                    }
                    break;

            }
        }

    }
}
