#define _HOME //!!!

#include "scrapheep.h"

#include <Arduino.h>
#include <HardwareSerial.h>
#include <ESP8266WiFi.h>
#include <WiFiUdp.h>

#ifndef _HOME
const char* wifi_ssid = "EARS_LANDER_2.4";
const char* wifi_password = "lunar-rover";
#else

#endif

void ESPControl::init(void)
{
	replyPacket[0] = 'R';
	replyPacket[1] = '\0';

	Serial.begin(115200);
	pinMode(LED_BUILTIN, OUTPUT);
	digitalWrite(LED_BUILTIN, HIGH);

	pinMode(PIN_MOTOR_A_DIR, OUTPUT);
	pinMode(PIN_MOTOR_A_SPEED, OUTPUT);
	pinMode(PIN_MOTOR_B_DIR, OUTPUT);
	pinMode(PIN_MOTOR_B_SPEED, OUTPUT);

	delay(10);

	Serial.print("\n\nConnecting to ");
	Serial.println(wifi_ssid);

	WiFi.begin(wifi_ssid, wifi_password);

	while (WiFi.status() != WL_CONNECTED)
	{
		delay(500);
		Serial.print(".");

		if (WiFi.status() == WL_NO_SSID_AVAIL)
		{
			Serial.printf("\nCannot find %s! Are you sure it is the correct name?", wifi_ssid);
			while (true)
				yield();
		}
		else if (WiFi.status() == WL_CONNECT_FAILED)
		{
			Serial.printf("\nFailed to connect to %s! Have you got the correct name and password?", wifi_ssid);
			while (true)
				yield();
		}
	}

	Serial.println("");
	Serial.println("WiFi connected.");
	Serial.println("IP address: ");
	Serial.println(WiFi.localIP());

	Udp.begin(localUdpPort);
	Serial.printf("Now listening at IP %s, UDP port %d\n", WiFi.localIP().toString().c_str(), localUdpPort);
}

void ESPControl::processPacket(void)
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

		digitalWrite(LED_BUILTIN, LOW);
		delay(1);
		digitalWrite(LED_BUILTIN, HIGH);

		digitalWrite(PIN_MOTOR_A_DIR, incomingPacketData[0] < 128);
		digitalWrite(PIN_MOTOR_B_DIR, incomingPacketData[1] < 128);

		byte motorASpeed = incomingPacketData[0];
		motorASpeed &= ~(1 << 1);

		byte motorBSpeed = incomingPacketData[1];
		motorBSpeed &= ~(1 << 1);

		int motorA = motorASpeed * 14;
		int motorB = motorBSpeed * 14;

		if (motorA > 1000) {
			motorA = 1000;
		}
		if (motorB > 1000) {
			motorB = 1000;
		}

		analogWrite(PIN_MOTOR_A_SPEED, motorA);
		analogWrite(PIN_MOTOR_B_SPEED, motorB);

		Serial.printf("Motor A speed: %d, %d\n", motorA, incomingPacketData[0] < 128);
		Serial.printf("Motor B speed: %d, %d\n", motorB, incomingPacketData[1] < 128);
	}
}

