#include <scrapheep.h>

ESPControl control;

void setup()
{
	control.init();
}

void loop()
{
  byte* data = control.processPacket();
  if (!data)
    return;

  digitalWrite(PIN_MOTOR_A_DIR, data[MOTOR_A] < 128);
  digitalWrite(PIN_MOTOR_B_DIR, data[MOTOR_B] < 128);
  
  byte motorASpeed = data[MOTOR_A];
  motorASpeed &= ~(1 << 1);
  
  byte motorBSpeed = data[MOTOR_B];
  motorBSpeed &= ~(1 << 1);
  
  int motorA = motorASpeed * 14;
  int motorB = motorBSpeed * 14;
  
  if (motorA > 1000)
    motorA = 1000;
  if (motorB > 1000)
    motorB = 1000;
  
  analogWrite(PIN_MOTOR_A_SPEED, motorA);
  analogWrite(PIN_MOTOR_B_SPEED, motorB);
  
  Serial.printf("Motor A speed: %d, %d\n", motorA, data[MOTOR_A] < 128);
  Serial.printf("Motor B speed: %d, %d\n", motorB, data[MOTOR_B] < 128);
}
