//change to Autonomous folder path
package org.firstinspires.ftc.teamcode.Auto;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;


//name that appears on the driver hub screen
@Autonomous(name = "EnumTest")
public class EnumTest extends LinearOpMode {
    //making a robot from project file (hardware map)
    public ProjectOdometryTest robot = new ProjectOdometryTest();


    @Override
    public void runOpMode() throws InterruptedException {
        //initialize hardware map
        robot.init(hardwareMap);

        Path p = Path.Red;
        ParkingLocation l = ParkingLocation.Substation;

        while(!isStarted()) {
            if (gamepad1.a) {
                p = Path.Red;
            }
            if (gamepad1.b) {
                p = Path.Blue;
            }
            if (gamepad1.x) {
                l = ParkingLocation.Substation;
            }
            if (gamepad1.y) {
                l = ParkingLocation.Terminal;
            }
            telemetry.addData("Path", p);
            telemetry.addData("Parking Location", l);
            telemetry.update();
        }

        waitForStart();
        //autonomous happens here
        if (p == Path.Red) {
            if (l == ParkingLocation.Substation) {
                robot.intake.setPower(1);
                sleep(2000);
                robot.intake.setPower(0);

            }
            if (l == ParkingLocation.Terminal) {
                robot.intake.setPower(1);
                sleep(4000);
                robot.intake.setPower(0);
            }
            /*
            if color =

             */

        }
        if (p == Path.Blue) {
            if (l == ParkingLocation.Substation) {
                robot.intake.setPower(1);
                sleep(6000);
                robot.intake.setPower(0);
            }
            if (l == ParkingLocation.Terminal) {
                robot.intake.setPower(1);
                sleep(8000);
                robot.intake.setPower(0);
            }
        }

    }
    enum Path {
        Red,
        Blue
    }
    enum ParkingLocation {
        Substation,
        Terminal
    }
}
