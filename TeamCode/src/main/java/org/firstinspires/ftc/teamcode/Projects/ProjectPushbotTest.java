package org.firstinspires.ftc.teamcode.Projects;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ProjectPushbotTest extends Project{
    public DcMotor rightMotor = null;
    public DcMotor leftMotor = null;

    @Override
    public void init(HardwareMap ahwMap) {
        //Save reference to Hardware map
        hwMap = ahwMap;


        //Define and Initialize Motors

        //Setup Motor directions and Encoder settings

        // Set all motors to zero power
        Stop();
    }

    public void Stop(){


    }
}
