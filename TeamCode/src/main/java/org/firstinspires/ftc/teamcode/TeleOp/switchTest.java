package org.firstinspires.ftc.teamcode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;

@TeleOp(name="switchTest", group="Mecanum")
public class switchTest extends LinearOpMode {
    private ProjectOdometryTest robot = new ProjectOdometryTest();
    ColorSensor ColorSteven;

    /* Setting variables */


    @Override
    public void runOpMode() throws InterruptedException {

        ColorSteven = hardwareMap.get(ColorSensor.class, "ColorSteven");
        robot.init(hardwareMap);
        waitForStart();


        while (opModeIsActive()) {


            telemetry.addData("Switch Value:", robot.slideSwitch.getState());
                telemetry.addData("green",ColorSteven.green());
                telemetry.addData("red", ColorSteven.red());
                telemetry.addData("blue", ColorSteven.blue());
                telemetry.addData("white", ColorSteven.alpha());

                telemetry.update();




        }
    }
}
