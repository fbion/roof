package org.roof.roof.dataaccess.api;

/**
 * 数据访问异常
 * 
 * @author liuxin 2011-9-9
 * @version 1.0 DaoException.java liuxin 2011-9-9
 */
public class DaoException extends Exception {

	private static final long serialVersionUID = 4640181036015959754L;

	public DaoException() {
		super();
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

}
