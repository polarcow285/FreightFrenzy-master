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
        boolean redTeam = true;


        while(!isStarted()){
            if(gamepad1.b){
                redTeam = true;
            }
            if(gamepad1.x){
                redTeam = false;
            }
            telemetry.addData("path:", redTeam);
            telemetry.update();
        }

        waitForStart();

        right(1, 5000);

    }

    void right(float power, int time){
        robot.frontright.setPower(-power);
        robot.frontleft.setPower(power);
        robot.backright.setPower(power);
        robot.backleft.setPower(-power);
        sleep(time);
        robot.frontright.setPower(0);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);
        robot.backleft.setPower(0);
    }

}
