package org.firstinspires.ftc.teamcode.Auto;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="TestMecanumAuto")
//"tag" that is displayed on driver android phone
public class TestMecanumAuto extends LinearOpMode{

    //creating robot object
    public ProjectOdometryTest robot = new ProjectOdometryTest();


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);


        waitForStart();

        forwards(1,2000);
        right(1,2000);
        diagForwardRight(1,2000);

    }

    void forwards(int power, int time){
        robot.frontright.setPower(power);
        robot.frontleft.setPower(power);
        robot.backright.setPower(power);
        robot.backleft.setPower(power);
        sleep(time);
        robot.frontright.setPower(0);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);
        robot.backleft.setPower(0);
    }

    void backwards(int power, int time){
        robot.frontright.setPower(-power);
        robot.frontleft.setPower(-power);
        robot.backright.setPower(-power);
        robot.backleft.setPower(-power);
        sleep(time);
        robot.frontright.setPower(0);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);
        robot.backleft.setPower(0);
    }



    void right(int power, int time){
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

    void left(int power, int time){
        robot.frontright.setPower(power);
        robot.frontleft.setPower(-power);
        robot.backright.setPower(-power);
        robot.backleft.setPower(power);
        sleep(time);
        robot.frontright.setPower(0);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);
        robot.backleft.setPower(0);
    }

    void diagForwardRight(int power, int time){
        robot.frontleft.setPower(power);
        robot.backleft.setPower(power);
        sleep(time);
        robot.frontright.setPower(0);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);
        robot.backleft.setPower(0);
    }

}



