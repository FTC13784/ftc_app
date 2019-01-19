package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
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

@Autonomous(name="Array Test", group="Linear Opmode")  // @Autonomous(...) is the other common choice
@Disabled
public class ArrayTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    //declare motors
    DcMotor[] leftDrive = new DcMotor[2];
    DcMotor[] rightDrive = new DcMotor[2];
    DcMotor[] allDrive = new DcMotor[4];


    DcMotor[] intakeSpinner = new DcMotor[1];
    DcMotor[] intakeLift = new DcMotor[1];

    DcMotor[] hangingMotor = new DcMotor[1];

    Servo hook;

    LinearOpMode opMode;
    HardwareMap hardwareMap = opMode.hardwareMap;
    /*
    this.opMode = opMode;
    hardwareMap = opMode.hardwareMap;
    telemetry = opMode.telemetry;
    */

    double ticksPerWheelRev = 1120;
    double ticksPerHangRev = 1680;
    double wheelRadius = 2;
    double hangGearDiameter = 0.75; //inches

    double ticksPerInHang = ticksPerHangRev / (hangGearDiameter * Math.PI);
    double ticksPerInWheel = ticksPerWheelRev / (wheelRadius * Math.PI * 2);

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


    private void InitializeHardware()
    {
        // initialize drive motors
        leftDrive[0] = hardwareMap.dcMotor.get("Left_Front");
        leftDrive[1] = hardwareMap.dcMotor.get("Left_Back");

        rightDrive[0] = hardwareMap.dcMotor.get("Right_Front");
        rightDrive[1] = hardwareMap.dcMotor.get("Right_Back");

        allDrive[0] = hardwareMap.dcMotor.get("Left_Front"); //neverest40
        allDrive[1] = hardwareMap.dcMotor.get("Left_Back"); //neverest40
        allDrive[2] = hardwareMap.dcMotor.get("Right_Front"); //neverest40
        allDrive[3] = hardwareMap.dcMotor.get("Right_Back"); //neverest40

        setDirection(leftDrive, DcMotorSimple.Direction.FORWARD);
        setDirection(rightDrive, DcMotorSimple.Direction.REVERSE);

        // initialize intake spinner & intake lift motors
        intakeSpinner[0] = hardwareMap.dcMotor.get("Intake_Spinner"); //neverest60
        intakeLift[0] = hardwareMap.dcMotor.get("Intake_Lift"); //tetrix motor

        setDirection(intakeSpinner, DcMotorSimple.Direction.FORWARD);
        setDirection(intakeLift, DcMotorSimple.Direction.REVERSE);

        // initialize hanging motor
        hangingMotor[0] = hardwareMap.dcMotor.get("Hanging"); //neverest60
        setDirection(hangingMotor, DcMotorSimple.Direction.FORWARD);

        // initialize hook servo
        hook = hardwareMap.servo.get("Hook");
    }

    private void setDirection(DcMotor[] motors, DcMotorSimple.Direction direction){
        for (DcMotor motor : motors) {
            motor.setDirection(direction);
        }
    }

    private void setMode(DcMotor[] motors, DcMotor.RunMode mode) {
        for (DcMotor motor : motors) {
            motor.setMode(mode);
        }
    }

    private void setPower(DcMotor[] motors, double power) {
        for (DcMotor motor : motors) {
            motor.setPower(power);
        }
    }

    private void setWheelTargetPosition(DcMotor[] motors, double distance) {
        double target = distance * ticksPerInWheel;
        for (DcMotor motor : motors) {
            motor.setTargetPosition((int) Math.round(target));
        }
    }

    private boolean isBusy(DcMotor[] motors) {
        for (DcMotor motor : motors) {
            if (motor.isBusy()) {
                return true;
            }
        }
        return false;
    }

    private void StopDriving(){
        setPower(allDrive, 0);
    }

    private void DriveForward(double power) {
        setPower(allDrive, 1.0);
    }

    private void DriveForwardDistance(double power, double distance)
    {
        // Reset encoders
        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // set target position
        setWheelTargetPosition(allDrive, ((int) (distance * ticksPerInWheel)));

        // Set to RUN_TO_POSITION mode
        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

        // Set drive power
        DriveForward(power);

        while(isBusy(allDrive) && opMode.opModeIsActive())
        {
            // Wait until target position is reached
        }

        // Stop and change modes back to normal
        StopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }
}