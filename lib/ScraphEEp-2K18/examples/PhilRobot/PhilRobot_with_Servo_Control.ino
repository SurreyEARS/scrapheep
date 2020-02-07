#include <Servo.h>
#include <scrapheep.h>

ESPControl control;
Servo servo_A;

void setup()
{
	control.init("EARSAP1", "ears-wifi");
  servo_A.attach(15); //GPIO 15 corresponds to physical pin D8
}

void loop()
{
  uint8_t* data = control.processPacket();
  if (!data)
    return;

  int8_t motorA = (int8_t) data[MOTOR_A];
  int8_t motorB = (int8_t) data[MOTOR_B];
  uint8_t servoA_angle = (int8_t) data[C1];

  digitalWrite(PIN_MOTOR_A_DIR, motorA >= 0);
  digitalWrite(PIN_MOTOR_B_DIR, motorB >= 0);
  
  analogWrite(PIN_MOTOR_A_SPEED, abs(motorA) * 8);
  analogWrite(PIN_MOTOR_B_SPEED, abs(motorB) * 8);
  
  Serial.printf("Motor A speed: %d\n", abs(motorA) * 8);
  Serial.printf("Motor B speed: %d\n", abs(motorB) * 8);
  
  servo_A.write( map(servoA_angle, 0, 255, 0, 180));
}
