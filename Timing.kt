/**
 * Simple class to hold data for a timing
 * @param hour Hour of the day as int 0..24
 * @param minute Minute of the day as int
 * @param mode Mode/Pattern to set, see PatternList.kt
 * @param _weekdays List of booleans to represent days a timing should be set on, starts with Monday
 */
class Timing (
    val hour: Int,
    val minute: Int,
    val mode: Int,
    private val _weekdays: List<Boolean>
){
    val weekdays = packWeekdays(_weekdays)

    private fun packWeekdays(
        weekdaysList: List<Boolean>
    ) : Int{
        var packed = 0
        for (i in 0..6)
        {
            if (weekdaysList[i]){
                val mask =  1 shl i
                packed = packed or mask
            }
        }
        return packed
    }
}