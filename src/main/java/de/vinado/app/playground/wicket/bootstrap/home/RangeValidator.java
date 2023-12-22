package de.vinado.app.playground.wicket.bootstrap.home;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import java.time.LocalDateTime;

public class RangeValidator implements IValidator<Range> {

    @Override
    public void validate(IValidatable<Range> validatable) {
        Range value = validatable.getValue();
        LocalDateTime start = value.start();
        LocalDateTime end = value.end();

        ValidationError error;
        if (start.isAfter(end)) {
            error = new ValidationError(this, "order");
        } else {
            return;
        }

        validatable.error(decorate(error, validatable));
    }

    protected IValidationError decorate(IValidationError error, IValidatable<Range> validatable) {
        return error;
    }
}
