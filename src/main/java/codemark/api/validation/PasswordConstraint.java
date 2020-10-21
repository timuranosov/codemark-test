package codemark.api.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default "Invalid password. It should contain at least 1 digit and 1 uppercase letter";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}