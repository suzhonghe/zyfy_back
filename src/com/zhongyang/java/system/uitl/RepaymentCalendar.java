package com.zhongyang.java.system.uitl;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @package com.zhongyang.java.system.uitl
 * @filename RepaymentCalendar.java
 * @date 2017年7月14日上午9:58:59
 * @author suzh
 */
public class RepaymentCalendar {
	// int j 集合数量
	// int newDate 当前日期
	/**
	 * 回款日期
	 * 
	 * @date 上午9:54:39
	 * @param j
	 * @return
	 * @author suzh
	 */
	public static String[] getNextmonths(int j) {
		j = j + 1;
		String[] last12Months = new String[j];
		Date dNow = new Date(); // 当前时间
		Date dBefore = null;
		Calendar calendar = Calendar.getInstance(); // 得到日历

		for (int i = 1; i < j; i++) {
			calendar.setTime(dNow);// 把当前时间赋给日历
			calendar.add(Calendar.MONTH, i); // 增加i个月
			dBefore = calendar.getTime(); // 得到增加i个月后的时间
			last12Months[i] = FormatUtils.simpleFormat(dBefore); // 格式化增加i个月后的时间yyyy-MM-dd

			System.out.println("回款计划：" + last12Months[i]);
		}
		return last12Months;
	}

	// int j 集合数量
	// int newDate 当前日期
	/**
	 * 最后回款日期
	 * 
	 * @date 上午9:55:28
	 * @param j
	 * @return
	 * @author suzh
	 */
	public static String[] getBulletmonths(int j) {
		j = j + 1;
		String[] last12Months = new String[j];
		Date dNow = new Date(); // 当前时间
		Date dBefore = null;
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(Calendar.MONTH, j);
		dBefore = calendar.getTime(); // 得到增加j个月后的时间
		
		last12Months[j]	= FormatUtils.simpleFormat(dBefore); // 格式化增加i个月后的时间yyyy-MM-dd
		
		return last12Months;
	}

	public static void main(String[] args) {
		getNextmonths(3);
		System.out.println(getBulletmonths(3));
	}
}
