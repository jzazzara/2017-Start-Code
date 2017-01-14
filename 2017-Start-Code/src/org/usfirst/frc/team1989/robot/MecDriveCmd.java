package org.usfirst.frc.team1989.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;

public class MecDriveCmd extends RobotDrive implements cmd {
    public String type = "MecDriveCmd";
    public ArrayList<cmd> list;
    private Timer t1 = new Timer();
    JsScaled driveStick;


    public MecDriveCmd(int leftMotorChannel, int rightMotorChannel) {
	super(leftMotorChannel, rightMotorChannel);
	// TODO Auto-generated constructor stub
    }

    public MecDriveCmd(SpeedController leftMotor, SpeedController rightMotor) {
	super(leftMotor, rightMotor);
	// TODO Auto-generated constructor stub
    }

    public MecDriveCmd(int frontLeftMotor, int rearLeftMotor,
	    int frontRightMotor, int rearRightMotor) {
	super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
	// TODO Auto-generated constructor stub
    }

    public MecDriveCmd(SpeedController frontLeftMotor, SpeedController rearLeftMotor, SpeedController frontRightMotor, SpeedController rearRightMotor, JsScaled driveStick) {
	super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
	this.driveStick = driveStick;
	// TODO Auto-generated constructor stub
    }

	@Override
    /*public void disabledInit() {
	// TODO Auto-generated method stub

    }*/

    //@Override
    public void autonomousInit() {
	// TODO Auto-generated method stub

    }

    @Override
    public void autonomousPeriodic() {
	// control auto with virtual p joystick values
	mecanumDrive_Cartesian(driveStick.pY, driveStick.pX, driveStick.pTwist, 0);

    }

    /*@Override
    public void DisabledPeriodic() {
	// TODO Auto-generated method stub
	this.mecanumDrive_Cartesian(0, 0, 0, 0);

    }*/

    public void testInit() {

    }

    public void teleopInit() {
	t1.stop();
	t1.reset();
	t1.start();

    }

    @Override
    public void teleopPeriodic() {
	// TODO Auto-generated method stub
	mecanumDrive_Cartesian(driveStick.sgetY()/2, driveStick.sgetX()/2,
		driveStick.sgetTwist()/2, 0);
//	display();

    }

    @Override
    public void testPeriodic() {
	// TODO Auto-generated method stub
	mecanumDrive_Cartesian(driveStick.sgetX()/2, driveStick.sgetY()/2,
		driveStick.sgetTwist()/2, 0);

    }

    public void Setjoystick(JsScaled js) {
	// TODO Auto-generated method stub
	this.driveStick = js;
    }

    public void display() {
	if (t1.get() > .5) {
	    t1.reset();
	    t1.start();
//	    SmartDashboard.putString("DB/String 9", "Y "
//		    + new Integer((int)(Math.floor(joystick.getY()*100))).toString()
//		    + " sY " +new Integer((int)(Math.floor(joystick.sgetY()*100))).toString());
	}

    }
}