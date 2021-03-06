package com.rambots4571.deepspace.robot.command;

import com.rambots4571.deepspace.robot.Constants;
import com.rambots4571.deepspace.robot.subsystem.Drivetrain;
import com.rambots4571.deepspace.robot.subsystem.Intake;
import com.rambots4571.rampage.sensor.pid.SourceSupplier;
import com.rambots4571.rampage.vision.Limelight;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SeekCargo extends Command {
    private Drivetrain drivetrain = Drivetrain.getInstance();
    private Intake intake = Intake.getInstance();
    private PIDController turnController;
    private double kP = 0;
    private double kI = 0;
    private double kD = 0;

    public SeekCargo() {
        requires(drivetrain);
        requires(intake);
        Limelight.setPipeline(Constants.Pipelines.CARGO);
        turnController = new PIDController(kP, kI, kD, new SourceSupplier(
                Limelight::getXOffset), output -> drivetrain
                .drive(-output, +output));
        turnController.setInputRange(Limelight.TX_MIN, Limelight.TX_MAX);
        turnController.setOutputRange(-0.5, 0.5);
        turnController.setAbsoluteTolerance(0.3);
    }

    @Override
    protected void initialize() {
        turnController.reset();
        turnController.setSetpoint(0);
        SmartDashboard.putData("limelight cargo seeking PID", turnController);
        turnController.enable();
    }

    @Override
    protected void execute() {
        intake.setIntakePower(0.5);
        SmartDashboard.putNumber("limelight xOffset", Limelight.getXOffset());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        turnController.disable();
        drivetrain.stop();
        intake.setIntakePower(0);
    }
}
