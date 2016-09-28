package com.zkn.springmvc.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 属性工具类，支持同时加截多个属性文件
 */
public class PropertiesUtils {
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
	private static final String CONFIG_FILE = "/META-INF/application.properties";

	private PropertiesUtils() {
		//隐藏的构造方法
	}

	private static final Properties PROPERTIES ;
	
	static{
		InputStream input = PropertiesUtils.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
		PROPERTIES = new Properties();
		try {
			PROPERTIES.load(input);
		} catch (IOException e) {
			logger.error("加载properties文件失败", e);
		} finally{
			try {
				input.close();
			} catch (IOException e) {
				logger.error("关闭输入流失败", e);
			}
		}
	}
	
	public static String getProperty(String key){
		try {
			return new String(PROPERTIES.getProperty(key).getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("编码类型错误",e);
		}
		return null;
	}
	
}
