package net.pps.lincoln.team13784.net.pps.lincoln.team13784.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import net.pps.lincoln.team13784.PhilSwift;


@Autonomous(name = "RedCrater", group = "Linear Opmode")
@Disabled
public class RedCrater extends LinearOpMode {

    PhilSwift philSwift;

    @Override
    public void runOpMode() throws InterruptedException {
        //distance & wheelRadius measurements in inches
        telemetry.setAutoClear(false);
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
        //drive back 10 in
        // drive forward 30? in
        // lower intake
        //turn on output and deploy team marker




        telemetry.addData("Status", "Done driving");
        telemetry.update();

        //double now = System.currentTimeMillis();
        //while(now + 15000< System.currentTimeMillis()) {

        //}
    }

}