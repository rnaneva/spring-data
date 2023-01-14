package softuni.exam.util;


import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;

@Component
public class ValidationUtilsImpl implements ValidationUtils{

    private Validator validator;

    public ValidationUtilsImpl() {
        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    @Override
    public <T> boolean validate(T entity) {
        return validator.validate(entity).isEmpty();
    }
}
