package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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

@TeleOp(name="ConfigTest", group="Linear Opmode")  // @Autonomous(...) is the other common choice
//@Disabled
public class ConfigTest extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    // DcMotor leftMotor = null;
    // DcMotor rightMotor = null;

    DcMotor c1p1;
    DcMotor c1p2;
    DcMotor c2p1;
    DcMotor c2p2;
    DcMotor c3p1;
    // DcMotor c3p2;
    DcMotor c4p1;
    DcMotor c4p2;


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        c1p1 = hardwareMap.dcMotor.get("c1p1");
        c1p2 = hardwareMap.dcMotor.get("c1p2");
        c2p1 = hardwareMap.dcMotor.get("c2p1");
        c2p2 = hardwareMap.dcMotor.get("c2p2");

        c3p1 = hardwareMap.dcMotor.get("c3p1");
        // c3p2 = hardwareMap.dcMotor.get("c3p2");
        c4p1 = hardwareMap.dcMotor.get("c4p1");
        c4p2 = hardwareMap.dcMotor.get("c4p2");

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            while (gamepad1.dpad_up == true) ; {
                c1p1.setPower(1.0);
            }

            while (gamepad1.dpad_right == true) ; {
                c1p2.setPower(1.0);
            }

            while (gamepad1.dpad_down == true) ; {
                c2p1.setPower(1.0);
            }

            while (gamepad1.dpad_left == true) ; {
                c2p2.setPower(1.0);
            }

            while (gamepad1.y == true) ; {
                c3p1.setPower(1.0);
            }

            /*while (gamepad1.b == true) ; {
                c3p2.setPower(1.0);
            }
            */

            while (gamepad1.a == true) ; {
                c4p1.setPower(1.0);
            }

            while (gamepad1.x == true) ; {
                c4p2.setPower(1.0);
            }
        }
    }
}