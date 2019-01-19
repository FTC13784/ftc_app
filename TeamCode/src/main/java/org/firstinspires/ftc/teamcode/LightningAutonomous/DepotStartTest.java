package org.firstinspires.ftc.teamcode.LightningAutonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "Depot Start TEST", group = "Linear Opmode")
@Disabled
public class DepotStartTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    OurBot ourBot;

    @Override
    public void runOpMode() throws InterruptedException {
        ourBot = new OurBot(this);
        telemetry.addData("Status", "OurBot Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        telemetry.addData("Status", "Start received");
        telemetry.update();

        ourBot.deploy();

        //drive forward 35? in
        ourBot.DriveDistance(1, 3119);

        //drive back 10 in
        ourBot.DriveDistance(-1, 891);

        // turn 90? degrees to the left
        //drive forward 40? in
        // turn left in place 45? degrees
        //drive forward 50? cm


    }
}
