package org.firstinspires.ftc.teamcode.LightningAutonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class OurBot {

    public ElapsedTime runtime = new ElapsedTime();

    //declare motors
    DcMotor leftMotorFront;
    DcMotor rightMotorFront;
    DcMotor leftMotorBack;
    DcMotor rightMotorBack;

    DcMotor hangingMotor;
    Servo hook;

    DcMotor intakeSpinner;
    DcMotor intakeLift;

    LinearOpMode opMode;
    HardwareMap hardwareMap;
    Telemetry telemetry;

    //double ticksPerWheelRev = 1120;

    public OurBot(LinearOpMode opMode) {
        InitializeHardware();
        this.opMode = opMode;
        hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;
    }

    private void InitializeHardware() {
        leftMotorFront = hardwareMap.dcMotor.get("Left_Front"); //neverest40
        leftMotorBack = hardwareMap.dcMotor.get("Left_Back"); //neverest40
        rightMotorFront = hardwareMap.dcMotor.get("Right_Front"); //neverest40
        rightMotorBack = hardwareMap.dcMotor.get("Right_Back"); //neverest40

        leftMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotorFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotorBack.setDirection(DcMotorSimple.Direction.FORWARD);

        hangingMotor = hardwareMap.dcMotor.get("Hanging"); //neverest60
        hook = hardwareMap.servo.get("Hook"); //servo

        intakeSpinner = hardwareMap.dcMotor.get("Intake_Spinner"); //neverest60
        intakeLift = hardwareMap.dcMotor.get("Intake_Lift"); //tetrix motor

        intakeSpinner.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeLift.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void Drive(double power) {
        leftMotorFront.setPower(power);
        leftMotorBack.setPower(power);
        rightMotorFront.setPower(power);
        rightMotorBack.setPower(power);
    }

    public void DriveDistance(double power, int distance) {
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
        Drive(power);

        while(leftMotorFront.isBusy() && rightMotorFront.isBusy() && leftMotorBack.isBusy() && rightMotorBack.isBusy() && opMode.opModeIsActive()) {
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

        Drive(power);

        while (Math.abs(leftMotorFront.getCurrentPosition()) < (distance) && opMode.opModeIsActive()) {
            //wait until target position is reached
        }

        StopDriving();
    }

    public void StopDriving() {
        Drive(0);
    }

    public void StopDrivingTime(double power, long time) throws InterruptedException {
        StopDriving();
        Thread.sleep(time);
    }

    public void deploy() {
        hangingMotor.setPower(1);
        this.opMode.sleep(1300);
        hangingMotor.setPower(0);

        if ((hangingMotor.getPower()) == 0 && opMode.opModeIsActive()) {
            hook.setPosition(0.5);
        }
    }

    public void lowerHanging() {
        hangingMotor.setPower(-1);
        this.opMode.sleep(1300);
        hangingMotor.setPower(0);
    }

    public void hang() {
        //assumes the hanging mechanism is all the way down and the servo is all the way down.
        hangingMotor.setPower(1);
        this.opMode.sleep(1300);
        hangingMotor.setPower(0);

        if ((hangingMotor.getPower()) == 0 && opMode.opModeIsActive()) {
            hook.setPosition(1);
        }
    }

}