package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
@Autonomous
public class odometryStrafetest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive robot = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence ComplexAutoBlueTop = robot.trajectorySequenceBuilder(new Pose2d(-7.5, -63, Math.toRadians(0)))
                .strafeLeft(30)
                .build();

        waitForStart();

        robot.followTrajectorySequence(ComplexAutoBlueTop);
    }
}
