package org.usfirst.frc3926.PersonalScienceBot.helpers;

import org.usfirst.frc3926.PersonalScienceBot.Robot;

import edu.wpi.first.wpilibj.Joystick;

public class XboxHelper {
	private XboxHelper() {}
	
	public static final int
	A = 1,
	B = 2,
	X = 3,
	Y = 4,
	TRIGGER_LT = 5,
	TRIGGER_RT = 6,
	START = 7,
	BACK = 8,
	STICK_LEFT = 9,
	STICK_RIGHT = 10;
	
	
//	private Joystick[] controllers;
	private static Joystick xbox;
	
	public static void init() {
		if (xbox == null) {
			xbox = Robot.oi.xbox;
		}
	}
	
	public static double getAxis(int axis) {
		return Math.abs(xbox.getRawAxis(axis)) > Constants.JOYSTICK_DEADZONE ? xbox.getRawAxis(axis) : 0;
	}
	
}
