
package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.CryptoboxDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaNavigation;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name="Concept: VuMark Id", group ="Concept")
public class GlyphPlacementRed extends LinearOpMode {

    public static final String TAG = "Vuforia VuMark Sample";

    private Robot robot = new Robot();
    private CryptoboxDetector cryptoboxDetector = null;

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
     private ClosableVuforiaLocalizer vuforia;

    @Override public void runOpMode() {

        // Get and start the camera
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        ClosableVuforiaLocalizer.Parameters parameters = new ClosableVuforiaLocalizer.Parameters(cameraMonitorViewId);

        // Licence key for vuforia
        parameters.vuforiaLicenseKey = "ATsODcD/////AAAAAVw2lR...d45oGpdljdOh5LuFB9nDNfckoxb8COxKSFX";

        // Set active camera to back
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = (ClosableVuforiaLocalizer) ClassFactory.createVuforiaLocalizer(parameters);


        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

        relicTrackables.activate();

        while (opModeIsActive()) {

            // Load VuMarks. RelicRecoveryVuMark is an Enum that holds the VuMarks for
            // the left, center and right of the cryptobox
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                // We found an instance of the VuMark. show which one it is for debug purposes.
                telemetry.addData("VuMark", "%s visible", vuMark);

                vuforia.close();

                cryptoboxDetector = new CryptoboxDetector();
                cryptoboxDetector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());

                cryptoboxDetector.downScaleFactor = .4;
                cryptoboxDetector.detectionMode = CryptoboxDetector.CryptoboxDetectionMode.HSV_RED;
                cryptoboxDetector.speed = CryptoboxDetector.CryptoboxSpeed.BALANCED;
                cryptoboxDetector.rotateMat = false;
                cryptoboxDetector.enable();

                switch (vuMark) {
                    case LEFT:

                        robot.findCryptoBox(.5f, 0f, 0f, cryptoboxDetector);
                        robot.alignWithCryptoBox(.2f, 0, 0, 0, cryptoboxDetector, telemetry);
                        // call functions to place glyph in column


                        break;
                    case CENTER:

                        robot.findCryptoBox(.5f, 0,0, cryptoboxDetector);
                        robot.alignWithCryptoBox(.2f, 0,0,1, cryptoboxDetector, telemetry);
                        break;
                    case RIGHT:

                        robot.findCryptoBox(.5f, 0,0, cryptoboxDetector);
                        robot.alignWithCryptoBox(.2f, 0,0,2, cryptoboxDetector, telemetry);
                        break;
                }
            }
            else {
                telemetry.addData("VuMark", "not visible");
            }

            telemetry.update();
        }
    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
}
