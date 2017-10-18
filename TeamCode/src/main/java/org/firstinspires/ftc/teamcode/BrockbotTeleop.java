/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;



@TeleOp(name="Brockbot: MakerFest", group="Brockbot")
public class BrockbotTeleop extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareBrockbot robot           = new HardwareBrockbot();
    private float gmJoystickOneY;
    private float gmJoystickOneX;
    private float gmJoystickTwoX;

    private boolean handClose;
    private boolean handOpen;
    private boolean rotateUp;
    private boolean rotateDown;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();

        while (opModeIsActive()) {

            getGamepad1Controls();
            getGamepad2Controls();

            moveWheels(gmJoystickOneY - gmJoystickOneX - gmJoystickTwoX,
                    gmJoystickOneY + gmJoystickOneX + gmJoystickTwoX,
                    gmJoystickOneY + gmJoystickOneX - gmJoystickTwoX,
                    gmJoystickOneY - gmJoystickOneY + gmJoystickTwoX, .4f);


            if(handClose)
            {
                closeHand();
                telemetry.addData("hand", "new position");
                telemetry.update();
            } else if (handOpen)
            {
                openHand();
                telemetry.addData("hand", "new position");
                telemetry.update();
            }

            if(rotateUp)
            {
                rotateArmUp();
                telemetry.addData("Rotate", "new position");
                telemetry.update();
            } else if(rotateDown)
            {
                rotateArmDown();
                telemetry.addData("Rotate", "new position");
                telemetry.update();
            }
        }
    }

    private void getGamepad1Controls()
    {
        gmJoystickOneX = gamepad1.left_stick_x;
        gmJoystickOneY = -gamepad1.left_stick_y;
        gmJoystickTwoX = gamepad1.right_stick_x;
    }

    private void getGamepad2Controls()
    {
        handClose = gamepad2.a;
        handOpen = gamepad2.b;
        rotateUp = gamepad2.right_bumper;
        rotateDown = gamepad2.left_bumper;
    }

    private void rotateArmUp()
    {
        robot.rotate.setPosition(1);
    }

    private void rotateArmDown()
    {
        robot.rotate.setPosition(0);
    }

    private void openHand()
    {
        robot.lefthand.setPosition(robot.rotate.getPosition() + .5);
        robot.rightHand.setPosition(robot.rotate.getPosition() + .5);
    }

    private void closeHand()
    {
        robot.lefthand.setPosition(robot.rotate.getPosition() - .5);
        robot.rightHand.setPosition(robot.rotate.getPosition() - .5);
    }

    private void moveWheels(float frontRight, float frontLeft, float backRight, float backLeft, float maxSpeed)
    {
        robot.frontRight.setPower(Range.clip(frontRight, -maxSpeed, maxSpeed));
        robot.frontLeft.setPower(Range.clip(frontLeft, -maxSpeed, maxSpeed));
        robot.backRight.setPower(Range.clip(backRight, -maxSpeed, maxSpeed));
        robot.backLeft.setPower(Range.clip(backLeft, -maxSpeed, maxSpeed));
    }
}
