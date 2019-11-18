package com.utils;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author: weiyf
 * @Description: 服务器系统日期时间工具类
 * http://www.ibm.com/developerworks/cn/java/j-jodatime.html
 * http://www.joda.org/joda-time/
 * http://www.jianshu.com/p/8f64811f58cf
 */
public class LocalDateUtil {

    public static void main(String[] args) {

        Date date = strToDate("2018-08-21 15:16:00", DatePattern.LONG);

        System.out.println("2017-12-05 12:00:00 在现在之后 == " + isAfterNow(date));

        System.out.println("2017-12-05 12:00:00 在现在之前 == " + isBefore(date, new Date()));

        System.out.println("2017-12-05 12:00:00 在现在之前 == " + isBeforeNow(date));

        System.out.println("今天日期(格式" + DatePattern.FULL_CN.pattern + ") == " + dateToStr(new Date(), DatePattern.FULL_CN));

        System.out.println("获取时间(格式" + DatePattern.LONG.pattern + ") == " + strToDate("2017-12-05 14:08:52", DatePattern.LONG));

        System.out.println("加上多少天后的日期(格式" + DatePattern.LONG.pattern + ") == " + plusDaysToStr(new Date(), 1, DatePattern.LONG));

        System.out.println("获取指定时间所经历的秒数 == " + dateToSecond(new Date()));

        System.out.println("所经历的秒数转换成时间 == " + secondToDate(1512446274L));

        System.out.println("获取今天的开始时间 == " + withTimeAtStartOfDayToStr(DatePattern.LONG));

        System.out.println("获取今天的结束时间 == " + withMaximumValueToStr(DatePattern.LONG));

        System.out.println("当前日期与指定日期剩余多少秒 == " + validTimeByNow(date));

        System.out.println("当前日期与指定日期之间 == " + isBetween(strToDate("2018-02-23 04:08:52", DatePattern.LONG),
                strToDate("2018-02-24 14:08:52", DatePattern.LONG)));

        DateTime dateTimeNow = getDateTimeNow();
        int year = dateTimeNow.getYear();
        int month = dateTimeNow.getMonthOfYear();
        int day = dateTimeNow.getDayOfMonth();
        int hour = dateTimeNow.getHourOfDay();
        int minute = dateTimeNow.getMinuteOfHour();
        System.out.println(year + "/" + month + "/" + day + " " + hour + ":" + minute);

        System.out.println("当前日期与指定日期剩余多少时间单元 == " + validTimeByNow(date, TimeUnit.SECONDS));

        System.out.println("秒数转换成String格式时间 == " + secondToStr(1535005392L, DatePattern.LONG));

    }

    /**
     * 获取当时服务器运行时间
     */
    public static Date getDateNow() {
        return getDateTimeNow().toDate();
    }

    /**
     * 获取当时服务器运行时间
     *
     * @return DateTime 格式
     */
    public static DateTime getDateTimeNow() {
        return DateTime.now();
    }

    /**
     * 将指定时间转换成DateTime格式
     *
     * @return DateTime 格式
     */
    public static DateTime getDateTimeByDate(Date date) {
        return new DateTime(date);
    }

    /**
     * 比较时间(date)是否在当前时间(new Date())之后
     * Date date = strToDate("2017-12-06 00:00:00", DatePattern.LONG);
     * true =  isAfterNow(date); //now = 2017-12-05 00:00:00
     *
     * @return
     */
    public static boolean isAfterNow(Date date) {
        return new DateTime(date).isAfterNow();
    }

    /**
     * 比较时间(date)是否在当前时间(new Date())之前
     * Date date = strToDate("2017-12-06 00:00:00", DatePattern.LONG);
     * false =   isBeforeNow(date); //now = 2017-12-05 13:00:00
     *
     * @return
     */
    public static boolean isBeforeNow(Date date) {
        return new DateTime(date).isBeforeNow();
    }

    /**
     * 比较时间(date1)是否在(date2)之后
     *
     * @return true(之后)
     */
    public static boolean isAfter(Date date1, Date date2) {
        return getDateTimeByDate(date1).isAfter(getDateTimeByDate(date2));
    }

    /**
     * 比较时间(date1)是否在(date2)之前
     *
     * @return true(之前)
     */
    public static boolean isBefore(Date date1, Date date2) {
        return getDateTimeByDate(date1).isBefore(getDateTimeByDate(date2));
    }

    /**
     * 当前时间是否在[begin,end]之间(包含)
     *
     * @return true(之间), 如果begin或者end为null，则返回false
     */
    public static boolean isBetween(Date begin, Date end) {
        if (begin == null || end == null) {
            return false;
        }
        return getDateTimeByDate(begin).isBeforeNow() && getDateTimeByDate(end).isAfterNow();
    }

