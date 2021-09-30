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
        waitForStart();

        //write autonomous code here
        //robot move forward for 2 seconds then stop
        robot.frontright.setPower(1);
        robot.frontleft.setPower(1);
        robot.backright.setPower(1);
        robot.backleft.setPower(1);
        sleep(2000);

        robot.frontright.setPower(0);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);
        robot.backleft.setPower(0);
        sleep(1000);

        robot.frontright.setPower(0.5);
        robot.frontleft.setPower(0.5);
        robot.backright.setPower(-0.5);
        robot.backleft.setPower(-0.5);
        sleep(3000);

        robot.frontright.setPower(0);
        robot.frontleft.setPower(0);
        robot.backright.setPower(0);
        robot.backleft.setPower(0);

    }
}

