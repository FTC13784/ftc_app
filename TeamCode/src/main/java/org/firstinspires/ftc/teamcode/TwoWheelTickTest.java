package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "TwoWheelDrive Tick Test", group = "Linear Opmode")
@Disabled
public class TwoWheelTickTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    DcMotor leftMotorFront;
    DcMotor rightMotorFront;
    DcMotor leftMotorBack;
    DcMotor rightMotorBack;

    int ticksPerWheelRev = 1120;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        InitializeHardware();

        waitForStart();
        runtime.reset();

        //put instructions in here
        TwoWheelDriveTicks(1, ticksPerWheelRev);
        StopDriving();
    }

    public void InitializeHardware() {
        leftMotorFront = hardwareMap.dcMotor.get("Left_Front");
        rightMotorFront = hardwareMap.dcMotor.get("Right_Front");
        leftMotorBack = hardwareMap.dcMotor.get("Left_Back");
        rightMotorBack = hardwareMap.dcMotor.get("Right_Back");
        leftMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotorFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotorBack.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void TwoWheelDrive(double power) {
        leftMotorFront.setPower(power);
        rightMotorFront.setPower(power);
    }

    public void TwoWheelDriveTicks(double power, int distance) {

        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftMotorFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        leftMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftMotorFront.setTargetPosition(distance);

        TwoWheelDrive(power);

        while (Math.abs(leftMotorFront.getCurrentPosition()) < (distance) && opModeIsActive()) {
            //wait until target position is reached
        }

        StopDriving();
    }

    public void StopDriving() {
        TwoWheelDrive(0);
        leftMotorBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftMotorFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void StopDrivingTime(double power, long time) throws InterruptedException {
        StopDriving();
        Thread.sleep(time);
    }
}