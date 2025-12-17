package uz.spring.delivery.exception;

import org.springframework.http.HttpStatus;
import uz.spring.delivery.constant.enums.ErrorType;

public class TransactionExampleException extends BussinesException {

    public TransactionExampleException(String message) {
        super(10011, message, ErrorType.INTERNAL, HttpStatus.NOT_FOUND);
    }
}
