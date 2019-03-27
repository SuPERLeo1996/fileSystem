package com.server.file.exception;

import com.alibaba.fastjson.JSONException;
import com.server.file.DTO.ResultTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @auther: Gonglj
 * @date: 2019/3/27 15:45
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    public ExceptionHandlerAdvice() {
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultTO exceptionResponse(Exception e) {
        ResultTO result = new ResultTO();
        result.setSuccess(false);
        result.setCode(500);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        result.setMsg("\r\n" + sw.toString() + "\r\n");
        logger.error(e.getMessage(), e);
        return result;
    }

//    @ExceptionHandler({LoginException.class})
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public ResultTO loginResponse(LoginException le) {
//        ResultTO result = new ResultTO();
//        result.setSuccess(false);
//        result.setCode(401);
//        result.setMsg(le.getMessage());
//        return result;
//    }

//    @ExceptionHandler({PollingException.class})
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public ResultTO pollingResponse(PollingException pe) {
//        ResultTO result = new ResultTO();
//        result.setSuccess(false);
//        result.setCode(205);
//        result.setMsg(pe.getMessage());
//        return result;
//    }

//    @ExceptionHandler({AuthException.class})
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public ResultTO authResponse(AuthException ae) {
//        ResultTO result = new ResultTO();
//        result.setSuccess(false);
//        result.setCode(403);
//        result.setMsg(ae.getMessage());
//        return result;
//    }

    @ExceptionHandler({CommonException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultTO commonResponse(CommonException ce) {
        ResultTO result = new ResultTO();
        result.setSuccess(false);
        result.setCode(ce.getCode());
        result.setMsg(ce.getMessage());
        return result;
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultTO httpRequestMethodResponse(HttpRequestMethodNotSupportedException hrmse) {
        ResultTO result = new ResultTO();
        result.setSuccess(false);
        result.setCode(405);
        result.setMsg(hrmse.getMessage());
        return result;
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultTO httpMessageNotReadableResponse(HttpMessageNotReadableException hmnre) {
        ResultTO result = new ResultTO();
        result.setSuccess(false);
        result.setCode(400);
        result.setMsg(hmnre.getMessage());
        return result;
    }

    @ExceptionHandler({JSONException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultTO httpMessageNotReadableResponse(JSONException je) {
        ResultTO result = new ResultTO();
        result.setSuccess(false);
        result.setCode(400);
        result.setMsg(je.getMessage());
        return result;
    }

}
