package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Projects.ProjectPushbotTest;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "TestVuforiaAuto")
public class TestVuforiaAuto extends LinearOpMode{
    public ProjectPushbotTest robot = new ProjectPushbotTest();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);



    }
}
