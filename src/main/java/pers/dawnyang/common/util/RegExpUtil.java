package pers.dawnyang.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpUtil {
	public static boolean checkEmail(String str) {
		// 邮箱验证规则
		String regEx = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
		return regCheck(str, regEx);
	}

	public static boolean checkName(String str) {
		// 用户名验证规则 只包含中文、英文、下划线
		String regEx = "^[一-龥A-Za-z0-9_]{1,10}$";
		return regCheck(str, regEx);
	}

	public static boolean checkPsw(String str) {
		// 检查密码， 密码(长度在6~30之间，只能包含字母、数字和下划线)：
		String regEx = "^[A-Za-z0-9_]{6,30}$";
		return regCheck(str, regEx);
	}

	/**
	 * @param str   被匹配的字符串
	 * @param regEx 正则表达式
	 * @return 是否匹配成功
	 */
	public static boolean regCheck(String str, String regEx) {
		if (str == null || str.equals("")) {
			return false;
		}
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		// 字符串是否与正则表达式相匹配
		boolean rs = matcher.matches();
		return rs;
	}
}
