package pers.dawnyang.common.util;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

/**
 *
 * @author dawn 2020年9月28日 下午5:32:48
 *
 */

public class FileUtil {

	public static boolean coverFile(String string, String path) {
		PrintStream stream = null;
		try {
			stream = new PrintStream(path);// 写入的文件path
			stream.print(string);// 写入的字符串
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean addToFile(String string, String path) {
		// 文件的续写
		FileWriter fw = null;
		try {
			fw = new FileWriter(path, true);
			// 写入换行
			fw.write("\r\n");// Windows平台下用\r\n，Linux/Unix平台下用\n
			// 续写一个hello world!
			fw.write(string);
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
