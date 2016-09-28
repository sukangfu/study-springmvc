package com.zkn.spring.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * 字符串转Date 数据转换器
 * @since Spring3.1 +
 * @author MR.SU
 *
 */
public class StingToDateConverter implements Converter<String, Date> {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public Date convert(String source) {
		SimpleDateFormat format;
		
		try {
			if(StringUtils.isNotEmpty(source) && source.length() == 10){
				format = new SimpleDateFormat("yyyy-MM-dd");
			} else {
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
			return format.parse(source);
		} catch (ParseException e) {
			logger.warn("日期[%s]转换出错", source);
		}
		return null;
	}

}
