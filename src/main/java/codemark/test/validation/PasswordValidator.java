package codemark.test.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements
        ConstraintValidator<PasswordConstraint, String> {

    @Override
    public void initialize(PasswordConstraint contactNumber) {
    }

    @Override
    public boolean isValid(String password,
                           ConstraintValidatorContext cxt) {
        char letter;
        boolean capitalFlag = false;
        boolean digitFlag = false;
        for (int i = 0; i < password.length(); i++) {
            letter = password.charAt(i);
            if (Character.isDigit(letter)) {
                digitFlag = true;
            } else if (Character.isUpperCase(letter)) {
                capitalFlag = true;
            }
            if (digitFlag && capitalFlag)
                return true;
        }
        return false;
    }

}