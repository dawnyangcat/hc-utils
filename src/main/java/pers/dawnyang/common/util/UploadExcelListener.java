package pers.dawnyang.common.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

/**
 * 模板的读取类
 *
 * @author Jiaju Zhuang
 */
// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class UploadExcelListener<T> extends AnalysisEventListener<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadExcelListener.class);
	/**
	 * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
	 */
	private static final int BATCH_COUNT = 5;
	List<T> list = new ArrayList<>();
	/**
	 * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
	 */

	private Object obj;

//如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
	public UploadExcelListener(Object obj) {
		this.obj = obj;
	}

	@Override
	public void invoke(T data, AnalysisContext context) {
		LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
		list.add(data);
		// 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
		if (list.size() >= BATCH_COUNT) {
			saveData();
			// 存储完成清理 list
			list.clear();
		}
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		// 这里也要保存数据，确保最后遗留的数据也存储到数据库
		saveData();
		LOGGER.info("所有数据解析完成！");
	}

	private void saveData() {
		LOGGER.info("{}条数据，开始存储数据库！", list.size());
		/*
		 * for (UserExcelRes userExcelRes : list) { HcUser hcUser = new HcUser();
		 * BeanUtils.copyProperties(userExcelRes, hcUser); hcUserMapper.insert(hcUser);
		 * }
		 */

		Class<? extends Object> clazz = obj.getClass();
		try {
			Method method = clazz.getMethod("saveBatch", Collection.class);
			method.invoke(obj, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(obj);
		// userService.saveBatch(list);
		LOGGER.info("存储数据库成功！");
	}
}