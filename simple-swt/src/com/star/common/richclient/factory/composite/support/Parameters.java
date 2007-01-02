package com.star.common.richclient.factory.composite.support;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 界面控件复合体的参数。
 * 
 * @author liuwei
 * @version 1.0
 */
public class Parameters {

	private Map<String, Object> paramMap;

	private String[] validParamKeys;

	/**
	 * .
	 * 
	 * @param validParamKeys .
	 * @param parameters .
	 */
	public Parameters(String[] validParamKeys, String parameters) {
		this.validParamKeys = validParamKeys;
		interpreterParametersStr(parameters);
		validateParameterKey();
	}

	// ======================================================

	/**
	 * .
	 * 
	 * @return .
	 */
	public Map<String, Object> getParamMap() {
		if (paramMap == null) {
			paramMap = new HashMap<String, Object>();
		}
		return paramMap;
	}

	/**
	 * .
	 * 
	 * @param key .
	 * @return .
	 */
	public Object getParam(String key) {
		return getParamMap().get(key);
	}

	/**
	 * .
	 * 
	 * @param key .
	 * @return .
	 */
	public boolean containsParamKey(String key) {
		return getParamMap().containsKey(key);
	}

	/**
	 * .
	 * 
	 * @param key .
	 * @param defaultValue .
	 * @return .
	 */
	public Object getParam(String key, Object defaultValue) {
		return containsParamKey(key) ? getParam(key) : defaultValue;
	}

	/**
	 * 得到参数，并转换为整数。
	 * 
	 * @param key
	 *            参数键值
	 * @return 参数
	 */
	public int getIntParam(String key) {
		Object p = getParam(key);
		if (p == null) {
			return 0;
		} 
        else if (p instanceof Number) {
			return ((Number) p).intValue();
		} 
        else {
			try {
				return Integer.parseInt(p.toString());
			} 
            catch (NumberFormatException e) {
				throwParameterError(key);
				return 0;
			}
		}
	}

	/**
	 * 得到浮点型参数
	 * 
	 * @param key
	 *            参数键值
	 * @return .
	 */
	public float getFloatParam(String key) {
		Object p = getParam(key);
		if (p == null) {
			return 0f;
		} 
        else if (p instanceof Number) {
			return ((Number) p).floatValue();
		} 
        else {
			try {
				return Float.parseFloat(p.toString());
			} 
            catch (NumberFormatException e) {
				throwParameterError(key);
				return 0f;
			}
		}
	}

	/**
	 * .
	 * 
	 * @param key .
	 * @param defaultValue .
	 * @return .
	 */
	public float getFloatParam(String key, float defaultValue) {
		return containsParamKey(key) ? getFloatParam(key) : defaultValue;
	}

	/**
	 * .
	 * 
	 * @param key .
	 * @param defaultValue .
	 * @return .
	 */
	public int getIntParam(String key, int defaultValue) {
		return containsParamKey(key) ? getIntParam(key) : defaultValue;
	}

	/**
	 * .
	 * 
	 * @param key .
	 * @return .
	 */
	public String getStrParam(String key) {
		Object p = getParam(key);
		return p == null ? null : p.toString();
	}

	/**
	 * .
	 * 
	 * @param key .
	 * @param defaultValue .
	 * @return .
	 */
	public String getStrParam(String key, String defaultValue) {
		return containsParamKey(key) ? getStrParam(key) : defaultValue;
	}

	/**
	 * .
	 * 
	 * @param key .
	 * @return .
	 */
	public boolean getBoolParam(String key) {
		Object p = getParam(key);
		return p == null ? false : Boolean.parseBoolean(p.toString());
	}

	/**
	 * .
	 * 
	 * @param key .
	 * @param defaultValue .
	 * @return .
	 */
	public boolean getBoolParam(String key, boolean defaultValue) {
		return containsParamKey(key) ? getBoolParam(key) : defaultValue;
	}
    
 	// ========================================

	private void interpreterParametersStr(String str) {
		if (str == null) {
			return;
		}

		Map<String, Object> paramMap = getParamMap();

		String[] keyAndValues = str.split("\\s+");
		for (String temp : keyAndValues) {
			String[] keyAndValue = temp.split("=");
			if (keyAndValue.length > 2 || keyAndValue.length < 1) {
				throw new IllegalArgumentException("parameterKey["
						+ keyAndValue + "] is illegal.");
			}
			paramMap.put(keyAndValue[0],
					keyAndValue.length > 1 ? keyAndValue[1] : null);
		}
	}

	private void validateParameterKey() {
		if (validParamKeys != null) {
			Set<String> set = new HashSet<String>();
			Collections.addAll(set, validParamKeys);
			for (String key : getParamMap().keySet()) {
				if (!set.contains(key)) {
					throw new IllegalArgumentException("parameterKey[" + key
							+ "] is illegal.");
				}
			}
		}
	}

	/**
	 * 抛出参数错误异常。
	 * 
	 * @param parameterKey
	 *            参数键值
	 */
	public void throwParameterError(String parameterKey) {
		throw new IllegalArgumentException(getClass().getSimpleName()
				+ "'s param[" + parameterKey + "=" + getParam(parameterKey)
				+ "] is illegal.");
	}

}
