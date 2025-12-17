package uz.spring.delivery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import uz.spring.delivery.constant.enums.ErrorType;

import static uz.spring.delivery.constant.enums.Error.HTTP_CLIENT_ERROR_CODE;

public class HttpClientException extends BussinesException {

    public HttpClientException(String message, HttpStatusCode status) {
        super(HTTP_CLIENT_ERROR_CODE.getCode(), message, ErrorType.EXTERNAL, HttpStatus.valueOf(status.value()));
    }
}
