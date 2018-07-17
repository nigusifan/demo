package net.intelink.zmframework.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.alibaba.fastjson.JSON;

public class StringUtil {
	/**
	 * 去掉字符串中的空格、回车、换行符、制表符
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	public static boolean isNotEmpty(String str) {
		return str != null && str.length() > 0;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return !isNotEmpty(str);
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(Object str) {
		boolean flag = true;
		if (str != null && !str.equals("")) {
			if (str.toString().length() > 0) {
				flag = true;
			}
		} else {
			flag = false;
		}
		return flag;
	}
	
	public static String toString(Object obj) {
		if (obj != null) {
			Class<? extends Object> objClazz = obj.getClass();
			StringBuilder sb = new StringBuilder();
			sb.append(objClazz).append(":");
			sb.append(JSON.toJSONString(obj));
			return sb.toString();
		}

		return "null";
	}

	public static String getStackTrace(Exception e) {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		e.printStackTrace(new java.io.PrintWriter(buf, true));
		String expMessage = buf.toString();
		try {
			buf.close();
		} catch (IOException e1) {
			return e.getMessage();
		}

		return expMessage;
	}

	public static String getStackTrace(Exception e, int length) {
		String stackTrace = getStackTrace(e);

		if (stackTrace != null && stackTrace.length() > length) {
			return stackTrace.substring(0, length);
		}

		return stackTrace;
	}

	/**
	 * 判断一个字符串是否为正整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPositiveInteger(String str) {

		return str.matches("[1-9]+\\d*");
	}

	/**
	 * 判断一个字符串是否为数字（包括正负数、小数）
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {

		return str.matches("-?[0-9]+.?[0-9]*");
	}

	/**
	 * 判断一个字符串是否为大于等于0数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric1(String str) {

		return str.matches("([0-9]{0,6})|([0]\\.\\d{1,2}|[0-9]{0,6}\\.\\d{1,2})");
	}

	/**
	 * 过滤特殊字符，用一个符号代替
	 * 
	 * @param str,flag
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String StringFilter(String str, String flag) throws PatternSyntaxException {
		// 只允许字母和数字 String regEx ="[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);

		return m.replaceAll(flag).trim();
	}

	/**
	 * 百度计算时长转换
	 * 
	 * @param baidu
	 * @return
	 */
	public static int getTimeFrom(String baidu) {
		int res = 0;
		int indexOf = baidu.indexOf("天");
		if (indexOf >= 0) {
			String t = baidu.substring(0, indexOf);
			res = Integer.valueOf(t) * 24 * 60;

			baidu = baidu.substring(indexOf + 1);
		}

		int indexOf2 = baidu.indexOf("小时");
		if (indexOf2 >= 0) {
			if (indexOf < 0) {
				indexOf = 0;
			}
			String t = baidu.substring(0, indexOf2);
			res += Integer.valueOf(t) * 60;

			baidu = baidu.substring(indexOf2 + 2);
		}

		int indexOf3 = baidu.indexOf("分钟");
		if (indexOf3 >= 0) {
			if (indexOf2 < 0) {
				indexOf2 = 0;
			}
			String t = baidu.substring(0, indexOf3);
			res += Integer.valueOf(t);
		}

		return res;
	}

	// 流水号加1后返回，流水号长度为length, 不足前面补0
	public static String formateSeq(String str, int length) {
		String strLenth = "0";
		for (int i = 1; i < length; i++) {
			strLenth = strLenth + "0";
		}
		Integer seqNO = Integer.parseInt(str);
		seqNO++;
		DecimalFormat df = new DecimalFormat(strLenth);
		return df.format(seqNO);
	}

	public static void main(String[] args) {

	}
}