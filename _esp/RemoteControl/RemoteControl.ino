#include <scrapheep.h>

ESPControl control;

void setup()
{
	control.init("House Ladz", "L6eydeJaqrLW");
}

void loop()
{
  uint8_t* data = control.processPacket();
  if (!data)
    return;

  int8_t motorA = (int8_t) data[MOTOR_A];
  int8_t motorB = (int8_t) data[MOTOR_B];

  digitalWrite(PIN_MOTOR_A_DIR, motorA >= 0);
  digitalWrite(PIN_MOTOR_B_DIR, motorB >= 0);
  
  analogWrite(PIN_MOTOR_A_SPEED, abs(motorA) * 8);
  analogWrite(PIN_MOTOR_B_SPEED, abs(motorB) * 8);
  
  Serial.printf("Motor A speed: %d\n", abs(motorA) * 8);
  Serial.printf("Motor B speed: %d\n", abs(motorB) * 8);
}
