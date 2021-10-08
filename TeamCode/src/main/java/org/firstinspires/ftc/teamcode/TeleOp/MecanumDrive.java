package org.firstinspires.ftc.teamcode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;

@TeleOp(name="MecanumDrive")
public class MecanumDrive extends LinearOpMode{
    private ProjectOdometryTest robot = new ProjectOdometryTest();


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        waitForStart();

        while(opModeIsActive()){
            
        }
    }

}
