// https://github.com/esp8266/Arduino/blob/master/doc/esp8266wifi/udp-examples.rst
#include <ESP8266WiFi.h>
#include <WiFiUdp.h>
#include "RemoteControl.h"

const char* ssid     = "EARS_LANDER_2.4";
const char* password = "lunar-rover";

WiFiUDP Udp;
unsigned int localUdpPort = 4210;
byte incomingPacketData[7];
char replyPacket[] = "R";

void setup()
{
	Serial.begin(115200);
	pinMode(LED, OUTPUT);
	digitalWrite(LED, HIGH);

	pinMode(PIN_MOTOR_A_DIR, OUTPUT);
	pinMode(PIN_MOTOR_A_SPEED, OUTPUT);
	pinMode(PIN_MOTOR_B_DIR, OUTPUT);
	pinMode(PIN_MOTOR_B_SPEED, OUTPUT);

	delay(10);

	Serial.print("Connecting to ");
	Serial.println(ssid);

	WiFi.begin(ssid, password);

	while (WiFi.status() != WL_CONNECTED)
	{
		delay(500);
		Serial.print(".");
	}

	Serial.println("");
	Serial.println("WiFi connected.");
	Serial.println("IP address: ");
	Serial.println(WiFi.localIP());

	Udp.begin(localUdpPort);
	Serial.printf("Now listening at IP %s, UDP port %d\n", WiFi.localIP().toString().c_str(), localUdpPort);
}

void loop()
{
	int packetSize = Udp.parsePacket();
	if (packetSize)
	{
		Serial.printf("Received %d bytes ", packetSize);

		if (packetSize == 1)
		{
			Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
			Udp.write(replyPacket);
			Udp.endPacket();
		}

		int bytesRead = Udp.read(incomingPacketData, packetSize);
		Serial.print(" packet contents: {");
		for (int i = 0; i < bytesRead; i++)
			Serial.printf("%d%s", incomingPacketData[i], i < 5 ? ", " : "");
		Serial.print("}\n");

		digitalWrite(LED, LOW);
		delay(1);
		digitalWrite(LED, HIGH);

		digitalWrite(PIN_MOTOR_A_DIR, incomingPacketData[0] & 1 ? LOW : HIGH);
		digitalWrite(PIN_MOTOR_B_DIR, incomingPacketData[1] & 1 ? LOW : HIGH);

		byte motorASpeed = incomingPacketData[0];
		motorASpeed &= ~(1 << incomingPacketData[0]);

		byte motorBSpeed = incomingPacketData[1];
		motorBSpeed &= ~(1 << incomingPacketData[1]);

		analogWrite(PIN_MOTOR_A_SPEED, motorASpeed);
		analogWrite(PIN_MOTOR_B_SPEED, motorBSpeed);

		Serial.printf("Motor A speed: %d\n", motorASpeed);
		Serial.printf("Motor B speed: %d\n", motorBSpeed);
	}
}
