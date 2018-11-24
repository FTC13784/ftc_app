package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

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

@TeleOp(name="DayBot", group="Linear Opmode")  // @Autonomous(...) is the other common choice
@Disabled
public class DayBot extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    // DcMotor leftMotor = null;
    // DcMotor rightMotor = null;

    DcMotor leftMotorFront;
    DcMotor rightMotorFront;
    DcMotor leftMotorBack;
    DcMotor rightMotorBack;

    DcMotor rightArmMotor;
    DcMotor leftArmMotor;

    Servo bottomLeftServo;
    Servo topLeftServo;
    Servo bottomRightServo;
    Servo topRightServo;

    double servoPosition = 0;
    public final static double ARM_HOME = 0.2; // starting position for servo arms
    public final static double ARM_MIN_RANGE = 0.0;
    public final static double ARM_MAX_RANGE = 0.5;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        InitializeHardware();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            leftMotorFront.setPower(-gamepad1.left_stick_y);
            leftMotorBack.setPower(-gamepad1.left_stick_y);
            rightMotorFront.setPower(-gamepad1.right_stick_y);
            rightMotorBack.setPower(-gamepad1.right_stick_y);

           /*
           bottomLeftServo .setPosition(ARM_HOME);
            topLeftServo.setPosition(ARM_HOME);
            bottomRightServo.setPosition(ARM_HOME);
            topRightServo.setPosition(ARM_HOME);
            */

            bottomLeftServo.setPosition(ARM_HOME);
            topLeftServo.setPosition(ARM_HOME);
            bottomRightServo.setPosition(ARM_HOME);
            topRightServo.setPosition(ARM_HOME);

            //this piece makes it so that when init is pressed, the servo should move to one end of its range

            while (gamepad1.dpad_up == true) ; {
                leftArmMotor.setPower(1.0);
            }
            while (gamepad1.dpad_down == true) ; {
                leftArmMotor.setPower(-0.5);
            }


            while (gamepad1.y == true) ; {
                rightArmMotor.setPower(1.0);
            }
            while (gamepad1.a == true) ; {
                rightArmMotor.setPower(-0.5);
            }

            if (gamepad1.dpad_left) {
                // move to 0 degrees.
                servoPosition = ARM_MIN_RANGE;
            } else if (gamepad1.dpad_right) {
                // move to 144 degrees.
                servoPosition = ARM_MAX_RANGE;
            }
        }
    }

        public void InitializeHardware(){
            leftMotorFront = hardwareMap.dcMotor.get("Left_Front");
            rightMotorFront = hardwareMap.dcMotor.get("Right_Front");
            leftMotorBack = hardwareMap.dcMotor.get("Left_Back");
            rightMotorBack = hardwareMap.dcMotor.get("Right_Back");

            leftMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
            leftMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);
            rightMotorFront.setDirection(DcMotorSimple.Direction.FORWARD);
            rightMotorBack.setDirection(DcMotorSimple.Direction.FORWARD);

            rightArmMotor = hardwareMap.dcMotor.get("Right_Arm");
            leftArmMotor = hardwareMap.dcMotor.get("Left_Arm");

            rightArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            bottomLeftServo = hardwareMap.servo.get("Bottom_Left_Servo");
            topLeftServo = hardwareMap.servo.get("Top_Left_Servo");
            bottomRightServo = hardwareMap.servo.get("Bottom_Right_Servo");
            topRightServo = hardwareMap.servo.get("Top_Right_Servo");
        }
    }
