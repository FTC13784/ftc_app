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

@TeleOp(name="PracticeBotServos", group="Linear Opmode")  // @Autonomous(...) is the other common choice
//@Disabled
public class PracticeBotServos extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    // DcMotor leftMotor = null;
    // DcMotor rightMotor = null;

    DcMotor leftMotorFront;
    DcMotor rightMotorFront;
    DcMotor leftMotorBack;
    DcMotor rightMotorBack;

    Servo leftServo;
    Servo rightServo;
    double servoPosition = 0.0;


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftMotorFront = hardwareMap.dcMotor.get("Left_Motor");
        rightMotorFront = hardwareMap.dcMotor.get("Right_Motor");
        leftMotorBack = hardwareMap.dcMotor.get("Left_Motor_Back");
        rightMotorBack = hardwareMap.dcMotor.get("Right_Motor_Back");

        leftMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);

        leftServo = hardwareMap.servo.get("Left_Servo");
        rightServo = hardwareMap.servo.get("Right_Servo");

        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */

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

            while(gamepad1.dpad_left == true) {
                // servos open
                servoPosition = 0.8;
                leftServo.setPosition(servoPosition);
                rightServo.setPosition(servoPosition);
            }
            while(gamepad1.dpad_right == true) {
                // servos close
                servoPosition = 0.2;
                leftServo.setPosition(servoPosition);
                rightServo.setPosition(servoPosition);
            }

        }
    }
}