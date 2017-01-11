package org.usfirst.frc.team1989.robot;

// All Imports - Will remove unecessary later
import edu.wpi.first.wpilibj.Timer;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Servo;

public class ShooterCmd implements cmd {
	
	CANTalon shootMotor1 = new CANTalon(2);
	CANTalon shootMotor2 = new CANTalon(8);
	
	CANTalon elevator = new CANTalon(5);
	JsScaled stick;
	Servo s1;
	Timer t1 = new Timer();
	int lastaction = 0; //0 off, 1 pickup 2 spinup
	double lasti =0.000; //last current	
	double nexttimer = 0.000;
	
	public ShooterCmd (JsScaled stick, Servo s1){
		this.stick = stick;
		this.s1 = s1;
		shootMotor1.enableLimitSwitch(false, false);
		shootMotor2.enableLimitSwitch(false, false);
	}
	public void killswitches(){
		 elevator.enableLimitSwitch(false, false);
	}

	public void elevatorOperation(){
		
		//Up ??
		if(stick.getPOV(0) == 180){
			elevator.set(.4);
		
		// Down ??
		}else if(stick.getPOV(0) == 0){
			elevator.set(-.4);
		}else{
			elevator.set(-.04);
		}
		
	}
	
	public void shootMotorOperation(){
		//so the shooter needs a timer and a state and some logic depending on state
//		SharedStuff.msg[0] =" Left s I " + shootMotor1.getOutputCurrent();
//		SharedStuff.msg[5] = "lasti " + lasti;

		if(stick.getRawButton(5) == true){
			SharedStuff.led[1] = false;
			this.lastaction = 1;
			
			shootMotor1.set(-.35);
			shootMotor2.set(.35);
			elevator.set(.5);
			SharedStuff.led[0] = false;
			lastaction = 0;
			if (shootMotor1.isFwdLimitSwitchClosed() ||shootMotor1.isRevLimitSwitchClosed())
			{
				SharedStuff.led[1] = true;
			}
			}
		else if (stick.getRawButton(3)){
			SharedStuff.led[1] = false;
			if(lastaction != 2){
				t1.stop();
				t1.reset();
				t1.start();
				nexttimer = t1.get() + 1.0;
				lastaction = 2;
			}
			else if(t1.get() > nexttimer && lastaction ==2){
				nexttimer = t1.get() + .3;
				if(Math.abs(lasti- shootMotor1.getOutputCurrent()) < 0.5){
					SharedStuff.led[0] = true;
					lastaction = 1;
				}
				lasti = shootMotor1.getOutputCurrent();
			}
			// CHANGE WAS UNDONE 5/6/16
			shootMotor1.set(1);
			shootMotor2.set(-1);
		}
		else
		{
			lastaction = 0;
			SharedStuff.led[0] = false;
			shootMotor1.set(0);
			shootMotor2.set(0);
			this.lastaction = 0;
//			SharedStuff.led[1] = false;
		}
		
	}
	
	public void servoOperation(){
		
		if(stick.getRawButton(1) == true){
			s1.set(1);
			t1.start();
			
			
		}
	}
	/* Teleop Init and Teleop Periodic */
	public void teleopInit(){
		// Once we have a limit switch then make the elevator start at the bottom.
		s1.set(0);
	}
    public void teleopPeriodic(){
    	if(t1.get() > .2 ){
    		s1.set(0);
    		t1.stop();
    		t1.reset();
    	}
		
    	servoOperation();
    	elevatorOperation();
    	shootMotorOperation();
    }

    
    public void autonomousPeriodic(){}
    public void DisabledPeriodic(){
    	shootMotor1.enableBrakeMode(false);
    	shootMotor2.enableBrakeMode(false);
    	elevator.enableBrakeMode(true);
    	shootMotor1.set(0);
    	shootMotor2.set(0);
    	elevator.set(0);
    }
    public void testInit(){}
    public void testPeriodic(){}
    public void disabledInit(){}
    public void autonomousInit(){}
}