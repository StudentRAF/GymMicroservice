package rs.raf.gym.commons.exception;

import org.springframework.http.HttpStatusCode;

public interface IException {

    String pattern();

    HttpStatusCode httpStatus();

    Severity severity();

}
