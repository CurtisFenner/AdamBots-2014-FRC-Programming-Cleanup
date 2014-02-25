/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.Autons.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class MainRobot extends IterativeRobot {

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		RobotActuators.initialize();
		RobotSensors.initialize();
		RobotDrive.initialize();
		RobotPickup.initialize();
		RobotShoot.initialize();
		RobotVision.initialize();
		RobotAuton.initialize();
		ControlBox.initialize();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		runCompressor();
		RobotAuton.update();
		DashboardPut.put();
	}

	public void teleopInit() {
		SmartDashboard.putNumber("Target Ticks", 1000);
		RobotDrive.enableSmoothing();
	}

	public void disabledInit() {
		StandardOneBallAuton.timer.stop();
		StandardOneBallAuton.timer.reset();
	}

	/**
	 * This function is called periodically during operator control
	 */
	boolean on = true;
	int trueCount = 0;
	int maxTrueCount = Integer.MIN_VALUE;

	public void teleopPeriodic() {

		RobotShoot.setTargetTicks(SmartDashboard.getNumber("Target Ticks"));

		boolean r = !RobotSensors.shooterAtBack.get();
		if (r) {
			System.out.println("SWITCH true");
			on = true;
			trueCount = 0;
		} else {
			if (on) {
				System.out.println("MaxTrueCount: " + maxTrueCount);
				System.out.println("True Count: " + trueCount);
				System.out.println("SWITCH false");
				on = false;
			}
			trueCount++;
			if (trueCount > maxTrueCount) {
				maxTrueCount = trueCount;
			}
		}

		ControlBox.update();
		RobotDrive.update();
		RobotPickup.update();
		RobotShoot.update();

		RobotPickup.moveToShootPosition();

		//RobotTeleop.update();
		if (ControlBox.getTopSwitch(2)) {
			RobotShoot.useAutomatic();
		} else {
			RobotShoot.useManual();
		}

		if (Gamepad.secondary.getTriggers() > .9) {
			RobotShoot.shoot();
		}

		SmartDashboard.putNumber("ANGLE ANGLE", RobotPickup.getArmAngleAboveHorizontal());
		SmartDashboard.putBoolean("PICKUP Upper Limit", RobotSensors.pickupSystemUpLim.get());
		SmartDashboard.putBoolean("PICKUP Lower Limit", RobotSensors.pickupSystemDownLim.get());
		runCompressor();

		SmartDashboard.putNumber("Red Distance", RobotVision.redDistance());

		DashboardPut.put();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		DashboardPut.put();
	}

	private void runCompressor() {
		SmartDashboard.putBoolean("Pressure Switch", RobotSensors.pressureSwitch.get());
		if (!RobotSensors.pressureSwitch.get()) {
			RobotActuators.compressor.set(Relay.Value.kOn);
			//System.out.println("Setting the compressor to ON");
		} else {
			RobotActuators.compressor.set(Relay.Value.kOff);
		}
		//System.out.println("runCompressor finished");
	}

	public void disabledPeriodic() {
		RobotDrive.stopDrive();
		RobotShoot.stopMotors();
		AutonZero.reset();
		DashboardPut.put();
		maxTrueCount = 0;
	}

	public void autonomousInit() {
		RobotAuton.initialize();
	}
}
