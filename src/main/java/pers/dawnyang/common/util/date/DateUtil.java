package pers.dawnyang.common.util.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String DateFormatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	public static String DateFormatDateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static String DateFormatByStr(Date date, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return sdf.format(date);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
