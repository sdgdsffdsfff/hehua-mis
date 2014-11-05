package com.hehua.mis.utils;

import com.hehua.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jerry
 * Date: 13-12-10
 * Time: 下午6:13
 * To change this template use File | Settings | File Templates.
 */
public class DateTimeUtil {


    // 默认日期格式化格式:"yyyy-MM-dd HH:mm:ss"
    private static final String DEFAULT_FORMAT     = "yyyy-MM-dd HH:mm:ss";

    private static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";


    private static String       dayNames[]         = { "星期日 ", "星期一 ", "星期二 ", "星期三 ", "星期四 ", "星期五 ", "星期六 " };

    private static Set<Integer> compareFields      = new TreeSet<Integer>(Arrays
            .asList(Calendar.YEAR, Calendar.MONTH, Calendar.DATE,
                    Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND,
                    Calendar.MILLISECOND));

    /**
     * Date型日期转换成字符串，转换的格式是"yyyy-MM-dd"；如：2012-02-19
     *
     * @param date
     *            要转换的日期类型
     * @return 返回格式化的日期字符串
     */
    public static String getSimpleDateFormat(Date date) {
        DateFormat df = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        return df.format(date);
    }

    public static List<Date> getDaysBetweenGivenRange(Date start, Date end) {
        if (start.after(end))
            throw new IllegalArgumentException("参数不合法");
        List<Date> result = new ArrayList<Date>();
        do {
            result.add(start);
            start = addDatesToDate(start, 1);
        } while (compareAccurateToDate(start, end) <= 0);
        return result;
    }

