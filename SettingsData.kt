import java.awt.Color

/*
To keep things consistent, and hopefully easier to understand for those less familiar with Java/Kotlin all values
will be expressed as a hex value with the toByte() function called even when it would not be necessary
 */


class SettingsData {
    /**
     * Makes characteristic value to turn the welcome lights on or off
     * @param welcomeOn True to turn on, false for off
     */
    fun makeWelcomeData(welcomeOn: Boolean) =
        byteArrayOf(
            0x7E.toByte(),
            0xFF.toByte(),
            0x12.toByte(),
            if (welcomeOn) 0x00.toByte() else 0x01.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xEF.toByte(),
        )

    /**
     *  Select the specific boxes (center=02, left front=03, right front=04 and so on).
     *  @param box The box to set.
     */
    fun makeBoxData(box: Box) =
        byteArrayOf(
            0x7E.toByte(),
            0xFF.toByte(),
            0x12.toByte(),
            box.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xFF.toByte(),
            0xEF.toByte(),
        )

    /**
     *  Set the lenght of the ARGB stripe (1~1024)
     *  @param lenght to set as a lenght (bytes)
     */
    fun makeLenghtData(lenght: Int): ByteArray {
        val lenghtByte = lenght.Int(1..1024)
        return byteArrayOf(
            0x7B.toByte(),
            0xFF.toByte(),
            0x05.toByte(),
            0x04.toByte(),
            0x00.toByte(),
            lenghtByte.toByte(),
            0x03.toByte(),
            0xFF.toByte(),
            0xBF.toByte(),
        )
    }
}
