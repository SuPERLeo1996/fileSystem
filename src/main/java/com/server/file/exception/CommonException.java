package com.server.file.exception;

/**
 * @auther: Gonglj
 * @date: 2019/3/27 15:46
 */
public class CommonException extends RuntimeException {
    private static final long serialVersionUID = 7608330395582067150L;
    private int code = 400;

    public CommonException(String message) {
        super(message);
    }

    public CommonException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CommonException(String message, Exception e) {
        super(message, e);
    }

    public CommonException(int code, String message, Exception e) {
        super(message, e);
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
