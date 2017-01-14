package org.usfirst.frc.team1989.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot implements cmd{
	
	
	CANTalon1989 frontLeft = new CANTalon1989(9);
	CANTalon1989 frontRight = new CANTalon1989(1);
	CANTalon1989 backLeft = new CANTalon1989(2);
	CANTalon1989 backRight = new CANTalon1989(3);
	JsScaled driveStick = new JsScaled(0);
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	MecDrive mDrive = new MecDrive(frontLeft, frontRight, backLeft, backRight, driveStick);
	
	@Override
	public void robotInit() {
	
	}

	
	@Override
	public void autonomousInit() {
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		
	
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		for (int i = 0; i < SharedStuff.cmdlist.size(); i++) {
			SharedStuff.cmdlist.get(i).teleopPeriodic();
	}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

