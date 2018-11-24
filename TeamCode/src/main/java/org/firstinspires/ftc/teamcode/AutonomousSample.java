package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="AutonomousSample", group="Linear Opmode")  // @Autonomous(...) is the other common choice
@Disabled
public class AutonomousSample extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    // DcMotor leftMotor = null;
    // DcMotor rightMotor = null;

    DcMotor leftMotor = null;
    DcMotor rightMotor = null;

    double power = 0.5;

    Servo bottomServo = null;
    Servo topServo = null;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

       InitializeHardware();
       RaiseArm();

        waitForStart();
        runtime.reset();

        DriveForwardTime(DRIVE_POWER, 4000);
        TurnLeftTime(DRIVE_POWER, 500);
        DriveForwardTime(DRIVE_POWER, 4000);
        TurnRightTime(DRIVE_POWER, 500);
        DriveForwardTime(DRIVE_POWER, 4000);
        StopDriving();
        LowerArm();
    }

    private void InitializeHardware()
    {
        //initialize motors
        leftMotor = hardwareMap.dcMotor.get("Left_Motor");
        rightMotor = hardwareMap.dcMotor.get("Right_Motor");


        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // initialize servos
        bottomServo = hardwareMap.servo.get("Bottom_Servo");
        topServo = hardwareMap.servo.get("Top_Servo");
    }

    double DRIVE_POWER = 1.0;

    private void DriveForward(double power)
    {
        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }

    private void DriveForwardTime(double power, long time) throws InterruptedException
    {
        DriveForward(power);
        Thread.sleep(time);
    }

    private void StopDriving()
    {
        DriveForward(0);
    }

    private void StopDrivingTime(double power, long time) throws InterruptedException
    {
        StopDriving();
        Thread.sleep(time);
    }

    private void TurnLeft(double power)
    {
        leftMotor.setPower(-power);
        rightMotor.setPower(power);
    }

    private void TurnLeftTime(double power, long time) throws InterruptedException
    {
        TurnLeft(power);
        Thread.sleep(time);
    }

    private void TurnRight(double power)
    {
        TurnLeft(-power);
    }

    private void TurnRightTime(double power, long time) throws InterruptedException
    {
        TurnRight(power);
        Thread.sleep(time);
    }

    double ARM_POS_HIGH = 0.8;
    double ARM_POS_LOW = 0.2;

    private void RaiseArm()
    {
        topServo.setPosition(ARM_POS_HIGH);
        bottomServo.setPosition(ARM_POS_HIGH);
    }

    private void LowerArm()
    {
        topServo.setPosition(ARM_POS_LOW);
        bottomServo.setPosition(ARM_POS_LOW);
    }

}