package com.star.common.richclient.form;

/**
 * @author myace
 * @version 1.0
 */
public interface IConvertor {

	<T> T convert(Object src, Class<T> targetClass) throws ConvertRuntimeException;
}
