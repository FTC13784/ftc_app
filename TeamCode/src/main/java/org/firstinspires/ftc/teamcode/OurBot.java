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
 * <p>
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name = "OurBot", group = "Linear Opmode")  // @Autonomous(...) is the other common choice
//@Disabled
public class OurBot extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    // DcMotor leftMotor = null;
    // DcMotor rightMotor = null;

    DcMotor[] leftDrive = null;
    DcMotor[] rightDrive = null;
    DcMotor[] allDrive = null;


    DcMotor intakeMotor = null;
    DcMotor lifterMotor = null;

    DcMotor hangingMotor = null;

    public void InitializeHardware() {
        leftDrive[0] = hardwareMap.dcMotor.get("Left_Front");
        leftDrive[1] = hardwareMap.dcMotor.get("Left_Back");

        rightDrive[0] = hardwareMap.dcMotor.get("Right_Front");
        rightDrive[1] = hardwareMap.dcMotor.get("Right_Back");

        allDrive[0] = hardwareMap.dcMotor.get("Left_Front");
        allDrive[1] = hardwareMap.dcMotor.get("Left_Back");
        allDrive[2] = hardwareMap.dcMotor.get("Right_Front");
        allDrive[3] = hardwareMap.dcMotor.get("Right_Back");

        setMode(allDrive, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intakeMotor = hardwareMap.dcMotor.get("Intake");
        lifterMotor = hardwareMap.dcMotor.get("Intake_Lifter");

        hangingMotor = hardwareMap.dcMotor.get("Hanging");
    }


    private void setMode(DcMotor[] motors, DcMotor.RunMode mode) {
        for (DcMotor motor : motors) {
            motor.setMode(mode);
        }
    }

    private void setPower(DcMotor[] motors, double power) {
        for (DcMotor motor : motors) {
            motor.setPower(power);
        }
    }

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

            double leftPower = -gamepad1.left_stick_y;
            double rightPower = -gamepad1.right_stick_y;

            setPower(leftDrive, leftPower);
            setPower(rightDrive, rightPower);

            /*
            leftMotorFront.setPower(-gamepad1.left_stick_y);
            leftMotorBack.setPower(-gamepad1.left_stick_y);
            rightMotorFront.setPower(-gamepad1.right_stick_y);
            rightMotorBack.setPower(-gamepad1.right_stick_y);
            */

           /* intakeMotor.setPower(-gamepad1.right_trigger);
            armMotor.setPower(-gamepad1.left_trigger);
            */

            while (gamepad1.dpad_up) ;
            {
                hangingMotor.setPower(0.5);
            }
            while (gamepad1.dpad_down) ;
            {
                hangingMotor.setPower(-0.5);
            }
        }

    }

}
