package org.firstinspires.ftc.teamcode.current;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Deploy Test", group = "Linear Opmode")
//@Disabled
public class Deploy extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    //declare motors
    DcMotor leftMotorFront;
    DcMotor rightMotorFront;
    DcMotor leftMotorBack;
    DcMotor rightMotorBack;

    DcMotor hangingMotor;

    DcMotor intakeLift;
    DcMotor intakeSpinner;

    Servo hook;

    double ticksPerWheelRev = 1120;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        InitializeHardware();

        waitForStart();
        runtime.reset();

        hangingMotor.setPower(1);
        sleep(2000);
        hangingMotor.setPower(0);

        if ((hangingMotor.getPower()) == 0 && opModeIsActive()) {
            hook.setPosition(0);
        }
        //to output marker, you don't need to put intake mehanism down, just turn it on to go out
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

        // initialize intake spinner & intake lift motors
        intakeSpinner = hardwareMap.dcMotor.get("Intake_Spinner"); //neverest60
        intakeLift = hardwareMap.dcMotor.get("Intake_Lift"); //tetrix motor
        intakeLift.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeSpinner.setDirection(DcMotorSimple.Direction.FORWARD);

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

        while(leftMotorFront.isBusy() && rightMotorFront.isBusy() && leftMotorBack.isBusy() && rightMotorBack.isBusy() && opModeIsActive()) {
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

    private void StopDriving() {
        DriveForward(0);
    }

    public void StopDrivingTime(double power, long time) throws InterruptedException {
        StopDriving();
        Thread.sleep(time);
    }

    /*
    private void deploy() {
        hangingMotor.setPower(1);
        sleep(1300);
        hangingMotor.setPower(0);

        if ((hangingMotor.getPower()) == 0 && opModeIsActive()) {
            hook.setPosition(0);
        }
        //to output marker, you don't need to put intake mehanism down, just turn it on to go out
    }
    */

    private void lowerHanging() {
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


}