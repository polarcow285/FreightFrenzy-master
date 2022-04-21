package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        // Declare a MeepMeep instance
        // With a field size of 800 pixels
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Required: Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                // Option: Set theme. Default = ColorSchemeRedDark()
                .setColorScheme(new ColorSchemeBlueDark())
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-63, -47, Math.toRadians(0)))
                                .lineToLinearHeading(new Pose2d(0,-59, Math.toRadians(0)))
                                .addDisplacementMarker(() -> {
                                    //robot.dropoffwobblegoal();
                                })
                                //robot.dropoffwobblegoal(); PUT A DISPLACEMENT MARKER OAEISJFO ISEJFLSDJ
                                //park on launchline
                                .lineToLinearHeading(new Pose2d(0,-34, Math.toRadians(0)))
                                .lineToLinearHeading(new Pose2d(12,-34, Math.toRadians(0)))
                                .build()


                );
        RoadRunnerBotEntity myFirstBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(11.6, -58.7, Math.toRadians(0)))
                                .strafeRight(7)
                                .forward(39)
                                .strafeLeft(12)
                                .strafeTo(new Vector2d(28, 42))
                                .lineToLinearHeading(new Pose2d(-54, -3, Math.toRadians(271)))
                                .addDisplacementMarker(() -> {
                                    // This marker runs after the first splineTo()

                                    // Run your action in here!
                                })
                                .splineTo(new Vector2d(36,-22), Math.toRadians(89))
                                .splineToLinearHeading(new Pose2d(21, 53, Math.toRadians(179)), Math.toRadians(0))
                                .build()
                );

        // Set field image
        meepMeep.setBackground(MeepMeep.Background.FIELD_ULTIMATEGOAL_INNOV8RZ_DARK)
                .setDarkMode(true)
                // Background opacity from 0-1
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .addEntity(myFirstBot)
                .start();
    }
}