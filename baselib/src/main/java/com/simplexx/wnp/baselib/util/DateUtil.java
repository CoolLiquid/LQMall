package com.simplexx.wnp.baselib.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by fan-gk on 2017/4/20.
 */


public class DateUtil {


    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String TIME_FORMAT_WEEK = "yyyy-MM-dd EE HH:mm";
    public static final String DAY_FORMAT = "yyyy-MM-dd";
    public static final String MONTH_FORMAT = "yyyy-MM";
    public static final String HOUR_MINUTES = "HH:mm";
    public static final String TIME_DAY = "dd";
    public static final String TIME_WEEK = "EE";
    private static SimpleDateFormat allFormat = null;

    private static Calendar getCalendar() {
        return Calendar.getInstance();
    }


    public static Date now() {
        return getCalendar().getTime();
    }

    /**
     * 获取输入参数的年月日返回
     *
     * @param date
     * @return 年月日
     */
    public static Date getDate(Date date) {
        Calendar calendar = getCalendar();
        calendar.setTime(date == null ? now() : date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date addSeconds(Date date, int seconds) {
        return add(date, Calendar.SECOND, seconds);
    }

    public static Date addMilliseconds(Date date, int milliseconds) {
        return add(date, Calendar.MILLISECOND, milliseconds);
    }

    public static Date addDays(Date date, int days) {
        return add(date, Calendar.DATE, days);
    }

    public static boolean isSameYear(Date d1, Date d2) {
        if (d1 == null || d2 == null)
            return false;
        Calendar calendar = getCalendar();
        calendar.setTime(d1);
        int y1 = calendar.get(Calendar.YEAR);
        calendar.setTime(d2);
        return y1 == calendar.get(Calendar.YEAR);
    }

    private static Date add(Date date, int field, int value) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.add(field, value);
        return calendar.getTime();
    }

    public static String getDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = sDateFormat.format(now());
        return date;
    }

    /**
     * 获取现在时间的字符串
     */
    public static String getCurrentTimeStr() {
        if (allFormat == null) {
            allFormat = new SimpleDateFormat(TIME_FORMAT);
        }
        String serverTime = allFormat.format(now()).replace(" ", "T");
        return serverTime;
    }

    /**
     * 获取当天
     */
    public static String getCurrentDay() {
        if (allFormat == null) {
            allFormat = new SimpleDateFormat(DAY_FORMAT);
        }
        String serverTime = allFormat.format(now()).replace(" ", "T");
        return serverTime;
    }

    /**
     * 将一个时区的时间戳转换为Utc时间戳
     *
     * @param time
     * @param zone
     * @return
     */
    public static long toUtc(long time, TimeZone zone) {
        time = time - TimeZone.getDefault().getRawOffset();
        return time;
    }

    /**
     * 将默认时区的时间戳转换为Utc时间戳
     *
     * @param time
     * @return
     */
    public static long toUtc(long time) {
        return toUtc(time, TimeZone.getDefault());
    }

    /**
     * 将Utc时间戳转换为一个时区的时间戳
     *
     * @param time
     * @param zone
     * @return
     */
    public static long fromUtc(long time, TimeZone zone) {
        return time + zone.getRawOffset();
    }

