/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotRunner extends IterativeRobot {

    //Gunner is Awesome!
    //DRIVE FIELDS BELOW!
    private static final Joystick DRIVE_JOYSTICK = new Joystick(1);
    private CANJaguar fljag;  //Front Left Wheel Jag
    private CANJaguar rljag; //Rear Left Wheel Jag
    private CANJaguar frjag; //Front Right Wheel Jag
    private CANJaguar rrjag; //Rear Right Wheel Jag
    private ADXL345_I2C accel = new ADXL345_I2C(1, ADXL345_I2C.DataFormat_Range.k2G);
    ;
    //ARM FIELDS BELOW!
    private static final Joystick ARM_JOYSTICK = new Joystick(2);
    private JoystickButton launchButt = new JoystickButton(ARM_JOYSTICK, 3);
    private JoystickButton retractButt = new JoystickButton(ARM_JOYSTICK, 2);
    private JoystickButton grabButt = new JoystickButton(ARM_JOYSTICK, 4); //4 on left side of joy, 5 on right side. in <-- out -->
    private JoystickButton releaseButt = new JoystickButton(ARM_JOYSTICK, 5);
    private Victor armVictor = new Victor(1);
    private Victor grabVictor = new Victor(2);
    private Victor launchVic = new Victor(3);
    //SWITCHES BELOW!
    private DigitalInput gFLimitSwitch = new DigitalInput(1);
    private DigitalInput gBLimitSwitch = new DigitalInput(2);
    private DigitalInput launchLimitSwitch = new DigitalInput(3);
    //TIME FOR BUSINESS! COMPONENTS INSTANTIATED BELOW!!
    private DriveComponent driveComp;
    private GrabComponent grabComp;
    private LaunchComponent launchComp;
    private LiftComponent liftComp;
    private CameraComponent cameraComp;
    private RobotComponent[] components = {driveComp, grabComp, launchComp, liftComp, cameraComp};
    //Put all components above in an array to traverse later

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        //Jag instantiations below
        try {
            fljag = new CANJaguar(2); //Front Left Wheel Jag
            rljag = new CANJaguar(3); //Rear Left Wheel Jag
            frjag = new CANJaguar(4); //Front Right Wheel Jag
            rrjag = new CANJaguar(5); //Rear Right Wheel Jag
            driveComp = new DriveComponent(DRIVE_JOYSTICK, fljag, rljag, frjag, rrjag, accel);

        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        grabComp = new GrabComponent(ARM_JOYSTICK, grabButt, releaseButt, grabVictor);
        launchComp = new LaunchComponent(ARM_JOYSTICK, launchButt, retractButt, launchVic);
        liftComp = new LiftComponent(ARM_JOYSTICK, armVictor, gFLimitSwitch, gBLimitSwitch);
        cameraComp = new CameraComponent();

//Run each component's initialize method
        for (int i = 0; i < components.length; i++) {
            components[i].initialize();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousInit() {
        for (int i = 0; i < components.length; i++) {
            components[i].autonomousInit();
        }
    }

    public void autonomousPeriodic() {
        for (int i = 0; i < components.length; i++) {
            components[i].autonomousPeriodic();
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void disabledInit() {
        for (int i = 0; i < components.length; i++) {
            components[i].disabledInit();
        }
    }

    public void disabledPeriodic() {
        for (int i = 0; i < components.length; i++) {
            components[i].disabledPeriodic();
        }
    }

    public void teleopInit() {
        for (int i = 0; i < components.length; i++) {
            components[i].teleopInit();
        }
    }

    public void teleopPeriodic() {
        for (int i = 0; i < components.length; i++) {
            components[i].teleopPeriodic();
        }
    }
    /**
     * This function is called periodically during test mode
     */
}