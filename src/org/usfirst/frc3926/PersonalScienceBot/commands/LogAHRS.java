package org.usfirst.frc3926.PersonalScienceBot.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.usfirst.frc3926.PersonalScienceBot.Robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LogAHRS extends Command {
	
	AHRS ahrs = Robot.ahrs;
	Timer timer;
	File logFolder;
	File logFile;
	DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	FileWriter writer;
	private final double SAMPLE_GAP = 0.5;
	private double prevTime;
	
	//tilt corrected heading, requires magnetometer calibration
	ArrayList<Float> compassHeading;
	
	//6-axis processed angle data
	ArrayList<Float> yaw;
	ArrayList<Float> pitch;
	ArrayList<Float> roll;
	
	//9-axis heading, requires magnetometer calibration
	ArrayList<Float> fusedHeading;
	
	//simple vars, compadible with WPI Gyro
	ArrayList<Double> angle;
	ArrayList<Double> rate;
	
	//processed acceleration data
	ArrayList<Float> worldLinearAccelX;
	ArrayList<Float> worldLinearAccelY;
	ArrayList<Float> worldLinearAccelZ;
	
	//motion detecting
	ArrayList<Boolean> isMoving;
	ArrayList<Boolean> isRotating;
	
	//estimate velocity and displacement
	//probably not accurate enough for robot control
	ArrayList<Float> estimateVelocityX;
	ArrayList<Float> estimateVelocityY;
	ArrayList<Float> estimateVelocityZ;
	ArrayList<Float> estimateDisplacementX;
	ArrayList<Float> estimateDisplacementY;
	ArrayList<Float> estimateDisplacementZ;
	
	//raw gyro/accel/mag values!
	//DON'T USE IF YOU DON'T KNOW WHAT YOU'RE DOING!!!
	ArrayList<Float> rawAccelX;
	ArrayList<Float> rawAccelY;
	ArrayList<Float> rawAccelZ;
	ArrayList<Float> rawGyroX;
	ArrayList<Float> rawGyroY;
	ArrayList<Float> rawGyroZ;
	ArrayList<Float> rawMagX;
	ArrayList<Float> rawMagY;
	ArrayList<Float> rawMagZ;
	
	//raw temperature
	ArrayList<Float> rawC;
	
	//quaternations, compact representation of orientation data
	//can derive yaw, pitch and roll values
	ArrayList<Float> quaternationW;
	ArrayList<Float> quaternationX;
	ArrayList<Float> quaternationY;
	ArrayList<Float> quaternationZ;
	
	//estimate altitude
	ArrayList<Float> altitude;
	
	//pressure
	ArrayList<Float> pressure;
	
	//barometric pressure
	ArrayList<Float> barometricPressure;
	
	@Override
	protected void initialize() {
		logFolder = new File("ahrsLogs");
		logFolder.mkdir();
		logFile = new File(logFolder, "AHRS Log: " + df.format(date) + ".txt");
		try {
			writer = new FileWriter(logFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		timer = new Timer();
		
		compassHeading = new ArrayList<>();
		yaw = new ArrayList<>();
		pitch = new ArrayList<>();
		roll = new ArrayList<>();
		fusedHeading = new ArrayList<>();
		angle = new ArrayList<>();
		rate = new ArrayList<>();
		worldLinearAccelX = new ArrayList<>();
		worldLinearAccelY = new ArrayList<>();
		worldLinearAccelZ = new ArrayList<>();
		isMoving = new ArrayList<>();
		isRotating = new ArrayList<>();
		estimateVelocityX = new ArrayList<>();
		estimateVelocityY = new ArrayList<>();
		estimateVelocityZ = new ArrayList<>();
		estimateDisplacementX = new ArrayList<>();
		estimateDisplacementY = new ArrayList<>();
		estimateDisplacementZ = new ArrayList<>();
		rawGyroX = new ArrayList<>();
		rawGyroY = new ArrayList<>();
		rawGyroZ = new ArrayList<>();
		rawAccelX = new ArrayList<>();
		rawAccelY = new ArrayList<>();
		rawAccelZ = new ArrayList<>();
		rawMagX = new ArrayList<>();
		rawMagY = new ArrayList<>();
		rawMagZ = new ArrayList<>();
		rawC = new ArrayList<>();
		quaternationW = new ArrayList<>();
		quaternationX = new ArrayList<>();
		quaternationY = new ArrayList<>();
		quaternationZ = new ArrayList<>();
		altitude = new ArrayList<>();
		pressure = new ArrayList<>();
		barometricPressure = new ArrayList<>();
		
		prevTime = timer.getFPGATimestamp();
	}

	@Override
	protected void execute() {
		if (timer.getFPGATimestamp() - SAMPLE_GAP <= prevTime) {
			compassHeading.add(ahrs.getCompassHeading());
			
			yaw.add(ahrs.getYaw());
			pitch.add(ahrs.getPitch());
			roll.add(ahrs.getRoll());
			
			fusedHeading.add(ahrs.getFusedHeading());
			
			angle.add(ahrs.getAngle());
			rate.add(ahrs.getRate());
			
			worldLinearAccelX.add(ahrs.getWorldLinearAccelX());
			worldLinearAccelY.add(ahrs.getWorldLinearAccelY());
			worldLinearAccelZ.add(ahrs.getWorldLinearAccelZ());
			isMoving.add(ahrs.isMoving());
			isRotating.add(ahrs.isRotating());
			
			estimateVelocityX.add(ahrs.getVelocityX());
			estimateVelocityY.add(ahrs.getVelocityY());
			estimateVelocityZ.add(ahrs.getVelocityZ());
			estimateDisplacementX.add(ahrs.getDisplacementX());
			estimateDisplacementY.add(ahrs.getDisplacementY());
			estimateDisplacementZ.add(ahrs.getDisplacementZ());
			
			rawAccelX.add(ahrs.getRawAccelX());
			rawAccelY.add(ahrs.getRawAccelY());
			rawAccelZ.add(ahrs.getRawAccelZ());
			rawGyroX.add(ahrs.getRawGyroX());
			rawGyroY.add(ahrs.getRawGyroY());
			rawGyroZ.add(ahrs.getRawGyroZ());
			rawMagX.add(ahrs.getRawMagX());
			rawMagY.add(ahrs.getRawMagY());
			rawMagZ.add(ahrs.getRawMagZ());
			rawC.add(ahrs.getTempC());
			
			quaternationW.add(ahrs.getQuaternionW());
			quaternationX.add(ahrs.getQuaternionX());
			quaternationY.add(ahrs.getQuaternionY());
			quaternationZ.add(ahrs.getQuaternionZ());
			
			altitude.add(ahrs.getAltitude());
			barometricPressure.add(ahrs.getBarometricPressure());
			pressure.add(ahrs.getPressure());
			
			prevTime = timer.getFPGATimestamp();
		}
		
		SmartDashboard.putNumber("Processed Yaw", ahrs.getYaw());
		SmartDashboard.putNumber("Processed Pitch", ahrs.getPitch());
		SmartDashboard.putNumber("Processed Roll", ahrs.getRoll());
		
		SmartDashboard.putNumber("World Linear Acceleration X", ahrs.getWorldLinearAccelX());
		SmartDashboard.putNumber("World Linear Acceleration Y", ahrs.getWorldLinearAccelX());
		SmartDashboard.putNumber("World Linear Acceleration Z", ahrs.getWorldLinearAccelX());
		
		SmartDashboard.putNumber("Fused Heading", ahrs.getFusedHeading());
		
		SmartDashboard.putNumber("Compass Heading", ahrs.getCompassHeading());
		
		SmartDashboard.putNumber("Angle", ahrs.getAngle());
		SmartDashboard.putNumber("Rate", ahrs.getRate());
		
		SmartDashboard.putNumber("Velocity X", ahrs.getVelocityX());
		SmartDashboard.putNumber("Velocity Y", ahrs.getVelocityY());
		SmartDashboard.putNumber("Velocity Z", ahrs.getVelocityZ());
		
		SmartDashboard.putNumber("Displacement X", ahrs.getDisplacementX());
		SmartDashboard.putNumber("Displacement Y", ahrs.getDisplacementY());
		SmartDashboard.putNumber("Displacement Z", ahrs.getDisplacementZ());
		
		SmartDashboard.putNumber("Quaternation W", ahrs.getQuaternionW());
		SmartDashboard.putNumber("Quaternation X", ahrs.getQuaternionX());
		SmartDashboard.putNumber("Quaternation Y", ahrs.getQuaternionY());
		SmartDashboard.putNumber("Quaternation Z", ahrs.getQuaternionZ());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		for (int i=0; i<compassHeading.size(); i++) {
			try {
				writer.write("Tick " + (i+1) + " out of " + compassHeading.size());
				
				writer.write("Compass: " + compassHeading.get(i) + "\n");
				writer.write("Yaw: " + yaw.get(i) + "\n");
				writer.write("Pitch: " + pitch.get(i) + "\n");
				writer.write("Roll: " + roll.get(i) + "\n");
				
				writer.write("Fused Heading: " + fusedHeading.get(i) + "\n");
				
				writer.write("Angle: " + angle.get(i) + "\n");
				writer.write("Rate: " + rate.get(i) + "\n");
				
				writer.write("Linear Accel X: " + worldLinearAccelX.get(i) + "\n");
				writer.write("Linear Accel Y: " + worldLinearAccelY.get(i) + "\n");
				writer.write("Linear Accel Z: " + worldLinearAccelZ.get(i) + "\n");
				
				writer.write("Is Moving: " + isMoving.get(i) + "\n");
				writer.write("Is Rotating: " + isRotating.get(i) + "\n");
				
				writer.write("Estimated Velocity X: " + estimateVelocityX.get(i) + "\n");
				writer.write("Estimated Velocity Y: " + estimateVelocityY.get(i) + "\n");
				writer.write("Estimated Velocity Z: " + estimateVelocityZ.get(i) + "\n");
				writer.write("Estimated Displacement X: " + estimateDisplacementX.get(i) + "\n");
				writer.write("Estimated Displacement Y: " + estimateDisplacementY.get(i) + "\n");
				writer.write("Estimated Displacement Z: " + estimateDisplacementZ.get(i) + "\n");
				
				writer.write("Raw Accel X: " + rawAccelX.get(i) + "\n");
				writer.write("Raw Accel Y: " + rawAccelY.get(i) + "\n");
				writer.write("Raw Accel Z: " + rawAccelZ.get(i) + "\n");
				writer.write("Raw Gyro X: " + rawGyroX.get(i) + "\n");
				writer.write("Raw Gyro Y: " + rawGyroY.get(i) + "\n");
				writer.write("Raw Gyro Z: " + rawGyroZ.get(i) + "\n");
				writer.write("Raw Mag X: " + rawMagX.get(i) + "\n");
				writer.write("Raw Mag Y: " + rawMagY.get(i) + "\n");
				writer.write("Raw Mag Z: " + rawMagZ.get(i) + "\n");
				writer.write("Raw Temperature (C): " + rawC.get(i) + "\n");
				
				writer.write("Quaternation W: " + quaternationW.get(i) + "\n");
				writer.write("Quaternation X: " + quaternationX.get(i) + "\n");
				writer.write("Quaternation Y: " + quaternationY.get(i) + "\n");
				writer.write("Quaternation Z: " + quaternationZ.get(i) + "\n");
				
				writer.write("Altitude: " + altitude.get(i) + "\n");
				writer.write("Barometric Pressure: " + barometricPressure.get(i) + "\n");
				writer.write("Pressure: " + pressure.get(i) + "\n\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
