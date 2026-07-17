/** AI-Generated, 2026-05-10 */
package fun.wraq.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class CalendarUtil {

    public static Calendar StringToCalendar(String DateString) throws ParseException {
        SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cal = Calendar.getInstance();
        if (!Objects.equals(DateString, "")) {
            Date date1 = tmpDate.parse(DateString);
            cal.setTime(date1);
        }
        return cal;
    }

    public static Calendar castStringToCalendar(String DateString) {
        try {
            return StringToCalendar(DateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String CalendarToString(Calendar calendar) {
        Date date = calendar.getTime();
        SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
        return tmpDate.format(date);
    }

    public static String castCalendarToString(Calendar calendar) {
        return CalendarToString(calendar);
    }

    public static long calenderDateDifference(Calendar cal1, Calendar cal2) {
        return ((cal1.getTimeInMillis() - cal2.getTimeInMillis()) / (24 * 60 * 60 * 1000));
    }

    public static long calenderMinuteDifference(Calendar cal1, Calendar cal2) {
        return ((cal1.getTimeInMillis() - cal2.getTimeInMillis()) / (60 * 60 * 1000));
    }

    public static String getDifferenceFormatText(Calendar cal1, Calendar cal2) {
        long delta = (cal1.getTimeInMillis() - cal2.getTimeInMillis());
        if (delta <= 0) return "00:00:00";
        long seconds = delta / 1000 % 60;
        long hours = delta / (1000 * 3600);
        long minute = delta / (1000 * 60) % 60;
        SimpleDateFormat tmpDate = new SimpleDateFormat("HH:mm:ss");
        Calendar deltaTime = Calendar.getInstance();
        deltaTime.set(Calendar.HOUR_OF_DAY, (int) hours);
        deltaTime.set(Calendar.MINUTE, (int) minute);
        deltaTime.set(Calendar.SECOND, (int) seconds);
        return tmpDate.format(deltaTime.getTime());
    }
}
