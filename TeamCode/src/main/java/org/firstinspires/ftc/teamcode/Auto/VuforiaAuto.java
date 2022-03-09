package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Projects.Project;
import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name = "VuforiaAuto")
public class VuforiaAuto extends LinearOpMode{
    public ProjectOdometryTest robot = new ProjectOdometryTest();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        Path p = Path.Red;

        robot.camera = hardwareMap.get(WebcamName.class, "webcam");

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AfwfsNH/////AAABmQl6N3O+8kPFua00/J4/sL0CuaSsu8CgiZ1nfYTMiFT3raE+bhkuQtPGnKmx8WqXUWoOvbC6SSqp7VFVNe3e2o+OgQBe9ALjh20pDTMvYwakoazWUolBcQwZSzl3eHY/bKGWEq56UVVSx71N66DDYRHvNSBst3e/0H2g/gBUMVBV2rk5Qn16drp5qK4a3zYh4exg7Oo3dRq3QYIUuTtts4CHwq+Ni1kwoZG3JV+k2wYMQ0J0m++MmyGuCPRlhnYn2MX07lduspnVXLYlICGbSVvinxNHxZ+ym4z/AA5KpW53Ar7DOz3jWZhQwc7F2PnJWd528Ktley5VPKiippqSZwjHoOZTbE/jNaAG8MCU2b2t";
        parameters.cameraName = robot.camera;

        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(parameters);
        VuforiaTrackables trackables = vuforia.loadTrackablesFromAsset("FreightFrenzy");

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

        //waitForStart();
        while(!isStarted()) {
            if (gamepad1.b) {
                p = Path.Red;
            }
            if (gamepad1.x) {
                p = Path.Blue;
            }
            telemetry.addData("Path: ", p);
            telemetry.update();
        }

        while(opModeIsActive()){
            while (shippingElementLocation == null) {
                shippingElementLocation = shippingElementListener.getVuforiaCameraFromTarget();
            }
            float closestX = Range.clip(shippingElementLocation.getTranslation().get(0), -30, 30);
            telemetry.addData("location: ", shippingElementLocation.getTranslation()); //x y z
            telemetry.addData("closest x: ", closestX);
            telemetry.update();




            shippingElementLocation = null;
        }


        //closest to shipping hub = -20
        //middle = 20

        /*
        int count = 0;
        while (shippingElementLocation == null) {
            shippingElementLocation = shippingElementListener.getVuforiaCameraFromTarget();
            count++;
            if(count>300000){
                break;
            }
        }

        float closestX;
        if(shippingElementLocation != null){
            closestX = Range.clip(shippingElementLocation.getTranslation().get(0), -20, 20);
        }else{
            closestX = 40;
        }

        if(p == Path.Red){
            if(closestX == -20){
                //strafing right
                robot.frontleft.setPower(-1);
                robot.frontright.setPower(1);
                robot.backleft.setPower(1);
                robot.backright.setPower(-1);
                sleep(500);

                robot.frontleft.setPower(0);
                robot.frontright.setPower(0);
                robot.backleft.setPower(0);
                robot.backright.setPower(0);
                sleep(200);

                //driving forward

                robot.frontleft.setPower(1);
                robot.frontright.setPower(1);
                robot.backleft.setPower(1);
                robot.backright.setPower(1);
                sleep(850);

                robot.frontleft.setPower(0);
                robot.frontright.setPower(0);
                robot.backleft.setPower(0);
                robot.backright.setPower(0);
                sleep(200);

                //turning right
                robot.frontleft.setPower(1);
                robot.frontright.setPower(-1);
                robot.backleft.setPower(1);
                robot.backright.setPower(-1);
                sleep(1000);

                robot.frontleft.setPower(0);
                robot.frontright.setPower(0);
                robot.backleft.setPower(0);
                robot.backright.setPower(0);
                sleep(200);

                //driving forward
                robot.frontleft.setPower(1);
                robot.frontright.setPower(1);
                robot.backleft.setPower(1);
                robot.backright.setPower(1);
                sleep(425);

                robot.frontleft.setPower(0);
                robot.frontright.setPower(0);
                robot.backleft.setPower(0);
                robot.backright.setPower(0);
                sleep(200);


            }

        }
        */




    }

    enum Path{
        Red,
        Blue
    }
}
