package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "OurBot", group = "TeleOp")
//@Disabled
public class OurBot extends LinearOpMode {

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
            telemetry.addData("trigger", "Trigger: " + gamepad1.right_trigger);
            telemetry.update();

            if ((gamepad1.left_bumper == false) && (gamepad1.right_bumper == false)) {
                setPower(leftDrive, .5 * (-gamepad1.left_stick_y));
                setPower(rightDrive, .5 * (-gamepad1.right_stick_y));
            }

            if (gamepad2.a == true) {
                setPower(intakeSpinner, 100);
            }
            if (gamepad2.b == true) {
                setPower(intakeSpinner, -100);
            } //intakeSpinner.setPower(0);
            if ((gamepad2.a == false) && (gamepad2.b == false)) {
                setPower(intakeSpinner, 0);
            }

            setPower(intakeLift, gamepad2.right_trigger);
            setPower(intakeLift, -gamepad2.right_trigger);

            //hanging motor on if gamepad2 dpad up arrow pressed, down if down pressed
            if (gamepad2.dpad_up == true) {
                setPower(hangingMotor, 50);
            }
            if (gamepad2.dpad_down == true) {
                setPower(hangingMotor, -50);
            }
            if ((gamepad2.dpad_up == false) && (gamepad2.dpad_down == false)) {
                setPower(hangingMotor, 0);
            }

            /*
            //set hanging to exact height
            if (gamepad2.b) {
                setHang();
            }
            */

            // set hook servo position up when gamepad2 x is pressed, return when y pressed
            if (gamepad2.x == true) {
                hook.setPosition(0);
            }
            if (gamepad2.y == true) {
                hook.setPosition(0.5);
            }
        }
    }

    public void InitializeHardware() {
        // initialize drive motors
        leftDrive[0] = hardwareMap.dcMotor.get("Left_Front");
        leftDrive[1] = hardwareMap.dcMotor.get("Left_Back");

        rightDrive[0] = hardwareMap.dcMotor.get("Right_Front");
        rightDrive[1] = hardwareMap.dcMotor.get("Right_Back");

        allDrive[0] = hardwareMap.dcMotor.get("Left_Front");
        allDrive[1] = hardwareMap.dcMotor.get("Left_Back");
        allDrive[2] = hardwareMap.dcMotor.get("Right_Front");
        allDrive[3] = hardwareMap.dcMotor.get("Right_Back");

        setDirection(leftDrive, DcMotorSimple.Direction.FORWARD);
        setDirection(rightDrive, DcMotorSimple.Direction.REVERSE);

        setMode(allDrive, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // initialize intake spinner & intake lift motors
        intakeSpinner[0] = hardwareMap.dcMotor.get("Intake_Spinner");
        intakeLift[0] = hardwareMap.dcMotor.get("Intake_Lift");

        setDirection(intakeSpinner, DcMotorSimple.Direction.FORWARD);
        setDirection(intakeLift, DcMotorSimple.Direction.REVERSE);

        setMode(intakeSpinner, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setMode(hangingMotor, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // initialize hanging motor
        hangingMotor[0] = hardwareMap.dcMotor.get("Hanging");
        setDirection(hangingMotor, DcMotorSimple.Direction.REVERSE);

        // initialize hook servo
        hook = hardwareMap.servo.get("Hook");
    }

    private void setMode(DcMotor[] motors, DcMotor.RunMode mode) {
        for (DcMotor motor : motors) {
            motor.setMode(mode);
        }
    }

    private void setDistance(DcMotor[] motors, double distance) {
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

    private void setHang() {
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

