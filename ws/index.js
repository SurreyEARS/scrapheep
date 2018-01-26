const WebSocket = require('ws')

const ws = new WebSocket('ws://192.168.0.102:1647')

var valueToSend = 0;

ws.on('open', function open() {
  ws.send(1754967246)
	ws.send(2529)
	ws.send(-18105)
	
	var bytes = [0xA1,0x34, 0xF6, 0xB7, 0x8D, 0xF8, 0x26, 0x31]
	ws.send(bytes)
	ws.send(1)
  ws.send(Array.prototype.fill)
})

ws.on('message', function incoming(data) {
  console.log(data)
})

ws.on('error', function incoming(data) {
  console.log(data)
})