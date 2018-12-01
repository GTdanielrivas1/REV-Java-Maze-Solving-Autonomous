package org.firstinspires.ftc.teamcode;
import android.app.Activity;
import android.view.View;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import java.util.Locale;

@Autonomous (name = "TeamRocketGUATEMALA", group = "")

public class LaberintoAutonomo extends LinearOpMode {

    DcMotor m0, m1, m2, m3; //MOTORES
    ColorSensor color1, color2; //COMPLEMENTO DE COLOR
    DistanceSensor frontal, derechad, izquierdac, atrasc; //SENSORES

    public void runOpMode() throws InterruptedException {

        m0 = hardwareMap.get(DcMotor.class, "motor1");
        m1 = hardwareMap.get(DcMotor.class, "motor2");
        m2 = hardwareMap.get(DcMotor.class, "motor3");
        m3 = hardwareMap.get(DcMotor.class, "motor4");

        m0.setDirection(DcMotor.Direction.REVERSE); //reversa
        m1.setDirection(DcMotor.Direction.FORWARD); //normal
        m2.setDirection(DcMotor.Direction.FORWARD); //normal
        m3.setDirection(DcMotor.Direction.REVERSE); //reversa

        color1 = hardwareMap.get(ColorSensor.class, "color1"); //color
        color2 = hardwareMap.get(ColorSensor.class, "color2"); //color

        frontal = hardwareMap.get(DistanceSensor.class, "frontal"); //color
        derechad = hardwareMap.get(DistanceSensor.class, "derechad"); //distancia
        izquierdac = hardwareMap.get(DistanceSensor.class, "izquierdac"); //distancia
        atrasc = hardwareMap.get(DistanceSensor.class, "atrasc"); //color

        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        final double SCALE_FACTOR = 255;
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        double frente, derecha, izquierda, atras; //VARIABLES ASIGNADAS
        double veli = 0.9;
        double veld = 0.8;

        waitForStart();
        while (opModeIsActive()) {

            frente = frontal.getDistance(DistanceUnit.CM);
            derecha = derechad.getDistance(DistanceUnit.CM);
            izquierda = izquierdac.getDistance(DistanceUnit.CM);
            atras = atrasc.getDistance(DistanceUnit.CM);
            telemetry.addData("Adelante (centimetros)",
                    String.format(Locale.US, "%.02f", frente)); //IMPRESIONES DE VALORES EN LA TABLET
            telemetry.addData("Derecha (centimetros)",
                    String.format(Locale.US, "%.02f", derecha));
            telemetry.update();
            //       M0=MOTOR1     M1=MOTOR2      M2=MOTOR3           M3=MOTOR4
            // SI M1 Y M3 SON POSITIVOS VAN A IZQUIERDA, PERO SI M1 Y M3 SON NEGATIVOS VAN A DERECHA
            //sleep();
            //start();

            if (frente >= 10 && derecha <= 10) {
                m0.setPower(veld);
                m2.setPower(veli);
                m1.setPower(0);
                m3.setPower(0);
                sleep(40);
            } else if (frente >= 10 && derecha > 10) {
                m0.setPower(0);
                m2.setPower(0);
                m1.setPower(veli);
                m3.setPower(veli);
                if (atras <= 12) {
                    m0.setPower(-veli);
                    m2.setPower(veld);
                    m1.setPower(veli);
                    m3.setPower(-veli);
                    sleep(24);
                }
            } else if (frente <= 10 && derecha <= 10) {
                m0.setPower(0);
                m2.setPower(0);
                m1.setPower(-veli);
                m3.setPower(-veli);
                sleep(100);
                m0.setPower(veli);
                m2.setPower(-veld);
                m1.setPower(-veli);
                m3.setPower(veli);

            }
        }
    }
}


