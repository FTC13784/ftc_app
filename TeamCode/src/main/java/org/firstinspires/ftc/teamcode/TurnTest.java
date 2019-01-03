package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "TurnTest", group = "Linear Opmode")
//@Disabled
public class TurnTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    DcMotor leftMotorFront;
    DcMotor rightMotorFront;
    DcMotor leftMotorBack;
    DcMotor rightMotorBack;

    LinearOpMode opMode;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        InitializeHardware();

        waitForStart();
        runtime.reset();

        TurnTest(500, 0.5);

    }

    public void InitializeHardware() {
        leftMotorFront = hardwareMap.dcMotor.get("Left_Front");
        rightMotorFront = hardwareMap.dcMotor.get("Right_Front");
        leftMotorBack = hardwareMap.dcMotor.get("Left_Back");
        rightMotorBack = hardwareMap.dcMotor.get("Right_Back");

    }


    private void TurnTest(int ticks, double power) {

        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotorFront.setTargetPosition(ticks);
        leftMotorBack.setTargetPosition(ticks);

        leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotorFront.setPower(power);
        leftMotorBack.setPower(power);

        while (leftMotorBack.isBusy() && leftMotorFront.isBusy() && opMode.opModeIsActive()) {
            //wait until target position in reached
        }

        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);

        leftMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
}