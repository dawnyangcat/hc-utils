package pers.dawnyang.common.util.excel;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;

public class EasyExcelUtil {

	public static <T> void writeToResponse(HttpServletResponse response, String filename, String sheetName,
			List<?> records, Class<T> clazz) {
		try {
			String fileName = URLEncoder.encode(filename, "UTF-8");
			response.setContentType("application/vnd.ms-excel");
			// 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			// 这里需要设置不关闭流
			EasyExcel.write(response.getOutputStream(), clazz).autoCloseStream(Boolean.FALSE).sheet(sheetName)
					.doWrite(records);
		} catch (IOException e) {
			// 重置response
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			Map<String, String> map = new HashMap<String, String>();
			map.put("status", "failure");
			map.put("message", "下载文件失败" + e.getMessage());
			try {
				response.getWriter().println(JSON.toJSONString(map));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
