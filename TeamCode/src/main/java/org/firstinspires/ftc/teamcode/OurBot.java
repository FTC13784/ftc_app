package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

//updated 8 nov
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

@TeleOp(name="OurBot", group="Linear Opmode")  // @Autonomous(...) is the other common choice
@Disabled
public class OurBot extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    // DcMotor leftMotor = null;
    // DcMotor rightMotor = null;

    DcMotor leftMotorFront;
    DcMotor rightMotorFront;
    DcMotor leftMotorBack;
    DcMotor rightMotorBack;

    DcMotor intakeMotor;
    DcMotor armMotor;

    DcMotor hangingMotor;


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

            leftMotorFront.setPower(-gamepad1.left_stick_y*0.7);
            leftMotorBack.setPower(-gamepad1.left_stick_y*0.7);
            rightMotorFront.setPower(-gamepad1.right_stick_y*0.7);
            rightMotorBack.setPower(-gamepad1.right_stick_y*0.7);

           /* intakeMotor.setPower(-gamepad1.right_trigger);
            armMotor.setPower(-gamepad1.left_trigger);
            */

            while (gamepad1.dpad_up);
            {
                hangingMotor.setPower(0.5);
            }
            while(gamepad1.dpad_down); {
                hangingMotor.setPower(-0.5);
            }
        }

     }

    public void InitializeHardware()
    {
        leftMotorFront = hardwareMap.dcMotor.get("Left_Front");
        rightMotorFront = hardwareMap.dcMotor.get("Right_Front");
        leftMotorBack = hardwareMap.dcMotor.get("Left_Back");
        rightMotorBack = hardwareMap.dcMotor.get("Right_Back");

        leftMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotorFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotorBack.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeMotor = hardwareMap.dcMotor.get("Intake");
        armMotor = hardwareMap.dcMotor.get("Intake_Arm");

        hangingMotor = hardwareMap.dcMotor.get("Hanging_Motor");

    }
}
