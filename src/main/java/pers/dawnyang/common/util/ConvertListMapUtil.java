package pers.dawnyang.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * List实体 集合转换成List Map 集合
 */
public class ConvertListMapUtil {
    public static <T> List<Map<String, Object>> convertListMap(List<T> list) {
	List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
	for (Object obj : list) {
	    if (null != obj) {
		Class<? extends Object> c = obj.getClass();
		Field[] fields = c.getDeclaredFields();
		Map<String, Object> map = new HashMap<String, Object>();
		for (Field fie : fields) {
		    try {
			fie.setAccessible(true); // 取消语言访问检查
			map.put(fie.getName(), fie.get(obj)); // 获取私有变量值
		    } catch (IllegalArgumentException e) {
			e.printStackTrace();
		    } catch (IllegalAccessException e) {
			e.printStackTrace();
		    }
		}
		// 获取父类的私有属性
		for (Field fie : c.getSuperclass().getDeclaredFields()) {
		    try {
			fie.setAccessible(true); // 取消语言访问检查
			map.put(fie.getName(), fie.get(obj)); // 获取私有变量值
		    } catch (IllegalArgumentException e) {
			e.printStackTrace();
		    } catch (IllegalAccessException e) {
			e.printStackTrace();
		    }
		}
		maps.add(map);
	    }
	}
	return maps;
    }
}