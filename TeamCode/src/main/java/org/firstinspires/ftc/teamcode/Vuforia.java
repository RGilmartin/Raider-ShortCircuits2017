package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.sun.tools.javac.util.Context;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by Ryan on 10/24/2017.
 */


public class Vuforia {

    public VuforiaLocalizer vuforia;

    float mmPerInch       = 25.4f;
    float mmFTCFieldWidth = (12*12 - 2) * mmPerInch;


    public Vuforia(HardwareMap hwMap, String licenceKey)
    {
        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = licenceKey;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
    }
}
