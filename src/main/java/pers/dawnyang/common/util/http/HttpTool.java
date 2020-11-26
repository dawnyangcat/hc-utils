package pers.dawnyang.common.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpTool {

	public static String urlPostMethod(String url, Map<String, String> params) {
		HttpClient hc = new HttpClient();
		PostMethod pm = new PostMethod(url);
		pm.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		hc.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		hc.getHttpConnectionManager().getParams().setSoTimeout(9000);
		for (Entry<String, String> e : params.entrySet()) {
			pm.addParameter(e.getKey(), e.getValue());
		}
		try {
			hc.executeMethod(pm);
			InputStream inputStream = pm.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer stringBuffer = new StringBuffer();
			String str;
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str);
			}
			String responses = new String(stringBuffer);
			br.close();

			return responses;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String urlPostMethodString(String url, String params) {
		HttpClient hc = new HttpClient();
		PostMethod pm = new PostMethod(url);
		pm.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		hc.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
		hc.getHttpConnectionManager().getParams().setSoTimeout(10000);
		try {
			if (params != null && !params.trim().equals("")) {
				StringRequestEntity requestEntity = new StringRequestEntity(params, "text/xml", "UTF-8");
				pm.setRequestEntity(requestEntity);
			}

			pm.releaseConnection();
			hc.executeMethod(pm);

			InputStream inputStream = pm.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer stringBuffer = new StringBuffer();
			String str;
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str);
			}
			String responses = new String(stringBuffer);
			br.close();

			return responses;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String urlPostMethodString(String url, String type, String params) {
		HttpClient hc = new HttpClient();
		PostMethod pm = new PostMethod(url);
		pm.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		hc.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
		hc.getHttpConnectionManager().getParams().setSoTimeout(10000);
		try {
			if (params != null && !params.trim().equals("")) {
				StringRequestEntity requestEntity = new StringRequestEntity(params, type, "UTF-8");
				pm.setRequestEntity(requestEntity);
			}

			pm.releaseConnection();
			hc.executeMethod(pm);

			InputStream inputStream = pm.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer stringBuffer = new StringBuffer();
			String str;
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str);
			}
			String responses = new String(stringBuffer);
			br.close();
			return responses;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String toJson(Object object) {
		ObjectMapper om = new ObjectMapper();
		String r = "";
		try {
			r = om.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return r;
	}

}
