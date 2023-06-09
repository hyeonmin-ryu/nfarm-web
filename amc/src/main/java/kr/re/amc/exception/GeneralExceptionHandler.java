package kr.re.amc.exception;


import static kr.re.amc.utils.ApiUtils.newResponse;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/*
 * @package : com.itsm.dranswer.errors
 * @name : GeneralExceptionHandler.java
 * @date : 2021-05-25 오후 3:33
 * @author : xeroman.k
 * @version : 1.0.0
 * @modifyed :
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GeneralExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

//    private ResponseEntity<ApiUtils.ApiResult<?>> newResponse(Throwable throwable, HttpStatus status) {
//        return newResponse(throwable.getMessage(), status);
//    }
//
//    private ResponseEntity<ApiUtils.ApiResult<?>> newResponse(String message, HttpStatus status) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//        return new ResponseEntity<>(error(message, status), headers, status);
//    }

    // 필요한 경우 적절한 예외타입을 선언하고 newResponse 메소드를 통해 응답을 생성하도록 합니다.

    @ExceptionHandler({
            NoHandlerFoundException.class,
            NotFoundException.class
    })
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        return newResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(Exception e) {
        return newResponse(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(HttpServletRequest request, HttpServletResponse response,
                                                         Exception e) throws IOException {
        String contentType = request.getHeader("Content-Type");

        if (contentType == null) {
            response.sendRedirect("/");
        }

        return newResponse(e, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class
    })
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        log.debug("Bad request exception occurred: {}", e.getMessage(), e);
        if (e instanceof MethodArgumentNotValidException) {
            return newResponse(
                    ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (e instanceof ConstraintViolationException) {
            return newResponse(
                    ((ConstraintViolationException) e).getConstraintViolations().iterator().next().getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
        return newResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindException(BindException e){
        return newResponse(e.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<?> handleHttpMediaTypeException(Exception e) {
        return newResponse(e, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowedException(Exception e) {
        return newResponse(e, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * SQL Exception 처리
     * @methodName : handleDataIntegrityViolationException
     * @date : 2021-05-25 오후 2:06
     * @author : xeroman.k
     * @param e
     * @return : org.springframework.http.ResponseEntity<?>
     * @throws
     * @modifyed :
     *
    **/
    @ExceptionHandler({DataIntegrityViolationException.class, SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {

        int errorCode = 0;
        LocalDateTime now = LocalDateTime.now();
        String errorMessage = "데이터 처리 중 오류가 발생 하였습니다.(관리자에게 문의하세요. 발생시각 : "+ now +")";


        switch (errorCode){
            case 1406 :
                errorMessage = "데이터 길이를 초과합니다.";
                break;
            case 1062 :
                errorMessage = "중복된 정보가 있습니다.";
                break;
            default:
                log.error("SQL-ERROR-EXCEPTION-CLASS : " + e.getCause().getClass().getName());
                log.error("SQL-ERROR-CODE            : " + String.valueOf(errorCode));
                e.printStackTrace();
                break;
        }

        return newResponse(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}