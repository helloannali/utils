package com.ag777.util.lang.model;

import java.nio.charset.Charset;

/**
 * 存放编码的类
 * 
 * @author 
 * @version create on 2017年11月03日,last modify at 2018年04月13日
 */
public class Charsets {

	private Charsets() {}
	
	/** 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块 */
	public static final String US_ASCII = "US-ASCII";

	/** ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1 */
	public static final String ISO_8859_1 = "ISO-8859-1";

	/** 8 位 UCS 转换格式 */
	public static final String UTF_8 = "UTF-8";

	/** 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序 */
	public static final String UTF_16BE = "UTF-16BE";

	/** 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序 */
	public static final String UTF_16LE = "UTF-16LE";

	/** 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识 */
	public static final String UTF_16 = "UTF-16";

	/** 中文超大字符集 */
	public static final String GBK = "GBK";
	
	public static Charset of(String encoding) {
		return Charset.forName(encoding);
	}
	
	public static Charset utf8() {
		return Charset.forName(UTF_8);
	}
}
