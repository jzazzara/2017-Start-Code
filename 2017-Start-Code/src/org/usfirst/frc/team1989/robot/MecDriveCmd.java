package org.usfirst.frc.team1989.robot;
import edu.wpi.first.wpilibj.*;
import com.ctre.CANTalon;
public class MecDriveCmd implements cmd {
	 
	RobotDrive driveTrain;
	JsScaled driveStick;
	
	public MecDriveCmd(SpeedController frontLeftMotor, SpeedController frontRightMotor, SpeedController backLeftMotor,
			SpeedController backRightMotor, JsScaled driveStick){
		driveTrain = new RobotDrive(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor);
		this.driveStick = driveStick;
	}
	public MecDriveCmd(int frontLeftMotor, int frontRightMotor, int backLeftMotor, int backRightMotor, JsScaled driveStick){
		driveTrain = new RobotDrive(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor);
		this.driveStick = driveStick;
	}
	

	public void autonomousInit(){}
	public void autonomousPeriodic() {
		driveTrain.mecanumDrive_Cartesian(driveStick.pX, driveStick.pY, driveStick.pTwist,0);//Last 0 is gyro angle needs to be checked if we get one
	}
	public void teleopInit(){}
	public void teleopPeriodic(){
		driveTrain.mecanumDrive_Cartesian(driveStick.sgetX(), driveStick.sgetY(), driveStick.sgetTwist(), 0);
	}
	public void testInit(){}
	public void testPeriodic(){}
	    
}
