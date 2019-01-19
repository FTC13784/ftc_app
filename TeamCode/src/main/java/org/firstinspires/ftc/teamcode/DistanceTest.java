package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="DistanceTest", group="Linear Opmode")  // @Autonomous(...) is the other common choice
@Disabled
public class DistanceTest extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    //declare motors
    DcMotor leftMotorFront;
    DcMotor rightMotorFront;
    DcMotor leftMotorBack;
    DcMotor rightMotorBack;

    double ticksPerWheelRev = 1120;

    double wheelDiameter = 4;
    double ticksPerInch = ticksPerWheelRev/(wheelDiameter * Math.PI);

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        InitializeHardware();

        waitForStart();
        runtime.reset();

        //put instructions in here
        DriveForwardDistance(0.2, 12);
        StopDriving();
    }

    public void InitializeHardware()
    {
        leftMotorFront = hardwareMap.dcMotor.get("Left_Front");
        leftMotorBack = hardwareMap.dcMotor.get("Left_Back");
        rightMotorFront = hardwareMap.dcMotor.get("Right_Front");
        rightMotorBack = hardwareMap.dcMotor.get("Right_Back");

        leftMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotorFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotorBack.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void DriveForward(double power)
    {
        leftMotorFront.setPower(power);
        leftMotorBack.setPower(power);
        rightMotorFront.setPower(power);
        rightMotorBack.setPower(power);
    }

    public void DriveForwardDistance(double power, int distance)
    {
        // Reset encoders
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // set target position
        leftMotorFront.setTargetPosition((int) (distance * ticksPerInch));
        rightMotorFront.setTargetPosition((int) (distance * ticksPerInch));
        leftMotorBack.setTargetPosition((int) (distance * ticksPerInch));
        rightMotorBack.setTargetPosition((int) (distance * ticksPerInch));

        // Set to RUN_TO_POSITION mode
        leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set drive power
        DriveForward(power);

        while(leftMotorFront.isBusy() && rightMotorFront.isBusy() && leftMotorBack.isBusy() && rightMotorBack.isBusy()) {
            // Wait until target position is reached
        }

        // Stop and change modes back to normal
        leftMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void StopDriving()
    {
        DriveForward(0);
    }

    public void StopDrivingTime(double power, long time) throws InterruptedException
    {
        StopDriving();
        Thread.sleep(time);
    }
}