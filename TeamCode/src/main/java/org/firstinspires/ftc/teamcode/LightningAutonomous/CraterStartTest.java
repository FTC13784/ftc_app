package org.firstinspires.ftc.teamcode.LightningAutonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import net.pps.lincoln.team13784.PhilSwift;

import org.firstinspires.ftc.teamcode.LightningAutonomous.OurBot;


@Autonomous(name = "Crater Start TEST", group = "Linear Opmode")
@Disabled
public class CraterStartTest extends LinearOpMode {
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

        ourBot.DriveDistance(50, 1120);
        ourBot.StopDriving();

        ourBot.lowerHanging();

        ourBot.DriveDistance(50, 2500);
        ourBot.StopDriving();
        telemetry.addData("Status", "done");
        telemetry.update();


    }
}
