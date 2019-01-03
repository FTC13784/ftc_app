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

@Autonomous(name="OurAutonomousSample", group="Linear Opmode")  // @Autonomous(...) is the other common choice
@Disabled
public class OurAutonomousSample extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    // DcMotor leftMotor = null;
    // DcMotor rightMotor = null;

    DcMotor leftMotorFront;
    DcMotor rightMotorFront;
    DcMotor leftMotorBack;
    DcMotor rightMotorBack;

    DcMotor intakeMotor;
    DcMotor armMotor;

    DcMotor hangingMotor;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

       InitializeHardware();

        waitForStart();
        runtime.reset();

        //put instructions in here
    }


    public void InitializeHardware()
    {
        leftMotorFront = hardwareMap.dcMotor.get("Left_Front");
        rightMotorFront = hardwareMap.dcMotor.get("Right_Front");
        leftMotorBack = hardwareMap.dcMotor.get("Left_Back");
        rightMotorBack = hardwareMap.dcMotor.get("Right_Back");

        leftMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotorFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotorBack.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeMotor = hardwareMap.dcMotor.get("Intake");
        armMotor = hardwareMap.dcMotor.get("Intake_Arm");

        hangingMotor = hardwareMap.dcMotor.get("Hanging_Motor");
    }

    double DRIVE_POWER = 1.0;

    public void DriveForward(double power)
    {
        leftMotorFront.setPower(power);
        leftMotorBack.setPower(power);
        rightMotorFront.setPower(power);
        rightMotorBack.setPower(power);
    }

    public void DriveForwardTime(double power, long time) throws InterruptedException
    {
        DriveForward(power);
        Thread.sleep(time);
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

    public void TurnLeft(double power)
    {
        leftMotorFront.setPower(-power);
        leftMotorBack.setPower(-power);
        rightMotorFront.setPower(power);
        rightMotorBack.setPower(power);
    }

    public void TurnLeftTime(double power, long time) throws InterruptedException
    {
        TurnLeft(power);
        Thread.sleep(time);
    }

    public void TurnRight(double power)
    {
        TurnLeft(-power);
    }

    public void TurnRightTime(double power, long time) throws InterruptedException
    {
        TurnRight(power);
        Thread.sleep(time);
    }


}