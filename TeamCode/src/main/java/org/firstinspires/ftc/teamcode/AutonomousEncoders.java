package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

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

@Autonomous(name="AutonomousEncoders", group="Linear Opmode")  // @Autonomous(...) is the other common choice
@Disabled
public class AutonomousEncoders extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    //declare motors
    DcMotor leftMotorFront;
    DcMotor rightMotorFront;
    DcMotor leftMotorBack;
    DcMotor rightMotorBack;

    double ticksPerWheelRev = 1120;
    double wheelRadius = 2; //inches
    double ticksPerInWheel = (1 / (wheelRadius * Math.PI * 2)) * ticksPerWheelRev;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        InitializeHardware();

        waitForStart();
        runtime.reset();

        //put instructions in here
        DriveForwardDistance(1, 12);
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

    public void DriveForwardDistance(double power, double distance)
    {
        // Reset encoders
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // set target position
        //RobotLog.d("Setting target postion:" + (int) (distance * ticksPerInWheel));
        leftMotorFront.setTargetPosition((int) ((distance * ticksPerInWheel)));
        rightMotorFront.setTargetPosition((int) ((distance * ticksPerInWheel)));
        leftMotorBack.setTargetPosition((int) ((distance * ticksPerInWheel)));
        rightMotorBack.setTargetPosition((int) ((distance * ticksPerInWheel)));

        // Set to RUN_TO_POSITION mode
        leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set drive power
        DriveForward(power);

        while(leftMotorFront.isBusy() && rightMotorFront.isBusy() && leftMotorBack.isBusy() && rightMotorBack.isBusy())
        {
            // Wait until target position is reached
        }

        // Stop and change modes back to normal
        StopDriving();
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

    /*
    public void TurnLeft(double power)
    {
        leftMotorFront.setPower(-power);
        leftMotorBack.setPower(-power);
        rightMotorFront.setPower(power);
        rightMotorBack.setPower(power);
    }

    public void TurnLeftDistance(double power, int distance)
    {
        // Reset encoders
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // set target position
        leftMotorFront.setTargetPosition(-distance);
        rightMotorFront.setTargetPosition(distance);
        leftMotorBack.setTargetPosition(-distance);
        rightMotorBack.setTargetPosition(distance);

        // Set to RUN_TO_POSITION mode
        leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set drive power
        TurnLeft(power);

        while(leftMotorFront.isBusy() && rightMotorFront.isBusy() && leftMotorBack.isBusy() && rightMotorBack.isBusy())
        {
            // Wait until target position is reached
        }

        // Stop and change modes back to normal
        StopDriving();
        leftMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void TurnRight(double power)
    {
        TurnLeft(-power);
    }

    public void TurnRightDistance(double power, int distance)
    {
        // Reset encoders
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // set target position
        leftMotorFront.setTargetPosition(distance);
        rightMotorFront.setTargetPosition(-distance);
        leftMotorBack.setTargetPosition(distance);
        rightMotorBack.setTargetPosition(-distance);

        // Set to RUN_TO_POSITION mode
        leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set drive power
        TurnLeft(power);

        while(leftMotorFront.isBusy() && rightMotorFront.isBusy() && leftMotorBack.isBusy() && rightMotorBack.isBusy())
        {
            // Wait until target position is reached
        }

        // Stop and change modes back to normal
        StopDriving();
        leftMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    */
}