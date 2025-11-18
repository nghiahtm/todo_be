package com.nghia.todolist.utils.custom_annotation.match_password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<CustomPasswordMatch, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        try {
            String password = (String) obj.getClass().getMethod("getPassword").invoke(obj);
            String confirm = (String) obj.getClass().getMethod("getConfirmPassword").invoke(obj);

            return password != null && password.equals(confirm);
        } catch (Exception e) {
            return false;
        }
    }
}
