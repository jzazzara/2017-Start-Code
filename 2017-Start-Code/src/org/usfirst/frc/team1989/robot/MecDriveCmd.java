package org.usfirst.frc.team1989.robot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;

import com.ctre.CANTalon;
public class MecDriveCmd implements cmd {
	 
	RobotDrive driveTrain;
	JsScaled driveStick;
	Boolean pwmBoolean = false;
	Encoder backLeftWheel;
	Encoder backRightWheel;
	long encoderLeftCount;
	long encoderRightCount;
	
	public MecDriveCmd(SpeedController frontLeftMotor, SpeedController backLeftMotor, SpeedController frontRightMotor,
		SpeedController backRightMotor, JsScaled driveStick){
		driveTrain = new RobotDrive(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
		this.driveStick = driveStick;
	}
	public MecDriveCmd(int frontLeftMotor, int backLeftMotor, int frontRightMotor, int backRightMotor, JsScaled driveStick){
		driveTrain = new RobotDrive(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
		this.driveStick = driveStick;
	}
	

	public void autonomousInit(){}
	public void autonomousPeriodic() {
		driveTrain.mecanumDrive_Cartesian(driveStick.pX, driveStick.pY, driveStick.pTwist,0);//Last 0 is gyro angle needs to be checked if we get one
	}
	public void teleopInit(){}
	public void teleopPeriodic(){
		
		
			driveTrain.mecanumDrive_Cartesian(driveStick.sgetX()/2, driveStick.sgetY()/2, driveStick.sgetTwist(), 0);
		
		
	}
	public void testInit(){}
	public void testPeriodic(){
		
		if(driveStick.sgetTwist() < 0.1 && driveStick.sgetX() < 0.1 && driveStick.sgetY() > 0.1){
			if(encoderLeftCount < encoderRightCount){
				driveTrain.mecanumDrive_Cartesian(driveStick.sgetX()/2, driveStick.sgetY()/2, -0.05, 0);
			}
			else if(encoderLeftCount > encoderRightCount){
				driveTrain.mecanumDrive_Cartesian(driveStick.sgetX()/2, driveStick.sgetY()/2, 0.05, 0);
			}
			else{
				if(encoderLeftCount < encoderRightCount){
					driveTrain.mecanumDrive_Cartesian(driveStick.sgetX()/2, driveStick.sgetY()/2, driveStick.sgetTwist(), 0);
				}
			}
		} else{
			driveTrain.mecanumDrive_Cartesian(driveStick.sgetX()/2, driveStick.sgetY()/2, driveStick.sgetTwist(), 0);
		}
	}
	

	    
}
