package uz.spring.delivery.exception;

import org.springframework.http.HttpStatus;
import uz.spring.delivery.constant.enums.ErrorType;

public class OrderNotFoundException extends ApplicationException {

    public OrderNotFoundException(String message) {
        super(10011, message, ErrorType.INTERNAL, HttpStatus.NOT_FOUND);
    }
}
