package com.ag777.util.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import com.ag777.util.lang.IOUtils;

/**
 * 针对属性文件的读写操作工具类
 * 
 * @author ag777
 * @version create on 2015年04月04日,last modify at 20218年04月19日
 */
public class PropertyUtils extends java.util.Properties{

	private static final long serialVersionUID = 5668981040083393897L;

	/**
	 * 加载文件
	 * <p>
	 *  默认utf-8编码
	 * </p>
	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public PropertyUtils load(String filePath) throws FileNotFoundException, IOException {
		return load(filePath, StandardCharsets.UTF_8);
	}
	
	/**
	 * 加载文件
	 * @param filePath
	 * @param charset
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public PropertyUtils load(String filePath, Charset charset) throws FileNotFoundException, IOException {
		BufferedReader reader = null;
		try {
			reader = FileNioUtils.getBufferedReader(Paths.get(filePath), charset);
			load(reader);
		} catch(IOException ex) {
			throw ex;
		} finally {
			IOUtils.close(reader);
		}
		return this;
	}
	
	public String get(String key) {
		return getProperty(key);
	}
	
	public String get(String key, String defaultValue) {
		return getProperty(key, defaultValue);
	}
	
	public Integer getInt(String key) {
		return getInt(key, null);
	}
	
	public Integer getInt(String key, Integer defaultValue) {
		String value = getProperty(key);
		if (value != null) {
			return Integer.parseInt(value.trim());
		}
		return defaultValue;
	}
	
	public Long getLong(String key) {
		return getLong(key, null);
	}
	
	public Long getLong(String key, Long defaultValue) {
		String value = getProperty(key);
		if (value != null) {
			return Long.parseLong(value.trim());
		}
		return defaultValue;
	}
	
	public Boolean getBoolean(String key) {
		return getBoolean(key, null);
	}
	
	public Boolean getBoolean(String key, Boolean defaultValue) {
		String value = getProperty(key);
		if (value != null && !"".equals(value)) {
			value = value.toLowerCase().trim();
			if ("true".equals(value)) {
				return true;
			} else if ("false".equals(value)) {
				return false;
			}
			throw new RuntimeException("The value can not parse to Boolean : " + value);
		}
		return defaultValue;
	}
	
	public boolean containsKey(String key) {
		return super.containsKey(key);
	}
	
	@Override
	public String getProperty(String key) {
		String temp = super.getProperty(key);
		if(temp != null) {
			temp.trim();
		}
		return temp;
	}
}
