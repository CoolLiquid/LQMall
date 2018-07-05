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
     * ��ȡ��������������շ���
     *
     * @param date
     * @return ������
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
     * ��ȡ����ʱ����ַ���
     */
    public static String getCurrentTimeStr() {
        if (allFormat == null) {
            allFormat = new SimpleDateFormat(TIME_FORMAT);
        }
        String serverTime = allFormat.format(now()).replace(" ", "T");
        return serverTime;
    }

    /**
     * ��ȡ����
     */
    public static String getCurrentDay() {
        if (allFormat == null) {
            allFormat = new SimpleDateFormat(DAY_FORMAT);
        }
        String serverTime = allFormat.format(now()).replace(" ", "T");
        return serverTime;
    }

    /**
     * ��һ��ʱ����ʱ���ת��ΪUtcʱ���
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
     * ��Ĭ��ʱ����ʱ���ת��ΪUtcʱ���
     *
     * @param time
     * @return
     */
    public static long toUtc(long time) {
        return toUtc(time, TimeZone.getDefault());
    }

    /**
     * ��Utcʱ���ת��Ϊһ��ʱ����ʱ���
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
     * ��Utcʱ���ת��ΪĬ��ʱ����ʱ���
     *
     * @param time
     * @return
     */
    public static long fromUtc(long time) {
        return fromUtc(time, TimeZone.getDefault());
    }

    /**
     * �Ѵ���ʱ���ַ�����T�ַ��滻
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
     * ��String����ת��ָ����ʽ
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


    //��ȡ������ʱ��
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
            interval = "�ո�";
        } else if (second < 60 * 60) {
            interval = (int) (second / 60) + "����ǰ";
        } else {
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);

            if (Calendar.getInstance().get(Calendar.YEAR) == year) {// ͬ��
                if (Calendar.getInstance().get(Calendar.MONTH) == month) {// ͬ��
                    switch (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                            - day) {
                        case 0:// ����
                            interval += dateToString(date, " HH:mm");
                            break;
                        case 1:// ����
                            interval += "���� ";
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
     * �뵱ǰʱ��Ƚ�
     * true ������  ��false��δ����
     *
     * @return
     */
    public static boolean isTimeOutFromNow(String time) {
        return getDistanceFromNow(time) > 0 ? false : true;
    }

    /**
     * �뵱ǰʱ��Ƚ�
     * true ������  ��false��δ����
     *
     * @return
     */
    public static boolean isTimeOutFromNow(Date date) {
        return getDistanceFromNow(date) > 0 ? false : true;
    }


    /**
     * ��ȡ�뵱ǰʱ���
     * ����ʱ���ȥ��ǰʱ��
     * ������
     *
     * @return����0����ʾδ���ڣ�С��0���ѵ���
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
     * ��ȡ�뵱ǰʱ���
     * ����ʱ���ȥ��ǰʱ��
     * ������
     *
     * @return����0����ʾδ���ڣ�С��0���ѵ���
     */
    public static long getDistanceFromNow(Date nextDate) {
        if (nextDate == null)
            return -1;
        long second = (nextDate.getTime() - now().getTime()) / 1000;
        return second;
    }


    /**
     * ��ȡ��ǰʱ���
     * ����Сʱ
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
     * ��ȡ��ǰʱ��������
     * �жϵ�ǰʱ���Ƿ񳬹�ָ��ʱ��n��
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
        Calendar cal = Calendar.getInstance();// ��ǰ����
        int hour = cal.get(Calendar.HOUR_OF_DAY);// ��ȡСʱ
        int minute = cal.get(Calendar.MINUTE);// ��ȡ����
        int minuteOfDay = hour * 60 + minute;// ��0:00�ֿ��ǵ�ĿǰΪֹ�ķ�����
        final int start = 22 * 60;// ��ʼʱ�� 00:20�ķ�����
        final int end = 8 * 60;// ����ʱ�� 8:00�ķ�����
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
     * ��ʱ���ַ�����ȡ���Զ���
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
     * ��ʽ��Ϊ ��-��
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
            return month + "��" + day;
        } else {
            return year + "��" + month + "��" + day;
        }
    }

    /**
     * ��ʽ��Ϊ ��-��-��
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


    public static String getShuntDate(int i) { // //��ȡǰ������ iΪ���� ����Ƴ�i�죬����ʱ��ǰ��ǰi��
        Date dat = null;
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.DATE, i);
        dat = cd.getTime();
        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
        return dformat.format(dat);
    }

    public static int getExpired(String expired) {
        // ��������
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        long time1 = cal.getTimeInMillis();
        // ת��ʽ
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
     * �õ�����ǰ��ʱ��
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
     * �õ�������ʱ��
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
     * ͨ��ʱ����������ж�����ʱ����������
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
