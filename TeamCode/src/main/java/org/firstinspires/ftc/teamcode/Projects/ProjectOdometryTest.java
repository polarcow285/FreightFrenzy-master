package org.firstinspires.ftc.teamcode.Projects;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ProjectOdometryTest extends Project{
    //Project file for testing on odometry chassis

    //Setup motors
    public DcMotor frontright = null;
    public DcMotor frontleft = null;
    public DcMotor backright = null;
    public DcMotor backleft = null;

    @Override
    public void init(HardwareMap ahwMap) {
        //Save reference to Hardware map
        hwMap = ahwMap;

        //Define and Initialize Motors
        frontright = hwMap.dcMotor.get("frontright");
        frontleft = hwMap.dcMotor.get("frontleft");
        backright = hwMap.dcMotor.get("backright");
        backleft = hwMap.dcMotor.get("backleft");

        //Setup Motor directions and Encoder settings
        frontright.setDirection((DcMotorSimple.Direction.FORWARD));
        frontleft.setDirection((DcMotorSimple.Direction.REVERSE));
        backright.setDirection((DcMotorSimple.Direction.FORWARD));
        backleft.setDirection((DcMotorSimple.Direction.REVERSE));

        frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set all motors to zero power
        Stop();
    }

    public void Stop(){
        frontright.setPower(0);
        frontleft.setPower(0);
        backright.setPower(0);
        backleft.setPower(0);
    }
}
