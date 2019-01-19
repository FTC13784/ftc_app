package net.pps.lincoln.team13784;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.RobotLog;

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

    double ticksPerInWheel;

    double ticksPerInHang;

    double wheelRadius = 2;

    double hangGearDiameter = 0.75;

    Telemetry telemetry;

    double ticksPerWheelRev = 1120;

    double ticksPerHangRev = 1680;

    public PhilSwift(LinearOpMode opMode) {
        this.opMode = opMode;
        hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;
        InitializeHardware();
        ticksPerInWheel = ticksPerWheelRev / (wheelRadius * Math.PI * 2);
        ticksPerInHang = ticksPerHangRev / (hangGearDiameter * Math.PI);
        telemetry.addData("LF:", allDrive[0].getCurrentPosition());
        telemetry.addData("LB:", allDrive[1].getCurrentPosition());
        telemetry.addData("RF:", allDrive[2].getCurrentPosition());
        telemetry.addData("RB:", allDrive[3].getCurrentPosition());
        telemetry.update();
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

    private void setWheelTargetPosition(DcMotor[] motors, double distance) {
        double target = distance * ticksPerInWheel;
        for (DcMotor motor : motors) {

            double targetPosition = Math.round(target);
            motor.setTargetPosition((int) targetPosition);
        }
    }

    private void setHangDistance(DcMotor[] motors, double distance) {
        double target = distance * ticksPerInHang;
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

    public void StopDriving(){
        setPower(allDrive, 0);
    }

    /**
     * drive distance specified
     * positive distance is forward, negative distance is backward
     *
     * @param distance distance in inches
     * @param speed    speed btwn -1 and 1
     */
    public void drive(double distance, double speed) {

        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // set target position
        setWheelTargetPosition(allDrive, distance);

        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

        // Set drive power
        setPower(allDrive, speed);

        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            //wait until target position in reached
            RobotLog.d("LF: "  + allDrive[0].getCurrentPosition());
            RobotLog.d("LB: " + allDrive[1].getCurrentPosition());
            RobotLog.d("RF: " + allDrive[2].getCurrentPosition());
            RobotLog.d("RB: " + allDrive[3].getCurrentPosition());
        }
        StopDriving();
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
     * sets hanging mechanism to hanging height, assumes that robot is not hanging when this called
     */
    public void setHang() {
        int hangHeight = 3;

        setMode(hangingMotor, DcMotor.RunMode.RUN_USING_ENCODER);

        setMode(hangingMotor, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setHangDistance(hangingMotor, hangHeight);

        setMode(hangingMotor, DcMotor.RunMode.RUN_TO_POSITION);

        setPower(hangingMotor, 1);

        while (isBusy(hangingMotor) && opMode.opModeIsActive()) {
            //wait until target position in reached
            }

        // Stop and change modes back to normal
        setPower(hangingMotor, 0);

    }
}
