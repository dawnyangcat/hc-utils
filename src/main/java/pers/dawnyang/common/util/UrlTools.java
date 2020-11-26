package pers.dawnyang.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UrlTools {

	public static void main(String[] args) {

		List<String> commonUrls = new ArrayList<>();
		// commonUrls.add("/");
		commonUrls.add("favicon.ico");
		commonUrls.add("base/loginView");
		commonUrls.add("common/common.html");
		commonUrls.add("sys/common/**");
		// ListStringConvertTwoArrays(commonUrls);
		String requestURL = "/sys/common/common.js";

		Boolean flag = checkCommonUrl(commonUrls, requestURL);
		System.out.println(flag);

	}

	// 去掉传入url开头的斜杠
	public static String dealUrl(String requestURL) {
		if (requestURL.startsWith("/")) {
			return requestURL.substring(1);
		}
		return requestURL;
	}

	// 检查url是否满足自定义白名单要求
	public static Boolean checkCommonUrl(List<String> commonUrls, String url) {
		if (url.equals("/")) {
			return true;
		}
		String requestURL = dealUrl(url);
		List<String[]> commonUrlsArrList = new ArrayList<>();
		for (String str : commonUrls) {
			String dealStr = dealUrl(str);
			String[] strArr = dealStr.split("/");
			commonUrlsArrList.add(strArr);
		}
		String[][] commonUrlsArr = commonUrlsArrList.toArray(new String[0][0]);
		String[] requestURLArr = requestURL.split("/");
		boolean flag = false;
		for1: for (int i = 0; i < requestURLArr.length;) {
			for2: for (int j = 0; j < commonUrlsArrList.size();) {
				if (!requestURLArr[i].equals(commonUrlsArr[j][i]) && !commonUrlsArr[j][i].equals("**")) {
					j++;
				} else {
					for3: for (int z = 0; z < commonUrlsArr[j].length;) {
						if (i == requestURLArr.length - 1) {
							flag = true;
							break for1;
						}
						if (commonUrlsArr[j][z].equals("**")) {
							flag = true;
							break for1;
						}
						if (!requestURLArr[i].equals(commonUrlsArr[j][z]) && !commonUrlsArr[j][z].equals("**")) {
							flag = false;
							break for1;
						} else {
							flag = true;
							z++;
							i++;
						}
					}
					j++;
				}
			}
			break for1;
		}
		return flag;
	}

	// List<String> 转二维数组
	public static void ListStringConvertTwoArrays(List<String> commonUrls) {
		List<String[]> commonUrlsArrList = new ArrayList<>();
		int columnLength = 0;
		for (String str : commonUrls) {
			String[] strArr = str.split("/");
			if (strArr.length > columnLength) {
				columnLength = strArr.length;
			}
			commonUrlsArrList.add(strArr);
		}
		String[][] commonUrlsArr = commonUrlsArrList.toArray(new String[commonUrlsArrList.size()][columnLength]);
		System.out.println(commonUrlsArr[4].length);
	}

	// 两个数组合并
	public static String[][] unite(String[][] os1, String[][] os2) {
		List<String[]> list = new ArrayList<String[]>(Arrays.<String[]>asList(os1));
		list.addAll(Arrays.<String[]>asList(os2));
		return list.toArray(os1);
	}
}
