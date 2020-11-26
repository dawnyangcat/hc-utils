package pers.dawnyang.common.util;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author dawn 2020年8月24日 上午11:41:24
 *
 */
@Slf4j
public class RequestUtil {

	public static String getRequestBody(HttpServletRequest req) {
		try {
			BufferedReader reader = req.getReader();
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			String json = sb.toString();
			return json;
		} catch (IOException e) {
			log.info("请求体读取失败" + e.getMessage());
		}
		return "";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
