package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "MayaNewTeleop", group = "TeleOp")
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
            telemetry.addData("LF:", allDrive[0].getCurrentPosition());
            telemetry.addData("LB:", allDrive[1].getCurrentPosition());
            telemetry.addData("RF:", allDrive[2].getCurrentPosition());
            telemetry.addData("RB:", allDrive[3].getCurrentPosition());
            telemetry.update();

            // set drive power
            setPower(leftDrive, gamepad1.left_stick_y*0.7);
            setPower(rightDrive, gamepad1.right_stick_y*0.7);

            // built in "dead zone" on joystick
            if(Math.abs(gamepad1.left_stick_y) < 0.05){
                gamepad1.left_stick_y =0;
            }
            if(Math.abs(gamepad1.right_stick_y) < 0.05){
                gamepad1.right_stick_y =0;
            }

            // when a on gamepad2 pressed, intake mechanism takes in minerals.
            // when b on gamepad2 pressed, intake mechanism outputs minerals.
            if(gamepad2.a){
                setPower(intakeSpinner, 100);
            }
            if(gamepad2.b){
                setPower(intakeSpinner, -100);
            }
            if(!gamepad2.a && !gamepad2.b){
                setPower(intakeSpinner, 0);
            }

            // when right trigger on gamepad2 is pressed, the intake mechanism lifts up.
            // when left trigger on gamepad2 is pressed, the intake mechanism lowers down. (in theory)
            setPower(intakeLift, gamepad2.right_trigger);
            setPower(intakeLift, -gamepad2.left_trigger);

            // when dpad up arrow is pressed on the gamepad2, hanging mechanism goes up.
            // when dpad down arrow is pressed on gamepad2, hanging mechanism goes down.
            if(gamepad2.dpad_up){
                setPower(hangingMotor, 50);
            }
            if(gamepad2.dpad_down){
                setPower(hangingMotor, -50);
            }
            if(!gamepad2.dpad_up && !gamepad2.dpad_down){
                setPower(hangingMotor, 0);
            }

            // set hook servo position up when gamepad2 x is pressed, return when y pressed
            if (gamepad2.x) {
                hook.setPosition(0);
            }
            if (gamepad2.y) {
                hook.setPosition(0.5);
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
}