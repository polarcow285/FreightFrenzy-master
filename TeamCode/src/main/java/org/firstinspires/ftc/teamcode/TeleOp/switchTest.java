package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;

@TeleOp(name="switchTest", group="Mecanum")
public class switchTest extends LinearOpMode {
    private ProjectOdometryTest robot = new ProjectOdometryTest();
    /* Setting variables */


    @Override
    public void runOpMode() throws InterruptedException {


        robot.init(hardwareMap);
        waitForStart();


        while (opModeIsActive()) {


            telemetry.addData("Switch Value:", robot.slideSwitch.getState());
            telemetry.update();


        }
    }
}
