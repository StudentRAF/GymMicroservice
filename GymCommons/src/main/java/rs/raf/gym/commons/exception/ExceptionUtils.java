package rs.raf.gym.commons.exception;

import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

public class ExceptionUtils {

    public static <Type> ResponseEntity<Type> handleResponse(Supplier<ResponseEntity<Type>> supplier) {
        try {
            return supplier.get();
        }
        catch (GymException exception) {
            return ResponseEntity.status(exception.getHttpStatus()).build();
        }
    }

}
