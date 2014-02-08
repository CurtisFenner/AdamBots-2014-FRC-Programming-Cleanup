/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Tyler
 */
public class RobotSensors {

////VARIABLES-------------------------------------------------------------------
    public static AnalogChannel currentSensor;
    public static AnalogChannel configSwitchA;
    public static AnalogChannel configSwitchB;
    public static AnalogChannel configSwitchC;
    public static Counter rightDriveEncoder;
    public static Counter leftDriveEncoder;
    public static Encoder pickupSystemEncoder;
    public static AnalogPotentiometer pickupPotentiometer;
    public static Encoder shooterWinchEncoder;
    public static DigitalInput ballReadyToLiftLim;
    public static DigitalInput pickupSystemDownLim;
    public static DigitalInput pickupSystemUpLim;
    public static DigitalInput shooterLoadedLim;
    public static DigitalInput shooterAtBack;
    public static DigitalInput pressureSwitch;
    public static ADXL345_I2C accelerometer;
    
    public static void initialize() {
	//// Analog
	currentSensor = new AnalogChannel(1);
	configSwitchA = new AnalogChannel(2);
	configSwitchB = new AnalogChannel(3);
	configSwitchC = new AnalogChannel(4);
        pickupPotentiometer = new AnalogPotentiometer(5, 1, 0);
	
	//// Digital In 1
	rightDriveEncoder = new Counter(1);
	leftDriveEncoder = new Counter(2);
	ballReadyToLiftLim = new DigitalInput(3);
	shooterWinchEncoder = new Encoder(4, 5);
	pickupSystemDownLim = new DigitalInput(6);
	pickupSystemUpLim = new DigitalInput(7);
	shooterLoadedLim = new DigitalInput(8);
	shooterAtBack = new DigitalInput(9);
	pickupSystemEncoder = new Encoder(10, 11);
	pressureSwitch = new DigitalInput(14);
	
	//// Digital 1 Serial
	accelerometer = new ADXL345_I2C(1, ADXL345_I2C.DataFormat_Range.k2G);
        
	System.out.println("Sensor init done");
    }
}
