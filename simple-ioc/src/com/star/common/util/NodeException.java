package com.star.common.util;

/**
 * 加入节点异常.
 * @author myace
 *
 */
public class NodeException extends RuntimeException {

	public NodeException() {
		super();
	}

	public NodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NodeException(String message) {
		super(message);
	}

	public NodeException(Throwable cause) {
		super(cause);
	}

}
