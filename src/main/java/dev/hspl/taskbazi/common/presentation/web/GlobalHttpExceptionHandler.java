package dev.hspl.taskbazi.common.presentation.web;

import dev.hspl.taskbazi.common.application.exception.ApplicationException;
import dev.hspl.taskbazi.common.domain.exception.DomainException;
import dev.hspl.taskbazi.task.domain.exception.ProjectRegistrationRestrictionException;
import dev.hspl.taskbazi.task.domain.exception.TooManyProjectInstanceException;
import dev.hspl.taskbazi.user.domain.exception.RegistrationSessionRestrictionException;
import dev.hspl.taskbazi.user.domain.exception.TooManyActiveLoginSessionException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Duration;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalHttpExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ProblemMessage> handleAllDomainExceptions(DomainException exception) {
        String problemKey = exception.problemKey();
        short httpStatusCode = exception.groupingValue();

        Object[] args = null;
        Map<String, Object> relatedData = null;

        // handle parameterized exceptions:
        if (exception instanceof RegistrationSessionRestrictionException mappedException) {
            args = new Object[]{mappedException.getSecondsToNextSession()};
            relatedData = Map.of(
                    "sessionLimitationDelay", mappedException.getSessionLimitationDelay(),
                    "secondsToNextSession", mappedException.getSecondsToNextSession()
            );
        } else if (exception instanceof TooManyActiveLoginSessionException mappedException) {
            args = new Object[]{mappedException.getMaxAllowedActiveSession()};
            relatedData = Map.of("maxAllowedActiveSession", mappedException.getMaxAllowedActiveSession());
        } else if (exception instanceof TooManyProjectInstanceException mappedException) {
            args = new Object[]{mappedException.getMaxAllowedProjectInstance()};
            relatedData = Map.of("maxAllowedProjectInstance", mappedException.getMaxAllowedProjectInstance());
        } else if (exception instanceof ProjectRegistrationRestrictionException mappedException) {
            long seconds = mappedException.getSecondsToNextAllowedRegistration();
            args = new Object[]{seconds <= 60 ? seconds + "s" : Duration.ofSeconds(seconds).toMinutes() + "m"};
            relatedData = Map.of(
                    "registrationLimitationDelaySeconds",mappedException.getRegistrationLimitationDelaySeconds(),
                    "secondsToNextAllowedRegistration",mappedException.getSecondsToNextAllowedRegistration()
            );
        }

        String localizedMessage = messageSource.getMessage(
                problemKey, args,
                "something went wrong!",
                LocaleContextHolder.getLocale()
        );

        return ResponseEntity.status(httpStatusCode).body(new ProblemMessage(localizedMessage, relatedData));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ProblemMessage> handleAllApplicationException(ApplicationException exception) {
        String problemKey = exception.problemKey();
        short httpStatusCode = exception.groupingValue();

        Object[] args = null;
        Map<String, Object> relatedData = null;

        String localizedMessage = messageSource.getMessage(
                problemKey, args,
                "something went wrong!",
                LocaleContextHolder.getLocale()
        );

        return ResponseEntity.status(httpStatusCode).body(new ProblemMessage(localizedMessage, relatedData));
    }
}
