package org.firstinspires.ftc.teamcode.Auto;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "ParkingAuto")
public class ParkingAuto extends LinearOpMode {
    public ProjectOdometryTest robot = new ProjectOdometryTest();


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        Path p = Path.Red;


        while(!isStarted()){
            if(gamepad1.b){
                p = Path.Red;
            }
            if(gamepad1.x){
                p = Path.Blue;
            }
            telemetry.addData("Path:", p);
            telemetry.update();
        }

        waitForStart();

        RunAuto(p == Path.Red);

    }
    public void RunAuto(boolean red){
        robot.frontright.setPower(1);
        robot.frontleft.setPower(1);
        robot.backright.setPower(1);
        robot.backleft.setPower(1);
        sleep(2000);

        robot.frontright.setPower(0);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);
        robot.backleft.setPower(0);
    }

}

enum Path{
    Red,
    Blue
}
