#include <scrapheep.h>

ESPControl control;

void setup()
{
	control.init();
}

void loop()
{
  control.processPacket();
}