    /**
     * 当前日期与指定日期剩余多少秒
     *
     * @return 小于等于返回0
     */
    public static long validTimeByNow(Date date) {
        return validTimeByNow(date, TimeUnit.SECONDS);
    }

    /**
     * 当前日期与指定日期剩余多少时间单位(@TimeUnit)
     *
     * @return 小于等于返回0
     */
    public static long validTimeByNow(Date date, TimeUnit timeUnit) {
        DateTime targetDate = getDateTimeByDate(date);
        DateTime now = getDateTimeNow();
        if (now.isAfter(targetDate)) {
            return 0;
        }
        long differ = targetDate.getMillis() - now.getMillis();
        switch (timeUnit) {
            case MILLISECONDS:
                return differ;
            case SECONDS:
                return differ / ns;
            case MINUTES:
                return differ / nm;
            case HOURS:
                return differ / nh;
            case DAYS:
                return differ / nd;
            default:
                throw new UnsupportedOperationException("timeUnit unSupport");
        }
    }


    /**
     * dateToStr(new Date(),DatePattern.LONG)
     *
     * @return 2017-12-05 11:35:27
     */
    public static String dateToStr(Date date, DatePattern pattern) {
        return new DateTime(date).toString(pattern.pattern);
    }

    /**
     * strToDate("2017-12-05 11:30:59",DatePattern.LONG)
     *
     * @return Date = Tue Dec 05 11:30:59 CST 2017
     */
    public static Date strToDate(String date, DatePattern pattern) {
        DateTimeFormatter format = DateTimeFormat.forPattern(pattern.pattern);
        return DateTime.parse(date, format).toDate();
    }

    /**
     * strToDate("2017-12-05 11:30:59",DatePattern.LONG)
     *
     * @return DateTime
     */
    public static DateTime strToDateTime(String date, DatePattern pattern) {
        DateTimeFormatter format = DateTimeFormat.forPattern(pattern.pattern);
        return DateTime.parse(date, format);
    }

    /**
     * 加上多少天后的日期
     * plusDaysToStr(new Date(),1,DatePattern.LONG) new Date() = 2017-12-05 11:46:50
     *
     * @return 2017-12-06 11:46:50 ,
     */
    public static String plusDaysToStr(Date date, int days, DatePattern pattern) {
        return new DateTime(date).plusDays(days).toString(pattern.pattern);
    }

    /**
     * 加上多少天后的日期
     * plusDaysToDate("2017-12-05 11:30:59",DatePattern.LONG)
     *
     * @return Date = Wed Dec 06 11:30:59 CST 2017
     */
    public static Date plusDaysToDate(String date, int days, DatePattern pattern) {
        DateTimeFormatter format = DateTimeFormat.forPattern(pattern.pattern);
        return DateTime.parse(date, format).plusDays(days).toDate();
    }

    /**
     * 获取指定时间所经历的秒数(of 1970-01-01 00:00:00)
     * dateToStr(new Date(),DatePattern.LONG)
     *
     * @return 2017-12-05 11:35:27
     */
    public static Long dateToSecond(Date date) {
        return new DateTime(date).getMillis() / 1000;
    }

    /**
     * 所经历的秒数转换成时间(of 1970-01-01 00:00:00)
     * minutesToDate(1512446274L)
     *
     * @return Date = Wed Jul 07 13:04:18 CST 49897(2017-12-05 11:57:54.2)
     */
    public static Date secondToDate(Long second) {
        return new DateTime(second * 1000).toDate();
    }

    /**
     * 所经历的秒数转换成String格式时间(of 1970-01-01 00:00:00)
     *
     * @param second  秒数
     * @param pattern 时间格式
     * @return String
     */
    public static String secondToStr(Long second, DatePattern pattern) {

        return dateToStr(secondToDate(second), pattern);
    }

    /**
     * 获取1970-01-01 00:00:00,到当前时间所经历的毫秒数
     * Returns the current time in milliseconds
     */
    public static long currentTimeMillis() {
        return getDateTimeNow().getMillis();
    }

    /**
     * 获取1970-01-01 00:00:00,到当前时间所经历的秒数
     *
     * @return currentTime = 2017-10-18 15:19:58 , return 1508311198
     */
    public static long currentTimeSecond() {
        return currentTimeMillis() / 1000;
    }

    /**
     * 获取今天的开始时间 如：2017-12-05 00:00:00
     */
    public static Date withTimeAtStartOfDay() {
        return getDateTimeNow().withTimeAtStartOfDay().toDate();
    }


    /**
     * 获取今天的开始时间 如：2017-12-05 00:00:00
     */
    public static String withTimeAtStartOfDayToStr(DatePattern pattern) {
        return getDateTimeNow().withTimeAtStartOfDay().toString(pattern.pattern);
    }

    /**
     * 获取指定日期的开始时间 如：2017-12-05 00:00:00
     */
    public static Date dateWithTimeAtStartOfDay(Date date) {
        return new DateTime(date).withTimeAtStartOfDay().toDate();
    }


