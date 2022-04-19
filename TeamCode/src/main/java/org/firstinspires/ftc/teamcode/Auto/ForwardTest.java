package org.firstinspires.ftc.teamcode.Auto;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "ForwardTest")
public class ForwardTest extends LinearOpMode{
    public ProjectOdometryTest robot = new ProjectOdometryTest();

    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        Path p = Path.Red;

        while (!isStarted()) {
            if (gamepad1.b) {
                p = Path.Red;
            }
            if (gamepad1.x) {
                p = Path.Blue;
            }
            telemetry.addData("Path:", p);
            telemetry.update();

        }

        RunShortAuto(p == Path.Red);

    }
    public void RunShortAuto(boolean red){
        robot.frontleft.setPower(0.5f);
        robot.backleft.setPower(0.5f);
        robot.frontright.setPower(0.5f);
        robot.backright.setPower(0.5f);
        sleep(2000);

        robot.frontright.setPower(0);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);
        robot.backleft.setPower(0);

        /*robot.frontright.setPower(red ?  0.5f : -0.5f);
        robot.frontleft.setPower(red ?  0.5f : -0.5f);
        robot.backright.setPower(red ?  0.5f : -0.5f);
        robot.backleft.setPower(red ?  0.5f : -0.5f);
        sleep(500);

        robot.frontright.setPower(0);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);
        robot.backleft.setPower(0);

        //strafes diagonally
        robot.frontleft.setPower(red ?  -0.2f : -1f);
        robot.backleft.setPower(red ?  1 : 0.2f);
        robot.frontright.setPower(red ?  1 : 0.2f);
        robot.backright.setPower(red ? -0.2f :  -1f);
        sleep(1200);

        robot.frontright.setPower(0);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);
        robot.backleft.setPower(0);
        sleep(200);

        //strafing to the side

        robot.frontleft.setPower(-0.5f);
        robot.backleft.setPower(0.5f);
        robot.frontright.setPower(0.5f);
        robot.backright.setPower(-0.5f);
        sleep(red? 800 : 900);

        robot.frontright.setPower(0);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);
        robot.backleft.setPower(0);
        sleep(200);

        //drive forward

        robot.frontright.setPower(red ?  0.5f : -0.5f);
        robot.frontleft.setPower(red ?  0.5f : -0.5f);
        robot.backright.setPower(red ?  0.5f : -0.5f);
        robot.backleft.setPower(red ?  0.5f : -0.5f);
        sleep(500);

        robot.frontright.setPower(0);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);
        robot.backleft.setPower(0);

        /*robot.frontleft.setPower(red ?  -1f : 1f);
        robot.backleft.setPower(red ?  1f : -1f);
        robot.frontright.setPower(red ? 1f : -1f);
        robot.backright.setPower(red ? -1f :  1f);
        sleep(800);
         */




    }
}
