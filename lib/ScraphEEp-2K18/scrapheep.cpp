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

uint8_t* ESPControl::processPacket(void)
{
	int packetSize = Udp.parsePacket();
	if (packetSize)
	{
#ifdef DEBUG
		Serial.printf("Received %d bytes.\n", packetSize);
#endif

		if (packetSize == 1)
		{
			Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
			Udp.write(replyPacket);
			Udp.endPacket();
			Serial.println("Received connection from controller!");
			return nullptr;
		}

		int bytesRead = Udp.read(incomingPacketData, packetSize);
		if (bytesRead != 6)
		{
			Serial.println("Corrupt data!");
			return nullptr;
		}

#ifdef DEBUG
		Serial.print("Packet contents: {");
		for (int i = 0; i < bytesRead; i++)
			Serial.printf("%d%s", incomingPacketData[i], i < 5 ? ", " : "");
		Serial.print("}\n");
#endif

		digitalWrite(LED_BUILTIN, LOW);
		delay(1);
		digitalWrite(LED_BUILTIN, HIGH);

		return incomingPacketData;
	}
	return nullptr;
}