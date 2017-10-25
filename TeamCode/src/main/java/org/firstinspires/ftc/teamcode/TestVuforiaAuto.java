package org.firstinspires.ftc.teamcode;

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

import java.util.ArrayList;
import java.util.List;

/**

 */

@Autonomous(name="Concept: VuMark Id", group ="Concept")
@Disabled
public class TestVuforiaAuto extends LinearOpMode {

    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;

    private VectorF redStraightCryptoPos = new VectorF(0,0,0);
    private VectorF redRightCryptoPos = new VectorF(0,0,0);
    private VectorF blueStraightCryptoPos = new VectorF(0,0,0);
    private VectorF blueRightCryptoPos = new VectorF(0,0,0);
    private VectorF phonePos = new VectorF(0,0,0);


    VuforiaLocalizer vuforia;

    @Override public void runOpMode() {
        // Gets the camera ready to be used
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // **NEED THIS** vuforia licence
        parameters.vuforiaLicenseKey = "ARkiT2//////AAAAGXLiKH9KtE9khLFDV47Uxk9/k2tW2LHnSHMrhIMYiYNHVv3fi+lJGozeGF6jAADNUoNwbQpYu" +
                "CQcfZLk0vmFjwf+BVjButuNSmi8IhbiyQZXcwurS/9iujsvZjnITkiSIgtAKhGtra6JNiGxo0ywdgmSzK0Hn2i2OFeIm1jPBwNnfiC8eftRU9BwI" +
                "ZTM9ao6OFJTOfggL5zsLqO9VVJItZs/6PW7KHPsv7pHixKY2iaE2oKUVclP+aL0OJO7+kvmVdVoOJnnWVersBkJAjZMSz7TwCE598DBrB0Te2Pbn" +
                "3oTMdg+lcpvlqfpdqasHLy9/Y7Nw2FRxgbH/npoYAqFnZxSBnLIQSad2KV1h3M4xcl+";

        //Tell Vuforia what camera to use
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        // Get the trackable image. There is only one and it contains all three variations
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary


        float mmPerInch        = 25.4f;
        float mmBotWidth       = 18 * mmPerInch;
        float mmFTCFieldWidth  = (12*12 - 2) * mmPerInch;


        // Create trackable objects for the four cryptoboxes
        VuforiaTrackables cryptoboxes = this.vuforia.loadTrackablesFromAsset("CryptoBoxes");
        VuforiaTrackable redStraightCrypto = cryptoboxes.get(0);
        redStraightCrypto.setName("RedStraightCrypto");

        VuforiaTrackable redRightCrypto = cryptoboxes.get(0);
        redRightCrypto.setName("RedRightCrypto");

        VuforiaTrackable blueStraightCrypto = cryptoboxes.get(1);
        blueStraightCrypto.setName("BlueStraightCrypto");
        VuforiaTrackable blueRightCrypto = cryptoboxes.get(1);

        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(cryptoboxes);


        OpenGLMatrix redStraightCryptoPosition = OpenGLMatrix
                .translation(redStraightCryptoPos.get(0), redStraightCryptoPos.get(1), redStraightCryptoPos.get(2))
                .multiplied(Orientation
                        .getRotationMatrix(AxesReference.EXTRINSIC
                                , AxesOrder.XYZ
                                , AngleUnit.DEGREES
                                , 0,0,0));
        redStraightCrypto.setLocation(redStraightCryptoPosition);

        OpenGLMatrix redRightCryptoPosition = OpenGLMatrix
                .translation(redRightCryptoPos.get(0), redRightCryptoPos.get(1), redRightCryptoPos.get(2))
                .multiplied(Orientation
                        .getRotationMatrix(AxesReference.EXTRINSIC
                                , AxesOrder.XYZ
                                , AngleUnit.DEGREES
                                , 0,0,0));
        redRightCrypto.setLocation(redRightCryptoPosition);

        OpenGLMatrix blueStraightCryptoPosition = OpenGLMatrix
                .translation(blueStraightCryptoPos.get(0), blueStraightCryptoPos.get(1), blueStraightCryptoPos.get(2))
                .multiplied(Orientation
                        .getRotationMatrix(AxesReference.EXTRINSIC
                                , AxesOrder.XYZ
                                , AngleUnit.DEGREES
                                , 0,0,0));
        blueStraightCrypto.setLocation(blueStraightCryptoPosition);

        OpenGLMatrix blueRightCryptoPosition = OpenGLMatrix
                .translation(blueRightCryptoPos.get(0), blueRightCryptoPos.get(1), blueRightCryptoPos.get(2))
                .multiplied(Orientation
                        .getRotationMatrix(AxesReference.EXTRINSIC
                                , AxesOrder.XYZ
                                , AngleUnit.DEGREES
                                , 0,0,0));
        blueRightCrypto.setLocation(blueRightCryptoPosition);

        OpenGLMatrix phonePositionOnRobot = OpenGLMatrix
                .translation(phonePos.get(0), phonePos.get(1), phonePos.get(2))
                .multiplied(Orientation
                        .getRotationMatrix(AxesReference.EXTRINSIC
                                , AxesOrder.XYZ
                                , AngleUnit.DEGREES
                                , 0,0,0));


        ((VuforiaTrackableDefaultListener)redStraightCrypto.getListener()).setPhoneInformation(phonePositionOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)redRightCrypto.getListener()).setPhoneInformation(phonePositionOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)blueStraightCrypto.getListener()).setPhoneInformation(phonePositionOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)blueRightCrypto.getListener()).setPhoneInformation(phonePositionOnRobot, parameters.cameraDirection);

        relicTrackables.activate();
        cryptoboxes.activate();

        while (opModeIsActive()) {


            for (VuforiaTrackable trackable : allTrackables) {
                /**
                 * getUpdatedRobotLocation() will return null if no new information is available since
                 * the last time that call was made, or if the trackable is not currently visible.
                 * getRobotLocation() will return null if the trackable is not currently visible.
                 */
                telemetry.addData(trackable.getName(), ((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible() ? "Visible" : "Not Visible");
                telemetry.update();

                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
            }



            // Check to see if any instances of the
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                /* Found an instance of the template. In the actual game, you will probably
                 * loop until this condition occurs, then move on to act accordingly depending
                 * on which VuMark was visible. */
                telemetry.addData("VuMark", "%s visible", vuMark);

                /* For fun, we also exhibit the navigational pose. In the Relic Recovery game,
                 * it is perhaps unlikely that you will actually need to act on this pose information, but
                 * we illustrate it nevertheless, for completeness. */
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
                telemetry.addData("Pose", format(pose));

                /* We further illustrate how to decompose the pose into useful rotational and
                 * translational components */
                if (pose != null) {
                    VectorF trans = pose.getTranslation();
                    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                    // Extract the X, Y, and Z components of the offset of the target relative to the robot
                    double tX = trans.get(0);
                    double tY = trans.get(1);
                    double tZ = trans.get(2);

                    // Extract the rotational components of the target relative to the robot
                    double rX = rot.firstAngle;
                    double rY = rot.secondAngle;
                    double rZ = rot.thirdAngle;
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