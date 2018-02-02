# ScraphEEp 2018
Code for EARS ScraphEEp 2018 challenge

## Setup
### ESP - Setting Up The Arduino IDE for ScraphEEp
1. *(Recommended)* Install the latest version of the Arduino Software from [arduino.cc](http://www.arduino.cc)
2. Download the project from the link above, unzip it 
3. In the Arduino IDE, navigate to `Sketch>Include Library>Add .ZIP` Library and select `lib-ScraphEEp-1K18` from the unzipped folder
4. Navigate to `File>Preferences` and set `Additional Boards Manager URLs` to [http://arduino.esp8266.com/stable/package_esp8266com_index.json](http://arduino.esp8266.com/stable/package_esp8266com_index.json) and click "OK"
5. Navigate to `Tools>Board>Boards Manager` and scroll down to find the entry for `esp8266 by ESP8266 Community`
6. Click on this entry and install the latest available version
7. To program the ESP correctly, you will need to tweak a few settings in the Tools menu. This will also make everything work the fastest:
	a. Board: NODEMCU 1.0 (ESP-12E Module) - this will make all the options below appear
	b. Flash Size - 4M (1M SPIFFS)
	c. Debug Port - Disabled
	d. Debug Level - None
	e. IwIP Variant - v2 Prebuilt (MSS=536)
	f. CPU Frequency 160MHz
	g. Upload Speed - 921600
	h. Port (select as needed when ESP is plugged in)
	i. Programmer - AVR ISP
	
You can find the driver for macOS [here](http://blog.sengotta.net/wp-content/uploads/2015/11/CH341SER_MAC-1.4.zip). (Thanks to [@DrChrisBridges](https://twitter.com/DrChrisBridges/status/959430562472701953))

### ESP - Writing Your Program
1. Add the ScraphEEp2k18 library to your code: `#include <scrapheep.h>`
2. (If you run into problems compiling this such as `multiple libraries were found for "WiFiUdp.h"` try adding `#include <ESP8266WiFi.h>` above this)
3. Above the line `void setup()`, type `ESPControl control;` to create the ESP manager.
4. Once this is done, simply call `control.init();` inside `setup()` to connect your processor to the WiFi.
	*(To change the WiFi network, add 2 parameters, SSID PASSWORD, to `control.init()`. E.g. `control.init("SSID", "PASSWORD")`)*
5. Finally, to start receiving data from the controller, put this code inside the `loop()` function:
	```
	uint8_t *data = control.processPacket();
	if (!data) return;
	```
6. All the data from the control software will be inside the `data[]` array, so simply access the data by calling `data[the data ID]`.
7. The data IDs are as follows:
```
MOTOR_A
MOTOR_B
C1
C2
C3
C4
```
8. To save time doing this, you can work straight from Phil’s example by clicking `File->Examples->ScraphEEp-2K18->PhilsRobot`


### ESP - Interfacing and Use
The ESP and motor shield as given to you have:
- Two separate power supplies (VIN for the ESP itself and VM for the motor driver output) on the blue screw terminals at the end -NOTE:  VIN<9V and VM<36V
- Two two-terminal screw connections for motors on the blue screw terminals, labelled A and B.
- Nine Digital Pins labelled D0-D8 and set up to plug directly into a servo (see Technical Hints) - NOTE: D1-D4 pins are shared with the motor driver chip, so can’t be used as logic inputs/outputs unless the motor driver isn’t in use

To Power the ESP and the motor shield you have a few options:
- Single battery power for both VIN and VM - the easiest way to power everything (as on Phil’s demo). Place the pin bridge over the pins directly behind the blue screw terminals to connect VIN and VM together on the board
- Single battery power with 5V regulated input for VIN (as on Alex’s demo). Find a 7805 5V regulator in the Makerspace and connect it heatsink-side-up in the three neighbouring screw terminals marked VM G VIN (you might also want to check a data sheet online). Feed your motor power to the VM and G terminals, and the ESP and servo power pins will be powered off 5V. This is really useful for stopping servo motors from blowing up if you want to use them
- Dual Battery Power for the motors and the ESP independently - connect the motor battery between VM and G and the ESP battery between VIN and G.

If using the LiPo battery, please use the provided fused battery harness to connect the battery. We will only be giving out batteries to teams who can demonstrate that their system is not short circuiting (to prevent any explosions etc) so before you get your battery it’s best to power your systems from a bench power supply to test everything (the battery voltage will be 8.4V full, 7.4V minimum usable)
Client

### Client
1. Enter your ESP’s IP address *(Written on a paper slip, or 192.168.0.1x where x is your team number (if <10, add leading 0))*
2. Click `Connect`
3. 2 control types
a. `Motor`: send differential motor values based on joystick location
b. `Coord`: send joystick coordinates
4. 4 control sliders (`C1-4`)
a. Customisable, sends a value between 0 and 255
b. 0 and 1 buttons jump to the end of the slider
5. Joystick canvas
a. For controlling motors
6. Click `Disconnect` to close connection, `Exit` to disconnect and close application