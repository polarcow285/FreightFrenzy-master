package org.firstinspires.ftc.teamcode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;

@TeleOp(name="MecanumDrive", group="Mecanum")
public class    MecanumDrive extends LinearOpMode {
    private ProjectOdometryTest robot = new ProjectOdometryTest();


    /* Setting variables */

    //Speed multiplier. The higher it is, the more likely to clip at high speeds because motor max is 1
    private float speedMultiplier = 1;

    //Speed multiplier when slow mode is active
    private float slowModeMultiplier = .4f;

    /* Calculation variables DO NOT CHANGE */

    //Robot controls:
    private float speed = 0;
    private float angle = 0;
    private float direction = 0;
    private VectorF vectorF = null;
    private boolean isTurning = false;
    private float rotation = 0;

    //Individual motor powers:
    float frontleft = 0;
    float frontright = 0;
    float backleft = 0;
    float backright = 0;

    float intake = 0;
    float carousel = 0;
    float storageunit = 0;

    //Highest motor power: (used for clipping high speeds above 1)
    private float greatestNum = 0;


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        waitForStart();

        while (opModeIsActive()) {
            //region Mecanum Drive math and controls
            //Get the 2 dimensional vector of the direction of left stick and rotation based on right stick
            vectorF = new VectorF(gamepad1.left_stick_x, -gamepad1.left_stick_y);
            speed = vectorF.magnitude();
            vectorF = new VectorF(vectorF.get(0) / speed, vectorF.get(1) / speed);
            angle = (float) Math.atan2(vectorF.get(0), vectorF.get(1));
            direction = gamepad1.right_stick_x;

            //Apply mathematical operations to find speeds of each motor
            frontleft = (float) (speed * Math.sin(angle + Math.PI / 4) + direction) * speedMultiplier;
            frontright = (float) (speed * Math.cos(angle + Math.PI / 4)- direction) * speedMultiplier;
            backleft = (float) (speed * Math.cos(angle + Math.PI / 4) + direction) * speedMultiplier;
            backright = (float) (speed * Math.sin(angle + Math.PI / 4) - direction) * speedMultiplier;

            //Make sure that speed never exceeds 1. If so, divide by largest
            greatestNum = Math.abs(frontleft);
            if (Math.abs(frontright) > greatestNum) {
                greatestNum = Math.abs(frontright);
            }
            if (Math.abs(backleft) > greatestNum) {
                greatestNum = Math.abs(backleft);
            }
            if (Math.abs(backright) > greatestNum) {
                greatestNum = Math.abs(backright);
            }

            if (greatestNum > 1) {
                frontleft /= greatestNum;
                frontright /= greatestNum;
                backleft /= greatestNum;
                backright /= greatestNum;
            }
            //endregion

            robot.frontleft.setPower(Float.isNaN(frontleft) ? 0 : frontleft * (gamepad1.left_trigger < .8 ? 1 : slowModeMultiplier));
            robot.frontright.setPower(Float.isNaN(frontright) ? 0 : frontright * (gamepad1.left_trigger < .8 ? 1 : slowModeMultiplier));
            robot.backleft.setPower(Float.isNaN(backleft) ? 0 : backleft * (gamepad1.left_trigger < .8 ? 1 : slowModeMultiplier));
            robot.backright.setPower(Float.isNaN(backright) ? 0 : backright * (gamepad1.left_trigger < .8 ? 1 : slowModeMultiplier));


            //controlling intake, duck spinning, elevator lift
            if(gamepad2.y == true && gamepad2.right_bumper == true){ //b to make the intake spin forward and slower
                robot.intake.setPower(0.5);
            }
            else{
                robot.intake.setPower(0);
            }
            if(gamepad2.y == true){ //y to make intake spin forward
                robot.intake.setPower(1);
            }
            else{
                robot.intake.setPower(0);
            }
            if(gamepad2.a == true && gamepad2.right_bumper == true){ //b to make the intake spin forward and slower
                robot.intake.setPower(-0.5);
            }
            else {
                robot.intake.setPower(0);
            }
            if(gamepad2.a == true){ //a to make intake spin backward
                robot.intake.setPower(-1);
            }
            else{
                robot.intake.setPower(0);
            }
            if(gamepad2.x == true){ //x to spin the carousel
                robot.carousel.setPower(1);
            }
            else{
                robot.carousel.setPower(0);
            }





        }
    }
}