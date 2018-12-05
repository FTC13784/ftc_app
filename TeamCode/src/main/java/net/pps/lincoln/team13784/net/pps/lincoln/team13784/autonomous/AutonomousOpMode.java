package net.pps.lincoln.team13784.net.pps.lincoln.team13784.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import net.pps.lincoln.team13784.PhilSwift;


@Autonomous(name = "AutonomousOpMode", group = "Linear Opmode")
//@Disabled
public class AutonomousOpMode extends LinearOpMode {

    PhilSwift philSwift = null;

    @Override
    public void runOpMode() throws InterruptedException {
        //distance & wheelRadius measurements in inches
        philSwift = new PhilSwift(hardwareMap, 2, 103);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        philSwift.drive(20, 0.7);
    }

}