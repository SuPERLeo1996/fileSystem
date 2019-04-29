package com.server.file.exception;

/**
 * @auther: Gonglj
 * @date: 2019/3/27 15:46
 */
public class LoginException extends RuntimeException {
    private static final long serialVersionUID = -7724462536568789306L;

    public LoginException(String message) {
        super(message);
    }
}
