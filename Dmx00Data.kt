import java.awt.Color
import java.util.Calendar

/*
To keep things consistent, and hopefully easier to understand for those less familiar with Java/Kotlin all values
will be expressed as a hex value with the toByte() function called even when it would not be necessary
 */


class Dmx00Data {
    /**
     * Makes characteristic value to turn the lights on or off
     * @param powerOn True to turn on, false for off
     */
    fun makePowerData(powerOn: Boolean) =
        byteArrayOf(
            0x7B.toByte(),
            0xFF.toByte(),
            0x04.toByte(),
            if (powerOn) 0x03.toByte() else 0x02.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xBF.toByte(),
        )

    /**
     *  Makes characteristic value to set light colour.
     *  @param color The colour to set.
     */
    fun makeColorData(color: Color) =
        byteArrayOf(
            0x7B.toByte(),
            0x00.toByte(),
            0x07.toByte(),
            color.red.toByte(),
            color.green.toByte(),
            color.blue.toByte(),
            0x00.toByte(),
            0xFF.toByte(),
            0xBF.toByte(),
        )

    /**
     *  Makes characteristic value to set light brightness
     *  @param brightness Brightness to set as a percentage
     */
    fun makeBrightnessData(brightness: Int): ByteArray {
        val brightnessPercentage = brightness.coerceIn(0..100)
        val adjustedPercentage = (brightnessPercentage * 32) / 100
        return byteArrayOf(
            0x7B.toByte(),
            0xFF.toByte(),
            0x01.toByte(),
            adjustedPercentage.toByte(),
            brightnessPercentage.toByte(),
            0x00.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xBF.toByte(),
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
            0x7B.toByte(),
            0xFF.toByte(),
            0x09.toByte(),
            adjustedPercent.toByte(),
            temperaturePercent.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xBF.toByte(),
        )
    }

    /**
     *  Makes characteristic value to set a pattern, please see PatternList.kt for a list of patterns.
     *  @param patternIndex Index of pattern to set, in range 0..210, 0 = off
     */

    fun makePatternData(patternIndex: Int) =
        byteArrayOf(
            0x7B.toByte(),
            0xFF.toByte(),
            0x03.toByte(),
            patternIndex.coerceIn(0..210).toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xBF.toByte(),
        )

    /**
     * Makes characteristic value to activate internal mic and set an eq
     * @param eqMode Index of eq mode to set 0..255. 0 turns mic off, all others turn it on
     */
    fun makeMicEqData(eqMode: Int) =
        byteArrayOf(
            0x7B.toByte(),
            0xFF.toByte(),
            0x0B.toByte(),
            eqMode.coerceIn(0..255).toByte(),
            0x00.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xBF.toByte(),
        )

    /**
     *  Makes characteristic value to set a timing list entry, days 1 Mon 7 Sun, all values to 0xFF to clear
     *  @param timing Timing object containing data for timing to set
     *  @param listPosition Integer value representing position in list of timings
     */
    fun makeTimingData(
        timing: Timing,
        listPosition: Int
    ): ByteArray {
        val calendar = Calendar.getInstance()
        val currentDay = adjustDayNumber(calendar.get(Calendar.DAY_OF_WEEK))
        val packedDayAndPosition = packDayWithPosition(
            currentDay = currentDay,
            listPosition = listPosition,
        )
        return byteArrayOf(
            0x8B.toByte(),
            packedDayAndPosition.toByte(),
            timing.mode.toByte(),
            timing.weekdays.toByte(),
            timing.hour.toByte(),
            timing.minute.toByte(),
            calendar.get(Calendar.HOUR_OF_DAY).toByte(),
            calendar.get(Calendar.MINUTE).toByte(),
            0xBF.toByte(),
        )
    }

    /**
     *  Adjusts DAY_OF_WEEK so that 1 = Monday, 7 = Sunday
     */
    private fun adjustDayNumber(day: Int): Int =
        if (day == 1) 7 else day - 1

    /**
     * Packs current weekday with position, upper 4 bits are weekday, lower are position
     */
    private fun packDayWithPosition(
        currentDay: Int,
        listPosition: Int,
    ) =
        (currentDay shl 4) or listPosition

    /**
     *  Makes characteristic value to send as a termination of a list of timings
     *  @param listSize Size of list of timings
     */

    fun makeTimingTerminationValue(listSize: Int) : ByteArray{
        val calendar = Calendar.getInstance()
        val currentDay = adjustDayNumber(calendar.get(Calendar.DAY_OF_WEEK))
        return byteArrayOf(
            0x7B.toByte(),
            0xFF.toByte(),
            0x10.toByte(),
            currentDay.toByte(),
            listSize.toByte(),
            0xFF.toByte(),
            calendar.get(Calendar.HOUR_OF_DAY).toByte(),
            calendar.get(Calendar.MINUTE).toByte(),
            0xBF.toByte(),
        )
    }

    /*
    Next are the function related to custom patterns. To set a custom pattern first send the list of colors then choose
    a mode or direction. There are 8 mode to choose from but AC represents off. Modes and directions are separate
    options, for example you could: Set a colour list, set a mode, then choose whether the pattern should go in the
    forward or reverse direction
     */

    /**
     * Makes data to set a color in the list for the custom pattern
     * @param color Color to add to list
     * @param listPosition Position in list of colors, List starts at 1!!
     * @param listSize Total amount of colors that will be set
     */

    fun makeCustomPatternColorData(
        color: Color,
        listPosition: Int,
        listSize: Int,
    ) = byteArrayOf(
        0x7B.toByte(),
        listPosition.toByte(),
        0x0E.toByte(),
        0xFD.toByte(),
        color.red.toByte(),
        color.green.toByte(),
        color.blue.toByte(),
        listSize.toByte(),
        0xBF.toByte(),
    )

    /**
     * Makes data to set custom pattern mode, please see CustomPatternMode.kt for modes
     * @param mode Mode to set
     */
    fun makeCustomPatternModeData(mode: CustomPatternMode) =
        byteArrayOf(
            0x7B.toByte(),
            0xFF.toByte(),
            0x13.toByte(),
            mode.ordinal.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xBF.toByte(),
        )


    /**
     * Makes data to set custom pattern direction (Forward/Backward), there are only two bytes that are different
     * from the previous function but have kept them as two separate functions for the sake of clarity
     * @param isForward True to set "Forward" false to set "Reverse"
     */
    fun makeCustomPatternDirectionData(isForward: Boolean) =
        byteArrayOf(
            0x7B.toByte(),
            0xFF.toByte(),
            0x0D.toByte(),
            if (isForward) 0x00.toByte() else 0x01.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xBF.toByte(),
        )
}
