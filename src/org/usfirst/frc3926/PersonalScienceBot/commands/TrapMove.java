package org.usfirst.frc3926.PersonalScienceBot.commands;

import org.usfirst.frc3926.PersonalScienceBot.Robot;
import org.usfirst.frc3926.PersonalScienceBot.subsystems.DriveTrain;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TrapMove extends Command {
	DriveTrain driveTrain = Robot.driveTrain;
	AHRS navx;
	
	/*final*/ double 
	START_SPEED,
	TARGET_DIST = 36, //Inches
	TARGET_SPEED = 10, //Inches/sec
	ACCEL = 0.5;
	
	double outSpeed, curSpeed, acceleration, curTime, curPos;
	double calcCurDist, calcCurVel;
	
	CurrentPhase curPhase = CurrentPhase.PLATEAU;
	
	private enum CurrentPhase {
		ACCELERATING, PLATEAU, DECELERATING;
	}

    public TrapMove() {
    	requires(Robot.driveTrain);
    }
    
    /**
     * @param targetDistance  in inches
     * @param targetSpeed  in inches/second
     */
    public TrapMove(double targetDistance, double targetSpeed) {
    	requires(Robot.driveTrain);
    	
    	this.TARGET_DIST = targetDistance;
    	this.TARGET_SPEED = targetSpeed;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	navx = Robot.navx;
    	
    	START_SPEED = navx.getVelocityY();
    	ACCEL = navx.getWorldLinearAccelY();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	curTime = Timer.getFPGATimestamp();
    	/*
    	switch (curPhase) {
		case ACCELERATING:
			
			break;
		case PLATEAU:
			
			break;
		case DECELERATING:
			
			break;

		default:
			break;
		}
    	*/
    	
    	calcCurDist = curPos + curSpeed + .5*ACCEL;
    	calcCurVel = curSpeed + ACCEL;
    	
    	outSpeed = calcCurVel;
    	
    	driveTrain.setMotorSpeeds(outSpeed, outSpeed);
    	
    }
    
    protected void logData() {
    	SmartDashboard.putNumber("CalculatedCurrentDistance", calcCurDist);
    	SmartDashboard.putNumber("CalculatedCurrentVelocity", calcCurVel);
    	
    	SmartDashboard.getNumber("AccelerationProfile", 0.5);
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	driveTrain.setMotorSpeeds(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	driveTrain.setMotorSpeeds(0, 0);
    }
}
