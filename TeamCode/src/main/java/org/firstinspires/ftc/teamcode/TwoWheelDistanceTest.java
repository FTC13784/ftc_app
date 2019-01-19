package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "TwoWheelDrive Distance Test", group = "Linear Opmode")
@Disabled
public class TwoWheelDistanceTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    DcMotor leftMotorFront;
    DcMotor rightMotorFront;

    double ticksPerWheelRev = 1120;
    double wheelDiameter = 4;
    double ticksPerInch = ticksPerWheelRev / (wheelDiameter * Math.PI);

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        InitializeHardware();

        waitForStart();
        runtime.reset();

        //put instructions in here
        TwoWheelDriveDistance(1, 12);
        StopDriving();
    }

    public void InitializeHardware() {
        leftMotorFront = hardwareMap.dcMotor.get("Left_Front");
        rightMotorFront = hardwareMap.dcMotor.get("Right_Front");
        leftMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotorFront.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    private void TwoWheelDrive(double power) {
        leftMotorFront.setPower(power);
        rightMotorFront.setPower(power);
    }

    private void TwoWheelDriveDistance(double power, int distance) {

        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftMotorFront.setTargetPosition((int) (distance * ticksPerInch));

        TwoWheelDrive(power);

        while (Math.abs(leftMotorFront.getCurrentPosition()) < (distance) && opModeIsActive()) {
            //wait until target position is reached
        }

        StopDriving();
    }

    public void StopDriving() {
        TwoWheelDrive(0);
    }

    public void StopDrivingTime(double power, long time) throws InterruptedException {
        StopDriving();
        Thread.sleep(time);
    }
}