package org.firstinspires.ftc.teamcode.current;

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
 * <p>
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name = "Hook Up", group = "Linear Opmode")
@Disabled
public class HookUp extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    //declare motors
    DcMotor leftMotorFront;
    DcMotor rightMotorFront;
    DcMotor leftMotorBack;
    DcMotor rightMotorBack;

    DcMotor hangingMotor;

    Servo hook;

    double ticksPerWheelRev = 1120;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        InitializeHardware();

        waitForStart();
        runtime.reset();

        hookUp();
    }

    public void InitializeHardware() {
        leftMotorFront = hardwareMap.dcMotor.get("Left_Front");
        leftMotorBack = hardwareMap.dcMotor.get("Left_Back");
        rightMotorFront = hardwareMap.dcMotor.get("Right_Front");
        rightMotorBack = hardwareMap.dcMotor.get("Right_Back");

        leftMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotorFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotorBack.setDirection(DcMotorSimple.Direction.FORWARD);

        hangingMotor = hardwareMap.dcMotor.get("Hanging"); //neverest60

        // initialize hook servo
        hook = hardwareMap.servo.get("Hook");
    }

    public void DriveForward(double power) {
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
        leftMotorFront.setTargetPosition(distance);
        rightMotorFront.setTargetPosition(distance);
        leftMotorBack.setTargetPosition(distance);
        rightMotorBack.setTargetPosition(distance);

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
        StopDriving();
        leftMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    public void oneEncoderDrive(double power, int distance) {

        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftMotorFront.setTargetPosition(distance);

        DriveForward(power);

        while (Math.abs(leftMotorFront.getCurrentPosition()) < (distance) && opModeIsActive()) {
            //wait until target position is reached
        }

        StopDriving();
    }

    public void StopDriving() {
        DriveForward(0);
    }

    public void StopDrivingTime(double power, long time) throws InterruptedException {
        StopDriving();
        Thread.sleep(time);
    }

    public void deploy() {
        hangingMotor.setPower(1);
        sleep(1300);
        hangingMotor.setPower(0);

        if ((hangingMotor.getPower()) == 0 && opModeIsActive()) {
            hook.setPosition(0.5);
        }
    }

    public void lowerHanging() {
        hangingMotor.setPower(-1);
        sleep(1300);
        hangingMotor.setPower(0);
    }

    public void hang() {
        //assumes the hanging mechanism is all the way down and the servo is all the way down.
        hangingMotor.setPower(1);
        sleep(1300);
        hangingMotor.setPower(0);

        if ((hangingMotor.getPower()) == 0 && opModeIsActive()) {
            hook.setPosition(1);
        }
    }

    public void hookUp() {
        //assumes the servo is all the way down.
        hook.setPosition(1);
    }


}