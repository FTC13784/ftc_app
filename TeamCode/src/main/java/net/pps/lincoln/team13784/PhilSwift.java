package net.pps.lincoln.team13784;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class PhilSwift {

    DcMotor[] leftDrive = new DcMotor[2];
    DcMotor[] rightDrive = new DcMotor[2];
    DcMotor[] allDrive = new DcMotor[4];


    DcMotor[] intakeSpinner = new DcMotor[1];
    DcMotor[] intakeLift = new DcMotor[1];

    DcMotor[] hangingMotor = new DcMotor[1];

    Servo hook;

    LinearOpMode opMode;

    HardwareMap hardwareMap;


    double ticksPerIn = 3;

    Telemetry telemetry;

    public PhilSwift(LinearOpMode opMode, double wheelRadius, double ticksPerRev) {
        this.opMode = opMode;
        hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;
        InitializeHardware();
        ticksPerIn = ticksPerRev / (wheelRadius * Math.PI * 2);
    }

    private void InitializeHardware() {
        // initialize drive motors
        leftDrive[0] = hardwareMap.dcMotor.get("Left_Front");
        leftDrive[1] = hardwareMap.dcMotor.get("Left_Back");
        rightDrive[0] = hardwareMap.dcMotor.get("Right_Front");
        rightDrive[1] = hardwareMap.dcMotor.get("Right_Back");

        setDirection(leftDrive, DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive, DcMotorSimple.Direction.FORWARD);

        allDrive[0] = hardwareMap.dcMotor.get("Left_Front");
        allDrive[1] = hardwareMap.dcMotor.get("Left_Back");
        allDrive[2] = hardwareMap.dcMotor.get("Right_Front");
        allDrive[3] = hardwareMap.dcMotor.get("Right_Back");

        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);

        // initialize intake spinner & intake lift motors
        intakeSpinner[0] = hardwareMap.dcMotor.get("Intake_Spinner");
        intakeLift[0] = hardwareMap.dcMotor.get("Intake_Lift");

        setDirection(intakeSpinner, DcMotorSimple.Direction.FORWARD);
        setDirection(intakeLift, DcMotorSimple.Direction.REVERSE);

        // initialize hanging motor
        hangingMotor[0] = hardwareMap.dcMotor.get("Hanging");
        setDirection(hangingMotor, DcMotorSimple.Direction.REVERSE);

        // initialize hook servo
        hook = hardwareMap.servo.get("Hook");
    }

    private void setDirection(DcMotor[] motors, DcMotorSimple.Direction direction) {
        for (DcMotor motor : motors) {
            motor.setDirection(direction);
        }
    }

    private void setMode(DcMotor[] motors, DcMotor.RunMode mode) {
        for (DcMotor motor : motors) {
            motor.setMode(mode);
        }
    }

    private void setDistance(DcMotor[] motors, double distance) {
        double target = distance * ticksPerIn;
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

    private void setPower(DcMotor[] motors, double power) {
        for (DcMotor motor : motors) {
            motor.setPower(power);
        }
    }

    /**
     * drive distance specified
     * positive distance is forward, negative distance is backward
     *
     * @param distance distance in inches
     * @param speed    speed btwn 0 and 1
     */
    public void drive(double distance, double speed) {

        // Reset encoders
        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // set target position
        setDistance(allDrive, distance);

        // Set to RUN_TO_POSITION mode
        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("PhilSwiftDrive", "about to start");
        telemetry.update();

        // Set drive power
        setPower(allDrive, speed);

        telemetry.addData("PhilSwiftDrive", "started");
        telemetry.update();

        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            //wait until target position in reached
        }

        telemetry.addData("PhilSwiftDrive", "finished, stopping");
        telemetry.update();
        // Stop and change modes back to normal
        setPower(allDrive, 0);

        telemetry.addData("PhilSwiftDrive", "stopped");
        telemetry.update();

        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }


    /**
     * turn in place degrees specified
     *
     * @param degrees degrees between -360 and 360
     * @param speed   speed btwn 0 and 1
     */
    public void turn(int degrees, double speed) {

    }


    /**
     * lowers robot down from hanging mechanism, assumes that robot is already hanging when this is called
     *
     * @param distance distance in inches
     * @param speed    speed btwn 0 and 1
     */
    public void lowerMe(double distance, double speed) {

    }


    /**
     * sets hanging mechanism to hanging height, assumes that robot is not haging when this called
     */
    public void setHang() {
        int hangHeight = 3;

        setMode(hangingMotor, DcMotor.RunMode.RUN_USING_ENCODER);

        // Reset encoders
        setMode(hangingMotor, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // set target position
        setDistance(hangingMotor, hangHeight);

        // Set to RUN_TO_POSITION mode
        setMode(hangingMotor, DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("PhilSwiftDrive", "about to start");
        telemetry.update();

        // Set drive power
        setPower(hangingMotor, 1);

        telemetry.addData("PhilSwiftDrive", "started");
        telemetry.update();

        while (isBusy(hangingMotor) && opMode.opModeIsActive()) {
            //wait until target position in reached
        }

        telemetry.addData("PhilSwiftDrive", "finished, stopping");
        telemetry.update();
        // Stop and change modes back to normal
        setPower(hangingMotor, 0);

        telemetry.addData("PhilSwiftDrive", "stopped");
        telemetry.update();
    }
}
