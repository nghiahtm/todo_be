package com.nghia.todolist.utils.custom_annotation.account_not_exited;

import com.nghia.todolist.utils.custom_annotation.match_password.PasswordMatchesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidAccountImple.class)
@Documented
public @interface ValidAccount {
    String message() default "Email or Password not found";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
