package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Projects.ProjectPushbotTest;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import com.acmerobotics.dashboard.FtcDashboard;


@Autonomous(name = "TestVuforiaAuto")
public class TestVuforiaAuto extends LinearOpMode{
    public ProjectPushbotTest robot = new ProjectPushbotTest();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        robot.camera = hardwareMap.get(WebcamName.class, "webcam");

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AfwfsNH/////AAABmQl6N3O+8kPFua00/J4/sL0CuaSsu8CgiZ1nfYTMiFT3raE+bhkuQtPGnKmx8WqXUWoOvbC6SSqp7VFVNe3e2o+OgQBe9ALjh20pDTMvYwakoazWUolBcQwZSzl3eHY/bKGWEq56UVVSx71N66DDYRHvNSBst3e/0H2g/gBUMVBV2rk5Qn16drp5qK4a3zYh4exg7Oo3dRq3QYIUuTtts4CHwq+Ni1kwoZG3JV+k2wYMQ0J0m++MmyGuCPRlhnYn2MX07lduspnVXLYlICGbSVvinxNHxZ+ym4z/AA5KpW53Ar7DOz3jWZhQwc7F2PnJWd528Ktley5VPKiippqSZwjHoOZTbE/jNaAG8MCU2b2t";
        parameters.cameraName = robot.camera;




        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(parameters);
        VuforiaTrackables trackables = vuforia.loadTrackablesFromAsset("Skystone");

        FtcDashboard.getInstance().startCameraStream(vuforia, 0);

        //An image of a redpanda is the first image in the list
        //gets first trackable (image)
        VuforiaTrackable shippingElementTrackable = trackables.get(0);
        shippingElementTrackable.setName("shippingElement");

        trackables.activate();

        //create listener based on trackable
        VuforiaTrackableDefaultListener shippingElementListener;
        shippingElementListener = (VuforiaTrackableDefaultListener) shippingElementTrackable.getListener();

        OpenGLMatrix shippingElementLocation = null;

        waitForStart();

        while(opModeIsActive()){
            while(shippingElementLocation == null){
                shippingElementLocation = shippingElementListener.getUpdatedRobotLocation();
            }
            //shippingElementLocation = shippingElementListener.getUpdatedRobotLocation();
            //telemetry.addData("location of shipping element", formatMatrix(shippingElementLocation));
            telemetry.addData("Shipping Element Found", formatMatrix(shippingElementLocation));
            telemetry.update();

            float x = shippingElementLocation.getTranslation().get(0);
            if(shippingElementLocation != null){
                if(x < -100) { //test to find out coordinate of tape
                    //right of robot
                    //upper level
                    robot.rightMotor.setPower(0.5);
                    robot.leftMotor.setPower(0);
                    sleep(50);

                }
                else if (-100 < x && x < 140){
                    //middle, in front of robot
                    //middle level
                    robot.rightMotor.setPower(0.5);
                    robot.leftMotor.setPower(0.5);
                    sleep(50);
                }
                else{
                    //to the left of the robot
                    //put on the lowest level
                    robot.rightMotor.setPower(0);
                    robot.leftMotor.setPower(0.5);
                    sleep(50);
                }
            }
            shippingElementLocation = null;


        }



    }
    String formatMatrix(OpenGLMatrix matrix){
        if (matrix != null) {
            return matrix.formatAsTransform();
        }
        else{
            return "not found";
        }
    }
}
