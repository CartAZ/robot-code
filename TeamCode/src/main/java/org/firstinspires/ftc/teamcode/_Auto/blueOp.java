package org.firstinspires.ftc.teamcode._Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="blueOp", group="Autonomous")
public class blueOp extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    // Declare the motor matrix
    private DcMotor[][] motors = new DcMotor[2][2];
    // Set default power to 100%
    private float motorPower = 1.f;
    private Servo servo = null;
    private ColorSensor colorSensor;

    @Override
    public void init() {
        // Set up the motor matrix (that sounds cool)
        motors[0][0] = hardwareMap.dcMotor.get("frontLeft");
        motors[0][1] = hardwareMap.dcMotor.get("frontRight");
        motors[1][0] = hardwareMap.dcMotor.get("backLeft");
        motors[1][1] = hardwareMap.dcMotor.get("backRight");
        servo = hardwareMap.get(Servo.class, "jewelArm");
        colorSensor = hardwareMap.colorSensor.get("colorSensor");
        // The motors on the left side of the robot need to be in reverse mode
        for (DcMotor[] motor : motors) {
            motor[0].setDirection(DcMotor.Direction.REVERSE);
        }
        // Being explicit never hurt anyone, right?
        for (DcMotor[] motor : motors) {
            motor[1].setDirection(DcMotor.Direction.FORWARD);
        }

        motors[1][1].setDirection(DcMotor.Direction.REVERSE);
    }

    public void loop(){


        //Kill ten seconds. -9. thats 1. quick mafs
        runtime.reset();
        while(runtime.seconds()<1);
        runtime.reset();
        while(runtime.milliseconds()<700) {
            servo.setPosition(1);
        }

        while(runtime.seconds()<2);

        telemetry.addData("Luminosity", colorSensor.alpha());
        telemetry.addData("Red sensor", colorSensor.red());
        telemetry.addData("Blue sensor", colorSensor.blue());
        runtime.reset();
        if(colorSensor.blue() - colorSensor.red() >= 0){
            telemetry.addData("Color Blue", colorSensor.blue() - colorSensor.red());
            runtime.reset();
            while(runtime.milliseconds()<200){
                // Loop through front and back motors
                for(DcMotor[] motor : motors){
                    // Set left motor power
                    motor[0].setPower(-5);
                    // Set right motor power
                    motor[1].setPower(5);
                }
            }
            runtime.reset();
            while(runtime.milliseconds()<1000) {
                servo.setPosition(0);
            }
//            runtime.reset();
//            while(runtime.milliseconds()<600){
//                // Loop through front and back motors
//                for(DcMotor[] motor : motors){
//                    // Set left motor power
//                    motor[0].setPower(5);
//                    // Set right motor power
//                    motor[1].setPower(-5);
//                }
//            }
        }
        else if(colorSensor.red() - colorSensor.blue() >= 10){
            telemetry.addData("Color Red", colorSensor.red() - colorSensor.blue());
            runtime.reset();
            while(runtime.milliseconds()<200){
                // Loop through front and back motors
                for(DcMotor[] motor : motors){
                    // Set left motor power
                    motor[0].setPower(5);
                    // Set right motor power
                    motor[1].setPower(-5);
                }
            }
            runtime.reset();
            while(runtime.milliseconds()<1000) {
                servo.setPosition(0);
            }
//            runtime.reset();
//            while(runtime.milliseconds()<600){
//                // Loop through front and back motors
//                for(DcMotor[] motor : motors){
//                    // Set left motor power
//                    motor[0].setPower(-5);
//                    // Set right motor power
//                    motor[1].setPower(5);
//                }
//            }
        }
        else{
            if(colorSensor.red() > colorSensor.blue()){
                telemetry.addData("Too close, red bigger than blue by", colorSensor.red() - colorSensor.blue());
                runtime.reset();
                while(runtime.milliseconds()<30000) {
                    servo.setPosition(0);
                    break;
                }
            }
            else{
                telemetry.addData("Colors are equal", 0);
                runtime.reset();
                while(runtime.milliseconds()<30000) {
                    servo.setPosition(0);
                    break;
                }
            }
        }

        runtime.reset();
        /*while(runtime.milliseconds()<570){
            //Loop through front and back motors
            for(DcMotor[] motor : motors){
                //Set left motor power
                motor[0].setPower(-100);
                //  Set right motor power
                motor[1].setPower(-100);
            }
        }*/

        runtime.reset();
        // Loop through front and back motors
        for(DcMotor[] motor : motors){
            // Set left motor power
            motor[0].setPower(0);
            // Set right motor power
            motor[1].setPower(0);
        }
    }
    public void stop(){

    }
}
