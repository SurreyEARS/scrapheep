#ifndef scrapheep_h
#define scrapheep_h

#include <WiFiUdp.h>

// Pin defines
#define PIN_MOTOR_A_DIR    0
#define PIN_MOTOR_A_SPEED  5
#define PIN_MOTOR_B_DIR    2
#define PIN_MOTOR_B_SPEED  4

#define PIN_D0	16
#define PIN_D1	5
#define PIN_D2	4
#define PIN_D3	0
#define PIN_D4	2
#define PIN_D5	14
#define PIN_D6	12
#define PIN_D7	13
#define PIN_D8	15

// Variable defines
#define MOTOR_A 0
#define MOTOR_B 1
#define C1 2
#define C2 3
#define C3 4
#define C4 5

class ESPControl
{
  public:
	void init(void);
	void init(const char*, const char*);
	uint8_t* processPacket(void);

  private:
	WiFiUDP Udp;
	uint16_t localUdpPort = 4210;
	uint8_t incomingPacketData[7];
	char replyPacket[1];
};

#endif
