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

        //Distance q = Distance.Close;


        while(!isStarted()){
            if(gamepad1.b){
                p = Path.Red;
            }
            if(gamepad1.x){
                p = Path.Blue;
            }
//            if(gamepad1.a){
//                q = Distance.Close;
//            }
//            if(gamepad1.y){
//                q = Distance.Far;
//            }
            telemetry.addData("Path:", p);
            //telemetry.addData("Distance: ", q);
            telemetry.update();
        }

        //waitForStart();

        RunAuto(p == Path.Red);
        //RunAuto(q == Distance.Close);

    }
    public void RunAuto(boolean red){
//        robot.frontright.setPower(1f);
//        robot.frontleft.setPower(1f);
//        robot.backright.setPower(1f);
//        robot.backleft.setPower(1f);
//        sleep(2000);





        robot.frontleft.setPower(red ?  1f : -1f);
        robot.backleft.setPower(red ?  1f : -1f);
        robot.frontright.setPower(red ?  1f : -1f);
        robot.backright.setPower(red ? 1f :  -1f);
        sleep(500);

        robot.frontright.setPower(0);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);
        robot.backleft.setPower(0);
        sleep(600);

        robot.frontleft.setPower(red ?  -1f : 1f);
        robot.backleft.setPower(red ?  1f : -1f);
        robot.frontright.setPower(red ?  1f : -1f);
        robot.backright.setPower(red ? -1f :  1f);
        sleep(1000);

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
enum Distance{
    Close,
    Far
}
