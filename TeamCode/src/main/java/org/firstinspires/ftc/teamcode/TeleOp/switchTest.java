package org.firstinspires.ftc.teamcode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;

@TeleOp(name="switchTest", group="Mecanum")
public class switchTest extends LinearOpMode {
    private ProjectOdometryTest robot = new ProjectOdometryTest();
    ColorSensor colorSteven;

    /* Setting variables */


    @Override
    public void runOpMode() throws InterruptedException {

        colorSteven = hardwareMap.get(ColorSensor.class, "ColorSteven");
        robot.init(hardwareMap);
        waitForStart();


        while (opModeIsActive()) {


            telemetry.addData("Switch Value:", robot.slideSwitch.getState());
            telemetry.addData("green",colorSteven.green());
            telemetry.addData("red", colorSteven.red());
            telemetry.addData("blue", colorSteven.blue());
            telemetry.addData("white", colorSteven.alpha());

            telemetry.update();




        }
    }
}
