package com.nghia.todolist.utils.custom_annotation.account_not_exited;

import com.nghia.todolist.entity.UserEntity;
import com.nghia.todolist.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ValidAccountImple implements ConstraintValidator<ValidAccount, Object> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        try {
            String email = (String) obj.getClass().getMethod("getUsername").invoke(obj);

            UserEntity userFindByEmail = userRepository.findByEmail(email).orElse(null);
            if (userFindByEmail == null) {
                return false;
            }
            String passwordByEmail = userFindByEmail.getPassword();
            String passwordInput = (String) obj.getClass().getMethod("getPassword").invoke(obj);

            return bCryptPasswordEncoder.matches(passwordInput,passwordByEmail);

        } catch (Exception e) {
            return false;
        }
    }
}
