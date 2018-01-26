#include <ESP8266WiFi.h>
#include <WiFiUdp.h>

const char* ssid     = "EARS_LANDER_2.4";//"House Ladz";//"DESKTOP-CJ191RT 8076";
const char* password = "lunar-rover";//"L6eydeJaqrLW";//"31391X,7";

// WiFiServer server(1647);
WiFiUDP Udp;
unsigned int localUdpPort = 4210;
char  replyPacekt[] = "R";

#define LED LED_BUILTIN

int timeSinceKeepAlive = -1;

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

	// server.begin();
	Udp.begin(localUdpPort);
	Serial.println("Now listening at IP %s, UDP port %d\n", WiFi.localIP().toString().c_str(), localUdpPort);
}

typedef struct _GUID {
	uint32_t Data1;
	uint16_t Data2;
	uint16_t Data3;
	uint8_t Data4[8];
} GUID_t;

void loop() {
// 	int NumBytesAvailable = 0;
// 	byte ByteRead, CommandValid, Command;
// 	uint32_t TimeOfLastValidPacket_MS = 0;

// 	WiFiClient client = server.available();   // listen for incoming clients

// 	GUID_t FormatID0;
// 	GUID_t FormatID;
// 	FormatID0.Data1 = 0xCEAC9A68;
// 	FormatID0.Data2 = 0xE109;
// 	FormatID0.Data3 = 0x47B9;
// 	FormatID0.Data4[0] = 0xA1;
// 	FormatID0.Data4[1] = 0x34;
// 	FormatID0.Data4[2] = 0xF6;
// 	FormatID0.Data4[3] = 0xB7;
// 	FormatID0.Data4[4] = 0x8D;
// 	FormatID0.Data4[5] = 0xF8;
// 	FormatID0.Data4[6] = 0x26;
// 	FormatID0.Data4[7] = 0x31;
	
// 	if (client) {
// 		Serial.println("New client.");
// 		TimeOfLastValidPacket_MS = millis();
// 		while (client.connected()) {
// 			if (millis() - TimeOfLastValidPacket_MS > 3000) {
// 				Serial.printf("Timeout\n");
// 				break;
// 			}
			
// 			if (client.available()) {
// 				yield();
// //        Serial.printf("00\n");
// //        delay(10);

// 				NumBytesAvailable = client.available();

// //        Serial.printf("00 NumBytesAvailable: %d\n", NumBytesAvailable);

// 				if (NumBytesAvailable >= 18) {
// 					uint8_t ValidFormat = 1;
// 					for (int ByteIndex = 0 ; ByteIndex < 16; ++ByteIndex) {
// 						ByteRead = client.read();
// //            Serial.printf("01 ByteRead: %d\n", ByteRead);
// //            Serial.printf("02 FormatID byte: %d\n", ((uint8_t *)&FormatID0)[ByteIndex]);
// 						if (ByteRead != ((uint8_t *)&FormatID0)[ByteIndex]) {
// 							ValidFormat = 0;
// 							break;
// 						}
// 					}

// 					if (ValidFormat) {
// 						TimeOfLastValidPacket_MS = millis();
						
// 						Serial.printf("Valid format\n");

// 						CommandValid = client.read();
// 						Command = client.read();

// 						if (CommandValid) {
// 							Serial.printf("Data: %d\n", Command);
// 							//digitalWrite(LED, LOW);
// 							//delay(1);
// 							//digitalWrite(LED, HIGH);

// 							digitalWrite(0, Command & 1 ? HIGH : LOW);
// 							digitalWrite(2, Command & 2 ? HIGH : LOW);
// 							digitalWrite(4, Command & 4 ? HIGH : LOW);
// 							digitalWrite(5, Command & 8 ? HIGH : LOW);
// 						}
// 					}
// 				}
				
// //        Serial.printf("02\n");
// //        digitalWrite(LED, !digitalRead(LED));

// //        timeSinceKeepAlive = millis();
// 			}
// 		}
// 		// close the connection:
// 		client.stop();
// 		Serial.println("Client disconnected.");
	int packetSize = Udp.parsePacket();
	if (packetSize) {
		// receive incoming UDP packets
		Serial.printf("Received %d bytes from %s, port %d\n", packetSize, Udp.remoteIP().toString().c_str(), Udp.remotePort());
		int len = Udp.read(incomingPacket, 255);
		if (len > 0) {
			incomingPacket[len] = 0;
		}
		Serial.printf("UDP packet contents: %s\n", incomingPacket);

		// send back a reply, to the IP address and port we got the packet from
		Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
		Udp.write(replyPacekt);
		Udp.endPacket();
	}
}
