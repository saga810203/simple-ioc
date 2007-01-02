package com.star.common.richclient.support;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * 默认的消息源
 * 
 * @author liuwei
 * 
 */
public class DefaultMessageSource implements MessageSource {

	private final Logger logger = Logger
			.getLogger(DefaultMessageSource.class.getName());

	private String languageResourcePath;

	private ResourceBundle resourceBundle;
	
	public String getMessage(String key) {
		return getMessage(key, (String)null);
	}
	
	public String getMessage(String key, Object[] arg) {
		return getMessage(key, null, arg);
	}

	public String getMessage(String key, String defaultMessage, Object... arg) {
		String str = defaultMessage;
		try {
			str = getResourceBundle().getString(key);
		} catch (Exception e) {
			if (logger.isLoggable(Level.INFO)) {
				logger.info("Miss Message: value=" + key);
			}
		}
		if(str==null){
			return null;
		}
		
		return MessageFormat.format(str,arg);
	}

	public ResourceBundle getResourceBundle() {
		if (resourceBundle == null) {
			resourceBundle = ResourceBundle.getBundle(
					getLanguageResourcePath(), Locale.getDefault());
		}
		return resourceBundle;
	}

	public String getLanguageResourcePath() {
		return languageResourcePath;
	}

	public void setLanguageResourcePath(String languageResourcePath) {
		this.languageResourcePath = languageResourcePath;
	}

}
