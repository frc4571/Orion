package org.usfirst.frc.team4571.robot.commands.teleop

import edu.wpi.first.wpilibj.command.Command
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import org.usfirst.frc.team4571.robot.Robot
import org.usfirst.frc.team4571.robot.subsystems.DriveSystem

object TeleOpDrive : Command() {
    init {
        requires(DriveSystem)
    }

    private fun log() {
        SmartDashboard.putNumber("Left Joystick", Robot.leftStick.yAxis)
        SmartDashboard.putNumber("Right Joystick", Robot.rightStick.yAxis)

        SmartDashboard.putNumber("Top Left Motor Output",
                                 DriveSystem.topLeftMotorSpeed)
        SmartDashboard.putNumber("Bottom Left Motor Output",
                                 DriveSystem.bottomLeftMotorSpeed)
        SmartDashboard.putNumber("Top Right Motor Output",
                                 DriveSystem.topRightMotorSpeed)
        SmartDashboard.putNumber("Bottom Right Motor Output",
                                 DriveSystem.bottomRightMotorSpeed)
    }

    override fun execute() {
        DriveSystem.drive(Robot.leftStick.yAxis, Robot.rightStick.yAxis)
        log()
    }

    override fun isFinished(): Boolean = false

    override fun end() = DriveSystem.stop()
}