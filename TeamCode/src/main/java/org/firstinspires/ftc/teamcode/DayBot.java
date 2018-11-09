package org.firstinspires.ftc.teamcode;

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
//@Disabled
public class DayBot extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    // DcMotor leftMotor = null;
    // DcMotor rightMotor = null;

    DcMotor leftMotorFront;
    DcMotor rightMotorFront;
    DcMotor leftMotorBack;
    DcMotor rightMotorBack;

    DcMotor armMotor;

    Servo bottomServo;
    Servo topServo;

    double servoPosition = 0;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftMotorFront = hardwareMap.dcMotor.get("Left_Front");
        rightMotorFront = hardwareMap.dcMotor.get("Right_Front");
        leftMotorBack = hardwareMap.dcMotor.get("Left_Back");
        rightMotorBack = hardwareMap.dcMotor.get("Right_Back");

        leftMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);

        armMotor = hardwareMap.dcMotor.get("Arm_Motor");

        bottomServo = hardwareMap.servo.get("Bottom_Servo");
        topServo = hardwareMap.servo.get("Top_Servo");
        bottomServo.setDirection(Servo.Direction.REVERSE);
        topServo.setDirection(Servo.Direction.REVERSE);

        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */
        // leftMotor  = hardwareMap.dcMotor.get("left_drive");
        // rightMotor = hardwareMap.dcMotor.get("right_drive");

        // eg: Set the drive motor directions:
        // "Reverse" the motor that runs backwards when connected directly to the battery
        // leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        // rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            leftMotorFront.setPower(-gamepad1.left_trigger);
            leftMotorBack.setPower(-gamepad1.left_trigger);
            rightMotorFront.setPower(-gamepad1.right_trigger);
            rightMotorBack.setPower(-gamepad1.right_trigger);

            topServo.setPosition(servoPosition);
            bottomServo.setPosition(servoPosition);
            //this piece makes it so that when init is pressed, the servo should move to one end of its range

            while (gamepad1.dpad_up == true) ; {
                armMotor.setPower(1.0);
            }
            while (gamepad1.dpad_down == true) ; {
                armMotor.setPower(-1.0);
            }


            if (gamepad1.dpad_left) {
                // move to 0 degrees.
                servoPosition = 0;
            } else if (gamepad1.dpad_right) {
                // move to 90 degrees.
                servoPosition = 0.5;
            }


            }
        }
    }
