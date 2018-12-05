package net.pps.lincoln.team13784;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class PhilSwift {
    DcMotor[] leftDrive = null;
    DcMotor[] rightDrive = null;
    DcMotor[] allDrive = null;


    DcMotor intakeMotor = null;
    DcMotor lifterMotor = null;

    DcMotor hangingMotor = null;

    HardwareMap hardwareMap = null;

    double ticksPerIn = 0;

    public PhilSwift(HardwareMap map, double wheelRadius, double ticksPerRev) {
        hardwareMap = map;
        InitializeHardware();
        ticksPerIn = ticksPerRev / (wheelRadius * Math.PI * 2);
    }

    private void InitializeHardware() {
        leftDrive[0] = hardwareMap.dcMotor.get("Left_Front");
        leftDrive[1] = hardwareMap.dcMotor.get("Left_Back");

        rightDrive[0] = hardwareMap.dcMotor.get("Right_Front");
        rightDrive[1] = hardwareMap.dcMotor.get("Right_Back");

        allDrive[0] = hardwareMap.dcMotor.get("Left_Front");
        allDrive[1] = hardwareMap.dcMotor.get("Left_Back");
        allDrive[2] = hardwareMap.dcMotor.get("Right_Front");
        allDrive[3] = hardwareMap.dcMotor.get("Right_Back");

        intakeMotor = hardwareMap.dcMotor.get("Intake");
        lifterMotor = hardwareMap.dcMotor.get("Intake_Lifter");

        hangingMotor = hardwareMap.dcMotor.get("Hanging");
    }

    private void setMode(DcMotor[] motors, DcMotor.RunMode mode) {
        for (DcMotor motor : motors) {
            motor.setMode(mode);
        }
    }

    private void setDistance(DcMotor[] motors, double distance) {
        double target = distance * ticksPerIn;
        for (DcMotor motor : motors) {
            motor.setTargetPosition((int) Math.round(target));
        }
    }

    private boolean isBusy(DcMotor[] motors) {
        for (DcMotor motor : motors) {
            if (motor.isBusy()) {
                return true;
            }
        }
        return false;
    }

    private void setPower(DcMotor[] motors, double power) {
        for (DcMotor motor : motors) {
            motor.setPower(power);
        }
    }

    /**
     * drive distance specified
     * positive distance is forward, negative distance is backward
     *
     * @param distance distance in cm
     * @param speed    speed btwn 0 and 1
     */
    public void drive(double distance, double speed) {

        // Reset encoders
        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // set target position
        setDistance(allDrive, distance);


        // Set to RUN_TO_POSITION mode
        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

        // Set drive power
        setPower(allDrive, speed);

        while (isBusy(allDrive)) {
            // Wait until target position is reached
        }

        // Stop and change modes back to normal
        setPower(allDrive, 0);

        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);

    }


    /**
     * turn in place degrees specified
     *
     * @param degrees degrees between -360 and 360
     * @param speed   speed btwn 0 and 1
     */
    public void turn(int degrees, double speed) {

    }


    /**
     * lowers robot down from hanging mechanism, assumes that robot is already hanging when this is called
     *
     * @param distance distance in inches
     * @param speed    speed btwn 0 and 1
     */
    public void lowerMe(double distance, double speed) {

    }
}