    /**
     * 获取1970-01-01 00:00:00,到今天00:00:00所经历的秒数
     */
    public static long withTimeAtStartOfDayToSecond() {
        return getDateTimeNow().withTimeAtStartOfDay().getMillis() / 1000;
    }

    /**
     * 获取指定日期的开始时间,到当日00:00:00所经历的秒数
     */
    public static long dateWithTimeAtStartOfDayToSecond(Date date) {
        return new DateTime(date).withTimeAtStartOfDay().getMillis() / 1000;
    }

    /**
     * 获取今天的结束时间 如：2017-12-05 23:59:59
     */
    public static Date withMaximumValue() {
        return getDateTimeNow().millisOfDay().withMaximumValue().toDate();
    }

    /**
     * 获取指定日期的结束时间 如：2017-12-05 23:59:59
     **/
    public static Date datewithTimeAtEndOfDay(Date date) {
        return getDateTimeByDate(date).millisOfDay().withMaximumValue().toDate();
    }

    /**
     * 获取指定日期的结束时间 精确获取 如：2017-12-05 23:59:59
     * tips： 通过withTimeAtStartOfDay获取
     */
    public static Date withTimeAtEndOfDay(Date date) {
        return getDateTimeByDate(date).plusDays(1).withTimeAtStartOfDay().minusSeconds(1).toDate();
    }

    /**
     * 获取指定日期加多少天后的结束时间 精确获取 如：2017-12-05 23:59:59
     * tips： 通过withTimeAtStartOfDay获取
     */
    public static Date withTimeAtEndOfDayPlusDays(Date date, Integer days) {
        return getDateTimeByDate(withTimeAtEndOfDay(date)).plusDays(days).toDate();
    }

    /**
     * 获取今天的结束时间 如：2017-12-05 23:59:59
     */
    public static String withMaximumValueToStr(DatePattern pattern) {
        return getDateTimeNow().millisOfDay().withMaximumValue().toString(pattern.pattern);
    }

    /**
     * 获取1970-01-01 00:00:00,到今天23:59:59所经历的秒数
     */
    public static long withMaximumValueToSecond() {
        return getDateTimeNow().millisOfDay().withMaximumValue().getMillis() / 1000;
    }

    /**
     * 获取指定日期的开始时间,到当日23:59:59所经历的秒数
     */
    public static long dateWithMaximumValueToSecond(Date date) {
        return new DateTime(date).millisOfDay().withMaximumValue().getMillis() / 1000;
    }

    /**
     * 根据传入的时间来计算指定时间的前或者后的时间，根据timeUnit单位来计算。三个都必填
     *
     * @param time     指定的时间
     * @param value    前或者后的值
     * @param timeUnit 时间单位。
     * @return
     */
    public static Date getTimeAfter(Date time, int value, TimeUnit timeUnit) {
        DateTime dateTime = new DateTime(time);
        switch (timeUnit) {
            case MILLISECONDS:
                return dateTime.plusMillis(value).toDate();
            case SECONDS:
                return dateTime.plusSeconds(value).toDate();
            case MINUTES:
                return dateTime.plusMinutes(value).toDate();
            case HOURS:
                return dateTime.plusHours(value).toDate();
            case DAYS:
                return dateTime.plusDays(value).toDate();
            default:
                throw new UnsupportedOperationException("timeUnit  unSupport");

        }

    }


    private static long ns = 1000;

    private static long nm = 1000 * 60;

    private static long nh = 1000 * 60 * 60;

    private static long nd = 1000 * 24 * 60 * 60;


    /**
     * 日期格式
     */
    public enum DatePattern {

        SHORT("yyyy-MM-dd"),

        LONG("yyyy-MM-dd HH:mm:ss"),

        FULL("yyyy-MM-dd HH:mm:ss.S"),

        VIRGULE_SHORT("yyyy/MM/dd"),

        VIRGULE_LONG("yyyy/MM/dd HH:mm:ss"),

        VIRGULE_FULL("yyyy/MM/dd HH:mm:ss.S"),

        INT_MINITE("yyyyMMddHHmmss"),

        YEAR_MONTH("yyyyMM"),

        INT_DATE("yyyyMMdd"),

        YEAR_MONTH_CN("yyyy年MM月"),

        SHORT_CN("yyyy年MM月dd日"),

        LONG_CN("yyyy年MM月dd日 HH时mm分ss秒"),

        FULL_CN("yyyy年MM月dd日 HH时mm分ss秒SSS毫秒"),

        SPOT_DATE("yyyy.MM.dd"),

        YEAR("YYYY"),

        MONTH("MM"),

        DAY("dd"),

        Hour_Minute("HH:mm");

        public final String pattern;

        DatePattern(String pattern) {
            this.pattern = pattern;
        }

    }


}