    /***
     * 得到日期的前几天还是后几天的日期，具体由参数offset决定
     *
     * @param date
     *            对应的日期
     * @param offset
     *            负数代表date的前几天，整数代表date的后几天
     * @return 返回一个日期型
     */
    public static Date addDatesToDate(Date date, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, offset);
        return calendar.getTime();
    }

    /**
     * 参数start和end日期进行比较大小，比较的精度是天。
     *
     * @param start
     * @param end
     *
     * @return 如果start>end，正数表示start比end早多少天；否则返回就是负数
     */
    public static int compareAccurateToDate(Date start, Date end) {
        Calendar startCalendar = getCalendar(start);
        Calendar endCalendar = getCalendar(end);
        return compareTimeAccurateTo(startCalendar, endCalendar, Calendar.DATE);
    }

    public static int compareAccurateToMonth(Date start, Date end) {
        Calendar startCalendar = getCalendar(start);
        Calendar endCalendar = getCalendar(end);
        return compareTimeAccurateTo(startCalendar, endCalendar, Calendar.MONTH);
    }

    public static int compareAccurateToYear(Date start, Date end) {
        Calendar startCalendar = getCalendar(start);
        Calendar endCalendar = getCalendar(end);
        return compareTimeAccurateTo(startCalendar, endCalendar, Calendar.YEAR);
    }

    public static int getDayBetweensDays(Date start, Timestamp end) {

        long DAY = 24L * 60L * 60L * 1000L;

        return (int) ((start.getTime() - end.getTime()) / DAY);
    }

    /**
     * 对date添加month月份
     *
     * @param date
     * @param month
     *            数字是1--12
     * @return
     */
    public static Date addMonth(Date date, int month) {
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    private static int compareTimeAccurateTo(Calendar startCalendar,
                                             Calendar endCalendar, Integer field) {
        Iterator<Integer> iter = compareFields.iterator();
        while (iter.hasNext()) {
            Integer compareField = iter.next();
            if (field.compareTo(compareField) == 0) {
                return compareDateWithField(startCalendar, endCalendar,
                        compareField);
            }
        }
        return 0;
    }

    private static int compareDateWithField(Calendar startCalendar,
                                            Calendar endCalendar, Integer compareField) {
        return startCalendar.get(compareField) - endCalendar.get(compareField);
    }

    private static Calendar getCalendar(Date start) {
        Calendar result = Calendar.getInstance();
        result.setTime(start);
        return result;
    }


    /**
     * 确定现在是否是上午
     *
     * @return 不是上午返回false，否则返回true
     */
    public static boolean nowIsPM() {
        Calendar now = Calendar.getInstance();
        int amOrPM = now.get(Calendar.AM_PM);
        return amOrPM == Calendar.AM ? false : true;
    }

    /***
     * 将参数currDate字符串格式化成日期型，格式化的格式为:"yyyy-MM-dd"
     *
     * @param currDate
     * @return 返回一个格式化的日期
     */
    public static Date formatStrToDate(String currDate) {
        return getFormatDate(currDate, "yyyy-MM-dd");
    }

    /***
     * 将参数currDate字符串格式化成日期型，格式化的格式为:"yyyy-MM-dd HH:mm:ss"
     *
     * @param currDate
     * @return 返回一个格式化的日期
     */
    public static Date formatStrToDate1(String currDate) {
        return getFormatDate(currDate, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将参数currDate日期型格式化成字符串，格式化的格式为："yyyy-MM-dd"
     *
     * @param currDate
     * @return 返回一个格式化的日期字符串
     */
    public static String formatDateToStr(Date currDate) {
        return getFormatDate(currDate, "yyyy-MM-dd");
    }

    public static String formatDateToStrBy(Date currDate) {
        return getFormatDate(currDate, "yyyy-MM-dd HH:mm:ss");
    }

    /***
     * 将参数currDate日期型格式化的成日期字符串，格式化的格式：参数format决定
     *
     * @param currDate
     *            要格式化的日期
     * @param format
     *            格式化的格式如："yyyy-MM-dd"，"yyyy-MM-dd HH:mm:ss"
     *
     * @return 返回格式化的日期字符串
     */
    public static String getFormatDate(Date currDate, String format) {
        if (currDate == null)
            return "";
        SimpleDateFormat dtFormatdB = null;
        String string;
        try {
            dtFormatdB = new SimpleDateFormat(format);
            string = dtFormatdB.format(currDate);
        } catch (Exception e) {
            dtFormatdB = new SimpleDateFormat("yyyy-MM-dd");
            String string_0_;
            try {
                string_0_ = dtFormatdB.format(currDate);
            } catch (Exception exception) {
                return null;
            }
            return string_0_;
        }
        return string;
    }

    /**
     * 将参数currDate字符串格式化的成日期型，格式化的格式：参数format决定
     *
     * @param currDate
     *            要格式化的日期字符串
     * @param format
     *            格式化的格式如："yyyy-MM-dd"，"yyyy-MM-dd HH:mm:ss"
     *
     * @return 格式化的日期Date
     */
    public static Date getFormatDate(String currDate, String format) {
        if (currDate == null)
            return null;
        SimpleDateFormat dtFormatdB = null;
        Date date;
        try {
            dtFormatdB = new SimpleDateFormat(format);
            date = dtFormatdB.parse(currDate);
        } catch (Exception e) {
            dtFormatdB = new SimpleDateFormat("yyyy-MM-dd");
            Date date_2_;
            try {
                date_2_ = dtFormatdB.parse(currDate);
            } catch (Exception exception) {
                return null;
            }
            return date_2_;
        }
        return date;
    }

    public static Date getFormatDateEndfix(String dateStr) {
        dateStr = addDateEndfix(dateStr);
        return getFormatDateTime(dateStr);
    }

    public static String addDateEndfix(String datestring) {
        if (datestring == null || datestring.equals(""))
            return null;
        return new StringBuilder().append(datestring).append(" 23:59:59")
                .toString();
    }

    public static String appendDateByGivenfix(Date date ,String datestring) {
        if (datestring == null || datestring.equals(""))
            return null;

        return new StringBuilder().append(formatDateToStr(date)).append(" ").append(datestring)
                .toString();
    }

    public static Date getFormatDateTime(String currDate) {
        return getFormatDate(currDate, DEFAULT_FORMAT);
    }

    public static String getFormatDateTime(Date currDate) {
        return getFormatDate(currDate, DEFAULT_FORMAT);
    }

    public static List<Object> getDaysListBetweenDates(Date first, Date second) {

        List<Object> dateList = new ArrayList<Object>();
        Date d1 = getFormatDate(formatDateToStr(first), "yyyy-MM-dd");
        Date d2 = getFormatDate(formatDateToStr(second), "yyyy-MM-dd");

        if (d1.compareTo(d2) > 0)
            return dateList;
        do {
            dateList.add(d1);
            d1 = addDatesToDate(d1, 1);
        } while (d1.compareTo(d2) <= 0);

        return dateList;
    }

    public static String getCurrDateStr() {
        return formatDateToStr(getCurrDate());
    }

    /**
     * 包含分秒的时间
     * @return
     */
    public static String getCurrDateStrBy() {
        return formatDateToStrBy(getCurrDate());
    }

    public static Date getCurrDate() {
        return new Date();
    }

    /**
     * 指定日期str得到对应星期几
     *
     * @param str
     * @return 返回星期几
     */
    public static String getDayOfWeek(String str) {

        Calendar calendar = Calendar.getInstance();
        Date date1 = formatStrToDate(str);

        calendar.setTime(date1);

        return dayNames[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 获取当前Q Title 2011Q1
     *
     * @author hao.zhang
     * @return
     */
    public static String getCurrentSeason() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        int month = now.get(Calendar.MONTH);
        int season = month / 3 + 1;
        return "" + now.get(Calendar.YEAR) + "Q" + season;
    }

//    public static int getCurrentYear() {
//        Calendar now = Calendar.getInstance();
//        return now.get(Calendar.YEAR);
//    }

    public static Calendar getCurrentMonthBefore(int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -month);
        return cal;
    }

    public static Calendar getCurrentWeekBefore(int week) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.WEEK_OF_MONTH, -week);
        return cal;
    }

    public static Calendar getCurrentDayBefore(int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -day);
        return cal;
    }

    public static Calendar getCurrentHourBefore(int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, -hour);
        return cal;
    }

    /**
     * 获取指定日期当前季度的第一天时间
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfSeason(Date date) {

        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int currentMonth = now.get(Calendar.MONTH) + 1;
        int newMonth = ((currentMonth - 1) / 3) * 3;
        now.set(Calendar.MONTH, newMonth);
        now.set(Calendar.DATE, 1);
        return now.getTime();
    }

    /**
     * 获得当前季度中指定的月份<br/>
     * 注意：如果monthOfSeason或dayOfMonth为负数，表示从后往前算。如果超出范围，以边界算。如果为0，则按1算。<br/>
     * <ul>
     * <li>getDateOfSeason(2011,1,1,0) = '2011-1-1'
     * <li>getDateOfSeason(2011,1,1,1) = '2011-1-1'
     * <li>getDateOfSeason(2011,1,1,2) = '2011-1-2'
     * <li>getDateOfSeason(2011,1,1,100) = '2011-1-31'
     * <li>getDateOfSeason(2011,1,1,-1) = '2011-1-31'
     * <li>getDateOfSeason(2011,1,1,-2) = '2011-1-30'
     * <li>getDateOfSeason(2011,1,1,-100) = '2011-1-1'
     * <li>getDateOfSeason(2011,1,3,1) = '2011-3-1'
     * <li>getDateOfSeason(2011,1,4,1) = '2011-3-1'
     * <li>getDateOfSeason(2011,5,1,0) = '2012-1-1'
     * <li>getDateOfSeason(2011,-1,1,0) = '2010-10-1'
     * </ul>
     *
     * @param year
     *            年（必须大于0）
     * @param season
     *            季度（从1开始），如果大于4，则表示下一年。如果为负数，表示从后往前算。
     * @param monthOfSeason
     *            季度中的第几个月（从1开始），如果为为负数，表示从后往前算。
     * @param dayOfMonth
     *            月份中的第几天（从1开始），如果为负数，表示从后往前算
     * @return
     */
    public static Date getDate(int year, int season, int monthOfSeason, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);

        // month
        int newMonth;
        if (season > 0) {
            newMonth = (season - 1) * 3;
        } else if (season < 0) {
            newMonth = season * 3;
        } else {// season == 0
            newMonth = 1;
        }

        if (monthOfSeason == 0) {
            monthOfSeason = 1;
        }
        if (monthOfSeason > 0) {
            newMonth += Math.min(3, monthOfSeason) - 1;
        } else {
            newMonth += 3 + Math.max(-3, monthOfSeason);
        }
        calendar.set(Calendar.MONTH, newMonth);

        // day:防止溢出
        if (dayOfMonth == 0) {
            dayOfMonth = 1;
        }
        if (dayOfMonth > 0) {
            if (dayOfMonth <= 28) {
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            } else {// > 28
                int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                calendar.set(Calendar.DAY_OF_MONTH, Math.min(dayOfMonth, maxDay));
            }
        } else {// <0
            if (dayOfMonth <= -31) {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
            } else {// > -31
                int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                calendar.set(Calendar.DAY_OF_MONTH, Math.max(1, maxDay + dayOfMonth + 1));
            }
        }

        return calendar.getTime();
    }

    /**
     * 获取当前Q Title 2011Q1
     *
     * @author hao.zhang
     * @return
     */
    public static String getCurrentSeasonString() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        int month = now.get(Calendar.MONTH);
        int season = month / 3 + 1;
        return "" + now.get(Calendar.YEAR) + "年Q" + season + "季度";
    }

    public static String getCurrentSeasonTitle() {

        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH);
        int season = month / 3 + 1;
        return now.get(Calendar.YEAR) + "Q" + season;

    }

    public static String getCurrentSeasonNotContainYear() {

        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH);
        int season = month / 3 + 1;
        return "Q" + season;

    }

    /**
     * 获取当前Q int值,20121
     *
     * @author hao.zhang
     * @return
     */
    public static int getIntSeason() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        int month = now.get(Calendar.MONTH);
        int season = month / 3 + 1;
        return now.get(Calendar.YEAR) * 10 + season;
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前季度(从1开始)
     *
     * @param date
     * @return
     */
    public static int getSeason(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) / 3 + 1;
    }

    /**
     * 返回是周的第几天（周一:0,周二:1,...,周日:6）
     *
     * @param calendar
     * @return
     */
    public static int getDayOfWeekFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getDayOfWeekFromCalendar(calendar);
    }

    /**
     * 返回是周的第几天（周一:0,周二:1,...,周日:6）
     *
     * @param calendar
     * @return
     */
    private static int getDayOfWeekFromCalendar(Calendar calendar) {
        return ((calendar.get(Calendar.DAY_OF_WEEK) + 7) - Calendar.MONDAY) % 7;
    }

    /**
     * 获取本周(周一到周日)的周一
     *
     * @param today
     * @return
     */
    public static Date getMondayOfWeek(Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);

        int weekDay = getDayOfWeekFromCalendar(calendar);
        calendar.add(Calendar.DAY_OF_WEEK, -weekDay);
        return calendar.getTime();
    }

    /**
     * 获取指定时间的上周周一的时间
     *
     * @param today
     * @return
     */
    public static Date getMondayOfLastWeek(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        int weekDay = getDayOfWeekFromCalendar(calendar);
        calendar.add(Calendar.DAY_OF_WEEK, -7 - weekDay);
        return calendar.getTime();

    }

    /**
     * 获取下周(周一到周日)的周一
     *
     * @param today
     * @return
     */
    public static Date getMondayOfNextWeek(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        int weekDay = getDayOfWeekFromCalendar(calendar);
        calendar.add(Calendar.DAY_OF_WEEK, 7 - weekDay);
        return calendar.getTime();

    }

    /**
     * 获取本周(周一到周日)的周五
     *
     * @param today
     * @return
     */
    public static Date getFridayOfWeek(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        int weekDay = getDayOfWeekFromCalendar(calendar);
        calendar.add(Calendar.DAY_OF_WEEK, 4 - weekDay);
        return calendar.getTime();
    }


    /**
     * 获取本周(周一到周日)的周六的00:00:00
     *
     * @param today
     * @return
     */
    public static Date getSaturdayOfWeek(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        int weekDay = getDayOfWeekFromCalendar(calendar);
        calendar.add(Calendar.DAY_OF_WEEK, 5 - weekDay);

        return DateTimeUtil.formatStrToDate(DateTimeUtil.formatDateToStr(calendar.getTime()));
    }

    /**
     * 获取本周(周一到周日)的周日
     *
     * @param today
     * @return
     */
    public static Date getSundayOfWeek(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        int weekDay = getDayOfWeekFromCalendar(calendar);
        calendar.add(Calendar.DAY_OF_WEEK, 6 - weekDay);
        return calendar.getTime();
    }

    public static Date formatFixHourBy(String str, int hour) {
        Date date = formatStrToDate(str);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar = DateUtils.truncate(calendar, Calendar.HOUR);
        calendar.set(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    public static boolean isSessionRange(Date startTime, Date endTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        long currentTime = calendar.getTime().getTime();

        return currentTime >= startTime.getTime() &&  currentTime <= endTime.getTime();
    }

    public static boolean isBefore(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        long currentTime = calendar.getTime().getTime();

        return currentTime >= date.getTime();
    }

    public static void main(String[] args) {
//        System.out.println(formatDateToStrBy(formatFixHourBy("2014-08-18 23:12:12", 2)));
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        long currentTime = calendar.getTime().getTime();
//        Date startTime = formatStrToDate1("2014-10-11 09:00:00");
//        Date endTime = formatStrToDate1("2014-10-13 09:00:00");

        Date date = DateTimeUtil.formatStrToDate("2012-09-01");
        Date currDate = new Date();
        int month = compareAccurateToMonth(currDate, date);
        int year = compareAccurateToYear(currDate, date);
        if (month < 0) {
            month = 12 + month;
            year = year - 1 ;
        }
        System.out.print(String.format("男宝宝%s年%s个月", year, month));



    }

}
