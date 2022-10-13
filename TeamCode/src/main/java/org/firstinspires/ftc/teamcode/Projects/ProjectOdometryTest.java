package org.firstinspires.ftc.teamcode.Projects;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class ProjectOdometryTest extends Project{

    //Project file for testing on odometry chassis


    //Setup motors
    public DcMotor frontright = null;
    public DcMotor frontleft = null;
    public DcMotor backright = null;
    public DcMotor backleft = null;

    public DigitalChannel slideSwitch = null;
    public DigitalChannel intakeSwitch = null;

    public DcMotor intake = null;
    //public DcMotor carousel = null;
    public DcMotor storageunit = null;

    public Servo trapdoor = null;
    public WebcamName camera = null;

    @Override
    public void init(HardwareMap ahwMap) {
        //Save reference to Hardware map
        hwMap = ahwMap;


        //Define and Initialize Motors
        frontright = hwMap.dcMotor.get("frontright"); //port c3
        frontleft = hwMap.dcMotor.get("frontleft"); //port e0
        backright = hwMap.dcMotor.get("backright"); //port c0
        backleft = hwMap.dcMotor.get("backleft"); //port e3


        intake = hwMap.dcMotor.get("intake"); //port c1
        //carousel = hwMap.dcMotor.get("carousel");
        storageunit = hwMap.dcMotor.get("storageunit"); //port e2

        trapdoor = hwMap.servo.get("trapdoor"); //port e0

        slideSwitch = hwMap.digitalChannel.get("slideSwitch");
        intakeSwitch = hwMap.digitalChannel.get("intakeSwitch");

        //Setup Motor directions and Encoder settings
        frontright.setDirection(DcMotor.Direction.FORWARD);
        frontleft.setDirection(DcMotor.Direction.REVERSE);
        backright.setDirection(DcMotor.Direction.FORWARD);
        backleft.setDirection(DcMotor.Direction.REVERSE);

        intake.setDirection(DcMotor.Direction.REVERSE);
       // carousel.setDirection(DcMotor.Direction.FORWARD);
        storageunit.setDirection(DcMotor.Direction.FORWARD);

        frontright.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontleft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //carousel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //storageunit.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       // carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        storageunit.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set all motors to zero power
        Stop();
    }

    public void Stop(){
        frontright.setPower(0);
        frontleft.setPower(0);
        backright.setPower(0);
        backleft.setPower(0);

        intake.setPower(0);
       // carousel.setPower(0);
        storageunit.setPower(0);

       trapdoor.setPosition(1);


    }
}
