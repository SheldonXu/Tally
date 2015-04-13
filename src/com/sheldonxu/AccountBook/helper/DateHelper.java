package com.sheldonxu.AccountBook.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

	// 获取当前日期
	public static String getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		return dateFormat.format(calendar.getTime());
	}

	// 格式化指定日期
	public static String formatDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	public static int daysBetween(String smdate, String bdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(between_days));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	//计算下n天
	public static String getDate(String str,int n){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, n);
		Date date1 = new Date(calendar.getTimeInMillis());
		return sdf.format(date1);
	}
}
