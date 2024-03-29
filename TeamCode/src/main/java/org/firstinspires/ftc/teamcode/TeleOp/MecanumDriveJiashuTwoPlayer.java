package org.firstinspires.ftc.teamcode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="JiashuTwoPlayer", group="Mecanum")
public class MecanumDriveJiashuTwoPlayer extends LinearOpMode {
    private ProjectOdometryTest robot = new ProjectOdometryTest();


    /* Setting variables */

    //Speed multiplier when slow mode is active
    private float slowModeMultiplier = .4f;

    boolean yPressed = false;
    boolean isTrapdoorClosed = false;

    boolean aPressed = false;
    boolean isIntakeSpinning = false;


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


            //controlling intake, elevator lift
            /*
            if(gamepad1.a == true){ //a to intake cargo on the ramp
                robot.intake.setPower(1);
            }
            else if (gamepad1.x == true){//x to spit out cargo from the ramp
                robot.intake.setPower(-1);
            }
            else{
                robot.intake.setPower(0);
            }
            */

            //toggling intake
            /*if(gamepad1.a){
                robot.intake.setPower(1);
            }

             */
            if(gamepad2.a){
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
            else if(gamepad2.x == true && isIntakeSpinning == false){
                robot.intake.setPower(-1);
                aPressed = false;
            }
            else if(gamepad2.x == false && isIntakeSpinning == false){
                robot.intake.setPower(0);
                aPressed = false;
            }
            else{
                aPressed = false;
            }

            telemetry.addData("isIntakeSpinning",isIntakeSpinning);
            telemetry.addData("aPressed",aPressed);
            telemetry.addData("Lift Encoder Count", robot.storageunit.getCurrentPosition());
            telemetry.update();

            /*if(gamepad1.x == true){ //x to spit out cargo from the ramp
                robot.intake.setPower(-1);
            }
            else{
                robot.intake.setPower(0);
            }
            */


            //Servo Toggle
            //0 is open trapdoor
            //1 is closed trapdoor
            if (gamepad2.y){
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
            if(gamepad2.right_bumper && robot.storageunit.getCurrentPosition() > -5125 && !robot.slideSwitch.getState()){
                //extend lift
                robot.storageunit.setPower(-1);
            }
            //retract lift when the current position is less than 0 (being extended)
            else if(gamepad2.left_bumper && robot.storageunit.getCurrentPosition() < 0){
                robot.storageunit.setPower(1);
            }
            else{
                robot.storageunit.setPower(0);
            }











        }
    }
}