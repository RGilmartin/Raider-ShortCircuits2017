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

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.List;


public class Robot
{
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;

    private List<DcMotor> driveMotors = new ArrayList<>();
    private float maxSpeed = 1f;
    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public Robot() {

    }

    public void setMode(List<DcMotor> list, DcMotor.RunMode runmode) {
        for(int i = 0; i < list.size(); i++) {
            list.get(i).setMode(runmode);

        }
    }

    public void drive(float xInput, float yInput, float zInput) {
        frontRight.setPower(Range.clip(yInput - xInput - zInput, -maxSpeed, maxSpeed));
        frontLeft.setPower(Range.clip(yInput + xInput + zInput, -maxSpeed, maxSpeed));
        backRight.setPower(Range.clip(yInput + xInput - zInput, -maxSpeed, maxSpeed));
        backLeft.setPower(Range.clip(yInput - xInput + zInput, -maxSpeed, maxSpeed));
    }

    public void findCryptoBox(float xInput, float yInput, float zInput, boolean cryptoboxDetected)
    {
        drive(xInput, yInput, zInput);
        while (!cryptoboxDetected)
        {

        }

        drive(0,0,0);
    }

    public void alignWithCryptoBox()
    {}

    public void setMaxSpeed(float max){
        maxSpeed = max;
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        frontRight = hwMap.get(DcMotor.class, "front_right_motor");
        frontLeft = hwMap.get(DcMotor.class, "front_left_motor");
        backRight = hwMap.get(DcMotor.class, "back_right_motor");
        backLeft = hwMap.get(DcMotor.class, "back_left_motor");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        driveMotors.add(frontRight);
        driveMotors.add(frontLeft);
        driveMotors.add(backRight);
        driveMotors.add(backLeft);


        // Set all motors to zero power
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);


        setMode(driveMotors, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
 }

