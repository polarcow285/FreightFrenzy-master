package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;


/* ARM LIFT ALTERNATE : UNTESTED & NOT BUILT
  ----------------------------------------------------------

  HD Hex 40:1 Specs -> RevRobotics40HdHexMotor
  @MotorType(ticksPerRev=2240, gearing=20, maxRPM=150, orientation=Rotation.CCW)

  !!! We should check all wiring. Motors don't seem to be responding according to correct encoder counts?

  Turn Arm motors off when the "down" position has been reached.
  This removes power from the motors and must only be applied near the bottom of the drop

  A few resources and notes:
  http://stemrobotics.cs.pdx.edu/node/4745

  https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6443-getting-motors-to-hold-their-position
  These guys are using negative power and negative encoder counts to step down??

  https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/51820-problem-with-using-encoders-run-to-position
  "Since you are using RunToPosition, you are using two elements of the built in motor control algorithm. That is "Closed loop velocity" and "Closed loop position".
  ...
  Closed loop velocity will regulate the speed at which the arm moves, adjusting to varying loads.
  Closed loop Position will attempt to regulate the approach velocity to make a smooth approach to the position."


*/

@TeleOp(name = "encoderLiftTest")
public class encoderLiftTest extends LinearOpMode {
    //private ProjectOdometryTest robot = new ProjectOdometryTest();

    private DcMotor lift;


    @Override
    public void runOpMode() throws InterruptedException
    {

        lift = hardwareMap.dcMotor.get("storageunit");

        // Set arm encoders to 0
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set arm run mode
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Zero Power Behavior
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Setup Telemetry, will not clear after cycle, setup reusable items for output
        telemetry.setAutoClear(false);
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

            /**
             * BEGIN ARM LIFT

             * Gamepad 1 btn A - arm lift up
             * Gamepad 1 btn B - arm lift down
             *
             * !!! concerned about reliability of encoder count as it doesn't seem to be tracking accurately
             **/

            if (gamepad1.a){ // Arm UP
                liftTarget = -2000;
                liftSpeed = 0.98;
                liftCurrentDirection = "up";

                lift.setPower(liftSpeed);
                lift.setTargetPosition(liftTarget);

            } else if (gamepad1.b){ // Arm DOWN
                liftTarget = 0;
                liftSpeed = 0.98;  // From my research, negative is ignore, so I don't understand why this *seemed* to work
                liftCurrentDirection = "down";

                lift.setPower(liftSpeed);
                lift.setTargetPosition(liftTarget);
            }

            // Remove Power from the Arm Motor if motor is close to 0 position, arm should drop
            if ( liftCurrentDirection == "down" && ( lift.getTargetPosition() < 5 ) ){
                liftSpeed = 0;
                lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }

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