package org.firstinspires.ftc.teamcode.Auto;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "ParkingAutoFar")
public class ParkingAutoFar extends LinearOpMode {
    public ProjectOdometryTest robot = new ProjectOdometryTest();


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        PathFar p = PathFar.Red;

        while(!isStarted()){
            if(gamepad1.b){
                p = PathFar.Red;
            }
            if(gamepad1.x){
                p = PathFar.Blue;
            }
            telemetry.addData("PathFar:", p);
            telemetry.update();
        }

        //waitForStart();

        RunAuto(p == PathFar.Red);

    }
    public void RunAuto(boolean red){




        robot.frontleft.setPower(red ?  -1f : -1f);
        robot.backleft.setPower(red ?  -1f : -1f);
        robot.frontright.setPower(red ?  -1f : -1f);
        robot.backright.setPower(red ? -1f :  -1f);
        sleep(2100);

        robot.frontright.setPower(0);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);
        robot.backleft.setPower(0);
        sleep(500);

        robot.frontleft.setPower(red ?  -1f : 1f);
        robot.backleft.setPower(red ?  1f : -1f);
        robot.frontright.setPower(red ?  1f : -1f);
        robot.backright.setPower(red ? -1f : 1f);
        sleep(800);

        robot.frontright.setPower(0);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);
        robot.backleft.setPower(0);
    }

}

enum PathFar{
    Red,
    Blue
}
