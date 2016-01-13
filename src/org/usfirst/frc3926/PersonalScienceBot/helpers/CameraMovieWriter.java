package org.usfirst.frc3926.PersonalScienceBot.helpers;

import org.usfirst.frc3926.PersonalScienceBot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.vision.AxisCamera;

public class CameraMovieWriter extends Command {
	
	protected static AxisCamera cam;
	
	
	@Override
	protected void initialize() {
		cam = Robot.camera;
	}

	@Override
	protected void execute() {
		
	}

	
	
	
	
	
	
	protected void finalizeMovie() {
		
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		//finalize the file
	}
	
	
}
