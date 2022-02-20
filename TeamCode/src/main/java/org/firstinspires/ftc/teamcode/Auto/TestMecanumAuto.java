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


//        forwards(0.5f, 1000);
//        right(0.5f, 1000);
//        diagonalRight(0.5f, 1000);

//motor test
//        robot.frontleft.setPower(1);
//        sleep(1000);
//        robot.frontleft.setPower(0);
//
//        robot.frontright.setPower(1);
//        sleep(1000);
//        robot.frontright.setPower(0);
//
//        robot.backleft.setPower(1);
//        sleep(1000);
//        robot.backleft.setPower(0);
//
//        robot.backright.setPower(1);
//        sleep(1000);
//        robot.backright.setPower(0);


    }

    void forwards(float power, int time){
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

    void backwards(float power, int time){
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

    void left(float power, int time){
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

    void diagonalRight(float power, int time){
        robot.frontleft.setPower(power);
        robot.backright.setPower(power);
        sleep(time);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);

    }

}


