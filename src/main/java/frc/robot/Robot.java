package frc.robot;

//import java.util.Set;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import com.revrobotics.CANSparkMax;
//import com.revrobotics.CANSparkMax.IdleMode;
//import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import com.ctre.phoenix.motorcontrol.ControlMode;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.cameraserver.CameraServer;
//import edu.wpi.first.cameraserver.CameraServerShared;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;

public class Robot extends TimedRobot {

  // Definitions for the hardware. Change this if you change what stuff you have
  // plugged in
  TalonSRX driveLeftA = new TalonSRX(1);
  TalonSRX driveLeftB = new TalonSRX(2);
  TalonSRX driveRightA = new TalonSRX(3);
  TalonSRX driveRightB = new TalonSRX(4);
  // TalonSRX renee = new TalonSRX(5);
  // CANSparkMax arm = new CANSparkMax(5, MotorType.kBrushless);
  // TalonSRX intake = new TalonSRX(6);

  UsbCamera camera1;

  Joystick driverController = new Joystick(0);
  Joystick driverController2 = new Joystick(1);

  // Constants for controlling the arm. consider tuning these for your particular
  // robot
  final double armHoldUp = 0.08;
  final double armHoldDown = 0.13;
  final double armTravel = 0.5;

  final double armTimeUp = 0.5;
  final double armTimeDown = 0.35;

  // Varibles needed for the code
  boolean armUp = true; // Arm initialized to up because that's how it would start a match
  boolean burstMode = false;
  double lastBurstTime = 0;

  double autoStart = 0;
  boolean goForAuto = false;
  private CvSource outputStream;
  private CvSink cvSink;

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */

  @Override
  public void robotInit() {

    
    CameraServer.startAutomaticCapture();
    this.cvSink = CameraServer.getVideo();
    // Setup a CvSource. This will send images back to the Dashboard
    this.outputStream = CameraServer.putVideo("Rectangle", 320, 240);

    // Set the resolution
    // camera1 = new UsbCamera("USB Camera 0", 0);
    // camera1.setResolution(320, 240);
    // Get a CvSink. This will capture Mats from the camera

  driveLeftA.setInverted(true);
  // driveLeftA.burnFlash();
  driveLeftB.setInverted(true);
  // driveLeftB.burnFlash();
  driveRightA.setInverted(false);
  // driveRightA.burnFlash();
  driveRightB.setInverted(false);
  // driveRightB.burnFlash();

  // arm.setInverted(false);
  // arm.setIdleMode(IdleMode.kBrake);
  // arm.burnFlash();

  // add a thing on the dashboard to turn off auto if needed
  SmartDashboard.putBoolean("Go For Auto",false);
  goForAuto=!SmartDashboard.getBoolean("Go For Auto",false);

  }

  @Override
  public void autonomousInit() {
    // get a time for auton start to do events based on time later
    autoStart = Timer.getFPGATimestamp();
    // check dashboard icon to ensure good to do auto
    goForAuto = SmartDashboard.getBoolean("Go For Auto", false);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {

  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
  }

  public void camStuff(){
    // Configure motors to tudrn correct direction. You may have to invert some of
    // your motors
    Mat mat = new Mat();
    if (cvSink.grabFrame(mat) == 0) {
      outputStream.notifyError(cvSink.getError());
      return;
    }
    Imgproc.rectangle(mat, new Point(100, 100), new Point(400, 400), new Scalar(255, 255, 255), 5);
    // Give the output stream a new image to display
    outputStream.putFrame(mat);
}
  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    camStuff();
    // Set up arcade steer
    double leftinput = driverController2.getRawAxis(1);
    double rightinput = driverController.getRawAxis(1);

    double driveLeftPower = leftinput * 0.70;
    double driveRightPower = rightinput * 0.70;

    driveLeftA.set(TalonSRXControlMode.PercentOutput, driveLeftPower);
    driveLeftB.set(TalonSRXControlMode.PercentOutput, driveLeftPower);
    driveRightA.set(TalonSRXControlMode.PercentOutput, driveRightPower);
    driveRightB.set(TalonSRXControlMode.PercentOutput, driveRightPower);

    // Intake controls
    // if(driverController.getRawButton(5)){
    // renee.set(TalonSRXControlMode.PercentOutput, 1);;
    // }
    // if(driverController.getRawButton(6)){
    // driveRightA.set(TalonSRXControlMode.PercentOutput, 1);;
    // }
    // else if(driverController.getRawButton(7)){
    // intake.set(TalonSRXControlMode.PercentOutput, -1);
    // }
    // else{
    // intake.set(TalonSRXControlMode.PercentOutput, 0);
    // }

    // Arm Controls
    // if(armUp){
    // if(Timer.getFPGATimestamp() - lastBurstTime < armTimeUp){
    // arm.set(armTravel);
    // }
    // else{
    // arm.set(armHoldUp);
    // }
    // }
    // else{
    // if(Timer.getFPGATimestamp() - lastBurstTime < armTimeDown){
    // arm.set(-armTravel);
    // }
    // else{
    // arm.set(-armHoldDown);
    // }
    // }

    // if(driverController.getRawButtonPressed(6) && !armUp){
    // lastBurstTime = Timer.getFPGATimestamp();
    // armUp = true;
    // }
    // else if(driverController.getRawButtonPressed(8) && armUp){
    // lastBurstTime = Timer.getFPGATimestamp();
    // armUp = false;
    // }

  }

  @Override
  public void disabledInit() {
    // On disable turn off everything
    // done to solve issue with motors "remembering" previous setpoints after
    // reenable
    driveLeftA.set(TalonSRXControlMode.PercentOutput, 0);
    driveLeftB.set(TalonSRXControlMode.PercentOutput, 0);
    driveRightA.set(TalonSRXControlMode.PercentOutput, 0);
    driveRightB.set(TalonSRXControlMode.PercentOutput, 0);
    // arm.set(0);
    // intake.set(ControlMode.PercentOutput, 0);
  }

}