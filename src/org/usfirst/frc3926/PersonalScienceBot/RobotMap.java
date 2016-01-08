// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3926.PersonalScienceBot;
    
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static SpeedController driveTrainMotorLeft;
    public static SpeedController driveTrainMotorRight;
    public static RobotDrive driveTrainRobotDrive;
    public static Encoder driveTrainEncoderLeft;
    public static Encoder driveTrainEncoderRight;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrainMotorLeft = new Jaguar(0);
        LiveWindow.addActuator("DriveTrain", "MotorLeft", (Jaguar) driveTrainMotorLeft);
        
        driveTrainMotorRight = new Jaguar(1);
        LiveWindow.addActuator("DriveTrain", "MotorRight", (Jaguar) driveTrainMotorRight);
        
        driveTrainRobotDrive = new RobotDrive(driveTrainMotorLeft, driveTrainMotorRight);
        
        driveTrainRobotDrive.setSafetyEnabled(true);
        driveTrainRobotDrive.setExpiration(0.1);
        driveTrainRobotDrive.setSensitivity(0.5);
        driveTrainRobotDrive.setMaxOutput(1.0);
        

        driveTrainEncoderLeft = new Encoder(4, 5, false, EncodingType.k4X);
        LiveWindow.addSensor("DriveTrain", "EncoderLeft", driveTrainEncoderLeft);
        driveTrainEncoderLeft.setDistancePerPulse(1.0);
        driveTrainEncoderLeft.setPIDSourceParameter(PIDSourceParameter.kRate);
        driveTrainEncoderRight = new Encoder(6, 7, false, EncodingType.k4X);
        LiveWindow.addSensor("DriveTrain", "EncoderRight", driveTrainEncoderRight);
        driveTrainEncoderRight.setDistancePerPulse(1.0);
        driveTrainEncoderRight.setPIDSourceParameter(PIDSourceParameter.kRate);
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

}
