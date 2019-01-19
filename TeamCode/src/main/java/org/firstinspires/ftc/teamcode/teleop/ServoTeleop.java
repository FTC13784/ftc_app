package org.firstinspires.ftc.teamcode.teleop;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Hook Move", group = "TeleOp")
//@Disabled
public class ServoTeleop extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    DcMotor[] leftDrive = new DcMotor[2];
    DcMotor[] rightDrive = new DcMotor[2];
    DcMotor[] allDrive = new DcMotor[4];

    DcMotor[] intakeSpinner = new DcMotor[1];
    DcMotor[] intakeLift = new DcMotor[1];
    DcMotor[] hangingMotor = new DcMotor[1];
    Servo hook;

    LinearOpMode opMode;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        InitializeHardware();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Hook:", hook.getPosition());
            telemetry.update();

            if (gamepad2.y) {
                hook.setPosition(0);
            }
            if (gamepad2.b) {
                hook.setPosition(0.25);
            }
            //to hang, somewhere in between here. 0.4?
            if (gamepad2.a) {
                hook.setPosition(0.5);
            }
            if (gamepad2.b) {
                hook.setPosition(0.75);
            }
            if (gamepad2.dpad_up) {
                hook.setPosition(1);
            }
        }
    }

    private void InitializeHardware() {
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

    private void setMode(DcMotor[] motors, DcMotor.RunMode mode) {
        for (DcMotor motor : motors) motor.setMode(mode);
    }

    private void setWheelDistance(DcMotor[] motors, double distance) {
        double ticksPerIn = 1;
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

    private void setDirection(DcMotor[] motors, DcMotorSimple.Direction direction) {
        for (DcMotor motor : motors) {
            motor.setDirection(direction);
        }
    }

    private void setHangDistance(DcMotor[] motors, double distanceFromGround) {
        double hangingTicksPerIn = 1;
        double target = distanceFromGround * hangingTicksPerIn;
        for (DcMotor motor : motors) {
            motor.setTargetPosition((int) Math.round(target));
        }
    }

    private void setHang() {
        int hangHeight = 3;
        //at the lowest, the hanging mechanism is at 17.5 in. it needs to go up to 23 in to hang.

        setMode(hangingMotor, DcMotor.RunMode.RUN_USING_ENCODER);

        // Reset encoders
        setMode(hangingMotor, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // set target position
        setWheelDistance(hangingMotor, hangHeight);

        // Set to RUN_TO_POSITION mode
        setMode(hangingMotor, DcMotor.RunMode.RUN_TO_POSITION);

        // Set drive power
        setPower(hangingMotor, 1);

        while (isBusy(hangingMotor) && opMode.opModeIsActive()) {
            //wait until target position in reached
        }

        // Stop and change modes back to normal
        setPower(hangingMotor, 0);
    }

    public void lowerHanging() {
        setPower(hangingMotor, -1);
        sleep(1300);
        setPower(hangingMotor, 0);
    }

    public void setUpHang() {
        //assumes the hanging mechanism is all the way down and the servo is all the way down.
        setPower(hangingMotor, 1);
        sleep(1300);
        setPower(hangingMotor, 1);

        if ((hangingMotor[0].getPower()) == 0 && opModeIsActive()) {
            hook.setPosition(1);
        }
    }

    public void hang() {
        //assumes the hanging mechanism is up & the hook is up in the lander's latch & the h
        setPower(hangingMotor, 1);
        sleep(1300);
        setPower(hangingMotor, 1);

        if ((hangingMotor[0].getPower()) == 0 && opModeIsActive()) {
            hook.setPosition(1);
        }
    }

}