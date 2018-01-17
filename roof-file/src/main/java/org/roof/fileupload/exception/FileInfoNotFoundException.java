package org.roof.fileupload.exception;

public class FileInfoNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1127471699517736191L;

	public FileInfoNotFoundException() {
		super();
	}

	public FileInfoNotFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileInfoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileInfoNotFoundException(String message) {
		super(message);
	}

	public FileInfoNotFoundException(Throwable cause) {
		super(cause);
	}

}
