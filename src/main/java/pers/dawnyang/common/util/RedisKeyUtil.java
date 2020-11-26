package pers.dawnyang.common.util;

/**
 * redisKey设计
 */
public class RedisKeyUtil {

	public static String getKeyWithColumn(String tableName, String majorKey, String majorKeyValue, String column) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(tableName).append(":");
		buffer.append(majorKey).append(":");
		buffer.append(majorKeyValue).append(":");
		buffer.append(column);
		return buffer.toString();
	}

	public static String getKey(String tableName, String majorKey, String majorKeyValue) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(tableName).append(":");
		buffer.append(majorKey).append(":");
		buffer.append(majorKeyValue).append(":");
		return buffer.toString();
	}
}