package com.xclone.userservice.validator;

import com.xclone.userservice.common.Interface.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches passwordMatches) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        try {
            Field passwordField = obj.getClass().getDeclaredField("password");
            Field confirmPasswordField = obj.getClass().getDeclaredField("confirmPassword");

            passwordField.setAccessible(true);
            confirmPasswordField.setAccessible(true);

            String password = (String) passwordField.get(obj);
            String confirmPassword = (String) confirmPasswordField.get(obj);

            boolean valid = password != null && password.equals(confirmPassword);
            if (!valid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode("confirmPassword")
                        .addConstraintViolation();
            }
            return valid;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }
}
