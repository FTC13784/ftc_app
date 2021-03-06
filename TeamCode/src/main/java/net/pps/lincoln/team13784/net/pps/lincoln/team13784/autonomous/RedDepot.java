package net.pps.lincoln.team13784.net.pps.lincoln.team13784.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import net.pps.lincoln.team13784.PhilSwift;


@Autonomous(name = "RedDepot", group = "Linear Opmode")
@Disabled
public class RedDepot extends LinearOpMode {

    PhilSwift philSwift;

    @Override
    public void runOpMode() throws InterruptedException {
        //distance & wheelRadius measurements in inches
        telemetry.addData("Status", "About to create philSwift");
        telemetry.update();

        philSwift = new PhilSwift(this);

        telemetry.addData("Status", "OurBot Initialized");
        telemetry.update();

        waitForStart();

        telemetry.addData("Status", "Start received");
        telemetry.update();

        //deploy
        //drive forward 35? in
        //drive back 10 cm
        // turn 90? degrees to the left
        //drive forward 40? in
        // turn left in place 45? degrees
        //drive forward 50? cm


        telemetry.addData("Status", "Done driving");
        telemetry.update();
    }

}