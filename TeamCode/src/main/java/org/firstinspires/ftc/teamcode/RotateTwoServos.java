package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

@Autonomous(name="RotateTwoServos", group="Linear Opmode")  // @Autonomous(...) is the other common choice
//@Disabled
public class RotateTwoServos extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    // DcMotor leftMotor = null;
    // DcMotor rightMotor = null;

    Servo leftServo;
    Servo rightServo;
    double servoPosition = 0.0;

    /** note from tutorial
     * servos have a range of 0-180 degrees
     * represented by range 0-1
     * 0 is 0 degrees
     * 1 is 180 degrees
     */

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftServo = hardwareMap.servo.get("Left_Servo");
        rightServo = hardwareMap.servo.get("Right_Servo");

        //make sure the name is quotations is the same name used in the robot configuration
        leftServo.setPosition(servoPosition);
        rightServo.setPosition(servoPosition);
        //this piece makes it so that when init is pressed, the servo should move to one end of its range

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        servoPosition = 0.5;
        leftServo.setPosition(servoPosition);
        rightServo.setPosition(servoPosition);
        sleep(2000); //tells robot to "sleep" 2 seconds before executing the next task
        // this piece makes it so that once the start button is pressed, the servo should move to the middle of its range
        //then wait 2 seconds, then

        servoPosition = 1.0;
        leftServo.setPosition(servoPosition);
        rightServo.setPosition(servoPosition);
        //this piece makes it move to the other end of its range
    }
}