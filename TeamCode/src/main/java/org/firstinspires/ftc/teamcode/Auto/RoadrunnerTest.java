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
        Trajectory exampleTrajectory = robot.trajectoryBuilder(new Pose2d(2,2,Math.toRadians(0)))
                .forward(20)
                .strafeLeft(10)
                .build();

        Trajectory trajectory1 = robot.trajectoryBuilder(exampleTrajectory.end())
                .forward(5)
                .strafeRight(9)
                .build();

        TrajectorySequence mySequence = robot.trajectorySequenceBuilder(trajectory1.end())
                .turn(Math.toRadians(90))
                .waitSeconds(3)
                .build();

        Pose2d startPose = new Pose2d(2,2,Math.toRadians(0));


        waitForStart();
        //initialize start pose
        robot.setPoseEstimate(startPose);

        //write autonomous path
        if(!isStopRequested()){
            robot.followTrajectory(exampleTrajectory);
            robot.followTrajectory(trajectory1);
            robot.followTrajectorySequence(mySequence);
        }


    }
}
