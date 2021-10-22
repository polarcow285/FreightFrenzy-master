package org.firstinspires.ftc.teamcode.Auto;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "BasicAuto")
public class BasicAuto extends LinearOpMode {
    public ProjectOdometryTest robot = new ProjectOdometryTest();

    @Override
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

        RunAutoBasic(p == Path.Red);

    }
    public void RunAutoBasic(boolean red) {
        robot.frontleft.setPower(red ? 0 : 1f);
        robot.backleft.setPower(red ? 1f : 0);
        robot.frontright.setPower(red ? 1f : 0);
        robot.backright.setPower(red ? 0 : 1f);
        sleep(2000);

        robot.frontright.setPower(0);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);
        robot.backleft.setPower(0);
        sleep(500);

        robot.frontleft.setPower(red ?  1f : -1f);
        robot.backleft.setPower(red ?  1f : -1f);
        robot.frontright.setPower(red ? -1f : 1f);
        robot.backright.setPower(red ? -1f :  1f);
        sleep(500);

        robot.frontright.setPower(0);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);
        robot.backleft.setPower(0);
        sleep(500);

        robot.frontleft.setPower(red ?  -1f : -1f);
        robot.backleft.setPower(red ?  -1f : -1f);
        robot.frontright.setPower(red ?  -1f : -1f);
        robot.backright.setPower(red ? -1f :  -1f);
        sleep(1000);

        robot.frontleft.setPower(red ?  1f : -1f);
        robot.backleft.setPower(red ?  -1f : 1f);
        robot.frontright.setPower(red ?  -1f : 1f);
        robot.backright.setPower(red ? 1f :  -1f);
        sleep(2000);
    }
}