    public static Date toDate(String time) {
        if (StringUtil.isNullOrEmpty(time))
            return new Date();
        time = timeFormat(time);
        Date next = new Date();
        try {
            SimpleDateFormat df = new SimpleDateFormat(MINUTE_FORMAT);
            next = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return next;
    }

    public static Date stringToDate(String time, String format) throws ParseException {
        String formatTime = timeFormat(time);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = simpleDateFormat.parse(formatTime);
        return date;

    }


    /**
     * 将Utc时间戳转换为默认时区的时间戳
     *
     * @param time
     * @return
     */
    public static long fromUtc(long time) {
        return fromUtc(time, TimeZone.getDefault());
    }

    /**
     * 把带有时间字符串的T字符替换
     *
     * @param time
     * @return
     */
    public static String timeFormat(String time) {
        if (!StringUtil.isNullOrEmpty(time))
            if (time.contains("T"))
                return time.replace("T", " ");
            else return time;
        else return time;
    }


    /**
     * 把String日期转成指定格式
     */
    public static String getDateFormat(String strDate, String format) {
        if (strDate != null && strDate.length() > 0) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date date = null;
            try {
                if (strDate.contains("T")) {
                    if (strDate.contains(".")) {
                        date = getFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(strDate);
                    } else {
                        date = getFormat("yyyy-MM-dd'T'HH:mm:ss").parse(strDate);
                    }

                } else if (!strDate.contains("-")) {
                    String[] array = strDate.split(" ");
                    String time;
                    if (array.length > 1) {
                        time = array[0].replaceAll(":", "-") + " " + array[1];
                        if (strDate.contains(".")) {
                            date = getFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(time);
                        } else {
                            date = getFormat("yyyy-MM-dd HH:mm:ss").parse(time);
                        }
                    }
                } else {
                    if (strDate.contains(".")) {
                        date = getFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(strDate);
                    } else {
                        date = getFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return strDate;
            }

            return simpleDateFormat.format(date);
        }
        return strDate;
    }

    public static SimpleDateFormat getFormat(String partten) {
        return new SimpleDateFormat(partten);
    }


    public static String dateToString(Date date, String strFormat) {
        if (date == null)
            return null;
        String time;
        SimpleDateFormat format = new SimpleDateFormat(strFormat);
        time = format.format(date);
        return time;
    }


    public static String parseTime(String serverTime) {
        try {
            String date = timeFormat(serverTime);
            SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
            Date d = sdf.parse(date);
            return getInterval(d);
        } catch (Exception e) {
            e.printStackTrace();
            return "--";
        }
    }

    public static String parseTime(String serverTime, String format) {
        try {
            String date = timeFormat(serverTime);
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date d = sdf.parse(date);
            return dateToString(d, format);
        } catch (Exception e) {
            e.printStackTrace();
            return "--";
        }
    }


    //获取处理后的时间
    public static String getInterval(Date date) {
        String interval = "";
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        long millisecond = Calendar.getInstance().getTimeInMillis()
                - date.getTime();
        long second = millisecond / 1000;
        if (second <= 0) {
            second = 0;
        }
        if (second < 60) {
            interval = "刚刚";
        } else if (second < 60 * 60) {
            interval = (int) (second / 60) + "分钟前";
        } else {
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);

            if (Calendar.getInstance().get(Calendar.YEAR) == year) {// 同年
                if (Calendar.getInstance().get(Calendar.MONTH) == month) {// 同月
                    switch (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                            - day) {
                        case 0:// 当天
                            interval += dateToString(date, " HH:mm");
                            break;
                        case 1:// 昨天
                            interval += "昨天 ";
                            break;
                        default:
                            interval = dateToString(date, "MM-dd");
                            break;
                    }
                } else {
                    interval = dateToString(date, "MM-dd");
                }
            } else {
                interval = dateToString(date, "yyyy-MM-dd");
            }
        }
        return interval;
    }

    /**
     * 与当前时间比较
     * true ：过期  ；false：未到期
     *
     * @return
     */
    public static boolean isTimeOutFromNow(String time) {
        return getDistanceFromNow(time) > 0 ? false : true;
    }

    /**
     * 与当前时间比较
     * true ：过期  ；false：未到期
     *
     * @return
     */
    public static boolean isTimeOutFromNow(Date date) {
        return getDistanceFromNow(date) > 0 ? false : true;
    }


    /**
     * 获取与当前时间差
     * 传入时间减去当前时间
     * 返回秒
     *
     * @return大于0，表示未到期；小于0，已到期
     */
    public static long getDistanceFromNow(String time) {
        if (StringUtil.isNullOrEmpty(time))
            return -1;
        time = timeFormat(time);
        long second = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat(TIME_FORMAT);
            Date next = df.parse(time);
            second = getDistanceFromNow(next);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return second;
    }


    /**
     * 获取与当前时间差
     * 传入时间减去当前时间
     * 返回秒
     *
     * @return大于0，表示未到期；小于0，已到期
     */
    public static long getDistanceFromNow(Date nextDate) {
        if (nextDate == null)
            return -1;
        long second = (nextDate.getTime() - now().getTime()) / 1000;
        return second;
    }


    /**
     * 获取当前时间差
     * 返回小时
     *
     * @param time
     * @return
     */
    public static int getDistanceHourFromNow(String time) {
        long second = getDistanceFromNow(time);
        float hour = (float) second / (60 * 60);
        if (0 < hour && hour < 1) return 1;
        else return (int) hour;
    }


    public static float getDistanceDayFromNow(String time) {
        long second = getDistanceFromNow(time);
        float day = (float) second / (60 * 60 * 24);
        return day;
    }

    /**
     * 获取当前时间差返回天数
     * 判断当前时间是否超过指定时间n天
     *
     * @return
     */
    public static boolean hasMissDays(String time, int day) {
        if (time == null)
            return false;
        try {
            int missDay = 0;
            SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
            Date now = now();
            Date t = dateFormat.parse(time);
            missDay = (int) ((now.getTime() - t.getTime()) / (1000 * 60 * 60 * 24));
            if (missDay < day)
                return false;
            else
                return true;
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return false;

    }

    public static boolean isNight() {
        Calendar cal = Calendar.getInstance();// 当前日期
        int hour = cal.get(Calendar.HOUR_OF_DAY);// 获取小时
        int minute = cal.get(Calendar.MINUTE);// 获取分钟
        int minuteOfDay = hour * 60 + minute;// 从0:00分开是到目前为止的分钟数
        final int start = 22 * 60;// 起始时间 00:20的分钟数
        final int end = 8 * 60;// 结束时间 8:00的分钟数
        if (minuteOfDay >= start && minuteOfDay <= end) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isToDay(String dateString) {

        return StringUtil.equal(getDateFormat(getDate(), DAY_FORMAT), getDateFormat(dateString, DAY_FORMAT).replaceAll("-", ""));

    }

    /**
     * 将时间字符串截取到自定义
     */
    public static String getTimeFormat(String dateString, String format) {
        String time = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat(TIME_FORMAT);
            Date date = df.parse(dateString);
            SimpleDateFormat dfs = new SimpleDateFormat(format);
            time = dfs.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 格式化为 月-日
     *
     * @param timeMillis
     * @return
     */
    public static String parseMonthDayDate(String timeMillis, String format) {
        long time = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);
            Date date = df.parse(timeMillis);
            time = date.getTime();
        } catch (ParseException e) {
            return "--";
        }
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        if (currentYear == year) {
            return month + "月" + day;
        } else {
            return year + "年" + month + "月" + day;
        }
    }

    /**
     * 格式化为 年-月-日
     *
     * @param timeMillis
     * @return
     */
    public static String getDynamicTime(String timeMillis) {
        long time = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat(TIME_FORMAT);
            Date date = df.parse(timeMillis);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        String hour = c.get(Calendar.HOUR_OF_DAY) + "";
        String min = c.get(Calendar.MINUTE) + "";
        if (min.length() == 1) {
            min = "0" + min;
        }
        if (hour.length() == 1) {
            hour = "0" + hour;
        }

        if (currentYear == year) {
            return month + "-" + day + " " + hour + ":" + min;
        } else {
            return year + "-" + month + "-" + day + " " + hour + ":" + min;
        }
    }


    public static String getShuntDate(int i) { // //获取前后日期 i为正数 向后推迟i天，负数时向前提前i天
        Date dat = null;
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.DATE, i);
        dat = cd.getTime();
        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
        return dformat.format(dat);
    }

    public static int getExpired(String expired) {
        // 设置天数
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        long time1 = cal.getTimeInMillis();
        // 转格式
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        try {
            expired = dateToString(stringToDate(expired, TIME_FORMAT), TIME_FORMAT);
            Date date;
            date = sdf.parse(expired);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        int expiredInt = Integer.parseInt(String.valueOf(between_days));
        return expiredInt;
    }

    public static String dateToString(Date date) {
        SimpleDateFormat dformat = new SimpleDateFormat("yyyy/MM/dd");
        return dformat.format(date);
    }


    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 通过时间秒毫秒数判断两个时间的天数间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getDistanceDays(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }
}
