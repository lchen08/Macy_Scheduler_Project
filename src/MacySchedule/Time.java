package MacySchedule;
import java.util.InputMismatchException;

/**
 * This class is for a clock time of the XX:XXa or XX:XXp (AM/PM) format. Inputs can be
 * either in string of the above given format or decimal value of the hour (calculated
 * using military time; ex: 1:15PM is 13.25). Time cannot be given empty input.
 *
 * Version 1.1: Added checks to ensure time input is valid.
 *
 * @author Lisa Chen
 * @since 28Nov18
 * @version 1.1
 */
public class Time
{
    private String strTime;
    private double decimalTime;
    private final double HALF_DAY_HOUR = 12;
    private final double MIN_IN_HOUR = 60;
    private final int MIN_INPUT_LENGTH = 5;
    private final int MAX_INPUT_LENGTH = 6;
    char[] clockPostfixes = {'a', 'p', 'A', 'P'}; //format from Macy's to represent AM/PM

    /**
     * Constructs a time with a given string of format XX:XXa or XX:XXp
     * @param strTime Time in string format
     */
    public Time(String strTime)
    {
        if (strTime == null)
            throw new NullPointerException("Time input cannot be null");
        strTime = strTime.trim();
        int strLength = strTime.length();
        int lastIndex = strLength - 1;
        char lastChar = strTime.charAt(lastIndex);
        boolean found = false;
        int postfixIndex = -1;

        //checks length to ensure follows format
        if (!(strLength == MAX_INPUT_LENGTH || strLength == MIN_INPUT_LENGTH))
        {
            this.strTime = "";
            System.out.println(strLength);
            System.out.println(strTime);
            throw new RuntimeException("Input does not follow XX:XXa or XX:XXp format.");
        }

        //loop to ensure last character is an a or p (capitalization is fixed)
        for (int i = 0; i < clockPostfixes.length; i++)
        {
            if (lastChar == clockPostfixes[i])
            {
                found = true;
                postfixIndex = i;
                if (i > 1)
                    postfixIndex -= 2; //matching letters are separated by 2
            }
        }
        //did not find postfix, so invalid input
        if (found == false)
        {
            strTime = "";
            throw new RuntimeException("Input does not end with \'a\' or \'p\'.");
        }

        setTime(strTime, postfixIndex);
    }

    /**
     * Constructs a time with a decimal value of a military time.
     * @param decimalTime Decimal value of the time
     */
    public Time(double decimalTime)
    {
        if (decimalTime >= 24 || decimalTime < 0)
            throw new InputMismatchException("Decimal time is outside the range of " +
                    "0-23.99.");
        setTime(decimalTime);
    }

    /**
     * Sets the time to an input time. The time's postfix was matched to the
     * clockPostfixes to see what time of day (AM/PM) it is.
     * @param strTime Time in string format XX:XXa or XX:XXp
     * @param postfixIndex The index in clockPostfixes that was matched
     */
    private void setTime(String strTime, int postfixIndex)
    {
        this.strTime = strTime;
        strToDecimal(postfixIndex);
    }

    /**
     * Sets the time to an input time.
     * @param decimalTime Decimal value of the time
     */
    private void setTime(double decimalTime)
    {
        this.decimalTime = decimalTime;
        decimalToStr();
    }

    /**
     * Retrieves the stored time in string format.
     * @return Time in string format
     */
    public String getStrTime() { return strTime; }

    /**
     * Retrieves the stored time in decimal format.
     * @return Time in decimal format
     */
    public double getDecimalTime() { return decimalTime; }

    /**
     * Converts the stored string time (which was set by the user) to decimal format,
     * which is stored. The time's postfix was matched to the clockPostfixes to see
     * what time of day (AM/PM) it is.
     * @param postfixIndex The index in clockPostfixes that was matched
     */
    private void strToDecimal(int postfixIndex)
    {
        int colonIndex = strTime.indexOf(':');
        int endClockIndex = colonIndex + 3; //string clock format: XX:XXp
        char clockPostfix = strTime.charAt(endClockIndex);
        int minutes = Integer.parseInt(strTime.substring(colonIndex + 1, endClockIndex));
        decimalTime = Double.parseDouble(strTime.substring(0, colonIndex));

        if (decimalTime > 12 || decimalTime < 1)
            throw new InputMismatchException("Hours is outside the range of 1-12, and " +
                    "thus not a proper AM/PM clock format.");

        //change to military hours for easier calculation
        if (postfixIndex == 1)
        {
            //12PM in military is still 12, so don't change
            if (decimalTime != HALF_DAY_HOUR)
                decimalTime += HALF_DAY_HOUR;
        }
        else if (decimalTime == HALF_DAY_HOUR)
            decimalTime -= HALF_DAY_HOUR; //12AM is 0 00 military

        //add the minutes to the time
        double decMinutes = minutes/MIN_IN_HOUR;
        decimalTime += decMinutes;
    }

    /**
     * Converts the stored decimal time (which was set by the user) to string format,
     * which is stored.
     */
    private void decimalToStr()
    {
        int wholeHour = (int) decimalTime;
        int minMinutesNoConvert = 10;

        //convert decimal minutes to clock format
        long minutes = Math.round((decimalTime - wholeHour) * MIN_IN_HOUR);
        String strMinutes = Long.toString(minutes);
        if (minutes < minMinutesNoConvert)
            strMinutes = "0" + strMinutes; //change to "00" for clock format

        //set AM for 0-11 military and fix 0 00 to 12AM
        if (wholeHour < HALF_DAY_HOUR)
        {
            strMinutes += clockPostfixes[0];
            //change 0 00 military time to 12 for 12AM
            if (wholeHour == 0)
                wholeHour += HALF_DAY_HOUR;
        }
        //set PM and fix military 24 hours to 12 hours standard
        else
        {
            strMinutes += clockPostfixes[1];
            if (wholeHour > HALF_DAY_HOUR)
                wholeHour -= HALF_DAY_HOUR;
        }
        strTime = Integer.toString(wholeHour) + ':' + strMinutes;
    }
}