package org.usfirst.frc.team1989.robot;

//import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
//import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.interfaces.Accelerometer;
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

	int state = 0;
	AnalogInput rf1;
	double driveramp = 6.0;

	// Not sure this is needed - will talk to Mr. Pirringer about it
		public String type = ""; // holds class type
		public static double Kp = 0.03; // const for multiplying gyro angle 
		
		// Instantiating TalonSRX Motors
		CANTalon1989 frontLeftMotor = new CANTalon1989(2);
		CANTalon1989 frontRightMotor = new CANTalon1989(1);
		CANTalon1989 rearLeftMotor = new CANTalon1989(6);
		CANTalon1989 rearRightMotor = new CANTalon1989(5);
		
// gyro;
//		AnalogInput rf1 = new AnalogInput(0);
//		Accelerometer b_acc;
		
		JsScaled uStick = new JsScaled(1);//The uStick will stand for the utility joystick responsible for shooting and arm movement
		int autoStatus = 0;
		int autoMode = 0;

	// Instantiating Timer
	Timer t1 = new Timer();

	// Instantiating Servo
	Servo s1 = new Servo(0);

	// Instantiating Joysticks

	

	// ArcadeDriveCMD Constructor - 4 motors
	MecDriveCmd aDrive = new MecDriveCmd(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor, driveStick);

	// WHAT THE HECK IS THIS!!!! NO SUPPORT IN CLASSES!
	// writemessage wmsg = new writemessage();

	// RangeFinder



	// ShooterCmd shooter2 = new ShooterCmd(driveStick, s1);
	// ArmsCmd arms2 = new ArmsCmd(driveStick);

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

