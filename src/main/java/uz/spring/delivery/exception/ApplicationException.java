package uz.spring.delivery.exception;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationException extends RuntimeException {

    int code;
    String message;
    HttpStatus status;

    public ApplicationException(int code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
