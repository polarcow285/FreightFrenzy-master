package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;

@TeleOp(name="MecanumDriveJiashu", group="Mecanum")
public class MecanumDriveJiashu extends LinearOpMode {
    private ProjectOdometryTest robot = new ProjectOdometryTest();
    /* Setting variables */

    //Speed multiplier when slow mode is active
    private float slowModeMultiplier = .4f;

    float intake = 0;
    //float carousel = 0;
    float storageunit = 0;
    boolean yPressed = false;
    boolean isTrapdoorClosed = false;

    int elevatorHeight = 1;
    boolean storageUnitUp = false;

    @Override
    public void runOpMode() throws InterruptedException {


        robot.init(hardwareMap);
        waitForStart();


        while (opModeIsActive()) {
            //Driving controls

            double y = 0; //back and forth
            double x = -gamepad1.right_stick_x * 1.1; //strafing
            double rx = gamepad1.left_stick_x; //turning

            //back and forth movement using triggers
            if(gamepad1.right_trigger > 0){
                y = gamepad1.right_trigger;
                x = 0;
            }
            else if(gamepad1.left_trigger > 0){
                y = -gamepad1.left_trigger;
                x = 0;
            }

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;


            robot.frontleft.setPower(frontLeftPower);
            robot.backleft.setPower(backLeftPower);
            robot.frontright.setPower(frontRightPower);
            robot.backright.setPower(backRightPower);

            telemetry.addData("storage unit encoder value", robot.storageunit.getCurrentPosition());
            telemetry.addData("frontLeftPower", frontLeftPower);
            telemetry.addData("frontRightPower", frontRightPower);
            telemetry.addData("backRightPower", backRightPower);
            telemetry.addData("backLeftPower", backLeftPower);
            telemetry.update();


            //controlling intake, duck spinning, elevator lift

            if(gamepad1.a == true){ //a to make intake spin backward
                robot.intake.setPower(1);
            }
            else if(gamepad1.x == true){
                robot.intake.setPower(-1);
            }
            else{
                robot.intake.setPower(0);
            }

            //Servo Toggle
            //0 is open trapdoor
            //1 is closed trapdoor
            if (gamepad1.y){
                if(yPressed == false){
                    yPressed = true;
                    isTrapdoorClosed = !isTrapdoorClosed;
                    if(isTrapdoorClosed == true){
                        robot.trapdoor.setPosition(0);
                    }else{
                        robot.trapdoor.setPosition(1);
                    }
                }
            }
            else {
                yPressed = false;
            }
            if(gamepad1.back){
                robot.storageunit.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.storageunit.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            //-1280 = able to close

            //extend lift until reaches its limit (encoder count -5215)
            if(gamepad1.right_bumper && robot.storageunit.getCurrentPosition() > -5215){
                robot.storageunit.setPower(-1);
            }
            //retract lift when the current position is less than 0 (being extended)
            else if(gamepad1.left_bumper && robot.storageunit.getCurrentPosition() < 0){
                robot.storageunit.setPower(1);
            }
            else{
                robot.storageunit.setPower(0);
            }

//            if(gamepad2.x == true){ //x to spin the carousel
//                robot.carousel.setPower(1);
//            }
//            else{
//                robot.carousel.setPower(0);
//            }

            // Elevator Lift

            /*if (gamepad2.right_bumper == true){ //right bumper toggles between the 3 elevator lift heights
                elevatorHeight++;
                if (elevatorHeight>3){
                    elevatorHeight = 1;
                }
            }
            if (gamepad2.left_bumper == true){

                if(elevatorHeight > 1){
                    elevatorHeight--;
                }
                else {
                    elevatorHeight = 3;
                }

            }


            if (gamepad2.y && !storageUnitUp){

                if(elevatorHeight == 1){
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setTargetPosition(1000);
                    robot.storageunit.setPower(0.5);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                }
                if(elevatorHeight == 2){
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setTargetPosition(2000);
                    robot.storageunit.setPower(0.5);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
                if(elevatorHeight == 3){
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setTargetPosition(3000);
                    robot.storageunit.setPower(0.5);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
                storageUnitUp = true;

            }

            if (gamepad2.y && storageUnitUp){
                robot.storageunit.setPower(0.5);
                robot.storageunit.setTargetPosition(0);
                storageUnitUp = false;
            }


            if(gamepad2.dpad_up == true){ //starts the elevator lift
                robot.storageunit.setPower(0.5);
                //robot.storageunit.setTargetPosition(3000);
            }
            else{
                robot.storageunit.setPower(0);
                robot.storageunit.setTargetPosition(0);
            }
            if(robot.storageunit.getCurrentPosition() > 3000 || robot.storageunit.getCurrentPosition() < 0){ //limit for the elevator lift
                robot.storageunit.setPower(0);
            }
            */


            //telemetry.addData("Elevator Height:", elevatorHeight);



            /*if(gamepad2.y == true){
                if(servoposition == 1){
                    servoposition = 0;
                }
                else{
                    servoposition = 1;
                }


            }
            robot.trapdoor.setPosition(servoposition);
            */










        }
    }
}
