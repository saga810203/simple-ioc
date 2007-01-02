package com.star.common.richclient.form;

/**
 * ◊™ªª“Ï≥£°£
 * 
 * @author myace
 * @version 1.0
 */
public class ConvertRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String property;

	private Object value;

	private Class targetClass;

	public ConvertRuntimeException() {
		super();
	}

	public ConvertRuntimeException(String property, Object value,
			Class targetClass) {
		super();
		this.property = property;
		this.value = value;
		this.targetClass = targetClass;
	}

	public ConvertRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConvertRuntimeException(String message) {
		super(message);
	}

	public ConvertRuntimeException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getLocalizedMessage() {
		return getPropertyName(property) + " ‰»Î¥ÌŒÛ°£";
	}

	protected String getPropertyName(String property) {
		return property;
	}

}
