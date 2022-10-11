//change to Autonomous folder path
package org.firstinspires.ftc.teamcode.Auto;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;


//name that appears on the driver hub screen
@Autonomous(name = "EnumTest")
public class EnumTest extends LinearOpMode {
    //making a robot from project file (hardware map)
    public ProjectOdometryTest robot = new ProjectOdometryTest();
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();

    @Override
    public void runOpMode() throws InterruptedException {
        //initialize hardware map
        robot.init(hardwareMap);

        Path p = Path.Red;
        ParkingLocation l = ParkingLocation.Substation;
        TileLocation t = TileLocation.Left;

        boolean isPathRed = true;
        boolean isSubstation = true;
        boolean isRightTile = true;

        while(!isStarted()) {
            try {
                previousGamepad1.copy(currentGamepad1);
                currentGamepad1.copy(gamepad1);
            }
            catch (RobotCoreException e) {

            }
            if (currentGamepad1.a && !previousGamepad1.a) {
                isPathRed = !isPathRed;
                //p = Path.Red;
            }
            if (currentGamepad1.b && !previousGamepad1.b) {
                isSubstation = !isSubstation;
                //p = Path.Red;
            }
            if (currentGamepad1.y && !previousGamepad1.y) {
                isRightTile = !isRightTile;
                //p = Path.Red;
            }

            /*if (currentGamepad1.y && !previousGamepad1.y) {
                t = TileLocation.Right;
            }
            if (currentGamepad1.y && previousGamepad1.y) {
                t = TileLocation.Left;
            }
            */
            if (isPathRed) {
                p = Path.Red;
            }
            else {
                p = Path.Blue;
            }
            if (isSubstation) {
                l = ParkingLocation.Substation;
            }
            else {
                l = ParkingLocation.Terminal;
            }
            if (isRightTile) {
                t = TileLocation.Right;
            }
            else {
                t = TileLocation.Left;;
            }
            telemetry.addData("Path", p);
            telemetry.addData("Parking Location", l);
            telemetry.addData("Tile Location", t);
            telemetry.update();
        }

        waitForStart();
        //autonomous happens here
        if (p == Path.Red) {
            if (t == TileLocation.Left) {
                if (l == ParkingLocation.Substation) {
                    robot.frontleft.setPower(-1);
                    robot.frontright.setPower(1);
                    robot.backleft.setPower(1);
                    robot.backright.setPower(-1);
                    sleep(1000);
                    robot.frontleft.setPower(0);
                    robot.frontright.setPower(0);
                    robot.backleft.setPower(0);
                    robot.backright.setPower(0);
                }
                if (l == ParkingLocation.Terminal) {
                    robot.frontleft.setPower(1);
                    robot.frontright.setPower(-1);
                    robot.backleft.setPower(-1);
                    robot.backright.setPower(1);
                    sleep(1000);
                    robot.frontleft.setPower(0);
                    robot.frontright.setPower(0);
                    robot.backleft.setPower(0);
                    robot.backright.setPower(0);
                }
            }
            if (t == TileLocation.Right) {
                if (l == ParkingLocation.Substation) {
                    robot.frontleft.setPower(1);
                    robot.frontright.setPower(-1);
                    robot.backleft.setPower(-1);
                    robot.backright.setPower(1);
                    sleep(1000);
                    robot.frontleft.setPower(0);
                    robot.frontright.setPower(0);
                    robot.backleft.setPower(0);
                    robot.backright.setPower(0);

                }
                if (l == ParkingLocation.Terminal) {
                    robot.frontleft.setPower(-1);
                    robot.frontright.setPower(1);
                    robot.backleft.setPower(1);
                    robot.backright.setPower(-1);
                    sleep(1000);
                    robot.frontleft.setPower(0);
                    robot.frontright.setPower(0);
                    robot.backleft.setPower(0);
                    robot.backright.setPower(0);
                }
            }
        }
        if (p == Path.Blue) {
            if (t == TileLocation.Left) {
                if (l == ParkingLocation.Substation) {
                    robot.frontleft.setPower(-1);
                    robot.frontright.setPower(1);
                    robot.backleft.setPower(1);
                    robot.backright.setPower(-1);
                    sleep(1000);
                    robot.frontleft.setPower(0);
                    robot.frontright.setPower(0);
                    robot.backleft.setPower(0);
                    robot.backright.setPower(0);
                }
                if (l == ParkingLocation.Terminal) {
                    robot.frontleft.setPower(1);
                    robot.frontright.setPower(-1);
                    robot.backleft.setPower(-1);
                    robot.backright.setPower(1);
                    sleep(1000);
                    robot.frontleft.setPower(0);
                    robot.frontright.setPower(0);
                    robot.backleft.setPower(0);
                    robot.backright.setPower(0);
                }
            }
            if (t == TileLocation.Right) {
                if (l == ParkingLocation.Substation) {
                    robot.frontleft.setPower(1);
                    robot.frontright.setPower(-1);
                    robot.backleft.setPower(-1);
                    robot.backright.setPower(1);
                    sleep(1000);
                    robot.frontleft.setPower(0);
                    robot.frontright.setPower(0);
                    robot.backleft.setPower(0);
                    robot.backright.setPower(0);
                }
                if (l == ParkingLocation.Terminal) {
                    robot.frontleft.setPower(-1);
                    robot.frontright.setPower(1);
                    robot.backleft.setPower(1);
                    robot.backright.setPower(-1);
                    sleep(1000);
                    robot.frontleft.setPower(0);
                    robot.frontright.setPower(0);
                    robot.backleft.setPower(0);
                    robot.backright.setPower(0);
                }
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
    enum TileLocation {
        Right,
        Left
    }
}
