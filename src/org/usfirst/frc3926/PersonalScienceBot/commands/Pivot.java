package org.usfirst.frc3926.PersonalScienceBot.commands;

import org.usfirst.frc3926.PersonalScienceBot.Robot;
import org.usfirst.frc3926.PersonalScienceBot.subsystems.DriveTrain;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 * @author Adam C
 *
 */
public class Pivot extends Command {

	DriveTrain driveTrain = Robot.driveTrain;
	AHRS ahrs = Robot.ahrs;
	Timer timer = new Timer();
	
	final double ROBOT_WIDTH = 30; //in INCHES
	final double TOLERANCE = 2; //in DEGREES
	final double startAngle = ahrs.getYaw();
	final double startTime = timer.getFPGATimestamp(); //in SECONDS
	final double startLeft = driveTrain.getLeftEncoderDist();
	final double startRight = driveTrain.getRightEncoderDist();
	double targetAngle, currentAngle; //in DEGREES, targetAngle is relative
	double targetTime, currentTime; //in SECONDS
	double arcLengthLeft, arcLengthRight; //in INCHES
	double currentDistLeft, currentDistRight; //in INCHES
	double radius;//in INCHES
	double angleError, leftDistError, rightDistError, leftSpeed, rightSpeed; //temporary variables for calculation, speeds in INCHES/SECOND
	double slowDownPoint; //in DEGREES
	
	public Pivot () {
		
	}
	
	public Pivot (double targetAngle, double time, double radius, double slowDownPoint) {
		this.radius = radius;
		this.slowDownPoint = slowDownPoint;
		this.targetAngle = targetAngle + currentAngle;
		targetTime = time + startTime;
		requires(driveTrain);
	}
	
	@Override
	protected void initialize() {
		if (targetAngle < 0) 
			radius = Math.min(radius, -radius);
		if (targetAngle > 0) 
			radius = Math.max(radius, -radius);
		
		//circumference * section
		arcLengthLeft = (Math.PI*(radius - (ROBOT_WIDTH/2))*2) * (targetAngle/360);
		arcLengthRight = (Math.PI*(radius + (ROBOT_WIDTH/2))*2) * (targetAngle/360);
		
		currentAngle = ahrs.getYaw();
		currentTime = timer.getFPGATimestamp();
	}

	@Override
	protected void execute() {
		currentAngle = ahrs.getYaw();
		currentTime = timer.getFPGATimestamp();
		currentDistLeft = driveTrain.getLeftEncoderDist();
		currentDistRight = driveTrain.getRightEncoderDist();
		
		//TODO: Check math!
		//calculates the error in angle at this time
		angleError = currentAngle - ((currentTime/targetTime) * targetAngle);
		//calculates the error in arc distance at this time
		//TODO: factor in angle error when calculating left and right errors and speeds
		leftDistError = currentDistLeft - ((currentTime/targetTime) * arcLengthLeft);
		rightDistError = currentDistRight - ((currentTime/targetTime) * arcLengthRight);
		
		//Math.min limits to 1
		//(arcLength - currentDist)/slowDownPoint is proportional control
		//(distError/slowDownPoint) is proportional control for error correction
		leftSpeed = Math.min(1, ((arcLengthLeft - currentDistLeft)/slowDownPoint) + (leftDistError/slowDownPoint));
		rightSpeed = Math.min(1, ((arcLengthRight - currentDistRight)/slowDownPoint) + (rightDistError/slowDownPoint));
		
		SmartDashboard.putNumber("Pivot Angle", targetAngle);
		SmartDashboard.putNumber("Pivot Radius", radius);
		SmartDashboard.putNumber("Pivot Angle Error", angleError);
		SmartDashboard.putNumber("Pivot Left Output", leftSpeed);
		SmartDashboard.putNumber("Pivot Right Output", rightSpeed);
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(currentAngle-targetAngle) < TOLERANCE;
	}

	@Override
	protected void end() {
		driveTrain.setRawSpeeds(0, 0);
	}

	@Override
	protected void interrupted() {
		driveTrain.setRawSpeeds(0, 0);
	}
	
	
	
}
