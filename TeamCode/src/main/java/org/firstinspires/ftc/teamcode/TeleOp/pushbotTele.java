package org.firstinspires.ftc.teamcode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Projects.ProjectPushbotTest;

@TeleOp(name="pushbotTele", group="Pushbot")
public class pushbotTele extends LinearOpMode {
    private ProjectPushbotTest robot = new ProjectPushbotTest();


    /* Setting variables */


    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);
        waitForStart();
        double slidePower = 0.0;
        boolean slideForward = true;

        while (opModeIsActive()) {

            if(gamepad1.right_bumper && robot.linearSlide.getPower() <= 1){
                slidePower = robot.linearSlide.getPower()+0.001;
            }
            if(gamepad1.left_bumper && robot.linearSlide.getPower() >= 0){
                slidePower = robot.linearSlide.getPower()-0.001;
            }
            if(gamepad1.a){
                slideForward = !slideForward;
            }

            if(slideForward)
                robot.linearSlide.setPower(slidePower);
            if(!slideForward)
                robot.linearSlide.setPower(-slidePower);

            telemetry.addData("Linear Slide Power:", robot.linearSlide.getPower());
            telemetry.addData("Linear Slide is going Forward:", slideForward);
            telemetry.update();




        }
    }
}
