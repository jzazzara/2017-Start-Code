package org.usfirst.frc.team1989.robot;

import edu.wpi.first.wpilibj.Servo;

public class CameraControl implements cmd {

	
	Servo servoX;
	Servo servoY;
	double sxValue = 0;
	double syValue = 0;
	JsScaled uStick;
	
	
	public CameraControl(Servo servoX, Servo servoY, JsScaled uStick ){
		this.servoX = servoX;
		this.servoY = servoY;
		this.uStick = uStick;
	}
	
	public void cameraMovement(){
		if(uStick.sgetX() < -.15){
			sxValue = sxValue - 0.05;
			servoX.set(sxValue);
		}
		else if (uStick.sgetX() > .15){
			sxValue = sxValue + 0.05;
			servoX.set(sxValue);
		}
		else if (uStick.sgetY() < -.15){
			syValue = syValue - 0.05;
			servoY.set(syValue);
		}
		else if (uStick.sgetY() > .15){
			syValue = syValue + 0.05;
			servoY.set(syValue);
		}
		
	}
	
	public void cameraReset(){
		
			servoX.setAngle(0);
			servoY.setAngle(0);
		
	}
	
	
	@Override
	public void autonomousInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void autonomousPeriodic() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testPeriodic() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub
		cameraReset();
	}

	@Override
	public void teleopPeriodic() {
		// TODO Auto-generated method stub
		
		if(uStick.getRawButton(2) == true){
			cameraReset();
		} else{
			cameraMovement();
		}
	}
	
	

}
