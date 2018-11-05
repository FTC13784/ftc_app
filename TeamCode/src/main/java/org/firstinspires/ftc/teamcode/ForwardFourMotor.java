package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

@Autonomous(name="ForwardFourMotor", group="Linear Opmode")  // @Autonomous(...) is the other common choice
//@Disabled
public class ForwardFourMotor extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    // DcMotor leftMotor = null;
    // DcMotor rightMotor = null;

    DcMotor leftMotorFront;
    DcMotor rightMotorFront;
    DcMotor leftMotorBack;
    DcMotor rightMotorBack;

    double power = 0.5;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftMotorFront = hardwareMap.dcMotor.get("Left_Front_Motor");
        leftMotorBack = hardwareMap.dcMotor.get("Left_Back_Motor");
        rightMotorBack = hardwareMap.dcMotor.get("Right_Back_Back");
        rightMotorFront = hardwareMap.dcMotor.get("Right_Front_Motor");

        leftMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        runtime.reset();

        leftMotorFront.setPower(power);
        leftMotorBack.setPower(power);
        rightMotorFront.setPower(power);
        rightMotorBack.setPower(power);

        sleep(2000);

        power = 0.0;

        leftMotorFront.setPower(power);
        leftMotorBack.setPower(power);
        rightMotorFront.setPower(power);
        rightMotorBack.setPower(power);
    }
}

