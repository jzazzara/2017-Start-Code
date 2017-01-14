// This is an addition for the purposes of tryign to commit and sync.
//Martin's Code

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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * 
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot implements cmd{

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
		JsScaled driveStick = new JsScaled(0);
		JsScaled uStick = new JsScaled(1);//The uStick will stand for the utility joystick responsible for shooting and arm movement
		int autoStatus = 0;
		int autoMode = 0;
	
	// Instantiating Timer
	Timer t1 = new Timer();

	// Instantiating Servo
	Servo s1 = new Servo(0);

	// Instantiating Joysticks

	// Instantiating writmessage
	writemessage wmsg = new writemessage();

	// ArcadeDriveCMD Constructor - 4 motors
	MecDriveCmd aDrive = new MecDriveCmd(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor, driveStick);

	// WHAT THE HECK IS THIS!!!! NO SUPPORT IN CLASSES!
	// writemessage wmsg = new writemessage();

	// RangeFinder

	ShooterCmd shooter = new ShooterCmd(driveStick, s1);
	ArmsCmd arms = new ArmsCmd(driveStick);

	// ShooterCmd shooter2 = new ShooterCmd(driveStick, s1);
	// ArmsCmd arms2 = new ArmsCmd(driveStick);

	public void robotInit() {

		CameraServer server = CameraServer.getInstance();
		/*server.setQuality(50);
		server.startAutomaticCapture("cam1");*/
		// System.out.println("i'm Alive");
		// gyro = new adxrsmp(SPI.Port.kOnboardCS0); // try 1,2,3 to find the
		// Gyro
		// rf1 = new AnalogInput(3);
		// b_acc = new BuiltInAccelerometer();
		// Construct CMD List
		SharedStuff.cmdlist.add(aDrive);
		SharedStuff.cmdlist.add(shooter);
		SharedStuff.cmdlist.add(arms);
		// SharedStuff.cmdlist.add(shooter2);
		// SharedStuff.cmdlist.add(arms2);
		SharedStuff.cmdlist.add(wmsg);
		// SharedStuff.cmdlist.add(wmsg); // sb added last so that other objects
		// can update first

		// Limit Switches- In for now, will be changed to CAN network
		// frontLeftMotor.enableLimitSwitch(false, false);
		// frontRightMotor.enableLimitSwitch(false, false);
		// rearLeftMotor.enableLimitSwitch(false, false);
		// rearRightMotor.enableLimitSwitch(false, false);
		

		// Voltage Ramps - none for now
		// frontLeftMotor.setVoltageRampRate(driveramp);
		// frontRightMotor.setVoltageRampRate(driveramp);
		// rearLeftMotor.setVoltageRampRate(driveramp);
		// rearRightMotor.setVoltageRampRate(driveramp);

		// add ref to list

	}

	public void killswitches()
	{
		arms.killswitches();
		shooter.killswitches();
	}
	public void disabledInit(){
	}
	
    public void disabledPeriodic(){
    	// Disable all motors
    	frontLeftMotor.set(0);
    	frontRightMotor.set(0);
    	rearLeftMotor.set(0);
    	rearRightMotor.set(0);
    	frontLeftMotor.enableBrakeMode(false);
    	frontRightMotor.enableBrakeMode(false);
    	rearLeftMotor.enableBrakeMode(false);
    	rearRightMotor.enableBrakeMode(false);
    	shooter.DisabledPeriodic();
    	arms.disabledPeriodic();
    	
    }
	//
	public void autonomousInit() {
		// Output RangeFinder Distance
		// rangeFinder.setDistance();
		frontLeftMotor.maxI = 0;
		frontRightMotor.maxI = 0;
		rearLeftMotor.maxI = 0;
		rearRightMotor.maxI = 0;
		frontLeftMotor.overcurrent = 0;
		frontRightMotor.overcurrent = 0;
		rearLeftMotor.overcurrent = 0;
		rearRightMotor.overcurrent = 0;
		autoStatus = 0;
		autoMode = (SmartDashboard.getBoolean("DB/Button 0") ? 1 : 0)
				+ (SmartDashboard.getBoolean("DB/Button 1") ? 2 : 0)
				+ (SmartDashboard.getBoolean("DB/Button 2") ? 4 : 0)
				+ (SmartDashboard.getBoolean("DB/Button 2") ? 8 : 0);
		SharedStuff.msg[0] = "Automode " + autoMode;
		t1.stop();
		t1.reset();
		t1.start();
		for (int i = 0; i < SharedStuff.cmdlist.size(); i++) {
			SharedStuff.cmdlist.get(i).autonomousInit();
		}

	}

	public void autonomousPeriodic() {
		// Output RangeFinder Distance
		// rangeFinder.setDistance()
		SharedStuff.msg[2] = "c " + frontRightMotor.overcurrent + " " + frontRightMotor.factor;

		if (autoMode == 1) {
			shootDemo();
			//autoMode1(false);
		} else if (autoMode == 2) {
			autoMode1(true);
		}

		if (autoMode == 3) {
			autoMode3(false);
		}

		else if (autoMode == 4) {
			autoMode4(true);
		} else if (autoMode == 8) {
			autoMode8();
		}
		/*else if (autoMode == 10){//demo shoot mode needs testing
			shootDemo();
		}*/
		for (int i = 0; i < SharedStuff.cmdlist.size(); i++) {
			SharedStuff.cmdlist.get(i).autonomousPeriodic();
		}

	}

	// Back and through Low Bar
	public void autoMode3(boolean armup) {
		// Either arms up or down

		// driveStick.buttons[6] = armup;
		driveStick.buttons[4] = !armup;

		if (autoStatus == 0) {
			autoStatus = 1;
			driveStick.pY = .75;
			t1.stop();
			t1.reset();
			t1.start();
		} else if (autoStatus == 1) {
			driveStick.pY = .75;
			if (t1.get() > 2) {
				t1.stop();
				t1.reset();
				t1.start();
				frontLeftMotor.maxI = 12;
				frontRightMotor.maxI = 12;
				rearLeftMotor.maxI = 12;
				rearRightMotor.maxI = 12;
				frontLeftMotor.overcurrent = 0;
				frontRightMotor.overcurrent = 0;
				rearLeftMotor.overcurrent = 0;
				rearRightMotor.overcurrent = 0;
				autoStatus = 2;
			}
		} else if (autoStatus == 2) {
			if (frontRightMotor.overcurrent > 20 || rearRightMotor.overcurrent > 20 || frontLeftMotor.overcurrent > 20
					|| rearLeftMotor.overcurrent > 20) {
				autoStatus = 3;
			}

			driveStick.pY = .75;
			// Total distance from start
			if (t1.get() > 2.5) {
				t1.stop();
				t1.reset();
				t1.start();
				autoStatus = 3;
				frontLeftMotor.maxI = 12;
				frontRightMotor.maxI = 12;
				rearLeftMotor.maxI = 12;
				rearRightMotor.maxI = 12;
				frontLeftMotor.overcurrent = 0;
				frontRightMotor.overcurrent = 0;
				rearLeftMotor.overcurrent = 0;
				rearRightMotor.overcurrent = 0;
			}

		} else if (autoStatus == 3) {
			if (frontRightMotor.overcurrent > 20 || rearRightMotor.overcurrent > 20 || frontLeftMotor.overcurrent > 20
					|| rearLeftMotor.overcurrent > 20) {
				autoStatus = 4;
			}

			driveStick.pY = -.75;
			// Total distance from start
			if (t1.get() > 3.55) {
				t1.stop();
				t1.reset();
				t1.start();
				autoStatus = 4;
				frontLeftMotor.maxI = 12;
				frontRightMotor.maxI = 12;
				rearLeftMotor.maxI = 12;
				rearRightMotor.maxI = 12;
				frontLeftMotor.overcurrent = 0;
				frontRightMotor.overcurrent = 0;
				rearLeftMotor.overcurrent = 0;
				rearRightMotor.overcurrent = 0;
			}

		} else if (autoStatus == 4) {
			if (frontRightMotor.overcurrent > 20 || rearRightMotor.overcurrent > 20 || frontLeftMotor.overcurrent > 20
					|| rearLeftMotor.overcurrent > 20) {
				autoStatus = 9;
			}

			driveStick.pY = 0;
			// Total distance from start
			if (t1.get() > 2) {
				t1.stop();
				t1.reset();
				t1.start();
				autoStatus = 9;
			}

		} else if (autoStatus == 9) {
			autoStatus = 9;
			driveStick.pY = 0;
		}
	}

	public void autoMode8() {
		// Either arms up or down
		// driveStick.buttons[6] = true;
		driveStick.buttons[4] = false;

		if (autoStatus == 0) {
			autoStatus = 1;
			driveStick.pY = .75;
			t1.stop();
			t1.reset();
			t1.start();
		} else if (autoStatus == 1) {
			driveStick.pY = .75;
			if (t1.get() > 1) {
				t1.stop();
				t1.reset();
				t1.start();
				frontLeftMotor.maxI = 4;
				frontRightMotor.maxI = 4;
				rearLeftMotor.maxI = 4;
				rearRightMotor.maxI = 4;
				frontLeftMotor.overcurrent = 0;
				frontRightMotor.overcurrent = 0;
				rearLeftMotor.overcurrent = 0;
				rearRightMotor.overcurrent = 0;
				autoStatus = 2;
			}
		} else if (autoStatus == 2) {
			if (frontRightMotor.overcurrent > 10 || rearRightMotor.overcurrent > 10 || frontLeftMotor.overcurrent > 10
					|| rearLeftMotor.overcurrent > 10) {
				t1.stop();
				t1.reset();
				t1.start();

				autoStatus = 3;
			}

			driveStick.pY = .55;
			// Total distance from start
			if (t1.get() > 3) {
				t1.stop();
				t1.reset();
				t1.start();
				autoStatus = 3;
			}

		} else if (autoStatus == 3) {
			driveStick.pY = -.5;
			frontLeftMotor.maxI = 22;
			frontRightMotor.maxI = 22;
			rearLeftMotor.maxI = 22;
			rearRightMotor.maxI = 22;
			frontLeftMotor.overcurrent = 0;
			frontRightMotor.overcurrent = 0;
			rearLeftMotor.overcurrent = 0;
			rearRightMotor.overcurrent = 0;
			driveStick.buttons[6] = false;
			driveStick.buttons[4] = true;

			if (t1.get() > .33) {
				autoStatus = 4;
				t1.stop();
				t1.reset();
				t1.start();

			}
		} else if (autoStatus == 4) {
			driveStick.pY = 0;
			driveStick.buttons[6] = false;
			driveStick.buttons[4] = true;

			if (t1.get() > 1) {
				autoStatus = 5;
				t1.stop();
				t1.reset();
				t1.start();

			}
		} else if (autoStatus == 5) {
			driveStick.pY = .75;
			driveStick.buttons[6] = false;
			driveStick.buttons[4] = true;

			if (t1.get() > 2.5) {
				autoStatus = 9;
				t1.stop();
				t1.reset();
				t1.start();

			}
		} else if (autoStatus == 9) {
			autoStatus = 10;
			driveStick.buttons[4] = false;
			driveStick.pY = 0;
		}
	}

	/*
	 * AutoMode 4: Rock Wall
	 */
	public void autoMode4(boolean armup) {
		// Either arms up or down

		// driveStick.buttons[6] = armup;
		driveStick.buttons[4] = !armup;

		if (autoStatus == 0) {
			autoStatus = 1;
			driveStick.pY = .75;
			t1.stop();
			t1.reset();
			t1.start();
		} else if (autoStatus == 1) {
			driveStick.buttons[4] = armup;
			driveStick.pY = .75;
			if (t1.get() > 2) {
				t1.stop();
				t1.reset();
				t1.start();
				frontLeftMotor.maxI = 12;
				frontRightMotor.maxI = 12;
				rearLeftMotor.maxI = 12;
				rearRightMotor.maxI = 12;
				frontLeftMotor.overcurrent = 0;
				frontRightMotor.overcurrent = 0;
				rearLeftMotor.overcurrent = 0;
				rearRightMotor.overcurrent = 0;
				autoStatus = 2;
			}
		} else if (autoStatus == 2) {
			if (frontRightMotor.overcurrent > 20 || rearRightMotor.overcurrent > 20 || frontLeftMotor.overcurrent > 20
					|| rearLeftMotor.overcurrent > 20) {
				autoStatus = 3;
			}

			driveStick.pY = .75;
			// Total distance from start
			if (t1.get() > 2.5) {
				t1.stop();
				t1.reset();
				t1.start();
				autoStatus = 3;
			}

		} else if (autoStatus == 3) {
			autoStatus = 3;
			driveStick.pY = 0;
		}
	} /*
		 * Auto Mode 1: Option 1: Arms Down Option 2: Arms Don't Move
		 */

	public void autoMode1(boolean armup) {
		// Either arms up or down

		// driveStick.buttons[6] = armup;
		driveStick.buttons[4] = !armup;

		if (autoStatus == 0) {
			autoStatus = 1;
			driveStick.pY = .75;
			t1.stop();
			t1.reset();
			t1.start();
		} else if (autoStatus == 1) {
			driveStick.pY = .75;
			if (t1.get() > 2) {
				t1.stop();
				t1.reset();
				t1.start();
				frontLeftMotor.maxI = 12;
				frontRightMotor.maxI = 12;
				rearLeftMotor.maxI = 12;
				rearRightMotor.maxI = 12;
				frontLeftMotor.overcurrent = 0;
				frontRightMotor.overcurrent = 0;
				rearLeftMotor.overcurrent = 0;
				rearRightMotor.overcurrent = 0;
				autoStatus = 2;
			}
		} else if (autoStatus == 2) {
			if (frontRightMotor.overcurrent > 20 || rearRightMotor.overcurrent > 20 || frontLeftMotor.overcurrent > 20
					|| rearLeftMotor.overcurrent > 20) {
				autoStatus = 3;
			}

			driveStick.pY = .75;
			// Total distance from start
			if (t1.get() > 2.5) {
				t1.stop();
				t1.reset();
				t1.start();
				autoStatus = 3;
			}

		} else if (autoStatus == 3) {
			autoStatus = 3;
			driveStick.pY = 0;
		}
	}
	
	public void shootDemo(){
		
		//System.out.println("Brendan's Case");
		
		if (autoStatus == 0) {
			autoStatus = 1;
			driveStick.pY = .75;
			t1.stop();
			t1.reset();
			t1.start();
		} else if (autoStatus == 1) {
			
			if(t1.get() > 4){
				autoStatus = 2;
				t1.stop();
				t1.reset();
				t1.start();
				driveStick.pY = 0;
				shooter.elevator.set(-0.6);
			}
		}
		else if (autoStatus == 2){
			if (t1.get() > .75){
				autoStatus = 3;
				t1.stop();
				t1.reset();
				t1.start();
				shooter.elevator.set(0);
				shooter.shootMotor1.set(1);
				shooter.shootMotor2.set(-1);
			}
		}
		else if(autoStatus == 3){
			if (t1.get() > 1){
				autoStatus = 4;
				t1.stop();
				t1.reset();
				t1.start();
				shooter.s1.set(1);
			}
		}
		else if(autoStatus == 4){
			if (t1.get() > .5){
				t1.stop();
				shooter.s1.set(0);
				shooter.shootMotor1.set(0);
				shooter.shootMotor2.set(0);
			}
		}
	}

	public void teleopInit() {
		t1.start();
		state = 0;
		frontLeftMotor.maxI = 0;
		frontRightMotor.maxI = 0;
		rearLeftMotor.maxI = 0;
		rearRightMotor.maxI = 0;
	}

	/**
	 * This function is called periodically during operator control
	 */

	public void teleopPeriodic() {
		if (driveStick.getRawButton(10))
		{
			this.killswitches();
		}
		//next 3 lines were the arm test in the pit
//		armMotor1.set(driveStick.getY());
//		armMotor2.set(driveStick.getY());
//		return;
		// gyrotest should display stuff at th display in string 2 on down

//		double distance = rf1.getVoltage() * 102.4;
			for (int i = 0; i < SharedStuff.cmdlist.size(); i++) {
				SharedStuff.cmdlist.get(i).teleopPeriodic();
		}
	}

	public void testInit() {
		// Output RangeFinder Distance
		// rangeFinder.setDistance();
		t1.start();
		state = 0;
	}

	/**
	 * This function is called periodically during test mode
	 * 
	 */
	public void testPeriodic() {
		// Output RangeFinder Distance
		// rangeFinder.setDistance();

		/*if (driveStick.getRawButton(7)) {
			state = 1;
		}
		if (state > 0) {
			if (state == 1) {
				state = 2;
				armMotor1.set(-.8);
				armMotor1.set(-.8);
				t1.reset();
				t1.stop();
				t1.start();
				driveStick.pY = 0;
				aDrive.autonomousPeriodic();
			} else if (state == 2) {
				armMotor1.set(-.5);
				armMotor2.set(-.5);
				aDrive.autonomousPeriodic();
				if (t1.get() > .5) {
					driveStick.pY = .65;
					state = 3;
				}
			} else if (state == 3) {
				armMotor1.set(-.5);
				armMotor2.set(-.5);
				aDrive.autonomousPeriodic();
				if (t1.get() > 3) {
					t1.reset();
					t1.stop();
					driveStick.pY = 0;
					armMotor1.set(0);
					armMotor1.set(0);
					state = 0;

				}

			}
		} else {
			aDrive.testPeriodic();
			// wmsg.testPeriodic();
			// Servo Logic
			if (driveStick.getRawButton(5) == true) {
				s1.set(0);
			} else if (driveStick.getRawButton(6) == true) {
				s1.set(1);
			}

			// Shooting Logic
			if (driveStick.getRawButton(2) == true) {

				// Motors for pickup
				shootMotor1.set(-.35);
				shootMotor2.set(.35);
			} else if (driveStick.getRawButton(1)) {

				// Motors for shoot
				shootMotor1.set(1);
				shootMotor2.set(-1);
			} else {
				shootMotor1.set(0);
				shootMotor2.set(0);
			}

			// Elevator Logic
			if (driveStick.getRawButton(3) == true) {
				elevator.set(.2);
			} else if (driveStick.getRawButton(4)) {
				elevator.set(-.2);
			} else {
				elevator.set(-.05);
			}
		}
		// Debug Output
		SharedStuff.led[2] = true;
		SharedStuff.msg[0] = " Left I " + frontLeftMotor.getOutputCurrent();
		SharedStuff.msg[5] = "right I " + frontRightMotor.getOutputCurrent();
		SharedStuff.msg[1] = " Left O " + frontLeftMotor.getOutputVoltage();
		SharedStuff.msg[6] = "right O " + frontRightMotor.getOutputVoltage();
		SharedStuff.msg[2] = " Left V " + frontLeftMotor.getBusVoltage();
		SharedStuff.msg[7] = "right V " + frontRightMotor.getBusVoltage();
		/*SharedStuff.msg[3] = " Enc pos " + elevator.getEncPosition();
		SharedStuff.msg[8] = "getpos" + elevator.getPosition();
		SharedStuff.msg[4] = " sh1 I " + shootMotor1.getOutputCurrent();
		SharedStuff.msg[9] = "right S " + shootMotor2.getOutputCurrent();*/
		//wmsg.testPeriodic();
	}

}