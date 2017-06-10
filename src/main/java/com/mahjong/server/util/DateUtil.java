package com.mahjong.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public static final String DATE_FORMAT2 = "yyyy年MM月dd日";

	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String TIME_FORMAT = "HH:ss:mm";

	public static String formatDataToChsStr(Date date) {
		if (null == date) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT2);
		return format.format(date);
	}

	public static String fromatDateToYYMMDDHHMMSS(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
		return sdf.format(date);
	}

	public static Date formatStrToDate(String datestr, String format) {
		if (null == datestr) {
			return null;
		}
		if (null == format) {
			format = DATETIME_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(datestr);
		} catch (Exception e) {
			return null;
		}
		return date;
	}

	/*
	 * 将时间转换为指定格式的字符串
	 * 
	 */
	public static String dateToStr(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String datestr = "";
		try {
			datestr = sdf.format(date);
		} catch (Exception e) {
			return datestr;
		}
		return datestr;
	}

	public static Date strToDate(String str, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (Exception e) {
			return date;
		}
		return date;
	}

	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	public static long daysBetween(Date start, Date end) {
		long diff = 0;
		if (start == null || end == null) {
			return diff;
		}
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		try {
			start = f.parse(f.format(start));
			end = f.parse(f.format(end));
		} catch (ParseException e) {
			return diff;
		}
		Calendar c = Calendar.getInstance();
		if (start.before(end)) {
			c.setTime(start);
			while (c.getTime().before(end)) {
				c.add(Calendar.DAY_OF_MONTH, 1);
				diff++;
			}
		} else {
			c.setTime(start);
			while (c.getTime().after(end)) {
				c.add(Calendar.DAY_OF_MONTH, -1);
				diff--;
			}
		}
		return diff;
	}

	public static String getStrFromDate(Date date) {
		return dateToStr(date, DATE_FORMAT);
	}

	/*
	 * 比较两个时间大小
	 * 
	 */
	public static int compare(Date date1, Date date2) {
		return date1.compareTo(date2);
	}

	/*
	 * 获取当天剩余时间
	 */
	public static Long getDaySurplusTime() {
		Calendar currentDate = new GregorianCalendar();
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);
		return (currentDate.getTime().getTime() - new Date().getTime()) / 1000;
	}

	/*
	 * Description:判断时间是否超时<br>
	 */
	public static boolean isExpireByNow(Date date) {
		if (null == date) {
			return false;
		}
		Date now = new Date();
		return now.compareTo(date) == -1;
	}
}
