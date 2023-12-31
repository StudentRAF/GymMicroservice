package rs.raf.gym.commons.exception;

import lombok.Getter;
import org.slf4j.Logger;
import org.springframework.http.HttpStatusCode;

import java.text.MessageFormat;
import java.util.Map;

@Getter
public class GymException extends RuntimeException {

    private static final Map<Severity, IExceptionLogger> loggerMap = Map.of(
            Severity.TRACE,       GymException::trace,
            Severity.DEBUG,       GymException::debug,
            Severity.INFORMATION, GymException::information,
            Severity.WARNING,     GymException::warning,
            Severity.ERROR,       GymException::error
    );

    private final HttpStatusCode httpStatus;

    public GymException(IException exception, String... args) {
        loggerMap.get(exception.severity()).log(MessageFormat.format(exception.pattern(), args));
        httpStatus = exception.httpStatus();
    }

    private static Logger logger = null;

    public static void setLogger(Logger logger) {
        GymException.logger = logger;
    }

    private static void trace(String message) {
        logger.trace(message);
    }

    private static void debug(String message) {
        logger.info(message);
    }

    private static void information(String message) {
        logger.info(message);
    }

    private static void warning(String message) {
        logger.warn(message);
    }

    private static void error(String message) {
        logger.error(message);
    }

    @FunctionalInterface
    private interface IExceptionLogger {

        void log(String message);

    }

}
