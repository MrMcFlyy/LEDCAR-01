# LEDCAR-01
This repo contains the payload data required to control light whose name has the prefix "LEDCAR-01". Some
of it may be applicable to other light types with similar prefixes however I have only tested with the aforementioned
"01" lights. The app that goes with these lights is called "LED LAMP" although there may be others
I have not yet come across.

The UUID of the service required for sending a payload is:

0000FFF0-0000-1000-8000-00805f9b34fb

The UUID of the characteristic that you should write to is:

0000FFF3-0000-1000-8000-00805f9b34fb

The strip can be found as three lights in the LED Lamp-app:
- LED BLE (only controls the RGB lights)
- LED DMX (controls the ARGB lights)
- LEDCAR01 (can control both lights)

- There is also an LED channel in the LEDCAR01-light which can control basic features of both lights.

- LED BLE sends signals starting with 7E FF...
- LED DMX sends signals starting with 7B 00...
- the LED channel sends signals starting with 7B 01...

There is also a settings Menu in which you can set up the control units, lenght of stripes and welcome lights, but i didn't managed to get it working (yet).
