import java.awt.Color
import java.util.Calendar

/*
To keep things consistent, and hopefully easier to understand for those less familiar with Java/Kotlin all values
will be expressed as a hex value with the toByte() function called even when it would not be necessary
 */


class Ble00Data {
    /**
     * Makes characteristic value to turn the lights on or off
     * @param powerOn True to turn on, false for off
     */
    fun makePowerData(powerOn: Boolean) =
        byteArrayOf(
            0x7E.toByte(),
            0xFF.toByte(),
            0x04.toByte(),
            if (powerOn) 0x01.toByte() else 0x00.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xEF.toByte(),
        )

    /**
     *  Makes characteristic value to set light colour.
     *  @param color The colour to set.
     */
    fun makeColorData(color: Color) =
        byteArrayOf(
            0x7E.toByte(),
            0xFF.toByte(),
            0x05.toByte(),
            0x03.toByte(),
            color.red.toByte(),
            color.green.toByte(),
            color.blue.toByte(),
            0xFF.toByte(),
            0xEF.toByte(),
        )

    /**
     *  Makes characteristic value to set light brightness
     *  @param brightness Brightness to set as a percentage
     */
    fun makeBrightnessData(brightness: Int): ByteArray {
        val brightnessPercentage = brightness.coerceIn(0..100)
        val adjustedPercentage = (brightnessPercentage * 32) / 100
        return byteArrayOf(
            0x7E.toByte(),
            0xFF.toByte(),
            0x01.toByte(),
            adjustedPercentage.toByte(),
            brightnessPercentage.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xEF.toByte(),
        )
    }

    /**
     *  Makes characteristic value to set colour temperature
     *  @param temperature Temperature as a percentage
     */
    fun makeColorTemperatureData(temperature: Int): ByteArray {
        val temperaturePercent = temperature.coerceIn(0..100)
        val adjustedPercent = (temperaturePercent * 32) / 100
        return byteArrayOf(
            0x7E.toByte(),
            0xFF.toByte(),
            0x09.toByte(),
            adjustedPercent.toByte(),
            temperaturePercent.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xEF.toByte(),
        )
    }
