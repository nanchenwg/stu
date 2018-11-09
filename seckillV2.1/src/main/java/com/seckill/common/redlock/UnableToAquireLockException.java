package com.seckill.common.redlock;

/**
 * 异常类
 */
public class UnableToAquireLockException extends RuntimeException {
	private static final long serialVersionUID = -3015897633811367270L;

	public UnableToAquireLockException() {
    }

    public UnableToAquireLockException(String message) {
        super(message);
    }

    public UnableToAquireLockException(String message, Throwable cause) {
        super(message, cause);
    }
}