// https://github.com/esp8266/Arduino/blob/master/doc/esp8266wifi/udp-examples.rst
#include <ESP8266WiFi.h>
#include <WiFiUdp.h>

const char* ssid     = "EARS_LANDER_2.4";
const char* password = "lunar-rover";

WiFiUDP Udp;
unsigned int localUdpPort = 4210;
char incomingPacket[6];
char replyPacket[] = "R";

#define LED LED_BUILTIN

void setup() {
	Serial.begin(115200);
	pinMode(LED, OUTPUT);
	digitalWrite(LED, HIGH);

	pinMode(0, OUTPUT); // Motor A direction
	pinMode(2, OUTPUT); // Motor B direction
	pinMode(4, OUTPUT); // Motor B speed
	pinMode(5, OUTPUT); // Motor A speed

	delay(10);

	Serial.println();
	Serial.println();
	Serial.print("Connecting to ");
	Serial.println(ssid);

	WiFi.begin(ssid, password);

	while (WiFi.status() != WL_CONNECTED) {
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

void loop() {
	int packetSize = Udp.parsePacket();
	if (packetSize) {
		// receive incoming UDP packets
		Serial.printf("Received %d bytes from %s, port %d\n", packetSize, Udp.remoteIP().toString().c_str(), Udp.remotePort());
		int len = Udp.read(incomingPacket, 6);
		if (len > 0) {
			incomingPacket[len] = 0;
		}
		Serial.printf("UDP packet contents: %d\n", incomingPacket[0]);
		byte command = (byte) incomingPacket[0];

		digitalWrite(LED, LOW);
		delay(1);
		digitalWrite(LED, HIGH);

		digitalWrite(0, command & 1 ? HIGH : LOW);
		digitalWrite(2, command & 2 ? HIGH : LOW);
		digitalWrite(4, command & 4 ? HIGH : LOW);
		digitalWrite(5, command & 8 ? HIGH : LOW);

		// send back a reply, to the IP address and port we got the packet from
		// Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
		// Udp.write(replyPacket);
		// Udp.endPacket();
	}
} // End loop
