package net.pps.lincoln.team13784.net.pps.lincoln.team13784.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import net.pps.lincoln.team13784.PhilSwift;


@Autonomous(name = "AutonomousOpMode", group = "Linear Opmode")
@Disabled
public class AutonomousOpMode extends LinearOpMode {

    PhilSwift philSwift;

    @Override
    public void runOpMode() throws InterruptedException {
        philSwift = new PhilSwift(this);

        telemetry.addData("Status", "OurBot Initialized");
        telemetry.update();

        waitForStart();

        telemetry.addData("Status", "Start received");
        telemetry.update();



        philSwift.drive(12, 0.9);

        telemetry.addData("Status", "Done driving");
        telemetry.update();

    }

}