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


    }
}

