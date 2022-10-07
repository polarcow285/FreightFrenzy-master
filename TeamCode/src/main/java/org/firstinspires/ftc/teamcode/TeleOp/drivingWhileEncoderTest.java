package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;

@TeleOp(name="drivingWhileEncoderTest", group="Mecanum")
public class drivingWhileEncoderTest extends LinearOpMode {
    private ProjectOdometryTest robot = new ProjectOdometryTest();
    /* Setting variables */

    boolean yPressed = false;
    boolean isTrapdoorClosed = false;
    private DcMotor lift;


    boolean aPressed = false;
    boolean isIntakeSpinning = false;

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);
        lift = robot.storageunit;
        // Set arm encoders to 0
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set arm run mode
        //lift.setTargetPosition(0);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Zero Power Behavior
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Setup Telemetry, will not clear after cycle, setup reusable items for output
        telemetry.setAutoClear(true);
        telemetry.addData("Lift Position", lift.getCurrentPosition());


        //code before waitForStart is run when Init button is pressed
//        while(!opModeIsActive()){
//            //print encoder counts to telemetry while we manually move the arm
//            telemetry.addData("Lift Position", lift.getCurrentPosition());
//            telemetry.update();
//        }


        //code after waitForStart is run when the start button is pressed


        int liftTarget = 0;
        double liftSpeed = 0;
        String liftCurrentDirection = "up";


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
            double backRightPower = ( y + x - rx) / denominator;


            robot.frontleft.setPower(frontLeftPower);
            robot.backleft.setPower(backLeftPower);
            robot.frontright.setPower(frontRightPower);
            robot.backright.setPower(backRightPower);

            telemetry.addData("storage unit encoder value", robot.storageunit.getCurrentPosition());
            telemetry.addData("frontLeftPower", frontLeftPower);
            telemetry.addData("frontRightPower", frontRightPower);
            telemetry.addData("backRightPower", backRightPower);
            telemetry.addData("backLeftPower", backLeftPower);
            telemetry.addData("Switch Value:", robot.slideSwitch.getState());
            telemetry.addData("Lift Encoder Count", robot.storageunit.getCurrentPosition());
            telemetry.update();


            //controlling intake, elevator lift
/*
            if(gamepad1.a == true){ //a to intake cargo on the ramp
                robot.intake.setPower(1);
            }
            else if(gamepad1.x == true){ //x to spit out cargo from the ramp
                robot.intake.setPower(-1);
            }
            else{
                robot.intake.setPower(0);
            }

 */

            //toggling intake
            if(gamepad1.a){
                if(aPressed == false){
                    aPressed = true;
                    isIntakeSpinning = !isIntakeSpinning;
                    if(isIntakeSpinning == true){
                        robot.intake.setPower(1);
                    }
                    else{
                        robot.intake.setPower(0);
                    }
                }
            }
            else if(gamepad1.x == true && isIntakeSpinning == false){
                robot.intake.setPower(-1);
                aPressed = false;
            }
            else if(gamepad1.x == false && isIntakeSpinning == false){
                robot.intake.setPower(0);
                aPressed = false;
            }
            else{
                aPressed = false;
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

            if(gamepad2.back){
                robot.storageunit.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.storageunit.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            //-1280 = able to close

            //extend lift until reaches its limit (encoder count -5215) OR hits limit switch
//            if(gamepad1.right_bumper && robot.storageunit.getCurrentPosition() > -5125 && !robot.slideSwitch.getState()){
//                //extend lift
//                robot.storageunit.setPower(-1);
//            }
//
//            //retract lift when the current position is less than 0 (being extended)
//            else if(gamepad1.left_bumper && robot.storageunit.getCurrentPosition() < 0){
//                //retract lift
//                robot.storageunit.setPower(1);
//            }
//            else{
//                robot.storageunit.setPower(0);
//            }



            /**
             * BEGIN ARM LIFT

             * Gamepad 1 btn A - arm lift up
             * Gamepad 1 btn B - arm lift down
             *
             * !!! concerned about reliability of encoder count as it doesn't seem to be tracking accurately
             **/

            if (lift.getCurrentPosition()>-2000 && gamepad2.x) { // Arm UP
                liftTarget = -2000;
                liftSpeed = 0.98;
                liftCurrentDirection = "up";

                lift.setPower(liftSpeed);
                lift.setTargetPosition(liftTarget);
            }else if (lift.getCurrentPosition()<-2000 && gamepad2.x) { // Arm UP
                liftTarget = -2000;
                liftSpeed = -0.98;
                liftCurrentDirection = "down";

                lift.setPower(liftSpeed);
                lift.setTargetPosition(liftTarget);
            }else if (lift.getCurrentPosition()>-3500 && gamepad2.a) { // Arm UP
                liftTarget = -3500;
                liftSpeed = 0.98;
                liftCurrentDirection = "up";

                lift.setPower(liftSpeed);
                lift.setTargetPosition(liftTarget);
            }else if (lift.getCurrentPosition()<-3500 && gamepad2.a) { // Arm UP
                liftTarget = -3500;
                liftSpeed = -0.98;
                liftCurrentDirection = "down";

                lift.setPower(liftSpeed);
                lift.setTargetPosition(liftTarget);
            } else if (gamepad2.b){ // Arm DOWN
                liftTarget = 0;
                liftSpeed = -0.98;  // From my research, negative is ignore, so I don't understand why this *seemed* to work
                liftCurrentDirection = "down";

                lift.setPower(liftSpeed);
                lift.setTargetPosition(liftTarget);
            }

            // Remove Power from the Arm Motor if motor is close to 0 position, arm should drop
//            if ( liftCurrentDirection == "down" && ( lift.getTargetPosition() < 5 ) ){
//                liftSpeed = 0;
//                lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            }

            /** END ARM LIFT **/


            idle();

            // Arm Lift Telemetry
            if( lift.isBusy() ){
                telemetry.addData("Lift Position", lift.getCurrentPosition());
                telemetry.update();
            }







        }
    }
}
