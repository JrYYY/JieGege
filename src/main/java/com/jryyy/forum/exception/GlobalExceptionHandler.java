package com.jryyy.forum.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.jryyy.forum.constant.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 将异常解析为json
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * The message source.
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * @param status    状态
     * @param message   信息
     * @return
     */
    private static ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", status.value());
        responseBody.put("message", message);
        return new ResponseEntity<>(responseBody, status);
    }

    /**
     * @param s {@link Status}
     * @return {@link ResponseEntity}
     */
    private static ResponseEntity<Object> buildErrorResponse(HttpStatus status, Status s) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", s.getCode());
        responseBody.put("message", s.getMsg());
        return new ResponseEntity<>(responseBody, status);
    }

    @ExceptionHandler(GlobalException.class)
    private static Object GlobalException(GlobalException e) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, e.status);
    }


    /**
     * 请求错误异常
     *
     * @param e {@link IllegalArgumentException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Object IllegalArgumentException(IllegalArgumentException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    /**
     * 请求错误异常
     *
     * @param ex {@link MultipartException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(MultipartException.class)
    public Object handleMultipartException(MultipartException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }


    /**
     * 运行时错误异常
     *
     * @param e {@link RuntimeException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(RuntimeException.class)
    public Object handleException(RuntimeException e) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    /**
     * 无法找到请求资源异常
     *
     * @param ex {@link EntityNotFoundException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public Object handleEntityNotFoundException(EntityNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * 被拒绝访问异常
     *
     * @param ex
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Object handleAccessDeniedException(AccessDeniedException ex) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    /**
     * 来定制所有异常类型的响应主体
     *
     * @param ex      the exception
     * @param body    the body for the response
     * @param headers the headers for the response
     * @param status  the response status
     * @param request the current request
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        String message = ex.getMessage();

        if (ex instanceof HttpMessageNotReadableException) {
            message = "请求正文缺失或无效";
            if (ex.getCause() != null && ex.getCause() instanceof JsonMappingException &&
                    ex.getCause().getCause() != null
                    && ex.getCause().getCause() instanceof IllegalArgumentException) {
                message = ex.getCause().getCause().getMessage();
            }
        } else if (ex instanceof MethodArgumentNotValidException) {
            message = convertErrorsToMessage(((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors());
        } else if (ex instanceof BindException) {
            message = convertErrorsToMessage(((BindException) ex).getBindingResult().getAllErrors());
        } else if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
            message = "内部服务器错误";
        }
        return buildErrorResponse(status, message);
    }

    /**
     * 将对象错误转换为错误消息字符串。
     *
     * @param objectErrors the list of object errors
     * @return the comma separated error message
     */
    private String convertErrorsToMessage(List<ObjectError> objectErrors) {
        List<String> messages = new ArrayList<>();

        for (ObjectError objectError : objectErrors) {
            messages.add(messageSource.getMessage(objectError, null));
        }

        return StringUtils.collectionToDelimitedString(messages, ", ");
    }

}