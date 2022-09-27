package org.firstinspires.ftc.teamcode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="DeclanToggleTest")
public class DeclanToggleTest extends LinearOpMode {
    private ProjectOdometryTest robot = new ProjectOdometryTest();
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();

    boolean forwardIntake = false;
    boolean backwardIntake = false;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()){
            try {
            previousGamepad1.copy(currentGamepad1);
            previousGamepad2.copy(currentGamepad2);

            currentGamepad1.copy(gamepad1);
            currentGamepad2.copy(gamepad2);
            }
            catch (RobotCoreException e) {

            }
            if (currentGamepad1.y && !previousGamepad1.y) {
                forwardIntake = !forwardIntake;
            }
            if (currentGamepad1.a && !previousGamepad1.a) {
                backwardIntake = !backwardIntake;
            }
            if (forwardIntake) {
                robot.intake.setPower(-1);
            }
            else if (backwardIntake) {
                robot.intake.setPower(1);
            }
            else {
                robot.intake.setPower(0);
            }
        }
    }
}
