package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


import org.firstinspires.ftc.teamcode.Vision.ShippingElementDetector;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

@Autonomous(name = "RoadrunnerTest")
public class RoadrunnerTest extends LinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive robot = new SampleMecanumDrive(hardwareMap);

        //initialize trajectories
        Trajectory exampleTrajectory = robot.trajectoryBuilder(new Pose2d(45, 25, 180))
                .forward(20)
                .strafeLeft(10)
                .build();

        Trajectory O = robot.trajectoryBuilder(exampleTrajectory.end())
                .strafeLeft(39)
                .splineTo(new Vector2d(-53, 12), Math.toRadians(134))
                .build();

        TrajectorySequence sequencewow = robot.trajectorySequenceBuilder(O.end())
                .turn(Math.toRadians(72))
                .waitSeconds(8)
                .build();

        Pose2d startPose = new Pose2d(49, 23, Math.toRadians(26));

        waitForStart();
        //initialize start pose
        robot.setPoseEstimate(startPose);

        //write autonomous path
        if(!isStopRequested()) {
            robot.followTrajectory(O);
            robot.followTrajectory(exampleTrajectory);
            robot.followTrajectorySequence(sequencewow);
        }
    }
}
