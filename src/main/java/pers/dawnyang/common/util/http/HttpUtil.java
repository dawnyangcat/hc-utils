package pers.dawnyang.common.util.http;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class HttpUtil {
	private HttpUtil() {
	}

	public static String sendGet(String finalUrl) throws IOException {
		GetMethod method = new GetMethod(finalUrl);
		HttpClient client = new HttpClient();

		client.getHttpConnectionManager().getParams().setConnectionTimeout(60 * 1000);
		client.getHttpConnectionManager().getParams().setSoTimeout(60 * 1000);

		client.executeMethod(method);
		InputStream inshttp = method.getResponseBodyAsStream();
		StringBuffer sbResult = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(inshttp));

		String temp = null;
		while ((temp = br.readLine()) != null) {
			sbResult.append(temp);
		}
		inshttp.close();
		return sbResult.toString();
	}

	public static String sendGet(String url, Map<String, String> params) throws IOException {
		StringBuffer sb = new StringBuffer();
		for (String string : params.keySet()) {
			sb.append(string).append("=").append(params.get(string)).append("&");
		}
		int length = sb.length();
		sb.deleteCharAt(length - 1);
		String finalUrl = url + "?" + sb;
		GetMethod method = new GetMethod(finalUrl);
		HttpClient client = new HttpClient();

		client.getHttpConnectionManager().getParams().setConnectionTimeout(60 * 1000);
		client.getHttpConnectionManager().getParams().setSoTimeout(60 * 1000);

		client.executeMethod(method);
		InputStream inshttp = method.getResponseBodyAsStream();
		StringBuffer sbResult = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(inshttp));

		String temp = null;
		while ((temp = br.readLine()) != null) {
			sbResult.append(temp);
		}
		inshttp.close();
		return sbResult.toString();
	}

	public static String sendPost(String url, Map<String, String> params) throws IOException {

		PostMethod method = new PostMethod(url);
		for (String string : params.keySet()) {
			method.addParameter(string, params.get(string));
		}
		HttpClient client = new HttpClient();

		client.getHttpConnectionManager().getParams().setConnectionTimeout(60 * 1000);
		client.getHttpConnectionManager().getParams().setSoTimeout(60 * 1000);

		client.executeMethod(method);
		InputStream inshttp = method.getResponseBodyAsStream();
		StringBuffer sbResult = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(inshttp));

		String temp = null;
		while ((temp = br.readLine()) != null) {
			sbResult.append(temp);
		}
		inshttp.close();
		return sbResult.toString();
	}

	public static String sendPost(String url, String content) throws IOException {

		PostMethod method = new PostMethod(url);
		method.setRequestEntity(new StringRequestEntity(content, "", "utf-8"));
		HttpClient client = new HttpClient();

		client.getHttpConnectionManager().getParams().setConnectionTimeout(60 * 1000);
		client.getHttpConnectionManager().getParams().setSoTimeout(60 * 1000);

		client.executeMethod(method);
		InputStream inshttp = method.getResponseBodyAsStream();
		StringBuffer sbResult = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(inshttp));

		String temp = null;
		while ((temp = br.readLine()) != null) {
			sbResult.append(temp);
		}
		inshttp.close();
		return sbResult.toString();
	}

	public String sendFile(String url, String filePath) throws IOException {
		String result = null;
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}
		// 第一部分
		URL urlObj = new URL(url);
		// 连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		con.setConnectTimeout(5 * 60 * 1000);
		con.setReadTimeout(60 * 1000);
		// 设置关键值
		con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存
		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		// 设置边界
		String BOUNDARY = "---------------------------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		// 请求正文信息
		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("utf-8");
		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);
		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("数据读取异常");
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return result;
	}

	public static String edcodeUrl(String url) {
		String retUrl = "";
		try {
			retUrl = URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return retUrl;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
